package mx.ws;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.WebServiceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.GsonBuilder;

import mx.bo.IfzUsuarioConexionTopicBO;
import mx.model.UsuarioConexionTopicDTO;

@RestController
@RequestMapping(value = "/usuarioConexionTopic", method = { RequestMethod.GET,
		RequestMethod.POST }, produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
public class UsuarioConexionTopicREST implements Serializable {

	private static final long serialVersionUID = 1L;
	private static final HttpHeaders HTTP_HEADERS = new HttpHeaders();

	@Autowired
	private IfzUsuarioConexionTopicBO usuarioConexionTopicBO;

	@Resource
	private WebServiceContext webServiceContext;

	private Map<String, Object> map = null;

	@PostConstruct
	public void config() {
		HTTP_HEADERS.setContentType(MediaType.APPLICATION_JSON_UTF8);
		HTTP_HEADERS.setAccessControlAllowOrigin("*");
	}

	@RequestMapping(value = "/listUsuarioConexionTopic", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<String> listUsuarioConexionTopic() {
		this.map = new HashMap<String, Object>();
		try {
			List<UsuarioConexionTopicDTO> listUsuarioConexionTopic = usuarioConexionTopicBO.listUsuarioConexionTopic();
			this.map.put("listUsuarioConexionTopic", listUsuarioConexionTopic);
		} catch (Exception e) {
			this.map.put("error", e);
		}

		return new ResponseEntity<String>(new GsonBuilder().create().toJson(this.map), HTTP_HEADERS, HttpStatus.OK);
	}

	@RequestMapping(value = "/insertUsuarioConexionTopic/", method = { RequestMethod.POST,
			RequestMethod.GET }, produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public ResponseEntity<String> insertUsuarioConexionTopic(HttpServletRequest request, HttpServletResponse response) {

		String valor = request.getParameter("valor");
		String idUsuarioFirebase = request.getParameter("idUsuarioFirebase");
		String clientId = request.getParameter("clientId");
		String idTopicFirebase = request.getParameter("idTopicFirebase");

		this.map = new HashMap<String, Object>();

		try {
			int msj = usuarioConexionTopicBO.insertUsuarioConexionTopic(valor, idUsuarioFirebase, clientId,
					idTopicFirebase);
			this.map.put("output", msj);

		} catch (Exception e) {
			this.map.put("output", e);
			System.out.println(e);

		}
		return new ResponseEntity<String>(new GsonBuilder().create().toJson(this.map), HTTP_HEADERS, HttpStatus.OK);

	}

	@RequestMapping(value = "/insertUsuarioConexionTopicGet/", method = {
			RequestMethod.GET }, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<String> insertUsuarioConexionTopicGet(@RequestParam("valor") String valor,
			@RequestParam("idUsuarioFirebase") String idUsuarioFirebase, @RequestParam("clientId") String clientId,
			@RequestParam("idTopicFirebase") String idTopicFirebase) {

		this.map = new HashMap<String, Object>();

		try {
			int msj = usuarioConexionTopicBO.insertUsuarioConexionTopic(valor, idUsuarioFirebase, clientId,
					idTopicFirebase);
			this.map.put("output", msj);

		} catch (Exception e) {
			this.map.put("output", e);
			System.out.println(e);

		}
		return new ResponseEntity<String>(new GsonBuilder().create().toJson(this.map), HTTP_HEADERS, HttpStatus.OK);

	}

}
