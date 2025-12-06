import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class TelaPrincipal extends JFrame {

    public TelaPrincipal() {
        setTitle("Sistema iFood Java - CESUPA");
        setSize(400, 350); // Aumentei um pouco a altura
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(4, 1, 10, 10));

        JLabel lblBemVindo = new JLabel("Bem-vindo! Quem é você?", SwingConstants.CENTER);
        lblBemVindo.setFont(new Font("Arial", Font.BOLD, 18));
        
        JButton btnSouDono = new JButton("Sou Dono de Restaurante");
        JButton btnSouCliente = new JButton("Sou Cliente");
        JButton btnSair = new JButton("Sair");

        add(lblBemVindo);
        add(btnSouDono);
        add(btnSouCliente);
        add(btnSair);

        // Ação para Dono (Cadastro de Restaurante ou Login)
        btnSouDono.addActionListener(e -> {
            String[] opcoes = {"Cadastrar Novo Restaurante", "Gerenciar Existente"};
            int escolha = JOptionPane.showOptionDialog(this, "O que deseja fazer?", "Acesso Dono",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, opcoes, opcoes[0]);

            if (escolha == 0) { // Cadastrar Novo
                cadastrarRestaurante();
            } else if (escolha == 1) { // Gerenciar Existente
                selecionarRestauranteParaGerenciar();
            }
        });

        // Ação para Cliente
        btnSouCliente.addActionListener(e -> {
            String nome = JOptionPane.showInputDialog("Digite seu nome:");
            if (nome != null && !nome.isEmpty()) {
                DadosGlobais.usuarioLogado = new Cliente(nome, "cliente@email.com");
                // Agora o cliente escolhe o restaurante DENTRO da tela dele
                new TelaCliente().setVisible(true);
            }
        });

        btnSair.addActionListener(e -> System.exit(0));
    }

    private void cadastrarRestaurante() {
        JTextField txtNome = new JTextField();
        JTextField txtEndereco = new JTextField();
        Object[] message = {
            "Nome do Restaurante:", txtNome,
            "Endereço:", txtEndereco
        };

        int option = JOptionPane.showConfirmDialog(this, message, "Novo Restaurante", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            String nome = txtNome.getText();
            String endereco = txtEndereco.getText();
            if (!nome.isEmpty() && !endereco.isEmpty()) {
                Restaurante novo = new Restaurante(nome, endereco);
                DadosGlobais.restaurantes.add(novo);
                
                // Loga automaticamente após criar
                DadosGlobais.usuarioLogado = new DonoRestaurante("Dono", "admin@rest.com", novo);
                new TelaDono().setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Preencha todos os campos!");
            }
        }
    }

    private void selecionarRestauranteParaGerenciar() {
        if (DadosGlobais.restaurantes.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nenhum restaurante cadastrado ainda!");
            return;
        }
        // Cria uma lista para o Dono escolher qual restaurante gerenciar
        Object[] rests = DadosGlobais.restaurantes.toArray();
        Restaurante selecionado = (Restaurante) JOptionPane.showInputDialog(this, 
            "Qual restaurante você quer gerenciar?", "Login Dono", 
            JOptionPane.QUESTION_MESSAGE, null, rests, rests[0]);

        if (selecionado != null) {
            DadosGlobais.usuarioLogado = new DonoRestaurante("Dono", "admin@rest.com", selecionado);
            new TelaDono().setVisible(true);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TelaPrincipal().setVisible(true));
    }
}
