package mx.model;

import org.apache.ibatis.type.Alias;

import mx.model.GenericoDTO;

@Alias("CatalogoDTO")
public class CatalogoDTO extends GenericoDTO {
	
	private static final long serialVersionUID = 1L;
	private int nivel;
	private int idPadre;
	
	public CatalogoDTO() {
	}

	public CatalogoDTO(int nivel, int idPadre, int id, String nombre, String descripcion, double valor) {
		super();
		this.nivel = nivel;
		this.idPadre = idPadre;
		this.setId(id);
		this.setNombre(nombre);
		this.setDescripcion(descripcion);
		this.setValor(valor);
	}

	public int getNivel() {
		return nivel;
	}

	public void setNivel(int nivel) {
		this.nivel = nivel;
	}

	public int getIdPadre() {
		return idPadre;
	}

	public void setIdPadre(int idPadre) {
		this.idPadre = idPadre;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CatalogoDTO [id=");
		builder.append(getId());
		builder.append(", nombre=");
		builder.append(getNombre());
		builder.append(", descripcion=");
		builder.append(getDescripcion());
		builder.append(", valor=");
		builder.append(getValor());
		builder.append(", nivel=");
		builder.append(nivel);
		builder.append(", idPadre=");
		builder.append(idPadre);
		builder.append("]");
		return builder.toString();
	}

}
