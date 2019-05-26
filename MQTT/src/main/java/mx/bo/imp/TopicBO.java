package mx.bo.imp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.bo.IfzTopicBO;
import mx.dao.TopicDAO;
import mx.model.TopicDTO;

@Service
public class TopicBO implements IfzTopicBO {
	
	private static final long serialVersionUID = 1L;
	private static final Logger LOG = Logger.getLogger(UsuarioBO.class.getName());

	@Autowired
	public TopicDAO topicDAO;

	@Override
	public List<TopicDTO> listTopics() {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			return topicDAO.listTopics(map);
		} catch (Exception e) {
			System.err.println(e.getMessage());
			LOG.error(e);
			return null;
		}
	}

	@Override
	public int insertTopics(String topic, String descripcion, String elemento, String imagen, String idTopicFirebase,
			String clientid, String idUsuarioFirebase) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {

			map.put("P_TOPIC", (topic != null && !topic.isEmpty()) ? topic : "");
			map.put("P_DESCRIPCION", (descripcion != null && !descripcion.isEmpty()) ? descripcion : "");
			map.put("P_ELEMENTO", (elemento != null && !elemento.isEmpty()) ? elemento : "");
			map.put("P_IMAGEN", (imagen != null && !imagen.isEmpty()) ? imagen : "");
			map.put("P_IDTOPICFIREBASE", (idTopicFirebase != null && !idTopicFirebase.isEmpty()) ? idTopicFirebase : "");
			map.put("P_CLIENTID", (clientid != null && !clientid.isEmpty()) ? clientid : "");
			map.put("P_IDUSUARIOFIREBASE", (idUsuarioFirebase != null && !idUsuarioFirebase.isEmpty()) ? idUsuarioFirebase : "");

			topicDAO.insertTopics(map);
			
			return 1;
			
		} catch (Exception e) {
			LOG.error(e);
			return 0;
		}
	}

	@Override
	public List<TopicDTO> listCatTopics() {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			return topicDAO.listCatTopics(map);
		} catch (Exception e) {
			System.err.println(e.getMessage());
			LOG.error(e);
			return null;
		}
	}

	@Override
	public int insertCatTopics(String nombre, String idUsuarioFirebase) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {

			map.put("P_NOMRE", (nombre != null && !nombre.isEmpty()) ? nombre : "");
			map.put("P_IDUSUARIOFIREBASE", (idUsuarioFirebase != null && !idUsuarioFirebase.isEmpty()) ? idUsuarioFirebase : "");

			topicDAO.insertCatTopics(map);
			
			return 1;
			
		} catch (Exception e) {
			LOG.error(e);
			return 0;
		}
	}

}
