package space.nyuki.questionnaire.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import space.nyuki.questionnaire.exception.ElementNotFoundException;
import space.nyuki.questionnaire.pojo.QuestionGroup;
import space.nyuki.questionnaire.pojo.Questionnaire;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * @author ning
 * @createTime 12/22/19 4:13 PM
 * @description 问题组服务
 */
@Service
public class QuestionGroupService {
    @Autowired
    private QuestionnaireService questionnaireService;
    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * 添加问题组
     *
     * @param id
     * @param questionGroup
     */
    @Transactional
    public void addQuestionGroup(String id, QuestionGroup questionGroup) {
        questionnaireService.getQuestionnaireById(id);
        Update update = new Update();
        update.addToSet("question_groups", questionGroup);
        questionnaireService.setQuestionnaireUpdate(id, update);
    }

    /**
     * 修改问题组
     *
     * @param id
     * @param index
     * @param questionGroup
     */
    @Transactional
    public void alterQuestionGroup(String id, int index, QuestionGroup questionGroup) {
        Questionnaire questionnaire = questionnaireService.getQuestionnaireById(id);
        List<QuestionGroup> questionGroups = questionnaire.getQuestionGroups();
        if (Objects.isNull(questionGroups)) {
            throw new ElementNotFoundException("questionGroups");
        } else {
            changeQuestionGroups(id, index, questionGroup, questionGroups);

        }
    }

    @Transactional
    public void changeQuestionGroups(String id, int index, QuestionGroup questionGroup, List<QuestionGroup> questionGroups) {
        questionGroups.set(index, questionGroup);
        Update update = new Update();
        update.set("question_groups", questionGroups);
        questionnaireService.setQuestionnaireUpdate(id, update);
    }
    @Transactional
    public void swapQuestionGroup(String id,int gidA,int gidB){
        Questionnaire questionnaire = questionnaireService.getQuestionnaireById(id);
        List<QuestionGroup> questionGroups = questionnaire.getQuestionGroups();
        if (Objects.isNull(questionGroups)) {
            throw new ElementNotFoundException("questionGroups");
        } else {
            Collections.swap(questionGroups,gidA,gidB);
            Update update = new Update();
            update.set("question_groups",questionGroups);
            questionnaireService.setQuestionnaireUpdate(id,update);
        }


    }

    /**
     * 删除选中的问题组
     *
     * @param id
     * @param index
     */
    @Transactional
    public void deleteQuestionGroup(String id, int index) {
        Questionnaire questionnaire = questionnaireService.getQuestionnaireById(id);
        List<QuestionGroup> questionGroups = questionnaire.getQuestionGroups();
        if (Objects.isNull(questionGroups)) {
            throw new ElementNotFoundException("questionGroups");
        } else {
            questionGroups.remove(index);
            Update update = new Update();
            update.set("question_groups", questionGroups);
            questionnaireService.setQuestionnaireUpdate(id, update);

        }
    }

    public List<QuestionGroup> getQuestionGroups(String id) {
        Questionnaire questionnaire =
                mongoTemplate.findOne(
                        Query.query(Criteria.where("_id").is(id)),
                        Questionnaire.class);
        if (Objects.isNull(questionnaire)) {
            return null;
        }else{
            return questionnaire.getQuestionGroups();
        }
    }
}