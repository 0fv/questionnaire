package space.nyuki.questionnaire.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import space.nyuki.questionnaire.exception.MailNotSetException;
import space.nyuki.questionnaire.exception.URLNotSetException;
import space.nyuki.questionnaire.pojo.BaseUrl;
import space.nyuki.questionnaire.pojo.MailInfo;
import space.nyuki.questionnaire.pojo.Member;
import space.nyuki.questionnaire.pojo.QuestionnaireEntity;
import space.nyuki.questionnaire.utils.SenderTemplateUtil;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Objects;

@Service
public class MailSenderService {
	@Autowired
	private MongoTemplate mongoTemplate;
	@Autowired
	private JavaMailSenderImpl mailSender;
	@Autowired
	private BaseUrlService baseUrlService;
	private MailInfo mailInfo;

	public void init() {
		MailInfo mailInfo = mongoTemplate.findOne(Query.query(Criteria.where("id").is("mailInfo")), MailInfo.class);
		if (Objects.nonNull(mailInfo)) {
			mailSender.setUsername(mailInfo.getUsername());
			mailSender.setPassword(mailInfo.getPassword());
			mailSender.setProtocol(mailInfo.getProtocol());
			mailSender.setHost(mailInfo.getHost());
			mailSender.setPort(mailInfo.getPort());
			mailSender.setDefaultEncoding("UTF-8");
			this.mailInfo = mailInfo;
		}
	}

	@Transactional
	public void setMailInfo(MailInfo mailInfo) {
		mongoTemplate.save(mailInfo);
		this.mailInfo = mailInfo;
		mailSender.setUsername(mailInfo.getUsername());
		mailSender.setPassword(mailInfo.getPassword());
		mailSender.setProtocol(mailInfo.getProtocol());
		mailSender.setHost(mailInfo.getHost());
		mailSender.setPort(mailInfo.getPort());
	}


	public MailInfo getMailInfo() {
		return this.mailInfo;
	}

	public void sendMail(Member member, QuestionnaireEntity questionnaireEntity) {
		BaseUrl baseUrl = baseUrlService.getBaseUrl();
		if (Objects.isNull(baseUrl)) {
			throw new URLNotSetException();
		}
		if (Objects.isNull(this.mailInfo)) {
			throw new MailNotSetException();
		}
		String mailTemplate = this.mailInfo.getTemplate();
		MimeMessage message = mailSender.createMimeMessage();
		try {
			MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
			helper.setFrom(this.mailInfo.getFrom());
			helper.setTo(member.getEmail());
			helper.setSubject(this.mailInfo.getSubject());
			String content =
					SenderTemplateUtil.generateMailContent(member, questionnaireEntity, mailTemplate, baseUrl.getUrl());
			helper.setText(content, true);
			mailSender.send(message);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
}
