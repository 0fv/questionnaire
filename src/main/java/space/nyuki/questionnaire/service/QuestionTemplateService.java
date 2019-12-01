package space.nyuki.questionnaire.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import space.nyuki.questionnaire.exception.TemplateNotFoundException;
import space.nyuki.questionnaire.pojo.answer.AnswerCell;

import java.util.Map;
import java.util.Objects;

/**
 * @author ning
 * @createTime 12/1/19 2:21 PM
 * @description
 */
@Service
public class QuestionTemplateService {
    @Autowired
    private Map<String, AnswerCell> answerCells;
    //获取所有 answerCells
    public AnswerCell getQuestionTemplateInstance(String type) {
        AnswerCell inquiryType = answerCells.get(type);
        if (Objects.isNull(inquiryType)) {
            //不存在就抛出错误
            throw new TemplateNotFoundException();
        }
        return inquiryType;
    }

    public Map<String, AnswerCell> getAnswerCells() {
        return answerCells;
    }
}