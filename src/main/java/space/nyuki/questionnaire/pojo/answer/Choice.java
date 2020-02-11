package space.nyuki.questionnaire.pojo.answer;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Field;
import space.nyuki.questionnaire.group.GroupView;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author ning
 * @createTime 12/1/19 1:48 PM
 * @description 选择型
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.ALWAYS)
@ApiModel("答题方框--单/多选")
public class Choice implements AnswerCell {
	@NotNull(
			message = "选项不能为空",
			groups = {
					GroupView.Create.class
			}
	)
	@ApiModelProperty(value = "选项以及答案", example = "{\"选择?\",\"true\"}")
	private List<String> choice;
	@NotNull(
			message = "请选择是否为多选",
			groups = {
					GroupView.Create.class
			}
	)
	@ApiModelProperty(value = "是否为多选")
	@Field("is_multi")
	@JsonProperty("is_multi")
	private Boolean isMulti;
	private List<String> answer;
	private List<Integer> index;
}