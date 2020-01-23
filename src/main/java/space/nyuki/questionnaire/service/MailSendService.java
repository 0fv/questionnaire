package space.nyuki.questionnaire.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import space.nyuki.questionnaire.exception.BaseUrlNotSetException;
import space.nyuki.questionnaire.exception.MailNotSetException;
import space.nyuki.questionnaire.exception.TemplateNotFoundException;
import space.nyuki.questionnaire.pojo.*;
import space.nyuki.questionnaire.utils.SenderTemplateUtil;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Objects;

@Service
public class MailSendService {
	@Autowired
	private MongoTemplate mongoTemplate;
	@Autowired
	private JavaMailSenderImpl mailSender;
	private String baseUrl;
	private MailTemplate mailTemplate;
	private MailInfo mailInfo;

	public String setMailInfo() {
		if (Objects.isNull(mailInfo)) {
			MailInfo mailInfo = mongoTemplate.findOne(Query.query(Criteria.where("id").is("mailInfo")), MailInfo.class);
			if (Objects.isNull(mailInfo)) {
				throw new MailNotSetException();
			}
			this.mailInfo = mailInfo;
		}
		mailSender.setUsername(mailInfo.getUsername());
		mailSender.setPassword(mailInfo.getPassword());
		mailSender.setProtocol(mailInfo.getProtocol());
		mailSender.setHost(mailInfo.getHost());
		mailSender.setPort(mailInfo.getPort());
		mailSender.setDefaultEncoding("UTF-8");
		return mailInfo.getFrom();
	}

	@Transactional
	public void setMailInfoToDB(MailInfo mailInfo) {
		mongoTemplate.save(mailInfo);
		this.mailInfo = mailInfo;
	}

	@Transactional
	public void setMailTemplate(MailTemplate mailTemplate) {
		mongoTemplate.save(mailTemplate);
		this.mailTemplate = mailTemplate;
	}

	@Transactional
	public void setBaseUrl(BaseUrl baseUrl) {
		mongoTemplate.save(baseUrl);
		this.baseUrl = baseUrl.getUrl();
	}

	public String getBaseUrl() {
		if (Objects.isNull(this.baseUrl)) {
			BaseUrl b = mongoTemplate.findOne
					(Query.query(Criteria.where("_id").is("url")), BaseUrl.class);
			if (Objects.isNull(b)) {
				throw new BaseUrlNotSetException();
			}
			this.baseUrl = b.getUrl();
		}
		return this.baseUrl;

	}

	public String getMailTemplate() {
		if (Objects.isNull(this.mailTemplate)) {
			MailTemplate mailTemplate = mongoTemplate.findOne
					(Query.query(Criteria.where("_id").is("mailTemplate")), MailTemplate.class);
			if (Objects.isNull(mailTemplate)) {
				throw new TemplateNotFoundException();
			}
			this.mailTemplate = mailTemplate;
		}
		return mailTemplate.getTemplate();
	}

	public void sendMail(Member member, QuestionnaireEntity questionnaireEntity) {
		MimeMessage message = mailSender.createMimeMessage();
		try {
			MimeMessageHelper helper = new MimeMessageHelper(message, true,"UTF-8");
			String from = setMailInfo();
			String mailTemplate = getMailTemplate();
			String baseUrl = getBaseUrl();
			helper.setFrom(from);
			helper.setTo(member.getEmail());
			helper.setSubject("调查问卷邀请");
			String content =
					SenderTemplateUtil.generateMailContent(member, questionnaireEntity, mailTemplate,baseUrl);
			helper.setText(content, true);
			mailSender.send(message);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
}
