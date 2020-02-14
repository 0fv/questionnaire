package space.nyuki.questionnaire.init;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import space.nyuki.questionnaire.service.QueueListenerService;

@Component
public class MQInit implements CommandLineRunner {
	@Autowired
	private QueueListenerService queueListenerService;

	@Override
	public void run(String... args) throws Exception {
		queueListenerService.init();
	}
}
