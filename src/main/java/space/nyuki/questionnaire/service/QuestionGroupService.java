package space.nyuki.questionnaire.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.transaction.annotation.Transactional;
import space.nyuki.questionnaire.exception.ElementNotFoundException;
import space.nyuki.questionnaire.pojo.QuestionGroup;
import space.nyuki.questionnaire.pojo.Questionnaire;

import java.util.List;
import java.util.Objects;

/**
 * @author ning
 * @createTime 12/22/19 4:13 PM
 * @description 问题组服务
 */
public class QuestionGroupService {
    @Autowired
    private QuestionnaireService questionnaireService;
    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * 添加问题组
     * @param id
     * @param groupName
     */
    @Transactional
    public void addQuestionGroup(String id,String groupName){
        questionnaireService.getQuestionnaireById(id);
        Update update = new Update();
        QuestionGroup questionGroup = new QuestionGroup();
        questionGroup.setGroupName(groupName);
        update.addToSet("question_groups",questionGroup);
        questionnaireService.setQuestionnaireUpdate(id,update);
    }

    /**
     * 修改问题组
     * @param id
     * @param index
     * @param groupName
     */
    @Transactional
    public void alterQuestionGroupName(String id,int index,String groupName){
        Questionnaire questionnaire = questionnaireService.getQuestionnaireById(id);
        List<QuestionGroup> questionGroups = questionnaire.getQuestionGroups();
        if (Objects.isNull(questionGroups)) {
            throw new ElementNotFoundException("questionGroups");
        }else {
            QuestionGroup questionGroup = questionGroups.get(index);
            questionGroup.setGroupName(groupName);
            changeQuestionGroups(id,index,questionGroup,questionGroups);

        }
    }
    @Transactional
    public void changeQuestionGroups(String id,int index,QuestionGroup questionGroup,List<QuestionGroup> questionGroups){
        questionGroups.set(index,questionGroup);
        Update update = new Update();
        update.set("question_groups",questionGroups);
        questionnaireService.setQuestionnaireUpdate(id,update);
    }

    /**
     * 删除选中的问题组
     * @param id
     * @param index
     */
    @Transactional
    public void deleteQuestionGroup(String id,int index){
        Questionnaire questionnaire = questionnaireService.getQuestionnaireById(id);
        List<QuestionGroup> questionGroups = questionnaire.getQuestionGroups();
        if (Objects.isNull(questionGroups)) {
            throw new ElementNotFoundException("questionGroups");
        }else {
            questionGroups.remove(index);
            Update update = new Update();
            update.set("question_groups",questionGroups);
            questionnaireService.setQuestionnaireUpdate(id,update);

        }
    }
}