package mx.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import mx.model.TopicDTO;

@Repository
public interface TopicDAO extends Serializable {

	public List<TopicDTO> listTopics(Map<String, Object> map);

	public String insertTopics(Map<String, Object> map);
	
	public List<TopicDTO> listCatTopics(Map<String, Object> map);

	public String insertCatTopics(Map<String, Object> map);


}
