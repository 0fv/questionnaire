package space.nyuki.questionnaire.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Field;
import space.nyuki.questionnaire.group.GroupView;

import java.util.Map;

@Data
public class ChoiceStatistics {
	@JsonView(GroupView.View.class)
	private String title;
	@Field("must_answer")
	@JsonProperty("must_answer")
	@JsonView(GroupView.View.class)
	private boolean mustAnswer;
	@JsonView(GroupView.View.class)
	private Map<String, Long> choice;
	private int groupIndex;
	private int choiceIndex;
}
