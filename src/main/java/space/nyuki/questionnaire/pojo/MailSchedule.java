package space.nyuki.questionnaire.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import space.nyuki.questionnaire.group.GroupView;

import java.util.Date;
import java.util.List;

@Data
@Document("mail_schedule")
public class MailSchedule {
	@Id
	@JsonView(GroupView.View.class)
	private String id;
	@Field("member_group_name")
	@JsonProperty("member_group_name")
	@JsonView(GroupView.View.class)
	private List<String> memberGroupName;
	@Field
	private List<Member> members;
	@Field("questionnaire_entity_id")
	@JsonProperty("questionnaire_entity_id")
	@JsonView(GroupView.View.class)
	private String questionnaireEntityId;
	@Field("questionnaire_title")
	@JsonProperty("questionnaire_title")
	@JsonView(GroupView.View.class)
	private String questionnaireTitle;
	@JsonView(GroupView.View.class)
	@JsonProperty("sending_time")
	@Field("sending_time")
	private Date sendingTime;
	@Field("is_delete")
	private int isDelete;
}
