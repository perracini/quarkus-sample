CREATE TABLE aluno
(
    aluno_id SERIAL PRIMARY KEY,
    name TEXT NOT NULL
);
ALTER SEQUENCE aluno_aluno_id_seq RESTART 1000000;

CREATE TABLE instrutor
(
    instrutor_id SERIAL PRIMARY KEY,
    name TEXT NOT NULL
);
ALTER SEQUENCE instrutor_instrutor_id_seq RESTART 1000000;

CREATE TABLE turma 
(
  turma_id SERIAL PRIMARY KEY,
  end_date date DEFAULT NULL,
  start_date date DEFAULT NULL,
  status TEXT NOT NULL,
  instrutor_id INT DEFAULT NULL,
  aluno_id INT DEFAULT NULL,
  CONSTRAINT turma_instrutor FOREIGN KEY (instrutor_id) REFERENCES instrutor (instrutor_id)
  ON DELETE SET NULL,
  CONSTRAINT turma_aluno FOREIGN KEY (aluno_id) REFERENCES aluno (aluno_id)
  ON DELETE SET NULL
);
ALTER SEQUENCE turma_turma_id_seq RESTART 1000000;

CREATE TABLE matricula 
(
  aluno_id INT NOT NULL,
  turma_id INT NOT NULL,
  status TEXT NOT NULL,
  CONSTRAINT matriculaIdentity PRIMARY KEY(aluno_id, turma_id)
);

CREATE TABLE telefone
(
    telefone_id SERIAL PRIMARY KEY,
    ddd TEXT,
    number TEXT,
    instrutor_id INT,
    CONSTRAINT telefone_instrutor FOREIGN KEY (instrutor_id) REFERENCES instrutor (instrutor_id)
    ON DELETE SET NULL
);
ALTER SEQUENCE telefone_telefone_id_seq RESTART 1000000;

CREATE TABLE titular
(
    titular_id SERIAL PRIMARY KEY,
    name TEXT NOT NULL
);
ALTER SEQUENCE titular_titular_id_seq RESTART 1000000;

CREATE TABLE dependente
(
    dependente_id SERIAL PRIMARY KEY,
    name TEXT NOT NULL,
    titular_id INT,
    CONSTRAINT dependente_titular FOREIGN KEY (titular_id) REFERENCES titular (titular_id)
    ON DELETE CASCADE
);
ALTER SEQUENCE dependente_dependente_id_seq RESTART 1000000;
