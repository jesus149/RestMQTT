package mx.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import mx.model.UsuarioDTO;

@Repository
public interface UsuarioDAO extends Serializable {

	public List<UsuarioDTO> listUsuarios(Map<String, Object> map);
	
	public String insertUsuario(Map<String, Object> map);

}
