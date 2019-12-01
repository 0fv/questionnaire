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
import space.nyuki.questionnaire.utils.MapUtil;

import java.sql.Timestamp;
import java.util.*;

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
        Questionnaire q = mongoTemplate.findOne(Query.query((Criteria.where("uuid").is(uuid))),
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
        mongoTemplate.findAndModify(Query.query(Criteria.where("_id").is(id))
                , update, Questionnaire.class);
    }

    @Transactional
    public void alterQuestionnaire(Questionnaire questionnaire) {
        Map<String, Object> map = MapUtil.objectToMap(questionnaire);
        Update update = new Update();
        map.forEach(update::set);
        update.set("modify_time", new Date());
        mongoTemplate.findAndModify(
                Query.query(Criteria.where("_id").is(questionnaire.getId())),
                update,
                Questionnaire.class);
    }

    public List<Questionnaire> getQuestionnaire(Integer page, Integer pageSize) {
        Query query = new Query();
        query.skip((page - 1) * pageSize);
        query.limit(pageSize);
        return mongoTemplate.find(query, Questionnaire.class);
    }

    public List<QuestionCell> getQuestions(String id) {
        Questionnaire questionnaire = mongoTemplate.findOne(
                Query.query(Criteria.where("_id").is(id)),
                Questionnaire.class
        );
        if (questionnaire != null) {
            return questionnaire.getQuestionCells();
        } else {
            return null;
        }
    }

    public void addQuestion(String id, QuestionCell questionCell) {
        Questionnaire questionnaire = mongoTemplate.findOne(
                Query.query(Criteria.where("_id").is(id)),
                Questionnaire.class
        );
        if (questionnaire != null) {
            List<QuestionCell> questionCells = questionnaire.getQuestionCells();
            if (Objects.isNull(questionCells)) {
                questionCells = new ArrayList<>();
            }
            questionCells.add(questionCell);
        }else{
            throw new ElementNotFoundException();
        }
    }

    public void alterQuestion(String qid, Integer cid, QuestionCell questionCell) {
        Questionnaire questionnaire = mongoTemplate.findOne(
                Query.query(Criteria.where("_id").is(qid)),
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
            }else {
                throw new ElementNotFoundException("questionnaire");
            }
        }else{
            throw new ElementNotFoundException("questionnaire");
        }
    }

    public void deleteQuestion(String qid, Integer cid) {
        Questionnaire questionnaire = mongoTemplate.findOne(
                Query.query(Criteria.where("_id").is(qid)),
                Questionnaire.class
        );
        if (questionnaire != null) {
            List<QuestionCell> questionCells = questionnaire.getQuestionCells();
            try {
                questionCells.remove(cid-1);
            } catch (Exception e) {
                throw new ElementNotFoundException("question cell");
            }
        }else{
            throw new ElementNotFoundException("questionnaire");
        }
    }
}