package space.nyuki.questionnaire.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import space.nyuki.questionnaire.pojo.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class ResultService {
	@Autowired
	private MongoTemplate mongoTemplate;

	public ResultTemplate getTemplate(String id) {
		ResultTemplate resultTemplate = new ResultTemplate();
		QuestionnaireEntity entity_id = mongoTemplate.findOne(Query.query(Criteria.where("_id").is(id)), QuestionnaireEntity.class);
		if (Objects.nonNull(entity_id)) {
			List<QuestionGroup> questionGroups = entity_id.getQuestionGroups();
			resultTemplate = getResultTemplate(id, questionGroups);
		}
		return resultTemplate;
	}

	private ResultTemplate getResultTemplate(String id, List<QuestionGroup> questionGroups) {
		List<ResultGroup> resultGroups = new ArrayList<>();
		questionGroups.forEach(n -> {
			ResultGroup resultGroup = new ResultGroup();
			resultGroup.setTitle(n.getTitle());
			List<Result> results = new ArrayList<>();
			n.getQuestionCells().forEach(m -> {
				Result result = new Result();
				result.setTitle(m.getTitle());
				result.setAnswerCells(m.getAnswerCells());
				result.setMustAnswer(m.getMustAnswer());
				results.add(result);
			});
			resultGroup.setResults(results);
			resultGroups.add(resultGroup);
		});
		ResultTemplate resultTemplate = new ResultTemplate();
		resultTemplate.setId(id);
		resultTemplate.setResultGroups(resultGroups);
		return resultTemplate;
	}

}
