public abstract class Produto {
    private String nome;
    private String descricao;
    private double preco;
    private int tempoPreparo; // em minutos

    public Produto(String nome, String descricao, double preco, int tempoPreparo) {
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
        this.tempoPreparo = tempoPreparo;
    }

    // Getters e Setters (Encapsulamento [cite: 30])
    public String getNome() { return nome; }
    public double getPreco() { return preco; }
    public int getTempoPreparo() { return tempoPreparo; }
    public String getDescricao() {
    return descricao;
}

    // Método abstrato para forçar polimorfismo na exibição
    public abstract String getDetalhesEspecificos();

    @Override
    public String toString() {
    // Agora estamos usando a 'descricao' aqui, o aviso vai sumir!
    return String.format("%s - R$ %.2f (%d min) - %s [%s]", 
        nome, preco, tempoPreparo, descricao, getDetalhesEspecificos());
}
}
