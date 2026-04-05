CREATE TABLE IF NOT EXISTS avaliador (
id BIGSERIAL PRIMARY KEY,
nome VARCHAR(100) NOT NULL,
sexo VARCHAR(10) NOT NULL CHECK (sexo IN ('FEMININO', 'MASCULINO', 'feminino', 'masculino')),
endereco VARCHAR(150),
telefone VARCHAR(20),
email VARCHAR(150) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS funcao(
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    descricao VARCHAR(150)
);

CREATE TABLE IF NOT EXISTS pais(
id BIGSERIAL PRIMARY KEY,
continente VARCHAR(30) NOT NULL CHECK (continente IN ('america', 'europa', 'asia', 'africa', 'oceania', 'antartida')),
nome VARCHAR(30)
);

CREATE TABLE IF NOT EXISTS servidor (
id BIGSERIAL PRIMARY KEY,
sexo VARCHAR(10) NOT NULL CHECK (sexo IN ('FEMININO', 'MASCULINO', 'feminino', 'masculino')),
data_nasc DATE,
salario DECIMAL(10,2),
email VARCHAR(150),
cpf VARCHAR(30),
nome_completo VARCHAR(100),
funcao_id BIGINT NOT NULL,
FOREIGN KEY (funcao_id) REFERENCES funcao(id)
);

CREATE TABLE IF NOT EXISTS presente(
    id BIGSERIAL PRIMARY KEY,
    data_entrega DATE,
    observacao VARCHAR(255),
    valor DECIMAL,
    pais_id BIGINT NOT NULL,
    avaliador_id BIGINT NOT NULL,
    servidor_id BIGINT NOT NULL,
    FOREIGN KEY (pais_id) REFERENCES pais(id),
    FOREIGN KEY (avaliador_id) REFERENCES avaliador(id),
    FOREIGN KEY (servidor_id) REFERENCES servidor(id)
);

CREATE SEQUENCE usuario_seq START 1 INCREMENT 1;

CREATE TABLE IF NOT EXISTS usuario(
    id BIGINT PRIMARY KEY DEFAULT nextval('usuario_seq'),
    full_name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS usuario_roles(
    usuario_id BIGINT NOT NULL,
    roles VARCHAR(50) NOT NULL,
    FOREIGN KEY (usuario_id) REFERENCES usuario(id) ON DELETE CASCADE
);
