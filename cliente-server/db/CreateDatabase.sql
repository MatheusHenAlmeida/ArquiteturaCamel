USE cliente-db;

CREATE TABLE cliente(
    id INT NOT NULL AUTO_INCREMENT,
    PRIMARY KEY(id),
    razao_social VARCHAR(255),
    base VARCHAR(255),
    segmento VARCHAR(255),
    endereco VARCHAR(255),
    email VARCHAR(255),
    telefone VARCHAR(255)
);

SET character_set_client = utf8;
SET character_set_connection = utf8;
SET character_set_results = utf8;
SET collation_connection = utf8_general_ci;

INSERT INTO cliente(razao_social, base, segmento, endereco, email, telefone) VALUES ('Unipecas SA', 'GRANDE_SP', 'Auto pecas', 'Av. Brasil, 123', 'unipecas@mail.com', '11 432 573 432');
INSERT INTO cliente(razao_social, base, segmento, endereco, email, telefone) VALUES ('Padaria Titanic Ltda', 'GRANDE_SP', 'Restaurantes', 'Rua Salvador, 67', 'Titanic@mail.com', '11 432 573 432');

INSERT INTO cliente(razao_social, base, segmento, endereco, email, telefone) VALUES ('Microsoft Brasil Ltda', 'LITORAL', 'Software', 'Av. Peru, 123', 'microsoft@mail.com', '11 432 573 432');
INSERT INTO cliente(razao_social, base, segmento, endereco, email, telefone) VALUES ('Gedore Ltda', 'LITORAL', 'Ferramentas', 'Rua dos Incas, 123', 'gedore@mail.com', '11 432 573 432');

INSERT INTO cliente(razao_social, base, segmento, endereco, email, telefone) VALUES ('Lavanderia Clean SA', 'GRANDE_CAMPINAS', 'Lavanderia', 'Av. Lisboa, 123', 'cleansa@mail.com', '11 432 573 432');
INSERT INTO cliente(razao_social, base, segmento, endereco, email, telefone) VALUES ('Banca dos Quadrinhos', 'GRANDE_CAMPINAS', 'Banca de Jornal', 'Rua Alegre, 123', 'qadrinhos@mail.com', '11 432 573 432');