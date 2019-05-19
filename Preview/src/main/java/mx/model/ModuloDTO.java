package mx.model;

import java.io.Serializable;
import java.util.List;

import org.apache.ibatis.type.Alias;
import org.springframework.stereotype.Component;

@Component
@Alias("ModuloDTO")
public class ModuloDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private int idModulo;
	private String modulo;
	private List<AccionDTO> accionDTO;

	public ModuloDTO() {
		// TODO Auto-generated constructor stub
	}

	public ModuloDTO(List<AccionDTO> accionDTO, int idModulo, String modulo) {
		super();
		this.accionDTO = accionDTO;
		this.idModulo = idModulo;
		this.modulo = modulo;
	}

	public int getIdModulo() {
		return idModulo;
	}

	public void setIdModulo(int idModulo) {
		this.idModulo = idModulo;
	}

	public String getModulo() {
		return modulo;
	}

	public void setModulo(String modulo) {
		this.modulo = modulo;
	}

	public List<AccionDTO> getAccionDTO() {
		return accionDTO;
	}

	public void setAccionDTO(List<AccionDTO> accionDTO) {
		this.accionDTO = accionDTO;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ModuloDTO [_id=");
		builder.append(idModulo);
		builder.append(", modulo=");
		builder.append(modulo);
		builder.append(", accionDTO=");
		builder.append(accionDTO);
		builder.append("]");
		return builder.toString();
	}

}
