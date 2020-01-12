package space.nyuki.questionnaire.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Field;
import space.nyuki.questionnaire.group.GroupView;

@Data
@JsonView({
		GroupView.Get.class
})
public class Permission {
	private boolean questionnaire;
	@JsonProperty("question_cells")
	@Field("question_cells")
	private boolean questionCells;
	@JsonProperty("question_groups")
	@Field("question_groups")
	private boolean questionGroups;
	@JsonProperty("inquiry_crew")
	@Field("inquiry_crew")
	private boolean inquiryCrew;
	@JsonProperty("inquiry_config")
	@Field("inquiry_config")
	private boolean inquiryConfig;
	@JsonProperty("result_show")
	@Field("result_show")
	private boolean resultShow;
	@JsonProperty("template_control")
	@Field("template_control")
	private boolean tmeplateControl;
	@JsonProperty("account_management")
	@Field("account_management")
	private boolean accountManagement;
}
