package space.nyuki.questionnaire.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Document("sender")
public class BaseUrl {
	@Id
	@JsonProperty(defaultValue = "baseUrl")
	private String id;
	@Field
	private String url;
}
