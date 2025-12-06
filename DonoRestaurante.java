public class DonoRestaurante extends Usuario {
    private Restaurante restaurante;

    public DonoRestaurante(String nome, String email, Restaurante restaurante) {
        super(nome, email);
        this.restaurante = restaurante;
    }

    public Restaurante getRestaurante() {
        return restaurante;
    }
}
