# Sistema de Pedidos de Restaurante

> Projeto acadêmico: sistema simplificado de pedidos (estudo de POO + GUI em Java)

## 📌 Objetivo

Desenvolver um sistema de pedidos para restaurante que permita praticar conceitos de **Programação Orientada a Objetos** (herança, polimorfismo, interfaces) e construir uma **interface gráfica** em Java (Swing ou JavaFX). O projeto pode ser desenvolvido em equipe de até 3 alunos e deve ficar hospedado em um repositório no GitHub.

---

## ✅ Funcionalidades Principais

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

## 🧭 Fluxo do Usuário (resumido)

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

## 🧱 Diagrama UML (requisitos)

Crie um diagrama de classes que represente as relações abaixo e salve no diretório `docs/diagrams/` como `uml-classes.png` ou `uml-classes.svg`.

**Classes principais sugeridas**:

* `Usuario` (superclasse)

  * `Cliente` (herda `Usuario`)
  * `DonoRestaurante` (herda `Usuario`)
* `Restaurante` (possui lista de `Produto`)
* `Produto` (superclasse)

  * `Comida` (tipo de cozinha, vegetariano/vegano)
  * `Bebida` (tamanho ml, alcoólica)
* `Pedido` (contém lista de `Produto`, referencia `Cliente`)

**Relações e conceitos**:

* Herança: `Cliente` / `DonoRestaurante` <- `Usuario`; `Comida` / `Bebida` <- `Produto`.
* Composição / agregação: `Restaurante` tem uma lista de `Produto`; `Pedido` tem uma lista de `Produto`.
* Interfaces: por exemplo, `Gerenciavel` com métodos `adicionar()`, `remover()`, `atualizar()`—podendo ser implementada por `Restaurante` ou por um gerenciador de produtos.

Inclua também os métodos principais em cada classe (constructors, getters/setters e operações relevantes).

---

## 🛠️ Tecnologias Sugeridas

* Linguagem: **Java 11+**
* GUI: **Swing** (mais simples) ou **JavaFX** (mais moderno)
* Build: **Maven** ou **Gradle**
* Persistência (opcional / extra): SQLite, H2, ou PostgreSQL
* Testes: JUnit 5

---

## 🔧 Estrutura de Pastas Sugerida

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

## 🚀 Como Executar (local)

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

## 🧪 Testes

* Escreva testes unitários com **JUnit 5** para: cadastro/edição/remover produto, cálculo de total do pedido e tempo, fluxo de login/cadastro (se aplicável).
* Comando para rodar testes:

```
# Maven
mvn test

# Gradle
./gradlew test
```

---

## 💾 Sugestões para Pontuação Extra (Extras / APIs Java)

Implemente um ou mais itens abaixo para ganhar pontos extras:

* **Banco de dados**: persistência com SQLite/H2/Postgres, uso de DAO/Repository.
* **Geolocalização**: calcular restaurantes próximos baseado no endereço (integração com APIs externas ou algoritmo próprio).
* **Tratamento de exceção**: cobertura robusta de erros e mensagens claras na GUI.
* **Testes Unitários e de Integração**: cobertura elevada e pipeline de CI.
* **Integração com API externa**: por exemplo, APIs de mapas, tempo de preparação estimado por ML simples, etc.
* **Arquitetura limpa**: separação em camadas (model, service, repository, ui).

---

## 🧾 Especificação breve de Classes (exemplo de métodos)

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

## 📁 Boas Práticas para o Repositório

* README claro (este arquivo).
* Commits atômicos e mensagens descritivas.
* Issues e um pequeno board (GitHub Projects) para organização das tarefas.
* Branching: `main` protegido, `develop` + `feature/*` para desenvolvimento.

---

## 🤝 Como Contribuir

1. Fork o repositório
2. Crie uma branch: `feature/nome-da-feature`
3. Faça commits claros e push
4. Abra um Pull Request descrevendo a alteração

---

## 👥 Equipe

* Máximo de 3 alunos por equipe. Incluam um arquivo `TEAM.md` com nome, matrícula, e e-mail de cada integrante.

---

## 📝 Licença

Escolham uma licença para o projeto (por exemplo, MIT). Adicione um arquivo `LICENSE` no repositório.

---

## Próximos passos sugeridos

* Criar o diagrama UML em `docs/diagrams`.
* Implementar as classes de domínio (modelo) e testes unitários básicos.
* Construir a interface gráfica mínima para cadastro e listagem de produtos.

---

> Se quiser, eu posso:
>
> * Gerar automaticamente o esqueleto de código Java (pacotes e classes) com base nessa estrutura.
> * Criar um diagrama UML simples (SVG) e adicionar ao diretório `docs/diagrams`.
> * Gerar o arquivo `pom.xml` ou `build.gradle` inicial.
