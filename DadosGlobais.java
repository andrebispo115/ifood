import java.util.ArrayList;
import java.util.List;

public class DadosGlobais {
    // Lista de restaurantes (para o Cliente ver todos)
    public static List<Restaurante> restaurantes = new ArrayList<>();
    
    // NOVO: Lista de usuários cadastrados (Login e Senha)
    public static List<Usuario> usuariosCadastrados = new ArrayList<>();
    
    public static Usuario usuarioLogado;
}
