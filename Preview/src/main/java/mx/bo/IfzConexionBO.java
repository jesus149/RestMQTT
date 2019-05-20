package mx.bo;

import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Component;

import mx.model.ConexionDTO;

@Component
public interface IfzConexionBO extends Serializable {

	public List<ConexionDTO> listConexiones();

	public int insertConexion(String nombreServicio, String host, int puerto, String ssl, String usuario,
			String password, String clientId, String idUsuarioFirebase);

}
