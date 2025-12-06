import java.util.ArrayList;
import java.util.List;

public class Restaurante implements Gerenciavel {
    private String nome;
    private String endereco;
    private List<Produto> cardapio;

    public Restaurante(String nome, String endereco) {
        this.nome = nome;
        this.endereco = endereco;
        this.cardapio = new ArrayList<>();
    }

    // Implementação da Interface
    @Override
    public void adicionarProduto(Produto p) {
        cardapio.add(p);
        System.out.println("Produto adicionado: " + p.getNome());
    }

    @Override
    public void removerProduto(Produto p) {
        cardapio.remove(p);
        System.out.println("Produto removido: " + p.getNome());
    }

    @Override
    public void listarCardapio() {
        System.out.println("--- Cardápio do " + nome + " ---");
        for (Produto p : cardapio) {
            System.out.println(p); // Chama o toString polimórfico
        }
    }
    
    // Getters
    public List<Produto> getCardapio() { return cardapio; }
    public String getNome() { return nome; }
    public String getEndereco() {
    return endereco;
}
@Override
public String toString() {
    // Usando o 'endereco' para o aviso sumir
    return nome + " (" + endereco + ")";
}
}
