# 📖 Documentação Swagger - Gestão de Presentes API

## 🚀 Como Acessar a Documentação

Após iniciar a aplicação, a documentação interativa do Swagger estará disponível em:

**Swagger UI (Interface Interativa):**
```
http://localhost:8080/swagger-ui/index.html
```

**OpenAPI JSON:**
```
http://localhost:8080/v3/api-docs
```

---

## 🔐 Como Usar a Autenticação JWT no Swagger

### Passo 1: Registrar um Usuário
1. Navegue até a seção **"Autenticação"**
2. Clique em **POST /api/auth/register**
3. Clique em **"Try it out"**
4. Insira os dados do usuário no body:
```json
{
  "fullName": "João Silva",
  "email": "joao@email.com",
  "password": "senha123"
}
```
5. Clique em **"Execute"**

### Passo 2: Fazer Login e Obter o Token
1. Clique em **POST /api/auth/login**
2. Clique em **"Try it out"**
3. Insira as credenciais:
```json
{
  "email": "joao@email.com",
  "password": "senha123"
}
```
4. Clique em **"Execute"**
5. **Copie o token** retornado no response (campo `data.token`)

### Passo 3: Autorizar no Swagger
1. Clique no botão **"Authorize"** 🔒 no topo da página
2. Na modal que abrir, cole o token **SEM** adicionar "Bearer " (já é adicionado automaticamente)
3. Clique em **"Authorize"**
4. Feche a modal

✅ Pronto! Agora você pode testar todos os endpoints protegidos!

---

## 📋 Endpoints Disponíveis

### 🔐 Autenticação
- `POST /api/auth/register` - Registrar novo usuário
- `POST /api/auth/login` - Fazer login (retorna JWT token)

### 👥 Servidores
- `GET /api/servidor` - Listar todos os servidores
- `GET /api/servidor/{email}` - Buscar servidor por email
- `POST /api/servidor` - Criar novo servidor
- `PATCH /api/servidor/{id}` - Atualizar servidor
- `DELETE /api/servidor/{id}` - Deletar servidor

### 📋 Avaliadores
- `GET /api/avaliadores` - Listar avaliadores (com paginação)
- `GET /api/avaliadores/{id}` - Buscar avaliador por ID
- `POST /api/avaliadores` - Criar novo avaliador

### 🎁 Presentes
- `POST /api/presente` - Criar presente (vinculado ao usuário autenticado)

### 🏢 Funções
- `POST /api/funcao` - Criar nova função

---

## 🎯 Recursos da Documentação

### ✅ O que foi documentado:

1. **Informações Gerais da API**
   - Título, versão e descrição completa
   - Informações de contato
   - Servidor de desenvolvimento

2. **Autenticação JWT**
   - Esquema de segurança configurado
   - Instruções de uso no header

3. **Todos os Endpoints**
   - Sumário e descrição detalhada
   - Parâmetros documentados (path, query, body)
   - Exemplos de valores
   - Códigos de resposta HTTP (200, 201, 400, 401, 404)
   - Schemas dos objetos de entrada e saída

4. **DTOs Documentados**
   - `ServidorInput` com todos os campos e validações
   - Exemplos de valores para cada campo
   - Descrições claras das propriedades

5. **Grupos de Validação**
   - `BasicInfo` - usado no POST (criação)
   - `AdvancedInfo` - usado no PATCH (atualização)

---

## 💡 Dicas

### Testando Endpoints Protegidos
- Todos os endpoints (exceto `/auth/login` e `/auth/register`) requerem autenticação
- O token JWT expira após um tempo configurado
- Se receber erro 401, faça login novamente para obter um novo token

### Paginação
Endpoints de listagem suportam paginação:
- `page` - número da página (começa do 0)
- `size` - quantidade de registros por página
- Exemplo: `/api/avaliadores?page=0&size=10`

### Performance (N+1)
Alguns endpoints têm o parâmetro `isWithPresente`:
- `false` (padrão) - não carrega presentes (mais rápido)
- `true` - carrega presentes associados (atenção ao N+1)

### Validação de Grupos
O `ServidorInput` usa validação em grupos:
- **POST** valida com `BasicInfo` - todos os campos obrigatórios
- **PATCH** valida com `AdvancedInfo` - todos os campos obrigatórios + ID vem do path

---

## 📚 Tecnologias Utilizadas

- **Springdoc OpenAPI 3.0.2** - Geração da documentação
- **Swagger UI** - Interface interativa
- **Spring Boot 4.0.5** - Framework
- **JWT** - Autenticação

---

## 🔧 Configuração

A documentação é gerada automaticamente através das anotações:
- `@Tag` - Agrupa endpoints por categoria
- `@Operation` - Documenta operações individuais
- `@ApiResponses` - Define códigos de resposta HTTP
- `@Parameter` - Documenta parâmetros
- `@Schema` - Documenta estruturas de dados
- `@SecurityRequirement` - Indica endpoints protegidos

Toda configuração está em: `OpenApiConfig.java`
