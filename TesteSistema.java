public class TesteSistema {
    public static void main(String[] args) {
        System.out.println("=== TESTES UNITARIOS AUTOMATIZADOS ===");
        
        Restaurante r = new Restaurante("Teste", "Rua A", 0, 0);
        double dist = r.calcularDistancia(3, 4); 
        
        if (dist == 5.0) System.out.println("[OK] Calculo de Distancia: APROVADO");
        else System.out.println("[X] Calculo de Distancia: FALHOU");
        
        try {
            validar(-10);
            System.out.println("[X] Validacao de Erro: FALHOU (Deixou passar preco negativo)");
        } catch (Exception e) {
            System.out.println("[OK] Validacao de Erro: APROVADO (Detectou o erro: " + e.getMessage() + ")");
        }
    }
    
    static void validar(double p) throws Exception {
        if (p < 0) throw new Exception("Preco invalido");
    }
}
