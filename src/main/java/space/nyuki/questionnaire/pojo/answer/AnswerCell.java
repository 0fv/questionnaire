package space.nyuki.questionnaire.pojo.answer;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.swagger.annotations.ApiModel;

/**
 * @author ning
 * @createTime 12/1/19 1:46 PM
 * @description 不同类型答案的父类
 */
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "type",
        include = JsonTypeInfo.As.PROPERTY
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = Choice.class, name = "choice"),
        @JsonSubTypes.Type(value = Comment.class, name = "comment"),
        @JsonSubTypes.Type(value = InquiryDate.class, name = "date")
})
@ApiModel("答题方框，不包含问题")
public interface AnswerCell {
}