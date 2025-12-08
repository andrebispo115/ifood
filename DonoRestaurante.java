public class DonoRestaurante extends Usuario {
    private Restaurante restaurante;

    // Construtor atualizado com Senha
    public DonoRestaurante(String nome, String email, String senha, Restaurante restaurante) {
        super(nome, email, senha);
        this.restaurante = restaurante;
    }

    public Restaurante getRestaurante() {
        return restaurante;
    }
}
