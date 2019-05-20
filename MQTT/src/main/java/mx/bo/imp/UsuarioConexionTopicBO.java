package mx.bo.imp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.bo.IfzUsuarioConexionTopicBO;
import mx.dao.UsuarioConexionTopicDAO;
import mx.model.UsuarioConexionTopicDTO;

@Service
public class UsuarioConexionTopicBO implements IfzUsuarioConexionTopicBO {
	
	private static final long serialVersionUID = 1L;
	private static final Logger LOG = Logger.getLogger(UsuarioBO.class.getName());
	
	@Autowired
	public UsuarioConexionTopicDAO UsuarioConexionTopicDTO;

	@Override
	public List<UsuarioConexionTopicDTO> listUsuarioConexionTopic() {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			return UsuarioConexionTopicDTO.listUsuarioConexionTopic(map);
		} catch (Exception e) {
			System.err.println(e.getMessage());
			LOG.error(e);
			return null;
		}
	}

	@Override
	public int insertUsuarioConexionTopic(String valor, String idUsuarioFirebase, String clientId,
			String idTopicFirebase) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {

			map.put("P_IDUSUARIOFIREBASE", (idUsuarioFirebase != null && !idUsuarioFirebase.isEmpty()) ? idUsuarioFirebase : "");
			map.put("P_CLIENTID", (clientId != null && !clientId.isEmpty()) ? clientId : "");
			map.put("P_IDTOPICFIREBASE", (idTopicFirebase != null && !idTopicFirebase.isEmpty()) ? idTopicFirebase : "");
			map.put("P_VALOR", (valor != null && !valor.isEmpty()) ? valor : "");

			UsuarioConexionTopicDTO.insertUsuarioConexionTopic(map);
			
			return 1;
			
		} catch (Exception e) {
			LOG.error(e);
			return 0;
		}
	}

}
