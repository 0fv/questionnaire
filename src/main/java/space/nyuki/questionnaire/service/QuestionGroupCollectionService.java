package space.nyuki.questionnaire.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import space.nyuki.questionnaire.pojo.QuestionGroupCollection;
import space.nyuki.questionnaire.utils.JWTUtil;
import space.nyuki.questionnaire.utils.MapUtil;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class QuestionGroupCollectionService {
	@Autowired
	private MongoTemplate mongoTemplate;

	public List<QuestionGroupCollection> getQuestionGroupCollection() {
		return mongoTemplate.find(Query.query(Criteria.where("is_delete").is(0)), QuestionGroupCollection.class);
	}

	public QuestionGroupCollection getQuestionGroupCollectionById(String id) {
		return mongoTemplate.findOne(Query.query(Criteria.where("_id").is(id)), QuestionGroupCollection.class);
	}

	@Transactional
	public void addData(QuestionGroupCollection collection, String token) {
		String username = JWTUtil.getUsername(token);
		collection.setCreatedAccount(username);
		collection.setCreatedDate(new Date());
		mongoTemplate.save(collection);
	}

	@Transactional
	public void updateData(QuestionGroupCollection collection, String token) {
		String username = JWTUtil.getUsername(token);
		String id = collection.getId();
		collection.setEditedAccount(username);
		collection.setEditedDate(new Date());
		Map<String, Object> map = MapUtil.objectToMap(collection);
		Update update = new Update();
		map.forEach(update::set);
		update.set("question_cells", collection.getQuestionCells());
		mongoTemplate.updateFirst(Query.query(Criteria.where("_id").is(id)), update, QuestionGroupCollection.class);
	}

	@Transactional
	public void deleteData(String id) {
		Update update = new Update();
		update.set("is_delete", 1);
		mongoTemplate.updateFirst(Query.query(Criteria.where("_id").is(id)), update, QuestionGroupCollection.class);
	}
}
