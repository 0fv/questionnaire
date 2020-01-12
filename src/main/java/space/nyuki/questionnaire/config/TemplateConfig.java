package space.nyuki.questionnaire.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import space.nyuki.questionnaire.pojo.answer.AnswerCell;
import space.nyuki.questionnaire.pojo.answer.Choice;
import space.nyuki.questionnaire.pojo.answer.Comment;
import space.nyuki.questionnaire.pojo.answer.InquiryDate;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ning
 * @createTime 12/1/19 2:15 PM
 * @description
 */
@Configuration
public class TemplateConfig {
    @Bean("answerCells")
    public Map<String, AnswerCell> answerCells(){
        Map<String,AnswerCell> AnswerCells = new HashMap<>();
        List<String> mp = new ArrayList<>();
        AnswerCells.put("choice",new Choice(mp,false));
        AnswerCells.put("comment",new Comment(""));
        AnswerCells.put("date",new InquiryDate(new Timestamp(System.currentTimeMillis())));
        return AnswerCells;
    }
}