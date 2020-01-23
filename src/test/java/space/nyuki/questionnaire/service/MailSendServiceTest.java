package space.nyuki.questionnaire.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import space.nyuki.questionnaire.pojo.*;

import java.util.Date;

@SpringBootTest
class MailSendServiceTest {
	@Autowired
	private MailSendService mailSendService;
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
		mailInfo.setHost("ffffffff");
		mailInfo.setPort(25);
		mailInfo.setUsername("ffffffffff");
		mailInfo.setPassword("fffffffff");
		mailInfo.setFrom("ffffffffff");
		mailInfo.setProtocol("smtp");
		mailSendService.setMailInfoToDB(mailInfo);
	}
	@Test
	public void setBaseUrl(){
		BaseUrl baseUrl = new BaseUrl();
		baseUrl.setId("url");
		baseUrl.setUrl("http://www.nindgsss.com");
		mailSendService.setBaseUrl(baseUrl);
	}


	@Test
	void setMailTemplate() {
		String content="<html>\n" +
				"<body>\n" +
				"    <h3>hello world ! 这是一封Html邮件!${name}</h3>\n" +
				"    <h3>hello world ! 这是一封Html邮件!${title}</h3>\n" +
				"    <h3>hello world ! 中文》》》!${from}</h3>\n" +
				"    <h3>hello world ! 这是一封Html邮件!${to}</h3>\n" +
				"    <h3>hello world ! 这是一封Html邮件!${url}</h3>\n" +
				"</body>\n" +
				"</html>";
		MailTemplate mailTemplate = new MailTemplate();
		mailTemplate.setId("mailTemplate");
		mailTemplate.setTemplate(content);
		mailSendService.setMailTemplate(mailTemplate);
	}

	@Test
	void sendMail() {
		Member member = new Member();
		member.setEigenvalue("sdfadfasdfaff");
		member.setName("ning");
		member.setEmail("adsfasdfasdf");
		QuestionnaireEntity questionnaireEntity = new QuestionnaireEntity();
		questionnaireEntity.setFrom(new Date());
		questionnaireEntity.setTitle("nihao");
		questionnaireEntity.setTo(new Date());
		questionnaireEntity.setId("adfafdafdadsf");
		mailSendService.sendMail(member,questionnaireEntity);
	}
}