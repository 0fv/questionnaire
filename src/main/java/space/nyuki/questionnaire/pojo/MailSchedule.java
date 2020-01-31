package space.nyuki.questionnaire.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;
import java.util.List;

@Data
@Document("mail_schedule")
public class MailSchedule {
	@Id
	private String id;
	@Field("member_group_name")
	@JsonProperty("member_group_name")
	private List<String> memberGroupName;
	@Field
	private List<Member> members;
	@Field("questionnaire_entity")
	private QuestionnaireEntity questionnaireEntity;
	@JsonProperty("questionnaire_title")
	private String questionnaireTitle;
	@JsonProperty("sending_time")
	@Field("sending_time")
	private Date sendingTime;
	@Field("is_delete")
	private int isDelete;
}
