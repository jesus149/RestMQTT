package mx;

import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import mx.bo.IfzTopicBO;
import mx.model.TopicDTO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "applicationContext.xml")
public class TopicTest {

	@Autowired
	private IfzTopicBO topicBO;

	@Ignore
	public void listTopics() {
		List<TopicDTO> msg = topicBO.listTopics();
		assertNotNull(msg);
	}

	@Ignore
	public void insertTopics() {
		int msg = topicBO.insertTopics("testJava", "testJava", "testJava", "testJava", "Letcvd08iOxqw8DXGQu",
				"myClientId1557892600979", "OsujYnGqLaRFDQLSLxmqrrC2Ikj1");
		assertNotNull(msg);
	}
	
	@Test
	public void insertCatTopics() {
		int msg = topicBO.insertCatTopics("testJava", "OsujYnGqLaRFDQLSLxmqrrC2Ikj1");
		assertNotNull(msg);
	}

}
