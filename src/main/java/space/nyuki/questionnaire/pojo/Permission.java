package space.nyuki.questionnaire.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Field;
import space.nyuki.questionnaire.group.GroupView;

@Data
@JsonView({
		GroupView.LoginInfo.class,
		GroupView.Create.class,
		GroupView.GetById.class,
		GroupView.UpdateAccess.class
})
public class Permission {
	private String questionnaire;
	@JsonProperty("question_cells")
	@Field("question_cells")
	private String questionCells;
	@JsonProperty("question_groups")
	@Field("question_groups")
	private String questionGroups;
	@JsonProperty("inquiry_crew")
	@Field("inquiry_crew")
	private String inquiryCrew;
	@JsonProperty("inquiry_config")
	@Field("inquiry_config")
	private String inquiryConfig;
	@JsonProperty("result_show")
	@Field("result_show")
	private String resultShow;
	@JsonProperty("template_control")
	@Field("template_control")
	private String templateControl;
	@JsonProperty("account_management")
	@Field("account_management")
	private String accountManagement;
	@JsonProperty("mail_management")
	@Field("mail_management")
	private String mailManagement;
}
