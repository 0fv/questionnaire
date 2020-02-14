package space.nyuki.questionnaire.init;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import space.nyuki.questionnaire.service.QuestionnaireEntityService;

@Component
public class RedisInit implements CommandLineRunner {
	@Autowired
	private QuestionnaireEntityService questionnaireEntityService;

	@Override
	public void run(String... args) throws Exception {
		questionnaireEntityService.initStoreToRedis();
	}
}
