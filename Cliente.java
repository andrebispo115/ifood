import java.util.ArrayList;
import java.util.List;

public class Cliente extends Usuario {
    private List<Produto> carrinho;

    // Construtor atualizado com Senha
    public Cliente(String nome, String email, String senha) {
        super(nome, email, senha);
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
        for (Produto p : carrinho) {
            tempoMax += p.getTempoPreparo();
        }
        return tempoMax;
    }
}
