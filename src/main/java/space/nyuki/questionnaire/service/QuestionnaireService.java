package space.nyuki.questionnaire.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import space.nyuki.questionnaire.exception.ElementNotFoundException;
import space.nyuki.questionnaire.pojo.QuestionCell;
import space.nyuki.questionnaire.pojo.Questionnaire;
import space.nyuki.questionnaire.pojo.ResultCollection;
import space.nyuki.questionnaire.utils.MapUtil;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static org.springframework.data.mongodb.core.query.Query.query;

/**
 * @author ning
 * @createTime 12/1/19 3:42 PM
 * @description 处理问卷调查表
 */
@Service
public class QuestionnaireService {
    @Autowired
    private MongoTemplate mongoTemplate;

    @Transactional
    public void createQuestionnaire(Questionnaire questionnaire) {
        String uuid = questionnaire.getUuid();
        System.out.println(uuid);
        Questionnaire q = mongoTemplate.findOne(query((Criteria.where("uuid").is(uuid))),
                Questionnaire.class, "questionnaire");
        System.out.println("q = " + q);
        if (Objects.isNull(q)) {
            questionnaire.setCreatedTime(new Timestamp(System.currentTimeMillis()));
            questionnaire.setQuestionCells(null);
            questionnaire.setIsDelete(0);
            mongoTemplate.save(questionnaire);

        }

    }

    @Transactional
    public void deleteQuestionnaire(String id) {
        Update update = new Update();
        update.set("is_delete", 1);
        update.set("modify_time", new Date());
        mongoTemplate.findAndModify(query(Criteria.where("_id").is(id))
                , update, Questionnaire.class);
    }

    @Transactional
    public void alterQuestionnaire(Questionnaire questionnaire) {
        Map<String, Object> map = MapUtil.objectToMap(questionnaire);
        Update update = new Update();
        map.forEach(update::set);
        update.set("modify_time", new Date());
        mongoTemplate.findAndModify(
                query(Criteria.where("_id").is(questionnaire.getId())),
                update,
                Questionnaire.class);
    }

    public List<Questionnaire> getQuestionnaire(Integer page, Integer pageSize) {
        Query query = new Query();
        query.skip((page - 1) * pageSize);
        query.limit(pageSize);
        query.addCriteria(Criteria.where("is_delete").is(0));
        return mongoTemplate.find(query, Questionnaire.class);
    }

    public List<QuestionCell> getQuestions(String id) {
        Questionnaire questionnaire = mongoTemplate.findOne(
                query(Criteria.where("_id").is(id)),
                Questionnaire.class
        );
        if (questionnaire != null) {
            System.out.println(questionnaire.getQuestionCells());
            return questionnaire.getQuestionCells();
        } else {
            return null;
        }
    }

    @Transactional
    public void addQuestion(String id, QuestionCell questionCell) {
        Questionnaire questionnaire = mongoTemplate.findOne(
                query(Criteria.where("_id").is(id)),
                Questionnaire.class
        );
        if (questionnaire != null) {
            List<QuestionCell> questionCells = questionnaire.getQuestionCells();
            Update update = new Update();
            update.addToSet("questions", questionCell);
            update.set("modify_time", new Date());
            mongoTemplate.findAndModify(
                    Query.query(Criteria.where("_id").is(id)),
                    update,
                    Questionnaire.class
            );
        } else {
            throw new ElementNotFoundException();
        }
    }

    @Transactional
    public void alterQuestion(String qid, Integer cid, QuestionCell questionCell) {
        Questionnaire questionnaire = mongoTemplate.findOne(
                query(Criteria.where("_id").is(qid)),
                Questionnaire.class
        );
        if (questionnaire != null) {
            List<QuestionCell> questionCells = questionnaire.getQuestionCells();
            if (Objects.nonNull(questionCells)) {
                try {
                    questionCells.set(cid - 1, questionCell);
                } catch (Exception e) {
                    throw new ElementNotFoundException("question cell");
                }
                Update update = new Update();
                update.set("questions", questionCells);
                update.set("modify_time", new Date());
                mongoTemplate.findAndModify(
                        Query.query(Criteria.where("_id").is(qid)),
                        update,
                        Questionnaire.class
                );
            } else {
                throw new ElementNotFoundException("questionnaire");
            }
        } else {
            throw new ElementNotFoundException("questionnaire");
        }
    }

    public void deleteQuestion(String qid, Integer cid) {
        Questionnaire questionnaire = mongoTemplate.findOne(
                query(Criteria.where("_id").is(qid)),
                Questionnaire.class
        );
        if (questionnaire != null) {
            List<QuestionCell> questionCells = questionnaire.getQuestionCells();
            try {
                questionCells.remove(cid - 1);
            } catch (Exception e) {
                throw new ElementNotFoundException("question cell");
            }
            Update update = new Update();
            update.set("modify_time", new Date());
            update.set("questions", questionCells);
            mongoTemplate.findAndModify(
                    Query.query(Criteria.where("_id").is(qid)),
                    update,
                    Questionnaire.class
            );
        } else {
            throw new ElementNotFoundException("questionnaire");
        }
    }

    public void startInvestigation(ResultCollection resultCollection) {
        Questionnaire questionnaire = mongoTemplate.findOne(
                query(Criteria.where("_id").is(resultCollection.getQuestionnaireId())),
                Questionnaire.class
        );
        if (Objects.nonNull(questionnaire)) {
            List<QuestionCell> questionCells = questionnaire.getQuestionCells();
            if (Objects.isNull(questionCells) || questionCells.isEmpty()) {
                throw new ElementNotFoundException("question cell");
            } else {
                resultCollection.setQuestionCells(questionCells);
            }
        }else{
                throw new ElementNotFoundException("questionnaire");
            }
        }
    }