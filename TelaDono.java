import javax.swing.*;
import java.awt.*;

public class TelaDono extends JFrame {
    private Restaurante restaurante;
    private DefaultListModel<Produto> listModel;

    public TelaDono() {
        DonoRestaurante dono = (DonoRestaurante) DadosGlobais.usuarioLogado;
        this.restaurante = dono.getRestaurante();

        setTitle("Gerenciamento - " + restaurante.getNome());
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
        dialog.setSize(450, 450);
        dialog.setLayout(new GridLayout(9, 2));
        dialog.setLocationRelativeTo(this);

        JTextField txtNome = new JTextField();
        JTextField txtDescricao = new JTextField();
        JTextField txtPreco = new JTextField();
        JTextField txtTempo = new JTextField();
        
        String[] tipos = {"Comida", "Bebida"};
        JComboBox<String> cmbTipo = new JComboBox<>(tipos);
        JTextField txtExtra1 = new JTextField(); // Cozinha ou Tamanho
        
        // --- MUDANÇA: APENAS DUAS OPÇÕES INTELIGENTES ---
        JPanel panelOpcoes = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        JCheckBox chkVegetariano = new JCheckBox("Vegetariano");
        JCheckBox chkVegano = new JCheckBox("Vegano");
        
        // Lógica de Exclusão Mútua (Um desmarca o outro)
        chkVegetariano.addActionListener(e -> {
            if (chkVegetariano.isSelected()) chkVegano.setSelected(false);
        });
        
        chkVegano.addActionListener(e -> {
            if (chkVegano.isSelected()) chkVegetariano.setSelected(false);
        });

        // Opção para Bebida
        JCheckBox chkAlcoolica = new JCheckBox("Alcoólica");
        
        panelOpcoes.add(chkVegetariano);
        panelOpcoes.add(chkVegano);
        panelOpcoes.add(chkAlcoolica);
        // --------------------------------------------------

        // Lógica de Preenchimento na Edição
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

        // Controla visibilidade
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

        btnSalvar.addActionListener(e -> {
            try {
                String nome = txtNome.getText();
                String desc = txtDescricao.getText();
                double preco = Double.parseDouble(txtPreco.getText().replace(",", "."));
                int tempo = Integer.parseInt(txtTempo.getText());

                if (produtoExistente != null) {
                    restaurante.removerProduto(produtoExistente);
                }

                if (cmbTipo.getSelectedItem().equals("Comida")) {
                    String cozinha = txtExtra1.getText();
                    
                    // Se nenhum estiver marcado, ambos serão false (o que é o "Normal")
                    boolean isVegano = chkVegano.isSelected();
                    boolean isVegetariano = chkVegetariano.isSelected();
                    
                    restaurante.adicionarProduto(new Comida(nome, desc, preco, tempo, cozinha, isVegano, isVegetariano));
                } else {
                    int ml = txtExtra1.getText().isEmpty() ? 0 : Integer.parseInt(txtExtra1.getText());
                    boolean alcool = chkAlcoolica.isSelected();
                    restaurante.adicionarProduto(new Bebida(nome, desc, preco, tempo, ml, alcool));
                }
                atualizarLista();
                dialog.dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialog, "Erro nos dados: " + ex.getMessage());
            }
        });

        dialog.setVisible(true);
    }
}
