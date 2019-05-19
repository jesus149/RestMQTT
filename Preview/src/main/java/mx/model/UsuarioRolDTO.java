package mx.model;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;
import org.springframework.stereotype.Component;

@Component
@Alias("UsuarioRolDTO")
public class UsuarioRolDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String usuario;
	private String curp;
	private int idRol;
	private String nombreRol;
	private String descrRol;
	
	public UsuarioRolDTO() {
		// TODO Auto-generated constructor stub
	}
	
	public String getCurp() {
		return curp;
	}
	
	public String getDescrRol() {
		return descrRol;
	}
	
	public String getNombreRol() {
		return nombreRol;
	}
	
	public String getUsuario() {
		return usuario;
	}
	
	public void setCurp(String curp) {
		this.curp = curp;
	}
	
	public void setDescrRol(String descrRol) {
		this.descrRol = descrRol;
	}
	
	public void setNombreRol(String nombreRol) {
		this.nombreRol = nombreRol;
	}
	
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	
	public int getIdRol() {
		return idRol;
	}
	
	public void setIdRol(int idRol) {
		this.idRol = idRol;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("UsuarioRolDTO [usuario=");
		builder.append(usuario);
		builder.append(", curp=");
		builder.append(curp);
		builder.append(", idRol=");
		builder.append(idRol);
		builder.append(", nombreRol=");
		builder.append(nombreRol);
		builder.append(", descrRol=");
		builder.append(descrRol);
		builder.append("]");
		return builder.toString();
	}
	
}
