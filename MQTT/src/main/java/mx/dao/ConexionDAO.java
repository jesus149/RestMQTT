package mx.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import mx.model.ConexionDTO;

@Repository
public interface ConexionDAO extends Serializable {

	public List<ConexionDTO> listConexiones(Map<String, Object> map);

	public String insertConexion(Map<String, Object> map);

}
