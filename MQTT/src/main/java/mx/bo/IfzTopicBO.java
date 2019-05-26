package mx.bo;

import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Component;

import mx.model.TopicDTO;

@Component
public interface IfzTopicBO extends Serializable {

	public List<TopicDTO> listTopics();

	public int insertTopics(String topic, String descripcion, String elemento, String imagen, String idTopicFirebase,
			String clientid, String idUsuarioFirebase);
	
	public List<TopicDTO> listCatTopics();

	public int insertCatTopics(String nombre, String idUsuarioFirebase);

}
