package space.nyuki.questionnaire.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.NotNull;

@Data
@Document("sender")
public class MailInfo {
	@NotNull
	@Id
	@JsonProperty(defaultValue = "mailInfo")
	private String id;
	@Field
	private String protocol;
	@Field
	private String host;
	@Field
	private Integer port;
	@Field
	private String username;
	@Field
	private String password;
	@Field
	private String from;
}
