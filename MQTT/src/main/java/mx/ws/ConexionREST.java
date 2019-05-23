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

import mx.bo.IfzConexionBO;
import mx.model.ConexionDTO;

@RestController
@RequestMapping(value = "/conexion", method = { RequestMethod.GET,
		RequestMethod.POST }, produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
public class ConexionREST implements Serializable {

	private static final long serialVersionUID = 1L;
	private static final HttpHeaders HTTP_HEADERS = new HttpHeaders();

	@Autowired
	private IfzConexionBO conexionBO;

	@Resource
	private WebServiceContext webServiceContext;

	private Map<String, Object> map = null;

	@PostConstruct
	public void config() {
		HTTP_HEADERS.setContentType(MediaType.APPLICATION_JSON_UTF8);
		HTTP_HEADERS.setAccessControlAllowOrigin("*");
	}

	@RequestMapping(value = "/listConexiones", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<String> listConexiones() {
		this.map = new HashMap<String, Object>();
		try {
			List<ConexionDTO> listConexiones = conexionBO.listConexiones();
			this.map.put("listConexiones", listConexiones);
		} catch (Exception e) {
			this.map.put("error", e);
		}

		return new ResponseEntity<String>(new GsonBuilder().create().toJson(this.map), HTTP_HEADERS, HttpStatus.OK);
	}

	@RequestMapping(value = "/insertConexion/", method = { RequestMethod.POST,
			RequestMethod.GET }, produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public ResponseEntity<String> insertConexion(HttpServletRequest request, HttpServletResponse response) {

		String nombreServicio = request.getParameter("nombreServicio");
		String host = request.getParameter("host");
		int puerto = Integer.parseInt(request.getParameter("puerto"));
		String ssl = request.getParameter("ssl");
		String usuario = request.getParameter("usuario");
		String password = request.getParameter("password");
		String clientId = request.getParameter("clientId");
		String idUsuarioFirebase = request.getParameter("idUsuarioFirebase");

		this.map = new HashMap<String, Object>();

		try {
			int msj = conexionBO.insertConexion(nombreServicio, host, puerto, ssl, usuario, password, clientId,
					idUsuarioFirebase);
			this.map.put("output", msj);

		} catch (Exception e) {
			this.map.put("output", e);
			System.out.println(e);

		}
		return new ResponseEntity<String>(new GsonBuilder().create().toJson(this.map), HTTP_HEADERS, HttpStatus.OK);

	}

	@RequestMapping(value = "/insertConexionGet/", method = {
			RequestMethod.GET }, produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public ResponseEntity<String> insertConexionGet(@RequestParam("nombreServicio") String nombreServicio,
			@RequestParam("host") String host, @RequestParam("puerto") int puerto, @RequestParam("ssl") String ssl,
			@RequestParam("usuario") String usuario, @RequestParam("password") String password,
			@RequestParam("clientId") String clientId, @RequestParam("idUsuarioFirebase") String idUsuarioFirebase) {

		this.map = new HashMap<String, Object>();

		try {
			int msj = conexionBO.insertConexion(nombreServicio, host, puerto, ssl, usuario, password, clientId,
					idUsuarioFirebase);
			this.map.put("output", msj);

		} catch (Exception e) {
			this.map.put("output", e);
			System.out.println(e);

		}
		return new ResponseEntity<String>(new GsonBuilder().create().toJson(this.map), HTTP_HEADERS, HttpStatus.OK);

	}

}
