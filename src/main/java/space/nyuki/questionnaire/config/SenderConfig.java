package space.nyuki.questionnaire.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class SenderConfig {
	@Bean
	public JavaMailSenderImpl javaMailSender() {
		return new JavaMailSenderImpl();
	}
}
