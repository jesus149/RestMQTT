package mx.bo;

import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Component;

import mx.model.UsuarioDTO;

@Component
public interface IfzUsuarioBO extends Serializable {
	
	public List<UsuarioDTO> listUsuarios();
	
	public int insertUsuario(String correo, String pass, String idUsuarioFirebase);
	
}
