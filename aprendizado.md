# 📚 Diário de Aprendizado - Java/Spring Boot

Este documento registra os aprendizados diários durante o desenvolvimento do projeto **Gestão de Presentes**.

---

## 📅 12/04/2026

###  Design de Código e Otimizações

- **Métodos na própria entidade**: Criar métodos na própria entidade reduz a quantidade de código e melhora a coesão
  
- **Supplier com `orElseThrow`**: Sintaxe eficiente para tratamento de exceções
  ```java
  () -> new Exception()
  ```

###  Performance e Banco de Dados

- **FETCH JOIN**: Trazer dados relacionados juntos, evitando o problema de N+1 queries

###  Design de API REST

- **Clareza no contrato da API**: Deixar o contrato mais claro quando relacionado a um objeto, passando o `{id}` na rota
  - Exemplo: `PATCH /api/servidor/{id}` ao invés de enviar o ID no body

---

## 📅 14/04/2026

###  Validação com Grupos

- **Validação em grupos**: Você pode validar grupos de informações específicas facilmente usando `@Validated`
  - **Exemplo**: `ServidorController` utiliza grupos de validação (`BasicInfo` e `AdvancedInfo`)
  - Permite reutilizar o mesmo DTO para diferentes operações (POST e PATCH) com validações distintas

---

```markdown
## 📅 DD/MM/AAAA

### 🏷️ Categoria do Aprendizado

- **Título do conceito**: Descrição breve do que foi aprendido
  - Sub-item com detalhes (opcional)
  - Exemplo de código (opcional)
  ```java
  // código aqui
  ```