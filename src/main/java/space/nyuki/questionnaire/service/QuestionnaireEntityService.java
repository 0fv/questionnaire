package space.nyuki.questionnaire.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import space.nyuki.questionnaire.pojo.QuestionnaireEntity;

import java.util.List;

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
	public void updateFinish(String id, int isFinish) {
		Update update = new Update();
		update.set("is_finish", isFinish);
		mongoTemplate.findAndModify(Query.query(Criteria.where("_id").is(id)), update, QuestionnaireEntity.class);
	}
}
