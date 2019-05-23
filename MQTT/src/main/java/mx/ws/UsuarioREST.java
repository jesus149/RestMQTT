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

import mx.bo.IfzUsuarioBO;
import mx.model.UsuarioDTO;

@RestController
@RequestMapping(value = "/usuario", method = { RequestMethod.GET,
		RequestMethod.POST }, produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
public class UsuarioREST implements Serializable {

	private static final long serialVersionUID = 1L;
	private static final HttpHeaders HTTP_HEADERS = new HttpHeaders();

	@Autowired
	private IfzUsuarioBO usuarioBO;

	@Resource
	private WebServiceContext webServiceContext;

	private Map<String, Object> map = null;

	@PostConstruct
	public void config() {
		HTTP_HEADERS.setContentType(MediaType.APPLICATION_JSON_UTF8);
		HTTP_HEADERS.setAccessControlAllowOrigin("*");
	}

	@RequestMapping(value = "/listUsuarios", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<String> porcentajeRnme() {
		this.map = new HashMap<String, Object>();
		try {
			List<UsuarioDTO> listUsuarios = usuarioBO.listUsuarios();
			this.map.put("listUsuarios", listUsuarios);
		} catch (Exception e) {
			this.map.put("error", e);
		}

		return new ResponseEntity<String>(new GsonBuilder().create().toJson(this.map), HTTP_HEADERS, HttpStatus.OK);
	}

	@RequestMapping(value = "/insertUsuario/", method = { RequestMethod.POST,
			RequestMethod.GET }, produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public ResponseEntity<String> insertUsuario(HttpServletRequest request, HttpServletResponse response) {

		String correo = request.getParameter("correo");
		String pass = request.getParameter("pass");
		String idUsuarioFirebase = request.getParameter("idUsuarioFirebase");

		this.map = new HashMap<String, Object>();

		try {
			int msj = usuarioBO.insertUsuario(correo, pass, idUsuarioFirebase);
			this.map.put("output", msj);

		} catch (Exception e) {
			this.map.put("output", e);
			System.out.println(e);

		}
		return new ResponseEntity<String>(new GsonBuilder().create().toJson(this.map), HTTP_HEADERS, HttpStatus.OK);

	}

	@RequestMapping(value = "/insertUsuarioGet/", method = {
			RequestMethod.GET }, produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public ResponseEntity<String> insertUsuarioGet(@RequestParam("correo") String correo,
			@RequestParam("pass") String pass, @RequestParam("idUsuarioFirebase") String idUsuarioFirebase) {

		this.map = new HashMap<String, Object>();

		try {
			int msj = usuarioBO.insertUsuario(correo, pass, idUsuarioFirebase);
			this.map.put("output", msj);

		} catch (Exception e) {
			this.map.put("output", e);
			System.out.println(e);

		}
		return new ResponseEntity<String>(new GsonBuilder().create().toJson(this.map), HTTP_HEADERS, HttpStatus.OK);

	}

}
