
# INTEGRANTES
* André Bispo Santos do Vale
* Edgar Vinicius Teixeira Teófilo
* Matheus Lima de Sá

# Sistema de Pedidos de Restaurante

> Projeto acadêmico: sistema simplificado de pedidos (estudo de POO + GUI em Java)

##  Objetivo

Desenvolver um sistema de pedidos para restaurante que permita praticar conceitos de **Programação Orientada a Objetos** (herança, polimorfismo, interfaces) e construir uma **interface gráfica** em Java (Swing ou JavaFX). O projeto pode ser desenvolvido em equipe de até 3 alunos e deve ficar hospedado em um repositório no GitHub.

---

##  Funcionalidades Principais

### Para o Dono do Restaurante

* Cadastro do restaurante (nome, endereço).
* Cadastro de produtos (nome, descrição, preço, tempo de preparo).
* Dois tipos de produto:

  * **Comida**: inclui tipo de cozinha (ex.: italiana, japonesa) e indicador vegetariano/vegano.
  * **Bebida**: inclui tamanho em ml e indicador alcoólico.
* Gerenciamento de produtos (editar, remover).
* Salvar alterações (persistência local ou em banco de dados).

### Para o Cliente

* Visualizar lista de restaurantes cadastrados.
* Selecionar restaurante e visualizar cardápio.
* Selecionar produtos para montar um pedido.
* Exibir cálculo do preço total e tempo estimado de espera.
* Finalizar pedido.

---

## Fluxo do Usuário (resumido)

### Dono do Restaurante

1. Login / Cadastro
2. Cadastrar Restaurante
3. Cadastrar Produtos
4. Gerenciar Produtos (editar/remover)
5. Salvar Alterações

### Cliente

1. Login / Cadastro
2. Ver lista de Restaurantes
3. Selecionar Restaurante e Produtos
4. Visualizar total do pedido e tempo estimado
5. Finalizar Pedido

---


##  Estrutura de Pastas Sugerida

```
restaurant-order-system/
├─ docs/
│  └─ diagrams/
├─ src/
│  ├─ main/
│  │  ├─ java/com/empresa/app/
│  │  └─ resources/
│  └─ test/
├─ build.gradle (ou pom.xml)
├─ README.md
└─ .gitignore
```

---

##  Como Executar (local)

1. Clone o repositório:

```bash
git clone https://github.com/SEU_USUARIO/restaurant-order-system.git
cd restaurant-order-system
```

2. Build com Maven:

```bash
mvn clean install
mvn exec:java -Dexec.mainClass="com.empresa.app.Main"
```

ou com Gradle:

```bash
./gradlew build
./gradlew run
```

> Se estiver usando JavaFX, certifique-se de que o runtime JavaFX esteja disponível (ou use OpenJFX via dependência).

---

##  Testes

* Escreva testes unitários com **JUnit 5** para: cadastro/edição/remover produto, cálculo de total do pedido e tempo, fluxo de login/cadastro (se aplicável).
* Comando para rodar testes:

```
# Maven
mvn test

# Gradle
./gradlew test
```

---


##  Especificação breve de Classes (exemplo de métodos)

* `Usuario`

  * `String id, String nome, String email, String senha`
  * `login()`, `logout()`
* `Cliente extends Usuario`

  * `fazerPedido(Pedido p)`
* `DonoRestaurante extends Usuario`

  * `cadastrarRestaurante(Restaurante r)`
* `Restaurante`

  * `String nome, String endereco, List<Produto> cardapio`
  * `adicionarProduto(Produto p)`, `removerProduto(Produto p)`, `editarProduto(Produto p)`
* `Produto`

  * `String id, String nome, String descricao, double preco, int tempoPreparo`
* `Comida extends Produto`

  * `String tipoCozinha, boolean vegetariano, boolean vegano`
* `Bebida extends Produto`

  * `int volumeMl, boolean alcoolica`
* `Pedido`

  * `Cliente cliente, List<Produto> itens, double total, int tempoEstimado`
  * `calcularTotal()`, `calcularTempo()`
 
<img width="903" height="1019" alt="image" src="https://github.com/user-attachments/assets/e30968f0-ff4b-4ce6-bc55-e2ba63b9000f" />


---

---

