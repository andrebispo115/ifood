import javax.swing.*;
import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TelaEntrega extends JDialog {
    private JProgressBar barraProgresso;
    private JLabel lblStatus;
    private String resumoPedido; // Guardamos o texto do pedido

    public TelaEntrega(JFrame parent, int tempoTotalMinutos, String textoPedido) {
        super(parent, "Acompanhamento do Pedido", true);
        this.resumoPedido = textoPedido; // Recebe o texto para salvar depois
        
        setSize(400, 250);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout(20, 20));

        JLabel lblTitulo = new JLabel("Seu pedido está sendo preparado...", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));
        add(lblTitulo, BorderLayout.NORTH);

        barraProgresso = new JProgressBar(0, 100);
        barraProgresso.setStringPainted(true);
        barraProgresso.setFont(new Font("Arial", Font.BOLD, 14));
        barraProgresso.setForeground(new Color(0, 150, 0)); 
        
        JPanel panelCentro = new JPanel(new GridLayout(2, 1));
        lblStatus = new JLabel("Enviando para a cozinha...", SwingConstants.CENTER);
        panelCentro.add(lblStatus);
        panelCentro.add(barraProgresso);
        panelCentro.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        add(panelCentro, BorderLayout.CENTER);

        JButton btnFechar = new JButton("Receber e Gerar Nota Fiscal");
        btnFechar.setEnabled(false);
        add(btnFechar, BorderLayout.SOUTH);

        // Ação do botão: Fecha e Gera o Arquivo
        btnFechar.addActionListener(e -> {
            gerarNotaFiscalArquivo();
            dispose();
        });

        // Simulação do Tempo (Thread)
        new Thread(() -> {
            try {
                atualizarStatus("Confirmando pagamento...", 10);
                Thread.sleep(1500);
                
                atualizarStatus("Preparando na cozinha...", 40);
                Thread.sleep(2000); 
                
                atualizarStatus("Saiu para entrega!", 80);
                Thread.sleep(1500); 
                
                atualizarStatus("Pedido Entregue!", 100);
                SwingUtilities.invokeLater(() -> btnFechar.setEnabled(true));
                
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void atualizarStatus(String texto, int progresso) {
        SwingUtilities.invokeLater(() -> {
            lblStatus.setText(texto);
            barraProgresso.setValue(progresso);
        });
    }

    // --- PONTO EXTRA: MANIPULAÇÃO DE ARQUIVOS (IO) ---
    private void gerarNotaFiscalArquivo() {
        // Formata a data e hora atual
        String dataHora = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm"));
        String nomeArquivo = "NotaFiscal_" + dataHora + ".txt";

        try (PrintWriter writer = new PrintWriter(new FileWriter(nomeArquivo))) {
            writer.println("=== COMPROVANTE DE PEDIDO IFOOD JAVA ===");
            writer.println("Data: " + LocalDateTime.now());
            writer.println("----------------------------------------");
            writer.println(resumoPedido); // Escreve o resumo que veio da TelaCliente
            writer.println("----------------------------------------");
            writer.println("Obrigado pela preferência!");
            
            JOptionPane.showMessageDialog(this, "Nota Fiscal salva em: " + nomeArquivo);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar nota: " + e.getMessage());
        }
    }
}
