import java.util.Random;

public abstract class Usuario {
    protected String id; 
    protected String nome;
    protected String email;
    protected String senha; 

    public Usuario(String nome, String email, String senha) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.id = gerarIdUnico(); 
    }
    
    
    private String gerarIdUnico() {
        Random rand = new Random();
        int numero = rand.nextInt(9000) + 1000; 
        return "#" + numero;
    }

    public String getNome() { return nome; }
    public String getId() { return id; }


    public String getEmail() { return email; }
    public String getSenha() { return senha; }
}
