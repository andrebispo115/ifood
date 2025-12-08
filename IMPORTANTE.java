
classe ifood
ArrayList<Cliente>listaClientes = new ArrayList();
        GerenciadorDeClientes gerenciador = new GerenciadorDeClientes();
        new Tela_1(gerenciador);
        new Tela_cadastro(gerenciador);
        new Tela_2(gerenciador);

*****************************************************************************************

classe tela_1
  private GerenciadorDeClientes gerenciador;
    
    public Tela_1(GerenciadorDeClientes gerenciador){
    // Errado: gerenciador = this.gerenciador;
    this.gerenciador = gerenciador; // Correto: Atribuir o parâmetro ao atributo
    initComponents(); 

 private void btnEntrarActionPerformed(java.awt.event.ActionEvent evt) {                                          
        String email = txtEmail.getText();
        String senha = txtSenha.getText();
    
    // 1. Usa o método verificarLogin do Gerenciador
    if (gerenciador.verificarLogin(email, senha)) {
        
        javax.swing.JOptionPane.showMessageDialog(this, "Login efetuado com sucesso!");
        
        // 2. Fecha a tela de login e abre a Tela_2
        this.setVisible(false);
        
        // ⚠️ Aqui você precisaria passar o Gerenciador e/ou os dados do cliente para a Tela_2
        new Tela_2(gerenciador).setVisible(true); 
        // Como a Tela_2 não foi fornecida, apenas simulamos a ação.
        
    } else {
        // 3. Exibe mensagem de erro
        javax.swing.JOptionPane.showMessageDialog(this, "E-mail ou senha incorretos!", "Erro de Login", javax.swing.JOptionPane.ERROR_MESSAGE);
    }   
    }

********************************************************************

classe Tela_Cadastro

  private GerenciadorDeClientes gerenciador;

 public Tela_cadastro(GerenciadorDeClientes gerenciador){
        this.gerenciador = gerenciador;
    }

private void btnCriarActionPerformed(java.awt.event.ActionEvent evt) {                                         
        
        String nome = txtNovoNome.getText();
        String email = txtNovoEmail.getText();
        String senha = txtNovaSenha.getText();
        
        // 1. Validação Simples (opcional, mas bom)
    if (nome.isEmpty() || email.isEmpty() || senha.isEmpty()) {
        javax.swing.JOptionPane.showMessageDialog(this, "Preencha todos os campos!");
        return;
    }
    
    // 2. ADICIONAR o novo cliente usando o método do Gerenciador
    // O Gerenciador agora cuida da verificação de email existente e geração de ID.
    boolean adicionado = gerenciador.adicionarCliente(nome, email, senha); 

    if (adicionado) {
        javax.swing.JOptionPane.showMessageDialog(this, "Usuário cadastrado com sucesso! ID: " + (GerenciadorDeClientes.proximoId - 1));
        
        // 3. Troca de Tela
        this.setVisible(false);
        // Passa o Gerenciador para a nova Tela_1
        new Tela_1(gerenciador).setVisible(true); 
        
    } else {
        javax.swing.JOptionPane.showMessageDialog(this, "Erro ao cadastrar: Email já existe!");
    }
    } 

**************************************************************************
classe Usuario

  public Usuario(String nome, String email, String senha, int id) {
        setEmail(email);
        setSenha(senha);
        nome = this.nome;
        id = this.id;
    }
String caractere = "@gmail.com";
    
    public void setEmail(String email) {
        if(this.email.endsWith(caractere)){
            this.email = email;
        }else{
            System.out.println("REESCRIVA SEU E MAIL com @gmail.com NO FINAL");
        }
    }
public void setSenha(String senha) {
        if(senha.length() >= 8){
            this.senha = senha;
        }else{
            System.err.println("Erro: A senha deve ter pelo menos 8 caracteres.");
        }
    }

*************************************************

public class GerenciadorDeClientes {
    private ArrayList<Cliente> listaCliente = new ArrayList<>();
    
    // NOVO: Variável estática para rastrear o próximo ID.
    // O valor inicial (10) garante que o primeiro cliente comece com 10.
    static int proximoId = 10; 

    public void setListaCliente(ArrayList<Cliente> listaCliente) {
        this.listaCliente = listaCliente;
    }

    /**
     * Gera um novo ID sequencial (10, 11, 12...).
     */
    private int gerarProximoId() {
        return proximoId++;
    }
    
    /**
     * Tenta adicionar um cliente após gerar um ID único.
     * @return true se adicionado, false se falhar (ex: email já existe).
     */
    public boolean adicionarCliente(String nome, String email, String senha) {
        
        // 1. Verifica se o cliente já existe antes de adicionar
        if (verificarClientePorEmail(email)) {
            return false; // Email já cadastrado
        }
        
        // 2. Gera o ID sequencial
        int idUnico = gerarProximoId();
        
        // 3. Cria e adiciona o cliente
        Cliente novoCliente = new Cliente(nome, email, senha, idUnico);  
        listaCliente.add(novoCliente);
        return true; 
    }
    
    /**
     * Verifica se um email já está cadastrado na lista.
     * @param email O email a ser verificado.
     * @return true se o email existe, false caso contrário.
     */
    public boolean verificarClientePorEmail(String email) {
        for (Cliente cliente : listaCliente) {
            if (cliente.getEmail() != null && cliente.getEmail().equalsIgnoreCase(email)) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * NOVO: Verifica se o login (email e senha) corresponde a um cliente cadastrado.
     * @return true se o login for válido, false caso contrário.
     */
    public boolean verificarLogin(String email, String senha) {
        for (Cliente cliente : listaCliente) {
            // Verifica email E senha
            boolean emailMatch = cliente.getEmail() != null && cliente.getEmail().equalsIgnoreCase(email);
            boolean senhaMatch = cliente.getSenha() != null && cliente.getSenha().equals(senha);
            
            if (emailMatch && senhaMatch) {
                return true;
            }
        }
        return false;
    }
}
