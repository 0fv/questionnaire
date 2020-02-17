package space.nyuki.questionnaire.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import space.nyuki.questionnaire.pojo.*;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class ResultCollectionService {
	@Autowired
	private MongoTemplate mongoTemplate;
	@Autowired
	private QuestionnaireEntityService questionnaireEntityService;
	@Autowired
	private RedisTemplate<String, String> redisTemplate;

	@Transactional
	public void saveData(SubmitResult submitResult) {
		String id = submitResult.getId();
		String fingerPrint = submitResult.getFingerPrint();
		String resultTable = "result:" + id;
		ResultCollection resultCollection = new ResultCollection();
		QuestionnaireEntity entity = questionnaireEntityService.getDataById(id);
		if (resultCheck(resultTable, fingerPrint) && entityCheck(entity, fingerPrint, resultCollection)) {
			resultCollection.setFingerPrint(fingerPrint);
			resultCollection.setEntityId(id);
			resultCollection.setSubmitDate(new Date());
			resultCollection.setSubmitResultGroups(submitResult.getSubmitResultGroups());
			mongoTemplate.save(resultCollection, resultTable);
			redisTemplate.opsForList().leftPush(resultTable, fingerPrint);
		}

	}

	private boolean resultCheck(String resultTable, String fingerPrint) {
		ResultCollection resultCollectionByFingerPrintId = getResultCollectionByFingerPrintId(fingerPrint, resultTable);
		return Objects.isNull(resultCollectionByFingerPrintId);
	}

	public ResultCollection getResultCollectionByFingerPrintId(String fingerPrint, String resultTable) {
		return mongoTemplate.findOne(Query.query(Criteria.where("finger_print").is(fingerPrint))
				, ResultCollection.class
				, resultTable);
	}

	private boolean entityCheck(QuestionnaireEntity entity, String memberId, ResultCollection resultCollection) {
		if (entity.getIsAnonymous() == 1) {
			List<Member> members = entity.getMembers();
			boolean flag = false;
			for (Member member : members) {
				if (member.getId().equals(memberId)) {
					flag = true;
					resultCollection.setName(member.getName());
					break;
				}
			}
			return flag;
		} else {
			return true;
		}
	}

	public List<ResultCollection> getData(String id) {
		return mongoTemplate.findAll(ResultCollection.class, "result:" + id);
	}
}
