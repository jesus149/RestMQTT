package mx.bo.imp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.bo.IfzCatalogoBO;
import mx.dao.CatalogoDAO;
import mx.model.CatalogoDTO;

@Service
public class CatalogoBO implements IfzCatalogoBO {

	private static final long serialVersionUID = 1L;
	private static final Logger LOG = Logger.getLogger(CatalogoBO.class.getName());

	@Autowired
	public CatalogoDAO catalogoDAO;

	@Override
	public List<CatalogoDTO> catRol() {
		List<CatalogoDTO> list = null;
		Map<String, Object> map = null;
		map = new HashMap<String, Object>();
		try {
			list = catalogoDAO.catRol(map);
			//list = (List<CatalogoDTO>) map.get("V_CURSOR");
			return list;
		} catch (Exception e) {
			System.err.println(e.getMessage());
			return null;
		}
		
		
	}

}
