package space.nyuki.questionnaire.pojo;

import lombok.Builder;
import lombok.ToString;
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
@Builder
@ToString
public class QuestionCell {
    @NotNull(
            message = "标题不能为空",
            groups = {GroupView.Create.class
            }
    )
    private String title;
    @Valid
    @NotNull(
            message = "题目内容不能为空",
            groups = {GroupView.Create.class
            }
    )
    private List<AnswerCell> answerCells;

}