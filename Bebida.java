public class Bebida extends Produto {
    private int tamanhoMl;
    private boolean isAlcoolica;

    public Bebida(String nome, String descricao, double preco, int tempoPreparo, int tamanhoMl, boolean isAlcoolica) {
        super(nome, descricao, preco, tempoPreparo);
        this.tamanhoMl = tamanhoMl;
        this.isAlcoolica = isAlcoolica;
    }

    // --- ADICIONE ESTES GETTERS PARA A EDIÇÃO FUNCIONAR ---
    public int getTamanhoMl() {
        return tamanhoMl;
    }

    public boolean isAlcoolica() {
        return isAlcoolica;
    }
    // ------------------------------------------------------

    @Override
    public String getDetalhesEspecificos() {
        return tamanhoMl + "ml" + (isAlcoolica ? " | Alcoólica" : "");
    }
}
