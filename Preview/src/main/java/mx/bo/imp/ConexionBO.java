package mx.bo.imp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.bo.IfzConexionBO;
import mx.dao.ConexionDAO;
import mx.model.ConexionDTO;

@Service
public class ConexionBO implements IfzConexionBO {

	private static final long serialVersionUID = 1L;
	private static final Logger LOG = Logger.getLogger(UsuarioBO.class.getName());

	@Autowired
	public ConexionDAO conexionDAO;

	@Override
	public List<ConexionDTO> listConexiones() {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			return conexionDAO.listConexiones(map);
		} catch (Exception e) {
			System.err.println(e.getMessage());
			LOG.error(e);
			return null;
		}
	}

	@Override
	public int insertConexion(String nombreServicio, String host, int puerto, String ssl, String usuario,
			String password, String clientId, String idUsuarioFirebase) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {

			map.put("P_NOMBRESERVICO", (nombreServicio != null && !nombreServicio.isEmpty()) ? nombreServicio : "");
			map.put("P_HOST", (host != null && !host.isEmpty()) ? host : "");
			map.put("P_PUERTO", (puerto > 0 ? puerto : 0));
			map.put("P_SSL", (ssl != null && !ssl.isEmpty()) ? ssl : "");
			map.put("P_USUARIO", (usuario != null && !usuario.isEmpty()) ? usuario : "");
			map.put("P_PASSWORD", (password != null && !password.isEmpty()) ? password : "");
			map.put("P_CLIENTID", (clientId != null && !clientId.isEmpty()) ? clientId : "");
			map.put("P_IDUSUARIOFIREBASE", (idUsuarioFirebase != null && !idUsuarioFirebase.isEmpty()) ? idUsuarioFirebase : "");

			conexionDAO.insertConexion(map);
			
			return 1;
			
		} catch (Exception e) {
			LOG.error(e);
			return 0;
		}
	}

}
