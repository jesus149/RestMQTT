package mx;

import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import mx.bo.IfzUsuarioBO;
import mx.model.UsuarioDTO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "applicationContext.xml")
public class UsuarioTest {

	@Autowired
	private IfzUsuarioBO usuarioBO;

	@Ignore
	public void listUsuarios() {
		List<UsuarioDTO> msg = usuarioBO.listUsuarios();
		assertNotNull(msg);
	}
	
	@Test
	public void insertUsuario() {
		int msg = usuarioBO.insertUsuario("javatest", "javaTest", "javaTest");
		assertNotNull(msg);
	}

}
