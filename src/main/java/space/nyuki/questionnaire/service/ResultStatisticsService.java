package space.nyuki.questionnaire.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import space.nyuki.questionnaire.pojo.*;
import space.nyuki.questionnaire.pojo.answer.AnswerCell;
import space.nyuki.questionnaire.pojo.answer.Choice;
import space.nyuki.questionnaire.pojo.result.ResultCell;
import space.nyuki.questionnaire.pojo.result.ResultChoice;

import java.util.*;

@Service
public class ResultStatisticsService {
	@Autowired
	private ResultCollectionService resultCollectionService;

	@Autowired
	private MongoTemplate mongoTemplate;

	@Autowired
	private QuestionnaireEntityService questionnaireEntityService;

	@Transactional
	@Scheduled(cron = "0 * * * * *")
	public void startStatics() {
		List<QuestionnaireEntity> entities = questionnaireEntityService.getData(0);
		for (QuestionnaireEntity entity : entities) {
			String id = entity.getId();
			ChoiceStatisticsCollection collection = this.getCollectionById(id);
			List<ResultCollection> data = null;
			if (collection == null) {
				collection = mongoTemplate.save(initCollection(entity));
				data = resultCollectionService.getData(id);
			} else {
				String lastId = collection.getLastId();
				if (lastId != null && (!lastId.isEmpty())) {
					data = resultCollectionService.getResultGreatThanId(id, lastId);
				} else {
					data = resultCollectionService.getData(id);
				}
			}
			if (data != null && (!data.isEmpty())) {
				addMoreDataList(collection, data);
			}
			mongoTemplate.save(collection);
		}
	}

	public void addMoreDataList(ChoiceStatisticsCollection collection, List<ResultCollection> data) {
		List<ChoiceStatisticsGroup> choiceStatisticsGroups = collection.getChoiceStatisticsGroups();
		for (int i = 0; i < data.size(); i++) {
			List<SubmitResultGroup> submitResultGroups = data.get(i).getSubmitResultGroups();
			addMoreData(collection, submitResultGroups);
			if (i == data.size() - 1) {
				collection.setLastId(data.get(i).getId());
			}
		}
		collection.setChoiceStatisticsGroups(choiceStatisticsGroups);
	}

	public void addMoreData(ChoiceStatisticsCollection collection, List<SubmitResultGroup> submitResultGroups) {
		List<ChoiceStatisticsGroup> choiceStatisticsGroups = collection.getChoiceStatisticsGroups();
		for (ChoiceStatisticsGroup choiceStatisticsGroup : choiceStatisticsGroups) {
			for (ChoiceStatistics choiceStatistic : choiceStatisticsGroup.getChoiceStatistics()) {
				int groupIndex = choiceStatistic.getGroupIndex();
				int choiceIndex = choiceStatistic.getChoiceIndex();
				ResultCell resultCell = submitResultGroups.get(groupIndex).getResultCells().get(choiceIndex);
				ResultChoice resultChoice = (ResultChoice) resultCell;
				List<String> answer = resultChoice.getAnswer();
				Map<String, Long> choice = choiceStatistic.getChoice();
				Set<String> choices = choice.keySet();
				for (String s : answer) {
					if (choices.contains(s)) {
						Long aLong = choice.get(s);
						aLong++;
						choice.put(s, aLong);
					} else {
						Long other = choice.get("其他");
						other++;
						choice.put("其他", other);
					}
				}
				choiceStatistic.setChoice(choice);
			}
		}
	}

	public ChoiceStatisticsCollection getCollectionById(String id) {
		return mongoTemplate.findOne(Query.query(Criteria.where("_id").is(id)), ChoiceStatisticsCollection.class);
	}

	private ChoiceStatisticsCollection initCollection(QuestionnaireEntity entity) {
		ChoiceStatisticsCollection choiceStatisticsCollection = new ChoiceStatisticsCollection();
		choiceStatisticsCollection.setId(entity.getId());
		choiceStatisticsCollection.setTitle(entity.getTitle());
		List<ChoiceStatisticsGroup> groups = new ArrayList<>();
		List<QuestionGroup> questionGroups = entity.getQuestionGroups();
		for (int i = 0; i < questionGroups.size(); i++) {
			QuestionGroup questionGroup = questionGroups.get(i);
			ChoiceStatisticsGroup choiceStatisticsGroup = new ChoiceStatisticsGroup();
			choiceStatisticsGroup.setGroupTitle(questionGroup.getTitle());
			List<QuestionCell> questionCells = questionGroup.getQuestionCells();
			List<ChoiceStatistics> choiceStatistics = new ArrayList<>();
			for (int a = 0; a < questionCells.size(); a++) {
				QuestionCell questionCell = questionCells.get(a);
				AnswerCell answerCell = questionCell.getAnswerCells().get(0);
				Class<? extends AnswerCell> aClass = answerCell.getClass();
				if (aClass.equals(Choice.class)) {
					ChoiceStatistics choiceStatistics1 = new ChoiceStatistics();
					choiceStatistics1.setTitle(questionCell.getTitle());
					Map<String, Long> map = new HashMap<>();
					int size = questionCell.getAnswerCells().size();
					Choice choice = (Choice) answerCell;
					List<String> choiceList = choice.getChoice();
					for (String s : choiceList) {
						map.put(s, 0L);
					}
					if (size == 2) {
						map.put("其他", 0L);
					}
					choiceStatistics1.setChoice(map);
					choiceStatistics1.setGroupIndex(i);
					choiceStatistics1.setChoiceIndex(a);
					choiceStatistics1.setMustAnswer(questionCell.getMustAnswer() == 1);
					choiceStatistics.add(choiceStatistics1);
				}

			}
			choiceStatisticsGroup.setChoiceStatistics(choiceStatistics);
			groups.add(choiceStatisticsGroup);
		}
		choiceStatisticsCollection.setChoiceStatisticsGroups(groups);
		return choiceStatisticsCollection;
	}

	public ChoiceStatisticsCollection getDataById(String id) {
		return mongoTemplate.findOne(Query.query(Criteria.where("_id").is(id)), ChoiceStatisticsCollection.class);
	}
}
