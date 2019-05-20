package mx.model;

import java.io.Serializable;
import java.util.List;

import org.apache.ibatis.type.Alias;

@Alias("ConexionDTO")
public class ConexionDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private int idConexion;
	private String nombreServicio;
	private String host;
	private int puerto;
	private String ssl;
	private String usuario;
	private String password;
	private String clientId;
	private List<UsuarioDTO> usuarioDTO;

	public ConexionDTO() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param idConexion
	 * @param nombreServicio
	 * @param host
	 * @param puerto
	 * @param ssl
	 * @param usuario
	 * @param contraseña
	 * @param clientId
	 * @param usuarioDTO
	 */
	public ConexionDTO(int idConexion, String nombreServicio, String host, int puerto, String ssl, String usuario,
			String password, String clientId, List<UsuarioDTO> usuarioDTO) {
		super();
		this.idConexion = idConexion;
		this.nombreServicio = nombreServicio;
		this.host = host;
		this.puerto = puerto;
		this.ssl = ssl;
		this.usuario = usuario;
		this.password = password;
		this.clientId = clientId;
		this.usuarioDTO = usuarioDTO;
	}

	/**
	 * @return the idConexion
	 */
	public int getIdConexion() {
		return idConexion;
	}

	/**
	 * @param idConexion the idConexion to set
	 */
	public void setIdConexion(int idConexion) {
		this.idConexion = idConexion;
	}

	/**
	 * @return the nombreServicio
	 */
	public String getNombreServicio() {
		return nombreServicio;
	}

	/**
	 * @param nombreServicio the nombreServicio to set
	 */
	public void setNombreServicio(String nombreServicio) {
		this.nombreServicio = nombreServicio;
	}

	/**
	 * @return the host
	 */
	public String getHost() {
		return host;
	}

	/**
	 * @param host the host to set
	 */
	public void setHost(String host) {
		this.host = host;
	}

	/**
	 * @return the puerto
	 */
	public int getPuerto() {
		return puerto;
	}

	/**
	 * @param puerto the puerto to set
	 */
	public void setPuerto(int puerto) {
		this.puerto = puerto;
	}

	/**
	 * @return the ssl
	 */
	public String getSsl() {
		return ssl;
	}

	/**
	 * @param ssl the ssl to set
	 */
	public void setSsl(String ssl) {
		this.ssl = ssl;
	}

	/**
	 * @return the usuario
	 */
	public String getUsuario() {
		return usuario;
	}

	/**
	 * @param usuario the usuario to set
	 */
	public void setUsuario(String usuario) {
		this.usuario = usuario;
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
	 * @return the clientId
	 */
	public String getClientId() {
		return clientId;
	}

	/**
	 * @param clientId the clientId to set
	 */
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	/**
	 * @return the usuarioDTO
	 */
	public List<UsuarioDTO> getUsuarioDTO() {
		return usuarioDTO;
	}

	/**
	 * @param usuarioDTO the usuarioDTO to set
	 */
	public void setUsuarioDTO(List<UsuarioDTO> usuarioDTO) {
		this.usuarioDTO = usuarioDTO;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ConexionDTO [idConexion=");
		builder.append(idConexion);
		builder.append(", nombreServicio=");
		builder.append(nombreServicio);
		builder.append(", host=");
		builder.append(host);
		builder.append(", puerto=");
		builder.append(puerto);
		builder.append(", ssl=");
		builder.append(ssl);
		builder.append(", usuario=");
		builder.append(usuario);
		builder.append(", password=");
		builder.append(password);
		builder.append(", clientId=");
		builder.append(clientId);
		builder.append(", usuarioDTO=");
		builder.append(usuarioDTO);
		builder.append("]");
		return builder.toString();
	}

}
