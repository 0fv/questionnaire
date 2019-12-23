package space.nyuki.questionnaire.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import space.nyuki.questionnaire.pojo.QuestionCell;
import space.nyuki.questionnaire.pojo.QuestionGroup;
import space.nyuki.questionnaire.pojo.Questionnaire;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author ning
 * @createTime 12/22/19 8:32 PM
 * @description 问题服务
 */
@Service
public class QuestionService {

    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private QuestionnaireService questionnaireService;
    @Autowired
    private QuestionGroupService questionGroupService;

    /**
     * 在问题组中添加一个问题
     * @param id
     * @param groupIndex
     * @param cell
     */
    @Transactional
    public void addQuestion(String id,int groupIndex,QuestionCell cell){
        Questionnaire questionnaire = questionnaireService.getQuestionnaireById(id);
        List<QuestionGroup> questionGroups = questionnaire.getQuestionGroups();
        QuestionGroup questionGroup = questionGroups.get(groupIndex);
        List<QuestionCell> questionCells = questionGroup.getQuestionCells();
        if (Objects.isNull(questionCells)) {
            questionCells = new ArrayList<>();
        }
        questionCells.add(cell);
        questionGroup.setQuestionCells(questionCells);
        questionGroupService.changeQuestionGroups(id,groupIndex,questionGroup,questionGroups);
    }

    /**
     * 修改在某个问题方框
     * @param id
     * @param groupIndex
     * @param questionIndex
     * @param cell
     */

    @Transactional
    public void alterQuestion(String id,int groupIndex,int questionIndex,QuestionCell cell){
        Questionnaire questionnaire = questionnaireService.getQuestionnaireById(id);
        List<QuestionGroup> questionGroups = questionnaire.getQuestionGroups();
        QuestionGroup questionGroup = questionGroups.get(groupIndex);
        List<QuestionCell> questionCells = questionGroup.getQuestionCells();
        questionCells.set(questionIndex,cell);
        questionGroup.setQuestionCells(questionCells);
        questionGroupService.changeQuestionGroups(id,groupIndex,questionGroup,questionGroups);
    }

    /**
     * 删除某个方框
     * @param id
     * @param groupIndex
     * @param questionIndex
     */
    @Transactional
    public void deleteQuestion(String id,int groupIndex,int questionIndex){
        Questionnaire questionnaire = questionnaireService.getQuestionnaireById(id);
        List<QuestionGroup> questionGroups = questionnaire.getQuestionGroups();
        QuestionGroup questionGroup = questionGroups.get(groupIndex);
        List<QuestionCell> questionCells = questionGroup.getQuestionCells();
        questionCells.remove(questionIndex);
        questionGroup.setQuestionCells(questionCells);
        questionGroupService.changeQuestionGroups(id,groupIndex,questionGroup,questionGroups);
    }
}