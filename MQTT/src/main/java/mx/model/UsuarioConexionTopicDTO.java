package mx.model;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;

@Alias("usuarioConexionTopicDTO")
public class UsuarioConexionTopicDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String valor;
	private String fechaHoraRecibio;
	private UsuarioDTO usuarioDTO;
	private ConexionDTO conexionDTO;
	private TopicDTO topicDTO;
	
	public UsuarioConexionTopicDTO() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * @param valor
	 * @param fechaHoraRecibio
	 * @param usuarioDTO
	 * @param conexionDTO
	 * @param topicDTO
	 */
	public UsuarioConexionTopicDTO(String valor, String fechaHoraRecibio, UsuarioDTO usuarioDTO,
			ConexionDTO conexionDTO, TopicDTO topicDTO) {
		super();
		this.valor = valor;
		this.fechaHoraRecibio = fechaHoraRecibio;
		this.usuarioDTO = usuarioDTO;
		this.conexionDTO = conexionDTO;
		this.topicDTO = topicDTO;
	}

	/**
	 * @return the valor
	 */
	public String getValor() {
		return valor;
	}

	/**
	 * @param valor the valor to set
	 */
	public void setValor(String valor) {
		this.valor = valor;
	}

	/**
	 * @return the fechaHoraRecibio
	 */
	public String getFechaHoraRecibio() {
		return fechaHoraRecibio;
	}

	/**
	 * @param fechaHoraRecibio the fechaHoraRecibio to set
	 */
	public void setFechaHoraRecibio(String fechaHoraRecibio) {
		this.fechaHoraRecibio = fechaHoraRecibio;
	}

	/**
	 * @return the usuarioDTO
	 */
	public UsuarioDTO getUsuarioDTO() {
		return usuarioDTO;
	}

	/**
	 * @param usuarioDTO the usuarioDTO to set
	 */
	public void setUsuarioDTO(UsuarioDTO usuarioDTO) {
		this.usuarioDTO = usuarioDTO;
	}

	/**
	 * @return the conexionDTO
	 */
	public ConexionDTO getConexionDTO() {
		return conexionDTO;
	}

	/**
	 * @param conexionDTO the conexionDTO to set
	 */
	public void setConexionDTO(ConexionDTO conexionDTO) {
		this.conexionDTO = conexionDTO;
	}

	/**
	 * @return the topicDTO
	 */
	public TopicDTO getTopicDTO() {
		return topicDTO;
	}

	/**
	 * @param topicDTO the topicDTO to set
	 */
	public void setTopicDTO(TopicDTO topicDTO) {
		this.topicDTO = topicDTO;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("usuarioConexionTopicDTO [valor=");
		builder.append(valor);
		builder.append(", fechaHoraRecibio=");
		builder.append(fechaHoraRecibio);
		builder.append(", usuarioDTO=");
		builder.append(usuarioDTO);
		builder.append(", usuarioDTO=");
		builder.append(usuarioDTO);
		builder.append(", conexionDTO=");
		builder.append(conexionDTO);
		builder.append(", topicDTO=");
		builder.append(topicDTO);
		builder.append("]");
		return builder.toString();
	}
	
}
