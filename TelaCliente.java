import javax.swing.*;
import java.awt.*;

public class TelaCliente extends JFrame {
    private Cliente cliente;
    private Restaurante restauranteSelecionado;
    private DefaultListModel<String> carrinhoModel;

    public TelaCliente() {
        this.cliente = (Cliente) DadosGlobais.usuarioLogado;

        // SELEÇÃO DE RESTAURANTE (Requisito: Visualizar e Selecionar Restaurante)
        if (DadosGlobais.restaurantes.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Não há restaurantes cadastrados!");
            dispose(); // Fecha se não tiver nada
            return;
        }

        // Pede para o usuário escolher o restaurante
        Object[] rests = DadosGlobais.restaurantes.toArray();
        restauranteSelecionado = (Restaurante) JOptionPane.showInputDialog(null, 
            "Escolha um restaurante para pedir:", "Seleção de Restaurante", 
            JOptionPane.QUESTION_MESSAGE, null, rests, rests[0]);

        if (restauranteSelecionado == null) {
            dispose(); // Se cancelar, fecha a tela
            return; 
        }

        // Configuração da Janela Principal
        setTitle("Cliente: " + cliente.getNome() + " | Pedindo em: " + restauranteSelecionado.getNome());
        setSize(800, 500);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(1, 2));

        // --- LADO ESQUERDO: CARDÁPIO ---
        JPanel panelEsq = new JPanel(new BorderLayout());
        panelEsq.setBorder(BorderFactory.createTitledBorder("Cardápio: " + restauranteSelecionado.getNome()));
        
        DefaultListModel<Produto> cardapioModel = new DefaultListModel<>();
        for (Produto p : restauranteSelecionado.getCardapio()) {
            cardapioModel.addElement(p);
        }
        JList<Produto> listaCardapio = new JList<>(cardapioModel);
        JButton btnAdicionar = new JButton("Adicionar ao Carrinho >>");

        panelEsq.add(new JScrollPane(listaCardapio), BorderLayout.CENTER);
        panelEsq.add(btnAdicionar, BorderLayout.SOUTH);

        // --- LADO DIREITO: CARRINHO ---
        JPanel panelDir = new JPanel(new BorderLayout());
        panelDir.setBorder(BorderFactory.createTitledBorder("Seu Carrinho"));
        
        carrinhoModel = new DefaultListModel<>();
        JList<String> listaCarrinho = new JList<>(carrinhoModel);
        
        JPanel panelResumo = new JPanel(new GridLayout(3, 1));
        JLabel lblTotal = new JLabel("Total: R$ 0.00");
        JLabel lblTempo = new JLabel("Tempo Espera: 0 min");
        JButton btnFinalizar = new JButton("Finalizar Pedido");
        
        panelResumo.add(lblTotal);
        panelResumo.add(lblTempo);
        panelResumo.add(btnFinalizar);

        panelDir.add(new JScrollPane(listaCarrinho), BorderLayout.CENTER);
        panelDir.add(panelResumo, BorderLayout.SOUTH);

        add(panelEsq);
        add(panelDir);

        // --- AÇÕES ---
        btnAdicionar.addActionListener(e -> {
            Produto p = listaCardapio.getSelectedValue();
            if (p != null) {
                cliente.adicionarAoCarrinho(p);
                // Exibe nome e preço no carrinho visual
                carrinhoModel.addElement(p.getNome() + " - R$ " + String.format("%.2f", p.getPreco()));
                
                // Atualiza Labels
                lblTotal.setText(String.format("Total: R$ %.2f", cliente.calcularTotal()));
                lblTempo.setText("Tempo Espera: " + cliente.calcularTempoEsperaTotal() + " min");
            } else {
                JOptionPane.showMessageDialog(this, "Selecione um item do cardápio!");
            }
        });

        btnFinalizar.addActionListener(e -> {
            if (carrinhoModel.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Seu carrinho está vazio!");
            } else {
                String msg = String.format("Pedido enviado para %s!\nValor: R$ %.2f\nChega em: %d min", 
                             restauranteSelecionado.getNome(),
                             cliente.calcularTotal(), cliente.calcularTempoEsperaTotal());
                JOptionPane.showMessageDialog(this, msg);
                this.dispose();
            }
        });
    }
}
