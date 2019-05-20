package mx.config;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.ParseException;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class Utils implements Serializable {
	private static final long serialVersionUID = 1L;
	private static final Logger LOG = Logger.getLogger(Utils.class.getName());

	/**
	 * 
	 * @param cadena
	 *            Es la cadena a encriptar
	 * @param algoritmo
	 *            Soporta algoritmos como MD2, MD5,SHA-1, SHA-224, SHA-256, SHA-384,
	 *            SHA-512 enviados en tipo cadena
	 * @return Cadena de texto de tipo String ya encriptada
	 * @throws NoSuchAlgorithmException
	 * @throws Exception
	 */
	public static String encrypt(String cadena, String algoritmo) throws NoSuchAlgorithmException {
		MessageDigest messageDigest = MessageDigest.getInstance(algoritmo);
		messageDigest.update(cadena.getBytes());

		byte byteData[] = messageDigest.digest();
		StringBuilder stringBuilder = new StringBuilder();

		for (int i = 0; i < byteData.length; i++) {
			stringBuilder.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
		}
		return stringBuilder.toString();
	}

	/**
	 * Carga un Properties
	 * 
	 * @param filePaht
	 *            Ubicacion de archivo
	 * @return Properties
	 */
	public final Properties loadPropoerties(String filePaht) {
		Properties properties = null;
		InputStream inputStream = null;

		try {
			inputStream = this.getClass().getClassLoader().getResourceAsStream(filePaht);
			// inputStream = new FileInputStream(filePaht);
			if (inputStream != null) {
				properties = new Properties();
				properties.load(inputStream);
			}
		} catch (FileNotFoundException e) {
			LOG.error(e);
		} catch (IOException e) {
			LOG.error(e);
		} catch (Exception e) {
			LOG.error(e);
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (Exception e) {
					LOG.error(e);
				}
			}
		}

		return properties;
	}

	/**
	 * Envia correo, para enviar a multiple correos separar por ,
	 * 
	 * @param remitente
	 * @param to
	 * @param cc
	 * @param bcc
	 * @param asunto
	 * @param mensaje
	 * @return
	 */
	public boolean enviarMail(String remitente, String to, String cc, String bcc, String asunto, String mensaje,
			File file) {

		Properties properties = loadPropoerties(Parametros.CONFIG_PROPERTIES);

		Session session = Session.getDefaultInstance(properties, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(properties.getProperty("mail.user"),
						properties.getProperty("mail.password"));
			}
		});

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(remitente));

			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));

			if (cc != null)
				message.setRecipients(Message.RecipientType.CC, InternetAddress.parse(cc));
			if (bcc != null)
				message.setRecipients(Message.RecipientType.BCC, InternetAddress.parse(bcc));

			message.setSubject(asunto);

			// This mail has 2 part, the BODY and the embedded image
			MimeMultipart multipart = new MimeMultipart("related");
			// first part (the html)
			BodyPart messageBodyPart = new MimeBodyPart();
			messageBodyPart.setContent(mensaje, "text/html");
			// add it
			multipart.addBodyPart(messageBodyPart);

			if (file != null) {
				// second part (the image)
				messageBodyPart = new MimeBodyPart();
				DataSource fds = new FileDataSource(file);
				messageBodyPart.setDataHandler(new DataHandler(fds));
				messageBodyPart.setHeader("Content-ID", "<image>");

				// add image to the multipart
				multipart.addBodyPart(messageBodyPart);
			}

			// put everything together
			message.setContent(multipart);

			Transport.send(message);

			LOG.info("Mail enviado");

			return true;

		} catch (AddressException e) {
			LOG.error(e);
			System.out.println(e);
			return false;
		} catch (ParseException e) {
			LOG.error(e);
			System.out.println(e);
			return false;
		} catch (MessagingException e) {
			LOG.error(e);
			System.out.println(e);
			return false;
		} catch (Exception e) {
			LOG.error(e);
			System.out.println(e);
			return false;
		}

	}

	@SuppressWarnings("unused")
	private static String base64Encode(String cadena) {
		byte[] encodedBytes = Base64.encodeBase64(cadena.getBytes());
		return new String(encodedBytes, Charset.forName("UTF-8"));
	}

	private static String base64Decode(String cadena) {
		byte[] decodedBytes = Base64.decodeBase64(cadena.getBytes());
		return new String(decodedBytes, Charset.forName("UTF-8"));
	}
	
	public static String DesencriptarMD5(String textoEncriptado) throws Exception {
		 
        String secretKey = textoEncriptado; //llave para encriptar datos
        String base64EncryptedString = "";
 
        try {
            byte[] message = Base64.decodeBase64(textoEncriptado.getBytes("utf-8"));
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digestOfPassword = md.digest(secretKey.getBytes("utf-8"));
            byte[] keyBytes = Arrays.copyOf(digestOfPassword, 24);
            SecretKey key = new SecretKeySpec(keyBytes, "DESede");
 
            Cipher decipher = Cipher.getInstance("DESede");
            decipher.init(Cipher.DECRYPT_MODE, key);
 
            byte[] plainText = decipher.doFinal(message);
 
            base64EncryptedString = new String(plainText, "UTF-8");
 
        } catch (Exception ex) {
        	System.err.println(ex);
        }
        return base64EncryptedString;
    }

	public static void main(String[] args) throws Exception {
		//System.out.println(new Utils().enviarMail("aleon@kionetworks.com", "jrojasm@kionetworks.com", null, null,"Comida", "Te invito una comida donde quieras, cuando quieras, tu pon fecha, espero tu respuesta Saludos.",null));
		// 6b86b273ff34fce19d6b804eff5a3f5747ada4eaa22f1d49c01e52ddb7875b4b
		// System.out.println(generaToken("ROMJ931110HMCJRS00"));
		// System.out.println(validaToken("Uk9NSjkzMTExMDEwLzE2"));
		
		System.out.println(base64Decode("TUFDTDg3MDgwM01ER1JTRDAx"));
		
		/*String remitente = "autonomiacurricular.fasecero@nube.sep.gob.mx";
		String asunto = "Activación de cuenta";
		String nombre = "Jesus";
		String primerAp = "Rojas";
		String segundoAp = "Moreno";
		String curp = "ROMJ931110HMCJRS00";
		String correo = "jrojasm@kionetworks.com";
		String mensaje = "<body style=\"background: #ccc\"><div style=\"background:white; margin: 20px 10%; padding: 60px; font-family: arial\"><h4>Buen dia "
				+ nombre + " " + primerAp + " " + segundoAp
				+ "</h4><p style=\"font-size:18px;line-height: 25px; text-align: justify\"><br><br>¡Tu registro has sido exitoso! para ingresar al portal de autonomia curricular accede con las siguientes credenciales:</p><style>label{font-weight: 700;}td{width: 50%;padding: 5px;border: 0px;}tr:nth-child(even)>td{background-color: #eee;}td>p{text-align: right;}table {width: 60%;margin: auto;}</style><table><tbody><tr><td><label>Usuario</label></td><td><p>"
				+ correo + " </p></td></tr><tr><td><label>Contraseña</label></td><td><p>" + curp
				+ "</p></td></tr></tbody></table><p> Y luego entrar al siguiente link: </p><br><h4 style=\"text-align:center\"><a href=\"http://localhost:4200/activavion.html?c=\">Activación</a></h4><br><br><p>Sin más por el momento reciba un cordial saludo por parte del equipo de autonomia curricular</p></p></div></body>";
		System.out.println(new Utils().enviarMail(remitente, correo, null, null, asunto, mensaje, null));*/
		
	}
}
