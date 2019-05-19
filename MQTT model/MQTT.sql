
USE s0f1s0ft_datacloud ;

-- -----------------------------------------------------
-- Table s0f1s0ft_datacloud.usuario
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS s0f1s0ft_datacloud.usuario (
  id_usuario INT NOT NULL AUTO_INCREMENT,
  correo VARCHAR(45) NULL,
  password VARCHAR(45) NULL,
  id_usuario_farebase VARCHAR(45) NULL,
  usuario_creo VARCHAR(45) NULL,
  fecha_creo DATETIME NULL,
  usuario_modifico VARCHAR(45) NULL,
  fecha_modifico DATETIME NULL,
  PRIMARY KEY (id_usuario))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table s0f1s0ft_datacloud.conexion
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS s0f1s0ft_datacloud.conexion (
  id_conexion INT NOT NULL AUTO_INCREMENT,
  nombreServicio VARCHAR(45) NULL,
  host VARCHAR(45) NULL,
  puerto VARCHAR(45) NULL,
  usuario VARCHAR(45) NULL,
  contraseña VARCHAR(45) NULL,
  client_id VARCHAR(45) NULL,
  id_usuario INT NOT NULL,
  usuario_creo VARCHAR(45) NULL,
  fecha_creo DATETIME NULL,
  usuario_modifico VARCHAR(45) NULL,
  fecha_modifico VARCHAR(45) NULL,
  PRIMARY KEY (id_conexion),
  INDEX fk_conexiones_usuarios_idx (id_usuario ASC),
  CONSTRAINT fk_conexiones_usuarios
    FOREIGN KEY (id_usuario)
    REFERENCES s0f1s0ft_datacloud.usuario (id_usuario)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table s0f1s0ft_datacloud.topics
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS s0f1s0ft_datacloud.topics (
  id_topic INT NOT NULL AUTO_INCREMENT,
  topic VARCHAR(45) NULL,
  descripcion VARCHAR(45) NULL,
  elemento VARCHAR(45) NULL,
  imagen VARCHAR(45) NULL,
  id_conexion INT NOT NULL,
  usuario_creo VARCHAR(45) NULL,
  fecha_creo VARCHAR(45) NULL,
  usuario_modifico VARCHAR(45) NULL,
  fecha_modifico VARCHAR(45) NULL,
  PRIMARY KEY (id_topic),
  INDEX fk_topics_conexiones1_idx (id_conexion ASC),
  CONSTRAINT fk_topics_conexiones1
    FOREIGN KEY (id_conexion)
    REFERENCES s0f1s0ft_datacloud.conexion (id_conexion)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table s0f1s0ft_datacloud.usuario_conexion_topic
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS s0f1s0ft_datacloud.usuario_conexion_topic (
  id_usuario INT NOT NULL,
  id_conexion INT NOT NULL,
  id_topic INT NOT NULL,
  valor_recibido VARCHAR(45) NULL,
  fecha_hora_recibio VARCHAR(45) NULL,
  INDEX fk_usuarioConexionTopic_usuarios1_idx (id_usuario ASC) ,
  INDEX fk_usuarioConexionTopic_conexiones1_idx (id_conexion ASC) ,
  INDEX fk_usuarioConexionTopic_topics1_idx (id_topic ASC) ,
  CONSTRAINT fk_usuarioConexionTopic_usuarios1
    FOREIGN KEY (id_usuario)
    REFERENCES s0f1s0ft_datacloud.usuario (id_usuario)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT fk_usuarioConexionTopic_conexiones1
    FOREIGN KEY (id_conexion)
    REFERENCES s0f1s0ft_datacloud.conexion (id_conexion)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT fk_usuarioConexionTopic_topics1
    FOREIGN KEY (id_topic)
    REFERENCES s0f1s0ft_datacloud.topics (id_topic)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

DROP PROCEDURE IF EXISTS s0f1s0ft_datacloud.SP_INSERT_USUARIO;

DELIMITER $$

CREATE PROCEDURE s0f1s0ft_datacloud.SP_INSERT_USUARIO (P_CORREO VARCHAR(250), 
	P_PASS VARCHAR(250),
    P_ID_USUARIO_FIREBASE VARCHAR(250))
BEGIN
	
	DECLARE exist INT;
    
    SELECT COUNT(ID_USUARIO) INTO exist
    FROM s0f1s0ft_datacloud.usuario
	WHERE ID_USUARIO_FAREBASE = P_ID_USUARIO_FIREBASE;
    
    IF exist > 0 THEN 
            
		UPDATE s0f1s0ft_datacloud.usuario
        SET CORREO = P_CORREO,
        PASSWORD = P_PASS,
        ID_USUARIO_FAREBASE = P_ID_USUARIO_FIREBASE,
        USUARIO_MODIFICO = USER(),
        FECHA_MODIFICO = NOW()
        WHERE ID_USUARIO_FAREBASE = P_ID_USUARIO_FIREBASE;

    ELSE
		INSERT INTO s0f1s0ft_datacloud.usuario 
			(CORREO, PASSWORD, ID_USUARIO_FAREBASE, USUARIO_CREO, FECHA_CREO, USUARIO_MODIFICO, FECHA_MODIFICO)
		VALUES 
			(P_CORREO, P_PASS, P_ID_USUARIO_FIREBASE, USER(), NOW(), NULL, NULL);
    END IF;

END;