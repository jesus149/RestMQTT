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
		List<UsuarioDTO> list = null;
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			list = usuarioDAO.listUsuarios(map);
			// list = (List<CatalogoDTO>) map.get("V_CURSOR");
			return list;
		} catch (Exception e) {
			System.err.println(e.getMessage());
			LOG.error(e);
			return null;
		}

	}

}
