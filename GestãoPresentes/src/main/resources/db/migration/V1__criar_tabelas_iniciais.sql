CREATE TABLE IF NOT EXISTS avaliador (
id BIGSERIAL PRIMARY KEY,
nome VARCHAR(100) NOT NULL,
sexo VARCHAR(10)  NOT NULL CHECK (sexo IN ('feminino','masculino')),
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
sexo VARCHAR(10) NOT NULL CHECK (sexo IN ('feminino','masculino')),
dataNasc DATE,
salario DECIMAL(10,2),
email VARCHAR(150),
cpf VARCHAR(30),
nomeCompleto VARCHAR(100),
funcao_id BIGINT NOT NULL,
FOREIGN KEY (funcao_id) REFERENCES funcao(id)
);

CREATE TABLE IF NOT EXISTS presente(
    id BIGSERIAL PRIMARY KEY,
    dataEntrega DATE,
    observacao VARCHAR(255),
    valor DECIMAL,
    pais_id BIGINT NOT NULL,
    avaliador_id BIGINT NOT NULL,
    servidor_id BIGINT NOT NULL,
    FOREIGN KEY (pais_id) REFERENCES pais(id),
    FOREIGN KEY (avaliador_id) REFERENCES avaliador(id),
    FOREIGN KEY (servidor_id) REFERENCES servidor(id)
);