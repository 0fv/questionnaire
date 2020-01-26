package space.nyuki.questionnaire.init;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import space.nyuki.questionnaire.service.BaseUrlService;
import space.nyuki.questionnaire.service.MailSenderService;

@Component
public class SenderInit implements CommandLineRunner {
	@Autowired
	private MailSenderService mailSenderService;
	@Autowired
	private BaseUrlService baseUrlService;

	@Override
	public void run(String... args) throws Exception {
		mailSenderService.init();
		baseUrlService.init();
	}
}
