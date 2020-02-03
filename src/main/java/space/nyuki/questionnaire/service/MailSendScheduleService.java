package space.nyuki.questionnaire.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import space.nyuki.questionnaire.pojo.MailSchedule;
import space.nyuki.questionnaire.pojo.Member;
import space.nyuki.questionnaire.pojo.QuestionnaireEntity;

import java.util.Date;
import java.util.List;

@Service
public class MailSendScheduleService {
	@Autowired
	private MongoTemplate mongoTemplate;
	@Autowired
	private MailSenderService mailSenderService;

	@Transactional
	public void addSchedule(List<Member> memberList, List<String> memberGroupName, QuestionnaireEntity questionnaireEntity, Date sendTime) {
		MailSchedule mailSchedule = new MailSchedule();
		mailSchedule.setMembers(memberList);
		mailSchedule.setMemberGroupName(memberGroupName);
		mailSchedule.setQuestionnaireEntityId(questionnaireEntity.getId());
		mailSchedule.setQuestionnaireTitle(questionnaireEntity.getTitle());
		mailSchedule.setSendingTime(sendTime);
		mongoTemplate.save(mailSchedule);
	}

	public List<MailSchedule> getData() {
		return mongoTemplate.find(Query.query(Criteria.where("is_delete").is(0)), MailSchedule.class);
	}

	@Scheduled(cron = "0 * * * * *")
	public void process() {
		List<MailSchedule> mails = mongoTemplate.find(
				Query.query(Criteria.where("sending_time").lte(new Date()).and("is_delete").is(0)), MailSchedule.class);
		if (!mails.isEmpty()) {
			mails.forEach(mailSchedule -> {
				delete(mailSchedule.getId());
				mailSenderService.sendMail(mailSchedule.getMembers(),mailSchedule.getQuestionnaireEntityId());
			});
		}
	}

	@Transactional
	public void delete(String id) {
		Update update = new Update();
		update.set("is_delete", 1);
		mongoTemplate.findAndModify(Query.query(Criteria.where("_id").is(id)), update, MailSchedule.class);
	}
}
