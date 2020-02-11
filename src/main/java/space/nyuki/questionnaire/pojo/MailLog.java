package space.nyuki.questionnaire.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

@Data
@Document("mail_log")
public class MailLog {
	@Id
	private String id;
	@JsonProperty("questionnaire_entity_id")
	@Field("questionnaire_entity_id")
	private String questionnaireEntityId;
	@Field
	private String title;
	@Field("send_time")
	@JsonProperty("send_time")
	private Date sendTime;
	@Field
	private String name;
	@Field
	private String email;
	@Field
	private int status;
	@Field
	private String message;
}
