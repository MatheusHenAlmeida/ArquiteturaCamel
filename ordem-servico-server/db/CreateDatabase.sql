USE ordem-servico-db;

CREATE TABLE ordem_servico(
    id INT NOT NULL AUTO_INCREMENT,
    PRIMARY KEY(id),
    prestador_nome VARCHAR(255),
    prestador_email VARCHAR(255),
    prestador_telefone VARCHAR(255),
    cliente_nome VARCHAR(255),
    cliente_endereco VARCHAR(255),
    cliente_email VARCHAR(255),
    cliente_telefone VARCHAR(255),
    atendida BOOLEAN DEFAULT false,
    descricao TEXT
);

SET character_set_client = utf8;
SET character_set_connection = utf8;
SET character_set_results = utf8;
SET collation_connection = utf8_general_ci;