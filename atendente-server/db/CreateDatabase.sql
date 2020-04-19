USE atendente-db;

CREATE TABLE atendente(
    id INT NOT NULL AUTO_INCREMENT,
    PRIMARY KEY(id),
    base VARCHAR(255),
    cargo VARCHAR(255),
    nivel VARCHAR(255),
    nome VARCHAR(255)
);

SET character_set_client = utf8;
SET character_set_connection = utf8;
SET character_set_results = utf8;
SET collation_connection = utf8_general_ci;

INSERT INTO atendente(nome, base, cargo, nivel) VALUES ('Roberto', 'GRANDE_SP', 'SUPERVISOR', 'PLENO');
INSERT INTO atendente(nome, base, cargo, nivel) VALUES ('Carlos', 'GRANDE_SP', 'ESTAGIARIO', 'PLENO');
INSERT INTO atendente(nome, base, cargo, nivel) VALUES ('Luiza', 'GRANDE_SP', 'ANALISTA', 'PLENO');
INSERT INTO atendente(nome, base, cargo, nivel) VALUES ('Silvia', 'LITORAL', 'SUPERVISOR', 'PLENO');
INSERT INTO atendente(nome, base, cargo, nivel) VALUES ('Alice', 'LITORAL', 'ESTAGIARIO', 'PLENO');
INSERT INTO atendente(nome, base, cargo, nivel) VALUES ('Marcos', 'LITORAL', 'ANALISTA', 'PLENO');
INSERT INTO atendente(nome, base, cargo, nivel) VALUES ('Tulio', 'GRANDE_CAMPINAS', 'SUPERVISOR', 'PLENO');
INSERT INTO atendente(nome, base, cargo, nivel) VALUES ('Astolfo', 'GRANDE_CAMPINAS', 'ESTAGIARIO', 'PLENO');
INSERT INTO atendente(nome, base, cargo, nivel) VALUES ('Carla', 'GRANDE_CAMPINAS', 'ANALISTA', 'PLENO');