package mx;

import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import mx.bo.IfzConexionBO;
import mx.model.ConexionDTO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "applicationContext.xml")
public class ConexionTest {
	
	@Autowired
	private IfzConexionBO conexionBO;

	@Test
	public void listConexiones() {
		List<ConexionDTO> msg = conexionBO.listConexiones();
		assertNotNull(msg);
	}
	
	@Ignore
	public void insertConexion() {
		int msg = conexionBO.insertConexion("testJava", "testJava", 1, "testJava", "testJava", "testJava", "testJava", "OsujYnGqLaRFDQLSLxmqrrC2Ikj1");
		assertNotNull(msg);
	}

}
