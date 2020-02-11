package space.nyuki.questionnaire.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Data
@Document
public class ResultTemplate {
	@Id
	private String id;
	@JsonProperty("finger_print")
	@Field("finger_print")
	private String fingerPrint;
	@Field("result_groups")
	@JsonProperty("result_groups")
	private List<ResultGroup> resultGroups;
}
