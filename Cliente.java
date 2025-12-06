import java.util.ArrayList;
import java.util.List;

public class Cliente extends Usuario {
    private List<Produto> carrinho;

    public Cliente(String nome, String email) {
        super(nome, email);
        this.carrinho = new ArrayList<>();
    }

    public void adicionarAoCarrinho(Produto p) {
        carrinho.add(p);
    }

    public double calcularTotal() {
        double total = 0;
        for (Produto p : carrinho) {
            total += p.getPreco();
        }
        return total;
    }
    
    public int calcularTempoEsperaTotal() {
        int tempoMax = 0;
        // Simplificação: o tempo total é o do prato mais demorado (assumindo preparo paralelo)
        // Ou pode ser a soma. O PDF não especifica, vou usar soma por segurança.
        for (Produto p : carrinho) {
            tempoMax += p.getTempoPreparo();
        }
        return tempoMax;
    }
}
