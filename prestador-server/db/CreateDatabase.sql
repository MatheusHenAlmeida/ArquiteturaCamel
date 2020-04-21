USE prestador-db;

CREATE TABLE prestador(
    id INT NOT NULL AUTO_INCREMENT,
    PRIMARY KEY(id),
    razao_social VARCHAR(255),
    base VARCHAR(255),
    endereco VARCHAR(255),
    email VARCHAR(255),
    telefone VARCHAR(255)
);

SET character_set_client = utf8;
SET character_set_connection = utf8;
SET character_set_results = utf8;
SET collation_connection = utf8_general_ci;

INSERT INTO prestador(razao_social, base, endereco, email, telefone) VALUES ('Garoa Tech ltda', 'GRANDE_SP', 'Av. Brasil, 123', 'matheushdealmeida@gmail.com', '11 432 573 432');
INSERT INTO prestador(razao_social, base, endereco, email, telefone) VALUES ('Baixada Geek ltda', 'LITORAL', 'Av. Peru, 123', 'matheushdealmeida@gmail.com', '11 432 573 432');
INSERT INTO prestador(razao_social, base, endereco, email, telefone) VALUES ('SysSuporte ltda', 'GRANDE_CAMPINAS', 'Av. Lisboa, 123', 'matheushdealmeida@gmail.com', '11 432 573 432');
INSERT INTO prestador(razao_social, base, endereco, email, telefone) VALUES ('Suporte AraraTECH ltda', 'REGIAO_ARARAQUARA', 'Av. Lisboa, 123', 'matheushdealmeida@gmail.com', '11 432 573 432');
INSERT INTO prestador(razao_social, base, endereco, email, telefone) VALUES ('SoftBauru ltda', 'REGIAO_BAURU', 'Av. Lisboa, 123', 'matheushdealmeida@gmail.com', '11 432 573 432');
INSERT INTO prestador(razao_social, base, endereco, email, telefone) VALUES ('Santo Tech ltda', 'VALE_PARAIBA', 'Av. Lisboa, 123', 'matheushdealmeida@gmail.com', '11 432 573 432');