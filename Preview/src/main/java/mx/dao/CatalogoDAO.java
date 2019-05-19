package mx.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import mx.model.CatalogoDTO;

@Repository
public interface CatalogoDAO extends Serializable {

	public List<CatalogoDTO> catRol(Map<String, Object> map);

}
