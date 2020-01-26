package space.nyuki.questionnaire.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Document("member")
public class Member {
	@Id
	private String id;
	@Field
	private String gid;
	@Field
	private String name;
	@Field
	private String email;
	@Field("additional_info")
	@JsonProperty("additional_info")
	private String additionalInfo;
	@Field
	private String eigenvalue;
}
