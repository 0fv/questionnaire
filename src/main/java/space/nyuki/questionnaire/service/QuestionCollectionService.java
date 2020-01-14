package space.nyuki.questionnaire.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import space.nyuki.questionnaire.pojo.QuestionCollection;
import space.nyuki.questionnaire.utils.JWTUtil;
import space.nyuki.questionnaire.utils.MapUtil;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class QuestionCollectionService {
	@Autowired
	private MongoTemplate mongoTemplate;

	public List<QuestionCollection> getQuestionCollection() {
		return mongoTemplate.find(Query.query(Criteria.where("is_delete").is(0)), QuestionCollection.class);
	}

	public QuestionCollection getQuestionCollectionById(String id) {
		return mongoTemplate.findOne(Query.query(Criteria.where("_id")), QuestionCollection.class);
	}

	@Transactional
	public void add(QuestionCollection questionCollection, String token) {
		String username = JWTUtil.getUsername(token);
		questionCollection.setCreatedAccount(username);
		questionCollection.setCreatedDate(new Date());
		mongoTemplate.save(questionCollection);
	}

	@Transactional
	public void update(QuestionCollection questionCollection, String token) {
		String username = JWTUtil.getUsername(token);
		String id = questionCollection.getId();
		questionCollection.setEditedAccount(username);
		questionCollection.setEditedDate(new Date());
		Map<String, Object> stringObjectMap = MapUtil.objectToMap(questionCollection);
		Update update = new Update();
		stringObjectMap.forEach(update::set);
		mongoTemplate.updateFirst(Query.query(Criteria.where("_id").is(id)), update, QuestionCollection.class);
	}

	@Transactional
	public void delete(String id) {
		Update update = new Update();
		update.set("is_delete", 1);
		mongoTemplate.updateFirst(Query.query(Criteria.where("_id").is(id)), update, QuestionCollection.class);
	}
}
