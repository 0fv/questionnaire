package space.nyuki.questionnaire.pojo.answer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import space.nyuki.questionnaire.group.GroupView;

import javax.validation.constraints.NotNull;

/**
 * @author ning
 * @createTime 12/1/19 1:52 PM
 * @description 评论型
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comment implements AnswerCell {
    @NotNull(
            message = "评论不能为null",
            groups = {
                    GroupView.Input.class
            }
    )
    private String comment;
}