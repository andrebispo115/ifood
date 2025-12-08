import javax.swing.*;
import java.awt.*;

public class TelaCliente extends JFrame {
    private Cliente cliente;
    private Restaurante restauranteSelecionado;
    private DefaultListModel<String> carrinhoModel;
    
    // Variáveis para Geolocalização e Frete
    private int clienteX;
    private int clienteY;
    private double valorFrete;

    public TelaCliente() {
        this.cliente = (Cliente) DadosGlobais.usuarioLogado;

        // Verifica se existem restaurantes
        if (DadosGlobais.restaurantes.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Não há restaurantes cadastrados!");
            dispose();
            return;
        }

        // --- GEOLOCALIZAÇÃO DO CLIENTE ---
        // Pergunta onde o cliente está para calcular o frete
        JTextField txtX = new JTextField("0");
        JTextField txtY = new JTextField("0");
        Object[] msgGPS = {"Defina sua localização para entrega:", "Posição X:", txtX, "Posição Y:", txtY};
        
        int gpsOption = JOptionPane.showConfirmDialog(null, msgGPS, "Geolocalização (GPS)", JOptionPane.OK_CANCEL_OPTION);
        if (gpsOption != JOptionPane.OK_OPTION) { 
            dispose(); 
            return; 
        }
        
        try {
            this.clienteX = Integer.parseInt(txtX.getText());
            this.clienteY = Integer.parseInt(txtY.getText());
        } catch(NumberFormatException e) {
            this.clienteX = 0; 
            this.clienteY = 0;
        }
        // ---------------------------------

        // Seleção do Restaurante
        Object[] rests = DadosGlobais.restaurantes.toArray();
        restauranteSelecionado = (Restaurante) JOptionPane.showInputDialog(null, 
            "Escolha um restaurante para pedir:", "Seleção de Restaurante", 
            JOptionPane.QUESTION_MESSAGE, null, rests, rests[0]);

        if (restauranteSelecionado == null) { 
            dispose(); 
            return; 
        }

        // CALCULA O FRETE INICIAL
        this.valorFrete = restauranteSelecionado.calcularFrete(clienteX, clienteY);
        double distancia = restauranteSelecionado.calcularDistancia(clienteX, clienteY);

        // Adicionei o ID no título
setTitle("Cliente: " + cliente.getNome() + " (ID: " + cliente.getId() + ") | Distância: " + String.format("%.1f km", distancia));
        setSize(850, 550);
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
        
        JPanel panelResumo = new JPanel(new GridLayout(4, 1)); 
        JLabel lblSubtotal = new JLabel("Subtotal: R$ 0.00");
        JLabel lblFrete = new JLabel(String.format("Frete (Distância %.1f): R$ %.2f", distancia, valorFrete));
        JLabel lblTotal = new JLabel("TOTAL: R$ " + String.format("%.2f", valorFrete));
        JButton btnFinalizar = new JButton("Finalizar Pedido");
        
        panelResumo.add(lblSubtotal);
        panelResumo.add(lblFrete);
        panelResumo.add(lblTotal);
        panelResumo.add(btnFinalizar);

        panelDir.add(new JScrollPane(listaCarrinho), BorderLayout.CENTER);
        panelDir.add(panelResumo, BorderLayout.SOUTH);

        add(panelEsq);
        add(panelDir);

        // --- AÇÃO: ADICIONAR AO CARRINHO ---
        btnAdicionar.addActionListener(e -> {
            Produto p = listaCardapio.getSelectedValue();
            if (p != null) {
                cliente.adicionarAoCarrinho(p);
                carrinhoModel.addElement(p.getNome() + " - R$ " + String.format("%.2f", p.getPreco()));
                
                double subtotal = cliente.calcularTotal();
                double totalFinal = subtotal + valorFrete;

                lblSubtotal.setText(String.format("Subtotal: R$ %.2f", subtotal));
                lblTotal.setText(String.format("TOTAL: R$ %.2f", totalFinal));
            } else {
                JOptionPane.showMessageDialog(this, "Selecione um item do cardápio!");
            }
        });

        // --- AÇÃO: FINALIZAR PEDIDO (COM GEOLOCALIZAÇÃO E TEMPO) ---
        btnFinalizar.addActionListener(e -> {
            if (carrinhoModel.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Seu carrinho está vazio!");
            } else {
                // 1. Tempo de Cozinha (Soma dos produtos)
                int tempoPreparo = cliente.calcularTempoEsperaTotal();
                
                // 2. Tempo de Entrega (Baseado na Geolocalização: 2 min por Km)
                double dist = restauranteSelecionado.calcularDistancia(clienteX, clienteY);
                int tempoEntrega = (int) (dist * 2); 

                // Tempo Total Real
                int tempoTotal = tempoPreparo + tempoEntrega;
                
                double totalFinal = cliente.calcularTotal() + valorFrete;

                String msgConfirmacao = String.format(
                    "Resumo do Pedido:\n" +
                    "- Subtotal: R$ %.2f\n" +
                    "- Frete (%.1f km): R$ %.2f\n" +
                    "----------------\n" +
                    "TOTAL A PAGAR: R$ %.2f\n\n" +
                    "Estimativa de Tempo:\n" +
                    "- Preparo: %d min\n" +
                    "- Entrega: %d min\n" +
                    "TEMPO TOTAL: %d min\n\n" +
                    "Deseja confirmar?",
                    cliente.calcularTotal(), dist, valorFrete, totalFinal,
                    tempoPreparo, tempoEntrega, tempoTotal
                );

                int confirm = JOptionPane.showConfirmDialog(this, msgConfirmacao,
                    "Finalizar Pagamento", JOptionPane.YES_NO_OPTION);

                if (confirm == JOptionPane.YES_OPTION) {
                    // Passamos 'msgConfirmacao' para ser salva no arquivo
                    new TelaEntrega(this, tempoTotal, msgConfirmacao).setVisible(true);
                    this.dispose(); // Fecha tela
                }
            }
        });
    }
}
