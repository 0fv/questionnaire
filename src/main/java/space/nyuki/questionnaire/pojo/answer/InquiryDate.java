package space.nyuki.questionnaire.pojo.answer;

import lombok.AllArgsConstructor;
import lombok.Data;
import space.nyuki.questionnaire.group.GroupView;

import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

/**
 * @author ning
 * @createTime 12/1/19 1:53 PM
 * @description 时间型
 */
@Data
@AllArgsConstructor
public class InquiryDate implements AnswerCell{
    @NotNull(
            message = "日期不能为空",
            groups = {
                    GroupView.Input.class
            }
    )
    private Timestamp timestamp;
}