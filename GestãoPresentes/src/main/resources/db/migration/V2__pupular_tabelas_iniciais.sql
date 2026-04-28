INSERT INTO avaliador(nome,sexo,endereco,telefone,email)
    VALUES ('Joao','MASCULINO','Ribeirao V','24 999854912','joao@test.com'),
    ('Gabriel','MASCULINO','Lotes','24 9991567466','gab@test.com');

INSERT INTO funcao(nome,descricao)
    VALUES ('gerente','gerencia os documentos');

INSERT INTO pais(continente,nome)
    VALUES('asia','China');

INSERT INTO servidor(sexo,data_nasc,salario,email,cpf,nome_completo,funcao_id)
    VALUES('feminino',
           '2024-10-31',
           2000.99,
           'gab@test.com',
           '85487027520',
           'gabriela silva',
           1);

INSERT INTO presente(data_entrega,observacao,valor,pais_id,avaliador_id,servidor_id)
    VALUES(now(),'fragil',1000000.88,1,1,1);



