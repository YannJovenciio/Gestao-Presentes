# Guia Completo: Flyway para Migrations de Banco de Dados

## 📋 Índice
1. [O que é Flyway?](#o-que-é-flyway)
2. [Estrutura de Diretórios](#estrutura-de-diretórios)
3. [Convenção de Nomes](#convenção-de-nomes)
4. [Exemplos de Migrations](#exemplos-de-migrations)
5. [Configuração](#configuração)
6. [Comandos e Boas Práticas](#comandos-e-boas-práticas)

---

## O que é Flyway?

**Flyway** é uma ferramenta de versionamento de banco de dados que permite:
- Controlar versão de scripts SQL
- Executar migrations automaticamente na inicialização da aplicação
- Garantir consistência entre ambientes (dev, staging, prod)
- Rastrear histórico de alterações

---

## Estrutura de Diretórios

```
src/main/resources/db/
├── migration/           ← Migrations de versão
│   ├── V1__nome.sql
│   ├── V2__nome.sql
│   └── V3__nome.sql
└── callback/            ← (Opcional) Callbacks antes/depois de migrations
    ├── beforeMigrate.sql
    └── afterMigrate.sql
```

---

## Convenção de Nomes

### Migrations de Versão (Executadas em ordem)
**Formato:** `V{VERSÃO}__{DESCRIÇÃO}.sql`

Exemplos:
```
V1__criar_tabelas_iniciais.sql
V2__adicionar_coluna_email.sql
V3__criar_indice_performance.sql
V4__inserir_dados_padrao.sql
```

**Regras:**
- ✅ `V` maiúsculo (versão)
- ✅ Números sequenciais (1, 2, 3...)
- ✅ Duplo underscore `__` separador
- ✅ Descrição em snake_case
- ✅ Extensão `.sql`

### Migrations Repetíveis (Executadas sempre que mudam)
**Formato:** `R__{DESCRIÇÃO}.sql`

Exemplos:
```
R__criar_views_relatorios.sql
R__criar_funcoes_utilitarias.sql
```

---

## Exemplos de Migrations

### 📝 Exemplo 1: Criar Tabela Básica
```sql
-- V1__criar_tabela_usuario.sql
CREATE TABLE IF NOT EXISTS usuario (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    data_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

COMMENT ON TABLE usuario IS 'Tabela de usuários do sistema';
```

### 📝 Exemplo 2: Alterar Tabela (Adicionar Coluna)
```sql
-- V2__adicionar_coluna_telefone.sql
ALTER TABLE usuario ADD COLUMN IF NOT EXISTS telefone VARCHAR(20);

-- Adicionar constraint
ALTER TABLE usuario ADD CONSTRAINT ck_telefone CHECK (telefone IS NULL OR LENGTH(telefone) >= 10);

-- Criar índice
CREATE INDEX IF NOT EXISTS idx_usuario_email ON usuario(email);
```

### 📝 Exemplo 3: Criar Relacionamento
```sql
-- V3__criar_tabela_pedido.sql
CREATE TABLE IF NOT EXISTS pedido (
    id BIGSERIAL PRIMARY KEY,
    usuario_id BIGINT NOT NULL,
    data_pedido TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    total DECIMAL(10, 2),
    CONSTRAINT fk_pedido_usuario FOREIGN KEY (usuario_id) 
        REFERENCES usuario(id) ON DELETE CASCADE
);

CREATE INDEX IF NOT EXISTS idx_pedido_usuario_id ON pedido(usuario_id);
```

### 📝 Exemplo 4: Inserir Dados Iniciais
```sql
-- V4__inserir_dados_iniciais.sql
INSERT INTO usuario (nome, email) VALUES 
    ('Admin', 'admin@exemplo.com'),
    ('Usuário Teste', 'teste@exemplo.com')
ON CONFLICT (email) DO NOTHING;
```

### 📝 Exemplo 5: Criar View (Repetível)
```sql
-- R__criar_view_relatorio_vendas.sql
CREATE OR REPLACE VIEW vw_relatorio_vendas AS
SELECT 
    u.id,
    u.nome,
    COUNT(p.id) AS total_pedidos,
    SUM(p.total) AS valor_total
FROM usuario u
LEFT JOIN pedido p ON u.id = p.usuario_id
GROUP BY u.id, u.nome;
```

### 📝 Exemplo 6: Deletar e Recriar (Importante!)
```sql
-- V5__remover_coluna_obsoleta.sql
-- Sempre fazer backup/verificação antes de deletar
ALTER TABLE usuario DROP COLUMN IF EXISTS campo_obsoleto;
```

---

## Configuração

### 🔧 application.properties

```properties
# Configuração do Flyway
spring.flyway.enabled=true
spring.flyway.baselineOnMigrate=true
spring.flyway.cleanDisabled=false
spring.flyway.locations=classpath:db/migration
spring.flyway.defaultSchema=public
```

### 🔧 application-prod.properties (Produção)

```properties
# Em produção, desabilitar limpeza
spring.flyway.enabled=true
spring.flyway.cleanDisabled=true
spring.flyway.validateOnMigrate=true
```

---

## Comandos e Boas Práticas

### ✅ Boas Práticas

1. **Versionamento Sequencial**: Sempre incrementar números
   ```
   ✅ V1, V2, V3...
   ❌ V1, V3, V5... (vai quebrar)
   ```

2. **Idempotência**: Usar `IF NOT EXISTS` / `IF EXISTS`
   ```sql
   CREATE TABLE IF NOT EXISTS usuario (...);
   ALTER TABLE IF EXISTS usuario ADD COLUMN email;
   ```

3. **Rollback Seguro**: Não há rollback automático - planeje bem!
   ```sql
   -- V1__criar.sql
   CREATE TABLE usuario (...);
   
   -- V2__recriar.sql (se precisar corrigir)
   DROP TABLE usuario;
   CREATE TABLE usuario (...); -- Versão corrigida
   ```

4. **Migrations Pequenas**: Uma mudança por arquivo
   ```
   ✅ V1__criar_tabela_usuario.sql
   ✅ V2__adicionar_coluna_email.sql
   
   ❌ V1__fazer_tudo.sql (com múltiplas mudanças)
   ```

5. **Comentários Descritivos**: Explicar o "por quê"
   ```sql
   -- V2__adicionar_indice_email.sql
   -- Índice necessário para melhora de performance em buscas por email
   CREATE INDEX idx_usuario_email ON usuario(email);
   ```

### 🔍 Monitoramento

Flyway cria tabela `flyway_schema_history` com histórico:
```sql
SELECT * FROM flyway_schema_history;
```

Colunas importantes:
- `version`: Número da versão
- `description`: Descrição da migration
- `type`: `SQL` ou `JAVA`
- `execution_time`: Tempo de execução em ms
- `success`: 1 se sucesso, 0 se falha

---

## 📚 Padrões Abstratos

### Padrão 1: Entidade Base
```sql
-- V1__criar_tabela_entidade_base.sql
CREATE TABLE IF NOT EXISTS entidade_base (
    id BIGSERIAL PRIMARY KEY,
    criado_em TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    atualizado_em TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    ativo BOOLEAN DEFAULT TRUE
);
```

### Padrão 2: Auditoria
```sql
-- V2__adicionar_auditoria.sql
ALTER TABLE entidade_base ADD COLUMN IF NOT EXISTS criado_por VARCHAR(255);
ALTER TABLE entidade_base ADD COLUMN IF NOT EXISTS atualizado_por VARCHAR(255);

COMMENT ON COLUMN entidade_base.criado_em IS 'Timestamp de criação';
COMMENT ON COLUMN entidade_base.criado_por IS 'Usuário que criou o registro';
```

### Padrão 3: Soft Delete
```sql
-- V3__implementar_soft_delete.sql
ALTER TABLE entidade_base ADD COLUMN IF NOT EXISTS deletado_em TIMESTAMP;

-- View para retornar apenas registros ativos
CREATE OR REPLACE VIEW vw_entidade_ativa AS
SELECT * FROM entidade_base WHERE deletado_em IS NULL;
```

---

## 🎯 Fluxo Típico

1. **Desenvolvimento Local**: 
   - Criar migration `V{N}__descricao.sql`
   - Testar localmente
   - Commitar no Git

2. **Integração**: 
   - CI/CD executa migrations automaticamente
   - Testes rodando contra dados migrados

3. **Produção**: 
   - Deployment automático executa migrations
   - Histórico registrado em `flyway_schema_history`

---

## ⚠️ Dicas de Recuperação

Se uma migration falhar:

```sql
-- 1. Verificar histórico
SELECT * FROM flyway_schema_history WHERE success = 0;

-- 2. Corrigir manualmente se necessário
-- ... executar SQL correto ...

-- 3. Marcar como sucesso (cuidado!)
UPDATE flyway_schema_history 
SET success = 1 
WHERE version = 5 AND success = 0;
```

---

## Referências

- [Documentação Oficial Flyway](https://flywaydb.org/documentation/)
- [Spring Boot + Flyway](https://spring.io/blog/2021/01/13/spring-boot-2-4-0-m1-released)
- [PostgreSQL Documentation](https://www.postgresql.org/docs/)

