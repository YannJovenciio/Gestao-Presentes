# 🧪 GUIA PRÁTICO: Testando Seu Controller Escalável

## 📌 Introdução

Você aprendeu a escrever código melhor. Agora precisa **validar** que está funcionando.

Existem 3 formas de testar uma API REST:
1. **Manual** (Postman, cURL, Browser)
2. **Automático** (Testes Unitários)
3. **Integração** (Testes de Integração)

Vamos explorar as 3! 🚀

---

## 1️⃣ TESTE MANUAL (Forma Rápida)

### Usando cURL (Terminal)

#### ✅ Teste 1: Listar Avaliadores (Sem Presentes)
```bash
curl -X GET "http://localhost:8080/api/avaliadores?page=0&size=10" \
  -H "Content-Type: application/json"
```

**Resposta esperada:**
```json
{
  "data": [
    {
      "id": 1,
      "nome": "João Silva",
      "sexo": "M",
      "email": "joao@example.com"
    }
  ],
  "message": "Avaliadores recuperados com sucesso",
  "statusCode": 200
}
```

#### ✅ Teste 2: Listar Avaliadores (Com Presentes)
```bash
curl -X GET "http://localhost:8080/api/avaliadores?page=0&size=10&isWithPresente=true" \
  -H "Content-Type: application/json"
```

**Resposta esperada:**
```json
{
  "data": [
    {
      "id": 1,
      "nome": "João Silva",
      "presentes": [        // ← Agora tem presentes!
        {
          "id": 1,
          "descricao": "Presente A"
        }
      ]
    }
  ],
  "message": "Avaliadores recuperados com sucesso",
  "statusCode": 200
}
```

#### ✅ Teste 3: Buscar Avaliador Específico
```bash
curl -X GET "http://localhost:8080/api/avaliadores/1" \
  -H "Content-Type: application/json"
```

#### ✅ Teste 4: Buscar com Presentes
```bash
curl -X GET "http://localhost:8080/api/avaliadores/1?isWithPresente=true" \
  -H "Content-Type: application/json"
```

#### ✅ Teste 5: Criar Avaliador (Válido)
```bash
curl -X POST "http://localhost:8080/api/avaliadores" \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "Maria Santos",
    "sexo": "F",
    "endereco": "Rua das Flores, 456",
    "telefone": "(21)98765-4321",
    "email": "maria@example.com"
  }'
```

**Resposta esperada:**
```json
{
  "data": {
    "id": 2,
    "nome": "Maria Santos",
    "message": "Avaliador criado com sucesso",
    "statusCode": 201
  }
}
```

#### ❌ Teste 6: Criar Avaliador (Inválido - Teste Validação!)
```bash
curl -X POST "http://localhost:8080/api/avaliadores" \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "Jo",                    # ❌ Menos de 3 caracteres
    "sexo": "M",
    "endereco": "Rua",               # ❌ Menos de 5 caracteres
    "telefone": "123",               # ❌ Formato inválido
    "email": "nao_e_email"           # ❌ Email inválido
  }'
```

**Resposta esperada (400 Bad Request):**
```json
{
  "data": {
    "nome": "Nome deve ter entre 3 e 100 caracteres",
    "endereco": "Endereço deve ter entre 5 e 200 caracteres",
    "telefone": "Telefone inválido: use formato (XX)XXXXX-XXXX",
    "email": "Email inválido"
  },
  "message": "Erro de validação",
  "statusCode": 400
}
```

---

### Usando Postman (GUI)

#### Passo 1: Criar Nova Requisição
```
1. Clique em "New" → "Request"
2. Nome: "GET Avaliadores"
3. Método: GET
4. URL: http://localhost:8080/api/avaliadores
```

#### Passo 2: Adicionar Query Parameters
```
1. Clique em "Params"
2. Key: "page" → Value: "0"
3. Key: "size" → Value: "10"
4. Key: "isWithPresente" → Value: "false"
```

#### Passo 3: Enviar
```
Clique em "Send"
```

---

## 2️⃣ TESTE AUTOMÁTICO (Forma Profissional)

### Teste Unitário com JUnit + Mockito

```java
package com.gestao.gestaopresente.presentation.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class AvaliadorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IAvaliadorService avaliadorService;

    /**
     * ✅ Teste 1: GET /avaliadores retorna status 200
     */
    @Test
    public void deveRetornarAvaliadores_ComStatus200() throws Exception {
        // Arrange (Preparar)
        List<AvaliadorResponse> avaliadores = List.of(
            new AvaliadorResponse(1L, "João Silva", "M", "joao@example.com")
        );
        when(avaliadorService.getAllAvaliador(0, 10))
            .thenReturn(avaliadores);

        // Act & Assert (Executar e Verificar)
        mockMvc.perform(
            get("/api/avaliadores")
                .param("page", "0")
                .param("size", "10")
        )
        .andExpect(status().isOk())                          // ✅ Status 200
        .andExpect(jsonPath("$.message").exists())           // ✅ Tem mensagem
        .andExpect(jsonPath("$.statusCode").value(200))      // ✅ Status correto
        .andExpect(jsonPath("$.data[0].nome").value("João Silva"));  // ✅ Dados corretos
    }

    /**
     * ✅ Teste 2: GET /avaliadores?isWithPresente=true carrega presentes
     */
    @Test
    public void deveCarregarComPresentes_QuandoParametroTrue() throws Exception {
        // Arrange
        List<AvaliadorResponse> avaliadores = List.of(
            new AvaliadorResponse(
                1L, 
                "João Silva", 
                "M", 
                "joao@example.com",
                List.of(new PresenteResponse(1L, "Livro"))  // ← Com presentes
            )
        );
        when(avaliadorService.getAllAvaliadorWithPresentes(0, 10))
            .thenReturn(avaliadores);

        // Act & Assert
        mockMvc.perform(
            get("/api/avaliadores")
                .param("isWithPresente", "true")
        )
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.data[0].presentes").isArray());  // ✅ Tem presentes
    }

    /**
     * ✅ Teste 3: GET /avaliadores/{id} retorna avaliador específico
     */
    @Test
    public void deveBuscarAvaliadorPorId() throws Exception {
        // Arrange
        Long id = 1L;
        AvaliadorResponse avaliador = new AvaliadorResponse(
            id, "João Silva", "M", "joao@example.com"
        );
        when(avaliadorService.getAvaliadorById(id))
            .thenReturn(avaliador);

        // Act & Assert
        mockMvc.perform(get("/api/avaliadores/{id}", id))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.id").value(id));
    }

    /**
     * ❌ Teste 4: POST com dados inválidos retorna 400
     */
    @Test
    public void deveRetornar400_QuandoDadosInvalidos() throws Exception {
        mockMvc.perform(
            post("/api/avaliadores")
                .contentType("application/json")
                .content("{" +
                    "\"nome\": \"Jo\"," +                    // ❌ Muito curto
                    "\"sexo\": \"M\"," +
                    "\"endereco\": \"R\"," +                 // ❌ Muito curto
                    "\"telefone\": \"123\"," +               // ❌ Formato inválido
                    "\"email\": \"invalido\"" +              // ❌ Email inválido
                "}")
        )
        .andExpect(status().isBadRequest())                  // ✅ Status 400
        .andExpect(jsonPath("$.statusCode").value(400));
    }

    /**
     * ✅ Teste 5: POST com dados válidos cria avaliador
     */
    @Test
    public void deveCriarAvaliador_ComDadosValidos() throws Exception {
        // Arrange
        AvaliadorResponse novoAvaliador = new AvaliadorResponse(
            2L, "Maria Santos", "F", "maria@example.com"
        );
        when(avaliadorService.createAvaliador(any(AvaliadorInput.class)))
            .thenReturn(novoAvaliador);

        // Act & Assert
        mockMvc.perform(
            post("/api/avaliadores")
                .contentType("application/json")
                .content("{" +
                    "\"nome\": \"Maria Santos\"," +
                    "\"sexo\": \"F\"," +
                    "\"endereco\": \"Rua das Flores, 456\"," +
                    "\"telefone\": \"(21)98765-4321\"," +
                    "\"email\": \"maria@example.com\"" +
                "}")
        )
        .andExpect(status().isCreated())                     // ✅ Status 201
        .andExpect(jsonPath("$.statusCode").value(201))
        .andExpect(jsonPath("$.data.nome").value("Maria Santos"));
    }

    /**
     * ✅ Teste 6: Verificar que validação está sendo chamada
     */
    @Test
    public void deveValidarEmailFormat() throws Exception {
        mockMvc.perform(
            post("/api/avaliadores")
                .contentType("application/json")
                .content("{" +
                    "\"nome\": \"João Silva\"," +
                    "\"sexo\": \"M\"," +
                    "\"endereco\": \"Rua 123\"," +
                    "\"telefone\": \"(11)98765-4321\"," +
                    "\"email\": \"email_invalido\"" +        // ❌ Sem @
                "}")
        )
        .andExpect(status().isBadRequest())                  // ✅ Rejeita
        .andExpect(jsonPath("$.data.email").exists());       // ✅ Erro específico
    }
}
```

### Como Rodar os Testes

```bash
# Rodar todos os testes
mvn test

# Rodar apenas um teste
mvn test -Dtest=AvaliadorControllerTest#deveCriarAvaliador_ComDadosValidos

# Rodar com cobertura
mvn test jacoco:report

# Ver relatório de cobertura
open target/site/jacoco/index.html
```

---

## 3️⃣ TESTE DE INTEGRAÇÃO (Forma Realista)

```java
package com.gestao.gestaopresente.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Testa a integração REAL com o banco de dados
 * (sem mock, tudo real)
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")  // ← Usa application-test.properties
public class AvaliadorIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AvaliadorRepository avaliadorRepository;

    @BeforeEach
    public void setup() {
        avaliadorRepository.deleteAll();  // ← Limpa BD antes de cada teste
    }

    /**
     * ✅ Teste de fluxo completo
     */
    @Test
    @Transactional
    public void deveExecutarFluxoCompleto() throws Exception {
        // 1. Criar um avaliador
        mockMvc.perform(
            post("/api/avaliadores")
                .contentType("application/json")
                .content(jsonAvaliador("João", "joao@example.com"))
        )
        .andExpect(status().isCreated());

        // 2. Verificar que foi criado
        mockMvc.perform(get("/api/avaliadores"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data").isArray())
            .andExpect(jsonPath("$.data.length()").value(1));

        // 3. Buscar específico
        mockMvc.perform(get("/api/avaliadores/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.nome").value("João"));
    }
}
```

---

## 📊 Matriz de Testes Recomendada

| Teste | Tipo | Quando | Velocidade | Confiabilidade |
|-------|------|--------|-----------|----------------|
| Manual (cURL) | Manual | Desenvolvimento | Rápido ⚡ | Baixa ❌ |
| Teste Unitário | Automático | CI/CD | Muito Rápido ⚡⚡ | Média ⚠️ |
| Teste Integração | Automático | Antes de Deploy | Lento 🐢 | Alta ✅ |

---

## ✅ Checklist: O Que Testar

```
FUNCIONALIDADE
☐ GET /avaliadores retorna lista paginada
☐ GET /avaliadores?isWithPresente=true carrega presentes
☐ GET /avaliadores/{id} retorna um avaliador
☐ POST /avaliadores cria novo avaliador
☐ POST /avaliadores rejeita dados inválidos

VALIDAÇÃO
☐ Nome obrigatório
☐ Email em formato válido
☐ Telefone em formato (XX)XXXXX-XXXX
☐ Endereço com mínimo 5 caracteres

STATUS HTTP
☐ 200 OK para GET
☐ 201 Created para POST
☐ 400 Bad Request para dados inválidos
☐ 404 Not Found para recurso inexistente

RESPOSTA
☐ Sempre retorna Response<T>
☐ Sempre tem statusCode
☐ Sempre tem message
☐ Data é estruturado corretamente
```

---

## 🐛 Debugging: Se Algo Der Errado

### Problema 1: "Validação não está funcionando"
```bash
# Verifique se @Valid está presente
@PostMapping
public ResponseEntity<Response<AvaliadorResponse>> createAvaliador(
    @Valid @RequestBody AvaliadorInput input) {  # ← Precisa de @Valid
}

# Verifique se as anotações estão no DTO
public record AvaliadorInput(
    @NotBlank(message = "Nome obrigatório")  # ← Precisa delas
    String nome
)
```

### Problema 2: "Teste retorna 415 Unsupported Media Type"
```java
// Adicione Content-Type
mockMvc.perform(
    post("/api/avaliadores")
        .contentType(MediaType.APPLICATION_JSON)  # ← Precisa disso
        .content("...")
)
```

### Problema 3: "Não consigo serializar a resposta"
```java
// Verifique se todos os DTOs têm getters/setters
@Data  # ← Lombok gera automaticamente
public class AvaliadorResponse { }
```

---

## 🚀 Próximos Passos

1. **Execute os testes manuais** com cURL
2. **Implemente os testes unitários** no seu projeto
3. **Configure CI/CD** (GitHub Actions, Jenkins) para rodar testes automaticamente
4. **Monitore cobertura** (Jacoco) para ter pelo menos 80% de cobertura

---

## 📌 Conclusão

Testar é tão importante quanto codificar!

**Código sem teste = Código que vai quebrar em produção** 💥

Teste desde o início, não deixe para depois! ✅

