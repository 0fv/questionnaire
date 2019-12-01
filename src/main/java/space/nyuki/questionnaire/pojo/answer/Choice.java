package space.nyuki.questionnaire.pojo.answer;

import lombok.AllArgsConstructor;
import lombok.Data;
import space.nyuki.questionnaire.group.GroupView;

import javax.validation.constraints.NotNull;
import java.util.Map;

/**
 * @author ning
 * @createTime 12/1/19 1:48 PM
 * @description 选择型
 */
@Data
@AllArgsConstructor
public class Choice implements AnswerCell{
    @NotNull(
            message = "选项不能为空",
            groups = {
                    GroupView.Create.class
            }
    )
    private Map<String,Boolean> choice;
    @NotNull(
            message = "请选择是否为多选",
            groups = {
                    GroupView.Create.class
            }
    )
    private Boolean isMulti;
}