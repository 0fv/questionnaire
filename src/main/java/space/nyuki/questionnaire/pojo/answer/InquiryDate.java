package space.nyuki.questionnaire.pojo.answer;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import space.nyuki.questionnaire.group.GroupView;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * @author ning
 * @createTime 12/1/19 1:53 PM
 * @description 时间型
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("答题方框--日期框")
public class InquiryDate implements AnswerCell {
	@ApiModelProperty(value = "日期内容", example = "1996-07-01")
	@NotNull(
			message = "日期不能为空",
			groups = {
					GroupView.Input.class
			}
	)
	private Date answer;
	private Boolean vdate;
	private Boolean vtime;
	private List<Integer> index;
}