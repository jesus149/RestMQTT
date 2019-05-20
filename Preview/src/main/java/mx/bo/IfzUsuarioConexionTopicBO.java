package mx.bo;

import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Component;

import mx.model.UsuarioConexionTopicDTO;

@Component
public interface IfzUsuarioConexionTopicBO extends Serializable {

		public List<UsuarioConexionTopicDTO> listUsuarioConexionTopic();

		public int insertUsuarioConexionTopic(String valor, String idUsuarioFirebase, String clientId, String idTopicFirebase);

}
