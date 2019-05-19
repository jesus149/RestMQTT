package mx.model;

import java.io.Serializable;
import java.util.List;

import org.apache.ibatis.type.Alias;
import org.springframework.stereotype.Component;


@Alias("CtDTO")
public class CtDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	private String ct;
	private String nombre;
	private int idCt;
	private int idPersona;
	private String curp;
	private int status;
	private int idTurno;
	private String nombreTurno;
	private List<UsuarioRolDTO> usuarioRolDTOs;
	private ModuloDTO moduloDTO;
	private int idNivel;
	private String nombreNivel;

	public CtDTO() {
	}

	public String getCt() {
		return ct;
	}

	public void setCt(String ct) {
		this.ct = ct;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getIdCt() {
		return idCt;
	}

	public int getIdPersona() {
		return idPersona;
	}

	public String getCurp() {
		return curp;
	}

	public void setIdCt(int idCt) {
		this.idCt = idCt;
	}

	public void setIdPersona(int idPersona) {
		this.idPersona = idPersona;
	}

	public void setCurp(String curp) {
		this.curp = curp;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public List<UsuarioRolDTO> getUsuarioRolDTOs() {
		return usuarioRolDTOs;
	}

	public void setUsuarioRolDTOs(List<UsuarioRolDTO> usuarioRolDTOs) {
		this.usuarioRolDTOs = usuarioRolDTOs;
	}

	public int getIdTurno() {
		return idTurno;
	}

	public void setIdTurno(int idTurno) {
		this.idTurno = idTurno;
	}
	
	

	public int getIdNivel() {
		return idNivel;
	}

	public void setIdNivel(int idNivel) {
		this.idNivel = idNivel;
	}

	public String getNombreNivel() {
		return nombreNivel;
	}

	public void setNombreNivel(String nombreNivel) {
		this.nombreNivel = nombreNivel;
	}
	
	public String getNombreTurno() {
		return nombreTurno;
	}
	
	public void setNombreTurno(String nombreTurno) {
		this.nombreTurno = nombreTurno;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CtDTO [ct=");
		builder.append(ct);
		builder.append(", nombre=");
		builder.append(nombre);
		builder.append(", turnoDTO=");
		builder.append(", idCt=");
		builder.append(getIdCt());
		builder.append(", idPersona=");
		builder.append(getIdPersona());
		builder.append(", curp=");
		builder.append(getCurp());
		builder.append(", status=");
		builder.append(getStatus());
		builder.append(", usuarioRolDTOs=");
		builder.append(usuarioRolDTOs);
		builder.append(", idTurno=");
		builder.append(idTurno);
		builder.append(", nombreTurno=");
		builder.append(nombreTurno);
		builder.append(", moduloDTO=");
		builder.append(moduloDTO);
		builder.append(", idNivel=");
		builder.append(idNivel);
		builder.append(", nombreNivel=");
		builder.append(nombreNivel);
		builder.append("]");
		return builder.toString();
	}

}
