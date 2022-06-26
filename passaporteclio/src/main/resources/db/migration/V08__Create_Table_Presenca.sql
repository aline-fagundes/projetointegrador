CREATE TABLE tb_presenca (
  id_presenca int NOT NULL AUTO_INCREMENT,
  data datetime NOT NULL,
  fk_id_pessoa_presenca int NOT NULL,
  fk_id_museu_presenca int NOT NULL,
  PRIMARY KEY (id_presenca),
  KEY fk_id_usuario_presenca (fk_id_pessoa_presenca),
  KEY fk_id_museu_presenca (fk_id_museu_presenca),
  CONSTRAINT fk_id_museu_presenca FOREIGN KEY (fk_id_museu_presenca) REFERENCES tb_museus (id_museu),
  CONSTRAINT fk_id_pessoa_presenca FOREIGN KEY (fk_id_pessoa_presenca) REFERENCES tb_pessoas (id_pessoa)
)