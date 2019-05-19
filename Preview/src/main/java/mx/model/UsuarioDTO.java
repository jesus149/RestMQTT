package mx.model;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;

@Alias("UsuarioDTO")
public class UsuarioDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private int idUsuario;
	private String usuario;
	private String curp;
	private String correo;
	private String telefono;
	private String cel;
	private String nombre;
	private String primerAp;
	private String segundoAp;
	private int idEntidad;
	private int idRol;
	private String rol;

	public UsuarioDTO() {
	}

	public UsuarioDTO(int idUsuario, String usuario, String curp, String correo, String telefono, String cel,
			String nombre, String primerAp, String segundoAp, int idEntidad, int idRol, String rol) {
		super();
		this.idUsuario = idUsuario;
		this.usuario = usuario;
		this.curp = curp;
		this.correo = correo;
		this.telefono = telefono;
		this.cel = cel;
		this.nombre = nombre;
		this.primerAp = primerAp;
		this.segundoAp = segundoAp;
		this.idEntidad = idEntidad;
		this.idRol = idRol;
		this.rol = rol;
	}

	public int getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getCurp() {
		return curp;
	}

	public void setCurp(String curp) {
		this.curp = curp;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getCel() {
		return cel;
	}

	public void setCel(String cel) {
		this.cel = cel;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getPrimerAp() {
		return primerAp;
	}

	public void setPrimerAp(String primerAp) {
		this.primerAp = primerAp;
	}

	public String getSegundoAp() {
		return segundoAp;
	}

	public void setSegundoAp(String segundoAp) {
		this.segundoAp = segundoAp;
	}

	public int getIdEntidad() {
		return idEntidad;
	}

	public void setIdEntidad(int idEntidad) {
		this.idEntidad = idEntidad;
	}

	public int getIdRol() {
		return idRol;
	}

	public void setIdRol(int idRol) {
		this.idRol = idRol;
	}

	public String getRol() {
		return rol;
	}

	public void setRol(String rol) {
		this.rol = rol;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("UsuarioDTO [idUsuario=");
		builder.append(idUsuario);
		builder.append(", usuario=");
		builder.append(usuario);
		builder.append(", curp=");
		builder.append(curp);
		builder.append(", correo=");
		builder.append(correo);
		builder.append(", telefono=");
		builder.append(telefono);
		builder.append(", cel=");
		builder.append(cel);
		builder.append(", nombre=");
		builder.append(nombre);
		builder.append(", primerAp=");
		builder.append(primerAp);
		builder.append(", segundoAp=");
		builder.append(segundoAp);
		builder.append(", idEntidad=");
		builder.append(idEntidad);
		builder.append(", idRol=");
		builder.append(idRol);
		builder.append(", rol=");
		builder.append(rol);
		builder.append("]");
		return builder.toString();
	}
}
