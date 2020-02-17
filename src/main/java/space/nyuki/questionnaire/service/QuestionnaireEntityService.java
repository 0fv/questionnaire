package space.nyuki.questionnaire.service;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import space.nyuki.questionnaire.exception.AccessDeniedException;
import space.nyuki.questionnaire.exception.QuestionnaireNotFoundException;
import space.nyuki.questionnaire.pojo.Member;
import space.nyuki.questionnaire.pojo.QuestionnaireEntity;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class QuestionnaireEntityService {
	@Autowired
	private MongoTemplate mongoTemplate;
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
	@Autowired
	private QueueListenerService queueListenerService;

	public List<QuestionnaireEntity> getData(int isFinish) {
		return mongoTemplate.
				find(Query.query(Criteria.where("is_delete").is(0).and("is_finish").is(isFinish))
						, QuestionnaireEntity.class);
	}

	@Transactional
	public QuestionnaireEntity save(QuestionnaireEntity questionnaireEntity) {
		QuestionnaireEntity save = mongoTemplate.save(questionnaireEntity);
		redisTemplate.opsForValue().set("entity:" + save.getId(), save);
		queueListenerService.addQueue(questionnaireEntity.getId());
		return save;
	}

	public QuestionnaireEntity getDataById(String id) {
		return mongoTemplate.findOne(Query.query(Criteria.where("_id").is(id)), QuestionnaireEntity.class);
	}

	public void deleteByName(String name) {
		Set<String> keys = redisTemplate.keys(name);
		if (CollectionUtils.isNotEmpty(keys)) {
			redisTemplate.delete(keys);
		}
	}

	public void initStoreToRedis() {
		deleteByName("entity:*");
		List<QuestionnaireEntity> list = getData(0);

		redisTemplate.executePipelined(new SessionCallback<Object>() {
			@Override
			public <K, V> Object execute(RedisOperations<K, V> operations) throws DataAccessException {
				list.forEach(questionnaireEntity -> {
					redisTemplate.opsForValue().set("entity:" + questionnaireEntity.getId(), questionnaireEntity);
				});
				return null;
			}
		});
	}

	@Transactional
	public void deleteData(String id) {
		Update update = new Update();
		update.set("is_delete", 1);
		mongoTemplate.findAndModify(Query.query(Criteria.where("_id").is(id).and("is_finish").is(1)), update, QuestionnaireEntity.class);
	}

	@Transactional
	public void updateFinish(String id) {
		Update update = new Update();
		update.set("is_finish", 1);
		mongoTemplate.findAndModify(Query.query(Criteria.where("_id").is(id)), update, QuestionnaireEntity.class);
		redisTemplate.delete("entity:" + id);
		queueListenerService.deleteQueue(id);
	}

	@Transactional
	public void updateDate(QuestionnaireEntity questionnaireEntity) {
		Update update = new Update();
		update.set("is_finish", 0);
		update.set("to", questionnaireEntity.getTo());
		QuestionnaireEntity q = mongoTemplate.findAndModify(
				Query.query(Criteria.where("_id").is(questionnaireEntity.getId())), update, QuestionnaireEntity.class);
		redisTemplate.opsForValue().set("entity:" + Objects.requireNonNull(q).getId(), q);
		queueListenerService.addQueue(questionnaireEntity.getId());
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
		QuestionnaireEntity data = getDataById1(id, false);
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
