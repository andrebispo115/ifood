public class Comida extends Produto {
    private String tipoCozinha;
    private boolean isVegano;
    private boolean isVegetariano; // Novo campo

    // Construtor atualizado recebendo os dois booleans
    public Comida(String nome, String descricao, double preco, int tempoPreparo, String tipoCozinha, boolean isVegano, boolean isVegetariano) {
        super(nome, descricao, preco, tempoPreparo);
        this.tipoCozinha = tipoCozinha;
        this.isVegano = isVegano;
        this.isVegetariano = isVegetariano;
    }

    public String getTipoCozinha() {
        return tipoCozinha;
    }

    public boolean isVegano() {
        return isVegano;
    }

    public boolean isVegetariano() {
        return isVegetariano;
    }

    @Override
    public String getDetalhesEspecificos() {
        // Monta a string de detalhes verificando os dois
        String detalhes = "Cozinha: " + tipoCozinha;
        if (isVegetariano) detalhes += " | Vegetariano";
        if (isVegano) detalhes += " | Vegano";
        return detalhes;
    }
}
