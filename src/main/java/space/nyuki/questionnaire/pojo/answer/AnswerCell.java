package space.nyuki.questionnaire.pojo.answer;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.AllArgsConstructor;

import java.awt.*;

/**
 * @author ning
 * @createTime 12/1/19 1:46 PM
 * @description 不同类型答案的父类
 */
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = Choice.class, name = "choice"),
        @JsonSubTypes.Type(value = Comment.class, name = "comment"),
        @JsonSubTypes.Type(value = InquiryDate.class,name = "date")
})
public interface AnswerCell {
}