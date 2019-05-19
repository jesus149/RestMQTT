package mx.model;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;
import org.springframework.stereotype.Component;

@Component
@Alias("AccionDTO")
public class AccionDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private int idAccion;
	private String accion;
 
	public AccionDTO() {
		// TODO Auto-generated constructor stub
	}

	public AccionDTO(int idAccion, String accion) {
		super();
		this.idAccion = idAccion;
		this.accion = accion;
	}

	public int getIdAccion() {
		return idAccion;
	}

	public void setIdAccion(int idAccion) {
		this.idAccion = idAccion;
	}

	public String getAccion() {
		return accion;
	}

	public void setAccion(String accion) {
		this.accion = accion;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("AccionDTO [_id=");
		builder.append(idAccion);
		builder.append(", accion=");
		builder.append(accion);
		builder.append("]");
		return builder.toString();
	}

}
