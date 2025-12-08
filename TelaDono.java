import javax.swing.*;
import java.awt.*;

public class TelaDono extends JFrame {
    private Restaurante restaurante;
    private DefaultListModel<Produto> listModel;

    public TelaDono() {
        DonoRestaurante dono = (DonoRestaurante) DadosGlobais.usuarioLogado;
        this.restaurante = dono.getRestaurante();

        // Adicionei o ID no título
setTitle("Gerenciamento - Dono ID: " + dono.getId() + " - " + restaurante.getNome());
        setSize(600, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel panelTopo = new JPanel();
        panelTopo.add(new JLabel("Gerenciando: " + restaurante.getNome() + " (" + restaurante.getEndereco() + ")"));
        add(panelTopo, BorderLayout.NORTH);

        listModel = new DefaultListModel<>();
        atualizarLista();
        JList<Produto> listaProdutos = new JList<>(listModel);
        add(new JScrollPane(listaProdutos), BorderLayout.CENTER);

        JPanel panelBotoes = new JPanel();
        JButton btnAdicionar = new JButton("Adicionar");
        JButton btnEditar = new JButton("Editar");
        JButton btnRemover = new JButton("Remover");

        panelBotoes.add(btnAdicionar);
        panelBotoes.add(btnEditar);
        panelBotoes.add(btnRemover);
        add(panelBotoes, BorderLayout.SOUTH);

        btnAdicionar.addActionListener(e -> abrirFormulario(null));

        btnEditar.addActionListener(e -> {
            Produto selecionado = listaProdutos.getSelectedValue();
            if (selecionado != null) {
                abrirFormulario(selecionado);
            } else {
                JOptionPane.showMessageDialog(this, "Selecione um produto para editar.");
            }
        });

        btnRemover.addActionListener(e -> {
            Produto selecionado = listaProdutos.getSelectedValue();
            if (selecionado != null) {
                restaurante.removerProduto(selecionado);
                atualizarLista();
            }
        });
    }

    private void atualizarLista() {
        listModel.clear();
        for (Produto p : restaurante.getCardapio()) {
            listModel.addElement(p);
        }
    }

    private void abrirFormulario(Produto produtoExistente) {
        JDialog dialog = new JDialog(this, produtoExistente == null ? "Novo Produto" : "Editar Produto", true);
        dialog.setSize(450, 480);
        dialog.setLayout(new GridLayout(9, 2));
        dialog.setLocationRelativeTo(this);

        JTextField txtNome = new JTextField();
        JTextField txtDescricao = new JTextField();
        JTextField txtPreco = new JTextField();
        JTextField txtTempo = new JTextField();
        
        String[] tipos = {"Comida", "Bebida"};
        JComboBox<String> cmbTipo = new JComboBox<>(tipos);
        JTextField txtExtra1 = new JTextField(); 
        
        // --- OPÇÕES INTELIGENTES (Vegano/Vegetariano) ---
        JPanel panelOpcoes = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        JCheckBox chkVegetariano = new JCheckBox("Vegetariano");
        JCheckBox chkVegano = new JCheckBox("Vegano");
        
        chkVegetariano.addActionListener(e -> {
            if (chkVegetariano.isSelected()) chkVegano.setSelected(false);
        });
        
        chkVegano.addActionListener(e -> {
            if (chkVegano.isSelected()) chkVegetariano.setSelected(false);
        });

        JCheckBox chkAlcoolica = new JCheckBox("Alcoólica");
        
        panelOpcoes.add(chkVegetariano);
        panelOpcoes.add(chkVegano);
        panelOpcoes.add(chkAlcoolica);
        // --------------------------------------------------

        // Preenchimento na Edição
        if (produtoExistente != null) {
            txtNome.setText(produtoExistente.getNome());
            txtDescricao.setText(produtoExistente.getDescricao());
            txtPreco.setText(String.valueOf(produtoExistente.getPreco()));
            txtTempo.setText(String.valueOf(produtoExistente.getTempoPreparo()));
            
            if (produtoExistente instanceof Comida) {
                cmbTipo.setSelectedItem("Comida");
                Comida c = (Comida) produtoExistente;
                txtExtra1.setText(c.getTipoCozinha());
                chkVegano.setSelected(c.isVegano());
                chkVegetariano.setSelected(c.isVegetariano());
            } else if (produtoExistente instanceof Bebida) {
                cmbTipo.setSelectedItem("Bebida");
                Bebida b = (Bebida) produtoExistente;
                txtExtra1.setText(String.valueOf(b.getTamanhoMl()));
                chkAlcoolica.setSelected(b.isAlcoolica());
            }
            cmbTipo.setEnabled(false);
        }

        dialog.add(new JLabel("Tipo:")); dialog.add(cmbTipo);
        dialog.add(new JLabel("Nome:")); dialog.add(txtNome);
        dialog.add(new JLabel("Descrição:")); dialog.add(txtDescricao);
        dialog.add(new JLabel("Preço (R$):")); dialog.add(txtPreco);
        dialog.add(new JLabel("Tempo (min):")); dialog.add(txtTempo);
        
        JLabel lblExtra1 = new JLabel("Cozinha:");
        dialog.add(lblExtra1); dialog.add(txtExtra1);
        dialog.add(new JLabel("Opções:")); dialog.add(panelOpcoes);

        Runnable atualizarVisibilidade = () -> {
            if (cmbTipo.getSelectedItem().equals("Comida")) {
                lblExtra1.setText("Tipo Cozinha:");
                chkVegetariano.setVisible(true);
                chkVegano.setVisible(true);
                chkAlcoolica.setVisible(false);
            } else {
                lblExtra1.setText("Tamanho (ml):");
                chkVegetariano.setVisible(false);
                chkVegano.setVisible(false);
                chkAlcoolica.setVisible(true);
            }
            dialog.revalidate();
            dialog.repaint();
        };

        atualizarVisibilidade.run();
        cmbTipo.addActionListener(e -> atualizarVisibilidade.run());

        JButton btnSalvar = new JButton("Salvar");
        dialog.add(new JLabel("")); dialog.add(btnSalvar);

        // --- AÇÃO SALVAR CORRIGIDA ---
        btnSalvar.addActionListener(e -> {
            try {
                if (txtNome.getText().trim().isEmpty()) {
                    throw new DadoInvalidoException("O nome do produto não pode ser vazio!");
                }
                
                String nome = txtNome.getText();
                String desc = txtDescricao.getText();
                double preco;
                int tempo;
                
                try {
                    preco = Double.parseDouble(txtPreco.getText().replace(",", "."));
                    tempo = Integer.parseInt(txtTempo.getText());
                } catch (NumberFormatException nfe) {
                    throw new DadoInvalidoException("Preço e Tempo devem ser números válidos!");
                }

                if (preco <= 0) throw new DadoInvalidoException("O preço deve ser maior que zero!");
                
                // CORREÇÃO AQUI: Agora permite tempo = 0, mas bloqueia negativo
                if (tempo < 0) throw new DadoInvalidoException("O tempo não pode ser negativo!");

                if (produtoExistente != null) {
                    restaurante.removerProduto(produtoExistente);
                }

                if (cmbTipo.getSelectedItem().equals("Comida")) {
                    String cozinha = txtExtra1.getText();
                    if (cozinha.trim().isEmpty()) throw new DadoInvalidoException("Tipo de cozinha obrigatório!");
                    
                    boolean isVegano = chkVegano.isSelected();
                    boolean isVegetariano = chkVegetariano.isSelected();
                    
                    restaurante.adicionarProduto(new Comida(nome, desc, preco, tempo, cozinha, isVegano, isVegetariano));
                } else {
                    String mlStr = txtExtra1.getText();
                    if (mlStr.trim().isEmpty()) throw new DadoInvalidoException("Tamanho (ml) obrigatório!");
                    int ml = Integer.parseInt(mlStr);
                    if (ml <= 0) throw new DadoInvalidoException("Tamanho deve ser positivo!");
                    
                    boolean alcool = chkAlcoolica.isSelected();
                    restaurante.adicionarProduto(new Bebida(nome, desc, preco, tempo, ml, alcool));
                }
                
                atualizarLista();
                dialog.dispose();

            } catch (DadoInvalidoException ex) {
                JOptionPane.showMessageDialog(dialog, "Erro: " + ex.getMessage(), "Dados Inválidos", JOptionPane.WARNING_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialog, "Erro inesperado: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });
        // -----------------------------

        dialog.setVisible(true);
    }
}
