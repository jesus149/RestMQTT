
USE s0f1s0ft_datacloud ;

-- -----------------------------------------------------
-- Table s0f1s0ft_datacloud.usuario
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS s0f1s0ft_datacloud.usuario (
  id_usuario INT NOT NULL AUTO_INCREMENT,
  correo VARCHAR(255) NULL,
  password VARCHAR(255) NULL,
  id_usuario_farebase VARCHAR(255) NULL,
  usuario_creo VARCHAR(255) NULL,
  fecha_creo DATETIME NULL,
  usuario_modifico VARCHAR(255) NULL,
  fecha_modifico DATETIME NULL,
  PRIMARY KEY (id_usuario))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table s0f1s0ft_datacloud.conexion
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS s0f1s0ft_datacloud.conexion (
  id_conexion INT NOT NULL AUTO_INCREMENT,
  nombreServicio VARCHAR(255) NULL,
  host VARCHAR(255) NULL,
  puerto INT NULL,
  `ssl` VARCHAR(255) NULL,
  usuario VARCHAR(255) NULL,
  password VARCHAR(255) NULL,
  client_id VARCHAR(255) NULL,
  id_usuario INT NOT NULL,
  usuario_creo VARCHAR(255) NULL,
  fecha_creo DATETIME NULL,
  usuario_modifico VARCHAR(255) NULL,
  fecha_modifico DATETIME NULL,
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
  topic VARCHAR(255) NULL,
  descripcion VARCHAR(255) NULL,
  elemento VARCHAR(255) NULL,
  imagen VARCHAR(255) NULL,
  id_topic_firebase VARCHAR(255) NULL,
  id_conexion INT NOT NULL,
  usuario_creo VARCHAR(255) NULL,
  fecha_creo DATETIME NULL,
  usuario_modifico VARCHAR(45) NULL,
  fecha_modifico DATETIME NULL,
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
  valor_recibido VARCHAR(255) NULL,
  fecha_hora_recibio DATETIME NULL,
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

-- -----------------------------------------------------
-- PROCEDURE s0f1s0ft_datacloud.SP_SELECT_USUARIO
-- -----------------------------------------------------

DROP PROCEDURE IF EXISTS s0f1s0ft_datacloud.SP_SELECT_USUARIO;

DELIMITER $$

CREATE PROCEDURE s0f1s0ft_datacloud.SP_SELECT_USUARIO ()
BEGIN
	
    SELECT ID_USUARIO, CORREO, PASSWORD, ID_USUARIO_FAREBASE
    FROM s0f1s0ft_datacloud.usuario;

END;

-- -----------------------------------------------------
-- PROCEDUREs s0f1s0ft_datacloud.SP_INSERT_USUARIO
-- -----------------------------------------------------

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

-- -----------------------------------------------------
-- PROCEDURE s0f1s0ft_datacloud.SP_SELECT_CONEXION
-- -----------------------------------------------------

DROP PROCEDURE IF EXISTS s0f1s0ft_datacloud.SP_SELECT_CONEXION;

DELIMITER $$

CREATE PROCEDURE s0f1s0ft_datacloud.SP_SELECT_CONEXION ()
BEGIN
	
   select a.id_conexion, a.nombreServicio, a.host, a.puerto, a.ssl, a.usuario, a.password, a.client_id, a.id_usuario,
	b.correo, b.password, b.id_usuario_farebase
	from s0f1s0ft_datacloud.conexion a
	inner join s0f1s0ft_datacloud.usuario b on a.id_usuario = b.id_usuario;

END;

-- -----------------------------------------------------
-- PROCEDURE s0f1s0ft_datacloud.SP_INSERT_CONEXION
-- -----------------------------------------------------

DROP PROCEDURE IF EXISTS s0f1s0ft_datacloud.SP_INSERT_CONEXION;

DELIMITER $$

CREATE PROCEDURE s0f1s0ft_datacloud.SP_INSERT_CONEXION (P_NOMBRESERVICO VARCHAR(250), 
	P_HOST VARCHAR(250),
	P_PUERTO INT,
    P_SSL VARCHAR(250),
    P_USUARIO VARCHAR(250),
    P_PASSWORD VARCHAR(250),
    P_CLIENTID VARCHAR(250),
    P_IDUSUARIOFIREBASE VARCHAR(250)
    )
BEGIN
	
	DECLARE exist INT;
    DECLARE idUsuario int;
    
    SELECT id_usuario INTO idUsuario
    FROM s0f1s0ft_datacloud.usuario
	WHERE ID_USUARIO_FAREBASE = P_IDUSUARIOFIREBASE;
    
    SELECT COUNT(id_conexion) INTO exist
    FROM s0f1s0ft_datacloud.conexion
	WHERE client_id = P_CLIENTID;
    
    IF exist > 0 THEN 
            
		UPDATE s0f1s0ft_datacloud.conexion
        SET nombreServicio = P_NOMBRESERVICO,
        host = P_HOST,
        puerto = P_PUERTO,
        `ssl` = P_SSL,
        usuario = P_USUARIO,
        password = P_PASSWORD,
        USUARIO_MODIFICO = USER(),
        FECHA_MODIFICO = NOW()
        WHERE client_id = P_CLIENTID;

    ELSE
		insert into s0f1s0ft_datacloud.conexion 
			(nombreServicio, host, puerto, `ssl`, usuario, password, client_id, id_usuario, usuario_creo, fecha_creo)
		values 
			(P_NOMBRESERVICO, P_HOST, P_PUERTO, P_SSL, P_USUARIO, P_PASSWORD, P_CLIENTID, idUsuario, USER(), NOW());
    END IF;

END;

-- -----------------------------------------------------
-- PROCEDURE s0f1s0ft_datacloud.SP_SELECT_TOPIC
-- -----------------------------------------------------

DROP PROCEDURE IF EXISTS s0f1s0ft_datacloud.SP_SELECT_TOPIC;

DELIMITER $$

CREATE PROCEDURE s0f1s0ft_datacloud.SP_SELECT_TOPIC ()
BEGIN

	select a.id_topic, a.topic, a.descripcion, a.elemento, a.imagen, a.id_topic_firebase, a.id_conexion, 
	b.nombreServicio, b.host, b.puerto, b.ssl, b.usuario, b.password, b.client_id
	from s0f1s0ft_datacloud.topics a
	inner join s0f1s0ft_datacloud.conexion b on a.id_conexion = b.id_conexion;

END;

-- -----------------------------------------------------
-- PROCEDURE s0f1s0ft_datacloud.SP_INSERT_TOPIC
-- -----------------------------------------------------

DROP PROCEDURE IF EXISTS s0f1s0ft_datacloud.SP_INSERT_TOPIC;

DELIMITER $$

CREATE PROCEDURE s0f1s0ft_datacloud.SP_INSERT_TOPIC (P_TOPIC VARCHAR(250), 
	P_DESCRIPCION VARCHAR(250),
	P_ELEMENTO VARCHAR(250),
    P_IMAGEN VARCHAR(250),
    P_IDTOPICFIREBASE VARCHAR(250),
    P_CLIENTID VARCHAR(250),
    P_IDUSUARIOFIREBASE VARCHAR(250)
    )
BEGIN
	
	DECLARE exist INT;
    DECLARE idConexion int;
    
    select id_conexion into idConexion
    from conexion
    where client_id = P_CLIENTID;
    
    select count(id_topic) into exist
    from topics
    where id_topic_firebase = P_IDTOPICFIREBASE;
    
    IF exist > 0 THEN 
            
		UPDATE s0f1s0ft_datacloud.topics
        SET topic = P_TOPIC,
        descripcion = P_DESCRIPCION,
        elemento = P_ELEMENTO,
        imagen = P_IMAGEN,
        USUARIO_MODIFICO = USER(),
        FECHA_MODIFICO = NOW()
        WHERE id_topic_firebase = P_IDTOPICFIREBASE;

    ELSE
		insert into s0f1s0ft_datacloud.topics
			(topic, descripcion, elemento, imagen, id_topic_firebase, id_conexion, usuario_creo, fecha_creo)
		values
			(P_TOPIC, P_DESCRIPCION, P_ELEMENTO, P_IMAGEN, P_IDTOPICFIREBASE, idConexion, USER(), NOW());
    END IF;

END;

-- -----------------------------------------------------
-- PROCEDURE s0f1s0ft_datacloud.SP_SELECT_USUARIOCONEXIONTOPIC
-- -----------------------------------------------------

DROP PROCEDURE IF EXISTS s0f1s0ft_datacloud.SP_SELECT_USUARIOCONEXIONTOPIC;

DELIMITER $$

CREATE PROCEDURE s0f1s0ft_datacloud.SP_SELECT_USUARIOCONEXIONTOPIC ()
BEGIN

	select a.id_usuario, b.correo, a.id_conexion, c.nombreServicio, a.id_topic, d.topic, a.valor_recibido, a.fecha_hora_recibio
	from s0f1s0ft_datacloud.usuario_conexion_topic a
	inner join s0f1s0ft_datacloud.usuario b on b.id_usuario = a.id_usuario
	inner join s0f1s0ft_datacloud.conexion c on c.id_conexion = a.id_conexion
	inner join s0f1s0ft_datacloud.topics d on d.id_topic = a.id_topic;

END;


-- -----------------------------------------------------
-- PROCEDURE s0f1s0ft_datacloud.SP_INSERT_USUARIOCONEXIONTOPIC
-- -----------------------------------------------------

DROP PROCEDURE IF EXISTS s0f1s0ft_datacloud.SP_INSERT_USUARIOCONEXIONTOPIC;

DELIMITER $$

CREATE PROCEDURE s0f1s0ft_datacloud.SP_INSERT_USUARIOCONEXIONTOPIC (P_IDUSUARIOFIREBASE VARCHAR(250),
    P_CLIENTID VARCHAR(250),
    P_IDTOPICFIREBASE VARCHAR(250),
    P_VALOR VARCHAR(250)
    )
BEGIN
	
    DECLARE idUsuario int;
    DECLARE idConexion int;
    DECLARE idTopic int;
    
    select id_usuario into idUsuario
    from usuario
    where id_usuario_farebase = P_IDUSUARIOFIREBASE;
    
    select id_conexion into idConexion
    from conexion
    where client_id = P_CLIENTID;
    
    select id_topic into idTopic
    from topics
    where id_topic_firebase = P_IDTOPICFIREBASE;

    insert into s0f1s0ft_datacloud.usuario_conexion_topic
		(id_usuario, id_conexion, id_topic, valor_recibido, fecha_hora_recibio)
	values
		(idUsuario,idConexion,idTopic,P_VALOR,now());

END;