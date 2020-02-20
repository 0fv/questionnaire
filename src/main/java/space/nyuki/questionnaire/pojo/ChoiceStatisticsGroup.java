package space.nyuki.questionnaire.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Field;
import space.nyuki.questionnaire.group.GroupView;

import java.util.List;

@Data
public class ChoiceStatisticsGroup {
	@JsonView(GroupView.View.class)
	@Field("group_title")
	@JsonProperty("group_title")
	private String groupTitle;
	@Field("choice_statistics")
	@JsonProperty("choice_statistics")
	@JsonView(GroupView.View.class)
	private List<ChoiceStatistics> choiceStatistics;
}
