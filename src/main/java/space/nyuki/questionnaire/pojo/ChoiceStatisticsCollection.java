package space.nyuki.questionnaire.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import space.nyuki.questionnaire.group.GroupView;

import java.util.List;

@Data
@Document("result_statistics_collection")
public class ChoiceStatisticsCollection {
	@Id
	@JsonView(GroupView.View.class)
	private String id;
	@JsonView(GroupView.View.class)
	private String title;
	@JsonView(GroupView.View.class)
	@JsonProperty("choice_statistics_groups")
	@Field("choice_statistics_groups")
	private List<ChoiceStatisticsGroup> choiceStatisticsGroups;
	@JsonProperty("last_fingerprint_id")
	@Field("last_id")
	private String lastId;
}
