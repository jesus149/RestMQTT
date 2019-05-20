package mx;

import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import mx.bo.IfzUsuarioConexionTopicBO;
import mx.model.UsuarioConexionTopicDTO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "applicationContext.xml")
public class UsuarioConexionTopicTest {
	
	@Autowired
	private IfzUsuarioConexionTopicBO usuarioConexionTopicBO;

	@Ignore
	public void listTopics() {
		List<UsuarioConexionTopicDTO> msg = usuarioConexionTopicBO.listUsuarioConexionTopic();
		assertNotNull(msg);
	}

	@Test
	public void insertTopics() {
		int msg = usuarioConexionTopicBO.insertUsuarioConexionTopic("3", "OsujYnGqLaRFDQLSLxmqrrC2Ikj1", "myClientId1557892600979", "Letcvd08iOxqw8DXGQu");
		assertNotNull(msg);
	}

}
