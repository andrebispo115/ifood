import java.util.ArrayList;
import java.util.List;

public class Restaurante implements Gerenciavel {
    private String nome;
    private String endereco;
    // Geolocalização (Ponto Extra)
    private int posX; 
    private int posY;
    
    private List<Produto> cardapio;

    public Restaurante(String nome, String endereco, int posX, int posY) {
        this.nome = nome;
        this.endereco = endereco;
        this.posX = posX;
        this.posY = posY;
        this.cardapio = new ArrayList<>();
    }

    // Método Matemático para Geolocalização (Distância Euclidiana)
    public double calcularDistancia(int clienteX, int clienteY) {
        // Fórmula: RaizQuadrada((x2-x1)² + (y2-y1)²)
        return Math.sqrt(Math.pow(this.posX - clienteX, 2) + Math.pow(this.posY - clienteY, 2));
    }

    public double calcularFrete(int clienteX, int clienteY) {
        double distancia = calcularDistancia(clienteX, clienteY);
        return distancia * 1.50; // Cobra R$ 1,50 por Km de distância
    }

    @Override
    public void adicionarProduto(Produto p) {
        cardapio.add(p);
    }

    @Override
    public void removerProduto(Produto p) {
        cardapio.remove(p);
    }

    @Override
    public void listarCardapio() {
        for (Produto p : cardapio) System.out.println(p);
    }
    
    public List<Produto> getCardapio() { return cardapio; }
    public String getNome() { return nome; }
    public String getEndereco() { return endereco; }
    
    @Override
    public String toString() {
        return nome + " (Loc: " + posX + ", " + posY + ")";
    }
}
