package space.nyuki.questionnaire.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import space.nyuki.questionnaire.pojo.MailInfo;
import space.nyuki.questionnaire.pojo.Member;
import space.nyuki.questionnaire.pojo.QuestionnaireEntity;

import java.util.Date;

@SpringBootTest
class MailSendServiceTest {
	@Autowired
	private MailSenderService mailSenderService;
	@Autowired
	private MongoTemplate mongoTemplate;

	@Test
	public void remove() {
		mongoTemplate.remove(Query.query(Criteria.where("port").is(25)), "sender");
	}

	@Test
	public void setMailInfoToDB() {
		MailInfo mailInfo = new MailInfo();
		mailInfo.setId("mailInfo");
		mailInfo.setHost("smtp.exmail.qq.com");
		mailInfo.setPort(25);
		mailInfo.setUsername("email");
		mailInfo.setPassword("email");
		mailInfo.setFrom("email");
		mailInfo.setProtocol("smtp");
		String content = "<html>\n" +
				"<body>\n" +
				"    <h3>hello world ! 这是一封Html邮件!${name}</h3>\n" +
				"    <h3>hello world ! 这是一封Html邮件!${title}</h3>\n" +
				"    <h3>hello world ! 中文》》》!${from}</h3>\n" +
				"    <h3>hello world ! 这是一封Html邮件!${to}</h3>\n" +
				"    <h3>hello world ! 这是一封Html邮件!${url}</h3>\n" +
				"</body>\n" +
				"</html>";
		mailInfo.setSubject("测试");
		mailInfo.setTemplate(content);
		mailSenderService.setMailInfo(mailInfo);
	}

	@Test
	void sendMail() {
		Member member = new Member();
		member.setId("sdfsdf");
		member.setName("ning");
		member.setEmail("email");
		QuestionnaireEntity questionnaireEntity = new QuestionnaireEntity();
		questionnaireEntity.setFrom(new Date());
		questionnaireEntity.setTitle("nihao");
		questionnaireEntity.setTo(new Date());
		questionnaireEntity.setId("adfafdafdadsf");
		mailSenderService.sendMail(member, questionnaireEntity);
	}
}