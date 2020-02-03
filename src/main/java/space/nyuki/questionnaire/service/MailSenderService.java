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
import space.nyuki.questionnaire.pojo.*;
import space.nyuki.questionnaire.utils.SenderTemplateUtil;

import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class MailSenderService {
	@Autowired
	private MongoTemplate mongoTemplate;
	@Autowired
	private JavaMailSenderImpl mailSender;
	@Autowired
	private BaseUrlService baseUrlService;
	@Autowired
	private QuestionnaireEntityService questionnaireEntityService;
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
		this.mailInfo = mailInfo;
		this.mailInfo.setId("mailInfo");
		mongoTemplate.save(mailInfo);
		mailSender.setUsername(mailInfo.getUsername());
		mailSender.setPassword(mailInfo.getPassword());
		mailSender.setProtocol(mailInfo.getProtocol());
		mailSender.setHost(mailInfo.getHost());
		mailSender.setPort(mailInfo.getPort());
	}


	public MailInfo getMailInfo() {
		return this.mailInfo;
	}

	public void sendMail(List<Member> members, QuestionnaireEntity questionnaireEntity) {
		BaseUrl baseUrl = baseUrlService.getBaseUrl();
		if (Objects.isNull(baseUrl)) {
			throw new URLNotSetException();
		}
		if (Objects.isNull(this.mailInfo)) {
			throw new MailNotSetException();
		}
		String mailTemplate = this.mailInfo.getTemplate();
		members.forEach(member -> {
			sendMail(member, questionnaireEntity, mailTemplate, baseUrl);
		});
	}

	public void sendMail(List<Member> members, String questionnaireEntityId) {
		BaseUrl baseUrl = baseUrlService.getBaseUrl();
		if (Objects.isNull(baseUrl)) {
			throw new URLNotSetException();
		}
		if (Objects.isNull(this.mailInfo)) {
			throw new MailNotSetException();
		}
		QuestionnaireEntity questionnaireEntity = questionnaireEntityService.getDataById(questionnaireEntityId);
		String mailTemplate = this.mailInfo.getTemplate();
		members.forEach(member -> {
			sendMail(member, questionnaireEntity, mailTemplate, baseUrl);
		});
	}


	@Transactional
	public void sendMail(Member member, QuestionnaireEntity questionnaireEntity, String mailTemplate, BaseUrl baseUrl) {
		MimeMessage message = mailSender.createMimeMessage();
		try {
			MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
			helper.setFrom(this.mailInfo.getFrom());
			helper.setTo(member.getEmail());
			helper.setSubject(this.mailInfo.getSubject());
			String content =
					SenderTemplateUtil.
							generateMailContent(member, questionnaireEntity, mailTemplate, baseUrl.getUrl());
			helper.setText(content, true);
			mailSender.send(message);
			MailLog mailLog = getSuccessMailLog(member, questionnaireEntity);
			mongoTemplate.save(mailLog);
		} catch (Exception e) {
			mongoTemplate.save(getFailedMailLog(member, questionnaireEntity, e));
		}
	}

	private MailLog getSuccessMailLog(Member member, QuestionnaireEntity questionnaireEntity) {
		MailLog mailLog = new MailLog();
		mailLog.setEmail(member.getEmail());
		mailLog.setStatus("success");
		mailLog.setName(member.getName());
		mailLog.setTitle(questionnaireEntity.getTitle());
		mailLog.setSendTime(new Date());
		mailLog.setQuestionnaireEntityId(questionnaireEntity.getId());
		return mailLog;
	}

	private MailLog getFailedMailLog(Member member, QuestionnaireEntity questionnaireEntity, Exception e) {
		MailLog mailLog = new MailLog();
		mailLog.setEmail(member.getEmail());
		mailLog.setStatus("failed");
		mailLog.setName(member.getName());
		mailLog.setSendTime(new Date());
		mailLog.setTitle(questionnaireEntity.getTitle());
		mailLog.setQuestionnaireEntityId(questionnaireEntity.getId());
		mailLog.setMessage(e.getMessage());
		return mailLog;
	}

	public List<MailLog> getLogData() {
		return mongoTemplate.findAll(MailLog.class);
	}

	public List<MailSchedule> getScheduleData() {
		return mongoTemplate.find(Query.query(Criteria.where("is_delete").is(0)), MailSchedule.class);
	}
}
