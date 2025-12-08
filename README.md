
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

---

##  Diagrama UML

<img width="903" height="1019" alt="image" src="https://github.com/user-attachments/assets/e30968f0-ff4b-4ce6-bc55-e2ba63b9000f" />

https://www.mermaidchart.com/app/projects/95c55d1c-1048-4551-ab63-0aae6f9580ec/diagrams/643887a2-8ec1-4480-b0bc-7ebfe79ece94/share/invite/eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJkb2N1bWVudElEIjoiNjQzODg3YTItOGVjMS00NDgwLWIwYmMtN2ViZmU3OWVjZTk0IiwiYWNjZXNzIjoiRWRpdCIsImlhdCI6MTc2NTIyNTkzMn0.Zt6vzim7Degn_d08EksnxNhLZ6cU5c2N79XT_prU-k4

---

---

