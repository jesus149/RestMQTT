package mx.bo;

import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Component;

import mx.model.CatalogoDTO;

@Component
public interface IfzCatalogoBO  extends Serializable {
	
	public List<CatalogoDTO> catRol();
	
}
