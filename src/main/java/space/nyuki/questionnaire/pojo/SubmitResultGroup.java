package space.nyuki.questionnaire.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import space.nyuki.questionnaire.group.GroupView;
import space.nyuki.questionnaire.pojo.result.ResultCell;

import java.util.List;

@Data
@JsonView(GroupView.InputView.class)
public class SubmitResultGroup {
	@JsonProperty("group_title")
	private String groupTitle;
	@JsonProperty("result_cells")
	private List<ResultCell> resultCells;
}
