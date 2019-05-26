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

import mx.bo.IfzTopicBO;
import mx.model.TopicDTO;

@RestController
@RequestMapping(value = "/topic", method = { RequestMethod.GET,
		RequestMethod.POST }, produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
public class TopicRest implements Serializable {

	private static final long serialVersionUID = 1L;
	private static final HttpHeaders HTTP_HEADERS = new HttpHeaders();

	@Autowired
	private IfzTopicBO topicBO;

	@Resource
	private WebServiceContext webServiceContext;

	private Map<String, Object> map = null;

	@PostConstruct
	public void config() {
		HTTP_HEADERS.setContentType(MediaType.APPLICATION_JSON_UTF8);
		HTTP_HEADERS.setAccessControlAllowOrigin("*");
	}

	@RequestMapping(value = "/listTopics", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<String> listTopics() {
		this.map = new HashMap<String, Object>();
		try {
			List<TopicDTO> listTopics = topicBO.listTopics();
			this.map.put("listTopics", listTopics);
		} catch (Exception e) {
			this.map.put("error", e);
		}

		return new ResponseEntity<String>(new GsonBuilder().create().toJson(this.map), HTTP_HEADERS, HttpStatus.OK);
	}

	@RequestMapping(value = "/insertTopics/", method = { RequestMethod.POST,
			RequestMethod.GET }, produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public ResponseEntity<String> insertTopics(HttpServletRequest request, HttpServletResponse response) {

		String topic = request.getParameter("topic");
		String descripcion = request.getParameter("descripcion");
		String elemento = request.getParameter("elemento");
		String imagen = request.getParameter("imagen");
		String idTopicFirebase = request.getParameter("idTopicFirebase");
		String clientId = request.getParameter("clientId");
		String idUsuarioFirebase = request.getParameter("idUsuarioFirebase");

		this.map = new HashMap<String, Object>();

		try {
			int msj = topicBO.insertTopics(topic, descripcion, elemento, imagen, idTopicFirebase, clientId,
					idUsuarioFirebase);
			this.map.put("output", msj);

		} catch (Exception e) {
			this.map.put("output", e);
			System.out.println(e);

		}
		return new ResponseEntity<String>(new GsonBuilder().create().toJson(this.map), HTTP_HEADERS, HttpStatus.OK);

	}

	@RequestMapping(value = "/insertTopicsGet/", method = {
			RequestMethod.GET }, produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public ResponseEntity<String> insertTopicsGet(@RequestParam("topic") String topic,
			@RequestParam("descripcion") String descripcion, @RequestParam("elemento") String elemento,
			@RequestParam("imagen") String imagen, @RequestParam("idTopicFirebase") String idTopicFirebase,
			@RequestParam("clientId") String clientId, @RequestParam("idUsuarioFirebase") String idUsuarioFirebase) {

		this.map = new HashMap<String, Object>();

		try {
			int msj = topicBO.insertTopics(topic, descripcion, elemento, imagen, idTopicFirebase, clientId,
					idUsuarioFirebase);
			this.map.put("output", msj);

		} catch (Exception e) {
			this.map.put("output", e);
			System.out.println(e);

		}
		return new ResponseEntity<String>(new GsonBuilder().create().toJson(this.map), HTTP_HEADERS, HttpStatus.OK);

	}

	@RequestMapping(value = "/listCatTopics", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<String> listCatTopics() {
		this.map = new HashMap<String, Object>();
		try {
			List<TopicDTO> listCatTopics = topicBO.listCatTopics();
			this.map.put("listCatTopics", listCatTopics);
		} catch (Exception e) {
			this.map.put("error", e);
		}

		return new ResponseEntity<String>(new GsonBuilder().create().toJson(this.map), HTTP_HEADERS, HttpStatus.OK);
	}

	@RequestMapping(value = "/insertCatTopics/", method = {
			RequestMethod.GET }, produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public ResponseEntity<String> insertCatTopics(@RequestParam("nombre") String nombre,
			@RequestParam("idUsuarioFirebase") String idUsuarioFirebase) {

		this.map = new HashMap<String, Object>();

		try {
			int msj = topicBO.insertCatTopics(nombre, idUsuarioFirebase);
			this.map.put("output", msj);

		} catch (Exception e) {
			this.map.put("output", e);
			System.out.println(e);

		}
		return new ResponseEntity<String>(new GsonBuilder().create().toJson(this.map), HTTP_HEADERS, HttpStatus.OK);

	}

}
