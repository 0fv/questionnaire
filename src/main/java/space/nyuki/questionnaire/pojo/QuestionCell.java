package space.nyuki.questionnaire.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;

import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import org.springframework.data.mongodb.core.mapping.Field;
import space.nyuki.questionnaire.group.GroupView;
import space.nyuki.questionnaire.pojo.answer.AnswerCell;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author ning
 * @createTime 12/1/19 1:38 PM
 * @description 单个问题
 */
//@JsonDeserialize
@Data
@ApiModel("问答方框")
@JsonView({
        Object.class
})
public class QuestionCell {
    @NotNull(
            message = "标题不能为空",
            groups = {GroupView.Create.class
            }
    )
    @ApiModelProperty(value = "问题",example = "你好吗")
    @Field("title")
    private String title;
    @Valid
    @NotNull(
            message = "题目内容不能为空",
            groups = {GroupView.Create.class
            }
    )
    @ApiModelProperty(value = "答案",example = "{\"comment\":{\"comment\": \"\"}}")
    @Field("answer_cells")
    @JsonProperty("answer_cells")
    private List<AnswerCell> answerCells;

}