package mx.bo.imp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.bo.IfzUsuarioBO;
import mx.dao.UsuarioDAO;
import mx.model.UsuarioDTO;

@Service
public class UsuarioBO implements IfzUsuarioBO {

	private static final long serialVersionUID = 1L;
	private static final Logger LOG = Logger.getLogger(UsuarioBO.class.getName());

	@Autowired
	public UsuarioDAO usuarioDAO;

	@Override
	public List<UsuarioDTO> listUsuarios() {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			return usuarioDAO.listUsuarios(map);
		} catch (Exception e) {
			System.err.println(e.getMessage());
			LOG.error(e);
			return null;
		}

	}

	@Override
	public int insertUsuario(String correo, String pass, String idUsuarioFirebase) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {

			map.put("P_CORREO", (correo != null && !correo.isEmpty()) ? correo : "");
			map.put("P_PASS", (pass != null && !pass.isEmpty()) ? pass : "");
			map.put("P_ID_USUARIO_FIREBASE", (idUsuarioFirebase != null && !idUsuarioFirebase.isEmpty()) ? idUsuarioFirebase : ""); 	

			usuarioDAO.insertUsuario(map);
			
			return 1;
			
		} catch (Exception e) {
			LOG.error(e);
			return 0;
		}
	}

}
