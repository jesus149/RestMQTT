package mx.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import mx.model.UsuarioConexionTopicDTO;

@Repository
public interface UsuarioConexionTopicDAO extends Serializable {

	public List<UsuarioConexionTopicDTO> listUsuarioConexionTopic(Map<String, Object> map);
	
	public String insertUsuarioConexionTopic(Map<String, Object> map);

}
