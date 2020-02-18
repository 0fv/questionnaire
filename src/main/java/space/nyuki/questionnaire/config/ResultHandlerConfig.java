package space.nyuki.questionnaire.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import space.nyuki.questionnaire.handler.ChoiceResultHandler;
import space.nyuki.questionnaire.handler.CommentResultHandler;
import space.nyuki.questionnaire.handler.DateResultHandler;
import space.nyuki.questionnaire.handler.ResultStringHandler;

@Configuration
public class ResultHandlerConfig {
	@Bean
	public ResultStringHandler resultStringHandler() {
		ChoiceResultHandler choiceResultHandler = new ChoiceResultHandler();
		CommentResultHandler commentResultHandler = new CommentResultHandler();
		DateResultHandler dateResultHandler = new DateResultHandler();
		choiceResultHandler.setNextHandler(commentResultHandler);
		commentResultHandler.setNextHandler(dateResultHandler);
		return choiceResultHandler;
	}
}
