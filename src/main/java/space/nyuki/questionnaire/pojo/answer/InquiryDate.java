package space.nyuki.questionnaire.pojo.answer;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
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
@NoArgsConstructor
@ApiModel("答题方框--日期框")
public class InquiryDate implements AnswerCell{
    @ApiModelProperty(value = "日期内容", example = "1996-07-01")
    @NotNull(
            message = "日期不能为空",
            groups = {
                    GroupView.Input.class
            }
    )
    private Timestamp date;
}