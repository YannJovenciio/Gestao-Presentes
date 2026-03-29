# GestaoPresentes com Docker

Este projeto foi configurado para rodar com Docker usando Spring Boot + PostgreSQL.

## Requisitos

- Docker Desktop
- Docker Compose (plugin `docker compose`)

## Subir a aplicacao

1. Opcional: copie o arquivo de exemplo de variaveis e ajuste as credenciais.
2. Construa e suba os containers.

```bash
cp .env.example .env
docker compose up --build
```

No Windows PowerShell, use:

```powershell
Copy-Item .env.example .env
docker compose up --build
```

## URLs uteis

- API: `http://localhost:8080`
- Swagger UI: `http://localhost:8080/swagger-ui/index.html`

## Encerrar

```bash
docker compose down
```

Para remover tambem o volume do banco:

```bash
docker compose down -v
```

