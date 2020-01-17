package space.nyuki.questionnaire.pojo.answer;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel("答题方框--自填框")
public class Comment implements AnswerCell {
	@ApiModelProperty(value = "自填框内容", example = "我发功出自内心")
	@NotNull(
			message = "评论不能为null",
			groups = {
					GroupView.Input.class
			}
	)
	private String comment;
	private Integer limit;
	private Integer line;
	private Boolean empty;
}