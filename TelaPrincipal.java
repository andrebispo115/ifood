import javax.swing.*;
import java.awt.*;

public class TelaPrincipal extends JFrame {

    public TelaPrincipal() {
        setTitle("Sistema iFood Java - CESUPA");
        setSize(400, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(4, 1, 10, 10));

        JLabel lblBemVindo = new JLabel("Bem-vindo! Identifique-se:", SwingConstants.CENTER);
        lblBemVindo.setFont(new Font("Arial", Font.BOLD, 18));
        
        JButton btnSouDono = new JButton("Sou Dono de Restaurante");
        JButton btnSouCliente = new JButton("Sou Cliente");
        JButton btnSair = new JButton("Sair");

        add(lblBemVindo);
        add(btnSouDono);
        add(btnSouCliente);
        add(btnSair);

        btnSouDono.addActionListener(e -> {
            String[] opcoes = {"Fazer Login", "Cadastrar Nova Conta"};
            int escolha = JOptionPane.showOptionDialog(this, "Voce ja tem cadastro?", "Acesso Dono",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, opcoes, opcoes[0]);

            if (escolha == 0)fazerLoginDono();
            else if (escolha == 1) cadastrarNovoDono();
        });

        btnSouCliente.addActionListener(e -> {
            String[] opcoes = {"Fazer Login", "Cadastrar Nova Conta"};
            int escolha = JOptionPane.showOptionDialog(this, "Voce ja tem cadastro?", "Acesso Cliente",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, opcoes, opcoes[0]);

            if (escolha == 0) fazerLoginCliente();
            else if (escolha == 1) cadastrarNovoCliente();
        });

        btnSair.addActionListener(e -> System.exit(0));
    }

    private void fazerLoginDono() {
        String[] credenciais = exibirPopupEmailSenha("Login - Dono", false);
        if (credenciais == null) return; 

        Usuario u = buscarUsuario(credenciais[0], credenciais[1]);

        if (u != null) {
            if (u instanceof DonoRestaurante) {
                DadosGlobais.usuarioLogado = u;
                new TelaDono().setVisible(true); 
            } else {
                JOptionPane.showMessageDialog(this, "Este email pertence a um Cliente, nao a um Dono!");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Email ou senha incorretos.");
        }
    }

    private void fazerLoginCliente() {
        String[] credenciais = exibirPopupEmailSenha("Login - Cliente", false);
        if (credenciais == null) return;

        Usuario u = buscarUsuario(credenciais[0], credenciais[1]);

        if (u != null) {
            if (u instanceof Cliente) {
                DadosGlobais.usuarioLogado = u;
                new TelaCliente().setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Este email pertence a um Dono, nao a um Cliente!");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Email ou senha incorretos.");
        }
    }

    private Usuario buscarUsuario(String email, String senha) {
        for (Usuario u : DadosGlobais.usuariosCadastrados) {
            if (u.getEmail().equals(email) && u.getSenha().equals(senha)) {
                return u;
            }
        }
        return null;
    }

    private void cadastrarNovoDono() {
        String[] dados = exibirPopupEmailSenha("Novo Cadastro - Dono", true);
        if (dados == null) return;

        String email = dados[0];
        String senha = dados[1];
        String nome = email.split("@")[0]; 

        if (emailJaExiste(email)) {
            JOptionPane.showMessageDialog(this, "Erro: Este email ja esta cadastrado!");
            return;
        }
        cadastrarRestauranteParaODono(nome, email, senha);
    }

    private void cadastrarNovoCliente() {
        String[] dados = exibirPopupEmailSenha("Novo Cadastro - Cliente", true);
        if (dados == null) return;

        String email = dados[0];
        String senha = dados[1];
        String nome = email.split("@")[0];

        if (emailJaExiste(email)) {
            JOptionPane.showMessageDialog(this, "Erro: Este email ja esta cadastrado!");
            return;
        }

        Cliente novoCliente = new Cliente(nome, email, senha);
        DadosGlobais.usuariosCadastrados.add(novoCliente);
        DadosGlobais.usuarioLogado = novoCliente;
        
        JOptionPane.showMessageDialog(this, "Cadastro realizado com sucesso!");
        new TelaCliente().setVisible(true);
    }

    private boolean emailJaExiste(String email) {
        for (Usuario u : DadosGlobais.usuariosCadastrados) {
            if (u.getEmail().equals(email)) return true;
        }
        return false;
    }

    private void cadastrarRestauranteParaODono(String nomeDono, String email, String senha) {
        JTextField txtNome = new JTextField();
        JTextField txtEndereco = new JTextField();
        JTextField txtX = new JTextField("0");
        JTextField txtY = new JTextField("0");

        Object[] message = {
            "--- Dados do seu Restaurante ---", "Nome do Restaurante:", txtNome,
            "Endereco:", txtEndereco, "Localizacao X:", txtX, "Localizacao Y:", txtY
        };

        int option = JOptionPane.showConfirmDialog(this, message, "Cadastrando Restaurante", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                String nomeRes = txtNome.getText();
                String endereco = txtEndereco.getText();
                int x = Integer.parseInt(txtX.getText());
                int y = Integer.parseInt(txtY.getText());

                if (!nomeRes.isEmpty() && !endereco.isEmpty()) {
                    Restaurante novoRestaurante = new Restaurante(nomeRes, endereco, x, y);
                    DadosGlobais.restaurantes.add(novoRestaurante);
                    
                    DonoRestaurante novoDono = new DonoRestaurante(nomeDono, email, senha, novoRestaurante);
                    DadosGlobais.usuariosCadastrados.add(novoDono);
                    DadosGlobais.usuarioLogado = novoDono;

                    JOptionPane.showMessageDialog(this, "Conta e Restaurante criados com sucesso!");
                    new TelaDono().setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(this, "Preencha o nome e endereco!");
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Coordenadas invalidas!");
            }
        }
    }

    private String[] exibirPopupEmailSenha(String titulo, boolean ehCadastro) {
        JTextField txtEmail = new JTextField();
        JPasswordField txtSenha = new JPasswordField();
        Object[] message = { "Email (@gmail.com):", txtEmail, "Senha:", txtSenha };

        int option = JOptionPane.showConfirmDialog(this, message, titulo, JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            String email = txtEmail.getText().trim();
            String senha = new String(txtSenha.getPassword());

            if (email.isEmpty() || senha.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Preencha todos os campos!");
                return null;
            }
            if (!email.endsWith("@gmail.com")) {
                JOptionPane.showMessageDialog(this, "O email deve terminar com @gmail.com");
                return null;
            }
            return new String[]{email, senha};
        }
        return null;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TelaPrincipal().setVisible(true));
    }
}
