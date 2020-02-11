package space.nyuki.questionnaire.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import space.nyuki.questionnaire.exception.AccessDeniedException;
import space.nyuki.questionnaire.exception.ElementNotFoundException;
import space.nyuki.questionnaire.exception.QuestionnaireNotFoundException;
import space.nyuki.questionnaire.pojo.Member;
import space.nyuki.questionnaire.pojo.QuestionnaireEntity;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class QuestionnaireEntityService {
	@Autowired
	private MongoTemplate mongoTemplate;

	public List<QuestionnaireEntity> getData(int isFinish) {
		return mongoTemplate.
				find(Query.query(Criteria.where("is_delete").is(0).and("is_finish").is(isFinish))
						, QuestionnaireEntity.class);
	}

	public QuestionnaireEntity getDataById(String id) {
		return mongoTemplate.findOne(Query.query(Criteria.where("_id").is(id)), QuestionnaireEntity.class);
	}

	@Transactional
	public void deleteData(String id) {
		Update update = new Update();
		update.set("is_delete", 1);
		mongoTemplate.findAndModify(Query.query(Criteria.where("_id").is(id)), update, QuestionnaireEntity.class);
	}

	@Transactional
	public void updateFinish(String id) {
		Update update = new Update();
		update.set("is_finish", 1);
		mongoTemplate.findAndModify(Query.query(Criteria.where("_id").is(id)), update, QuestionnaireEntity.class);
	}

	@Transactional
	public void updateDate(QuestionnaireEntity questionnaireEntity) {
		Update update = new Update();
		update.set("is_finish", 0);
		update.set("to", questionnaireEntity.getTo());
		mongoTemplate.findAndModify(
				Query.query(Criteria.where("_id").is(questionnaireEntity.getId())), update, QuestionnaireEntity.class);
	}

	@Scheduled(cron = "0 * * * * *")
	public void moveToFinish() {
		List<QuestionnaireEntity> data =
				mongoTemplate.find(Query.query(Criteria.where("is_finish").is(0).and("to").lte(new Date())),
						QuestionnaireEntity.class);
		data.forEach(n -> {
			updateFinish(n.getId());
		});
	}

	public QuestionnaireEntity getDataById1(String id, boolean noMember) {
		QuestionnaireEntity data =
				mongoTemplate.findOne(Query.query(Criteria.where("is_finish").is(0)
						.and("_id").is(id)), QuestionnaireEntity.class);
		if (Objects.isNull(data)) {
			throw new QuestionnaireNotFoundException();
		}
		if (noMember && (data.getIsAnonymous() == 1)) {
			throw new AccessDeniedException();
		}
		return data;
	}

	public QuestionnaireEntity getDataById2(String id, String mId) {
		QuestionnaireEntity data = getDataById1(id,false);
		if (data.getIsAnonymous() == 1) {
			List<Member> members = data.getMembers();
			System.out.println(data);
			List<String> collect = members.stream()
					.map(Member::getId)
					.filter(s -> s.equals(mId)).collect(Collectors.toList());
			if (collect.isEmpty()) {
				throw new AccessDeniedException();
			}
		}
		return data;
	}
}
