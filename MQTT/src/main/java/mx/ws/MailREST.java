package mx.ws;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.xml.ws.WebServiceContext;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.GsonBuilder;

import mx.config.Utils;

@RestController
@RequestMapping(value = "/mail", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class MailREST extends Utils implements Serializable {

	private static final long serialVersionUID = 1L;
	private static final HttpHeaders HTTP_HEADERS = new HttpHeaders();

	@Resource
	private WebServiceContext webServiceContext;

	private Map<String, Object> map = null;

	@PostConstruct
	public void config() {
		HTTP_HEADERS.setContentType(MediaType.APPLICATION_JSON_UTF8);
		HTTP_HEADERS.setAccessControlAllowOrigin("*");
	}

	@RequestMapping(value = "/contacto/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<String> moduloMailTutor(@RequestParam("nombre") String nombre,
			@RequestParam("correo") String correo, @RequestParam("asunto") String asunto,
			@RequestParam("mensaje") String mensaje) {

		this.map = new HashMap<String, Object>();

		String remitente = "info@sofisoft.com.mx";

		String body = "<body style='background: #ccc'><div style='background:white; margin: 20px 10%; padding: 60px; font-family: arial'><p style='font-size: 20px;line-height: 25px; text-align: justify'><strong>Buen dia "
				+ nombre
				+ "</strong></p><br><p style='font-size: 18px;line-height: 25px; text-align: justify'>Su mensaje ha sido recibido, uno de nuestros agentes se pondra en contacto con usted.</p><p style='font-size: 18px;line-height: 25px; text-align: justify'>Sin otro particular, saludos.</p><br><p>Servicios Especializados en SOA, BPM, CMS, WEB 2.0</p><p>Copyright &copy; SofiSoft 2019. Derechos Reservados</p></div></body>";

		System.out.println("asunto: " + asunto);

		String body2 = "<body style='background: #ccc'><div style='background:white; margin: 20px 10%; padding: 60px; font-family: arial'><p style='font-size: 20px;line-height: 25px; text-align: justify'><strong>Buen dia</strong></p><br><p style='font-size: 18px;line-height: 25px; text-align: justify'>Nuevo mensaje ha sido recibido</p><br><p>Nombre: "
				+ nombre + "</p><p>Correo: " + correo + "</p><p>Asunto: " + asunto + "</p><p>Mensaje: " + mensaje
				+ "</p><br></div></body>";

		try {
			boolean msj = new Utils().enviarMail(remitente, correo, null, null, asunto, body, null);
			boolean msj2 = new Utils().enviarMail(remitente, remitente, null, null, asunto, body2, null);
			System.out.println(msj);
			this.map.put("cliente", msj);
			this.map.put("sofisoft", msj2);

		} catch (Exception e) {
			this.map.put("Enviado", false);
			System.out.println("Error: ");
			System.out.println(e);

		}

		return new ResponseEntity<String>(new GsonBuilder().create().toJson(this.map), HTTP_HEADERS, HttpStatus.OK);

	}

}
