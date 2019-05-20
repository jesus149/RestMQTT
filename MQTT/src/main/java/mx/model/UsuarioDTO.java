package mx.model;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;

@Alias("UsuarioDTO")
public class UsuarioDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private int idUsuario;
	private String correo;
	private String password;
	private String idUsuarioFirebase;

	public UsuarioDTO() {
	}

	/**
	 * @param idUsuario
	 * @param correo
	 * @param password
	 * @param idUsuarioFirebase
	 */
	public UsuarioDTO(int idUsuario, String correo, String password, String idUsuarioFirebase) {
		super();
		this.idUsuario = idUsuario;
		this.correo = correo;
		this.password = password;
		this.idUsuarioFirebase = idUsuarioFirebase;
	}

	/**
	 * @return the idUsuario
	 */
	public int getIdUsuario() {
		return idUsuario;
	}

	/**
	 * @param idUsuario the idUsuario to set
	 */
	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}

	/**
	 * @return the correo
	 */
	public String getCorreo() {
		return correo;
	}

	/**
	 * @param correo the correo to set
	 */
	public void setCorreo(String correo) {
		this.correo = correo;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the idUsuarioFirebase
	 */
	public String getIdUsuarioFirebase() {
		return idUsuarioFirebase;
	}

	/**
	 * @param idUsuarioFirebase the idUsuarioFirebase to set
	 */
	public void setIdUsuarioFirebase(String idUsuarioFirebase) {
		this.idUsuarioFirebase = idUsuarioFirebase;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("UsuarioDTO [idUsuario=");
		builder.append(idUsuario);
		builder.append(", correo=");
		builder.append(correo);
		builder.append(", password=");
		builder.append(password);
		builder.append(", idUsuarioFirebase=");
		builder.append(idUsuarioFirebase);
		builder.append("]");
		return builder.toString();
	}
}
