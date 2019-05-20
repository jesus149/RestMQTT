package mx.model;

import java.io.Serializable;
import java.util.List;

import org.apache.ibatis.type.Alias;

@Alias("TopicDTO")
public class TopicDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private int idTopic;
	private String topic;
	private String descripcion;
	private String elemento;
	private String imagen;
	private String idTopicFirebase;
	private List<ConexionDTO> conexionDTO;

	public TopicDTO() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param idTopic
	 * @param topic
	 * @param descripcion
	 * @param elemento
	 * @param imagen
	 * @param idTopicFirebase
	 * @param conexionDTO
	 */
	public TopicDTO(int idTopic, String topic, String descripcion, String elemento, String imagen,
			String idTopicFirebase, List<ConexionDTO> conexionDTO) {
		super();
		this.idTopic = idTopic;
		this.topic = topic;
		this.descripcion = descripcion;
		this.elemento = elemento;
		this.imagen = imagen;
		this.idTopicFirebase = idTopicFirebase;
		this.conexionDTO = conexionDTO;
	}

	/**
	 * @return the idTopic
	 */
	public int getIdTopic() {
		return idTopic;
	}

	/**
	 * @param idTopic the idTopic to set
	 */
	public void setIdTopic(int idTopic) {
		this.idTopic = idTopic;
	}

	/**
	 * @return the topic
	 */
	public String getTopic() {
		return topic;
	}

	/**
	 * @param topic the topic to set
	 */
	public void setTopic(String topic) {
		this.topic = topic;
	}

	/**
	 * @return the descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * @param descripcion the descripcion to set
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	/**
	 * @return the elemento
	 */
	public String getElemento() {
		return elemento;
	}

	/**
	 * @param elemento the elemento to set
	 */
	public void setElemento(String elemento) {
		this.elemento = elemento;
	}

	/**
	 * @return the imagen
	 */
	public String getImagen() {
		return imagen;
	}

	/**
	 * @param imagen the imagen to set
	 */
	public void setImagen(String imagen) {
		this.imagen = imagen;
	}

	/**
	 * @return the idTopicFirebase
	 */
	public String getIdTopicFirebase() {
		return idTopicFirebase;
	}

	/**
	 * @param idTopicFirebase the idTopicFirebase to set
	 */
	public void setIdTopicFirebase(String idTopicFirebase) {
		this.idTopicFirebase = idTopicFirebase;
	}

	/**
	 * @return the conexionDTO
	 */
	public List<ConexionDTO> getConexionDTO() {
		return conexionDTO;
	}

	/**
	 * @param conexionDTO the conexionDTO to set
	 */
	public void setConexionDTO(List<ConexionDTO> conexionDTO) {
		this.conexionDTO = conexionDTO;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("TopicDTO [idTopic=");
		builder.append(idTopic);
		builder.append(", topic=");
		builder.append(topic);
		builder.append(", descripcion=");
		builder.append(descripcion);
		builder.append(", elemento=");
		builder.append(elemento);
		builder.append(", imagen=");
		builder.append(imagen);
		builder.append(", idTopicFirebase=");
		builder.append(idTopicFirebase);
		builder.append(", conexionDTO=");
		builder.append(conexionDTO);
		builder.append("]");
		return builder.toString();
	}
}
