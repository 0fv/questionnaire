package space.nyuki.questionnaire.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Field;
import space.nyuki.questionnaire.group.GroupView;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author ning
 * @createTime 12/22/19 2:37 PM
 * @description 问题组
 */
@Data
@ApiModel("问题组")
public class QuestionGroup {
    @NotNull(
            message = "问题组名不能为空",
            groups = {GroupView.Create.class}
    )
    @JsonProperty("group_name")
    @Field("group_name")
    @ApiModelProperty(value="问题组名",example = "xxx问题组")
    private String groupName;
    @ApiModelProperty(value = "问题组成员",example = "sdfsf")
    @Field("question_cells")
    @JsonProperty("question_cells")
    private List<QuestionCell> questionCells;
}