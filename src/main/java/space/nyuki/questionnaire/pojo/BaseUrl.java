package space.nyuki.questionnaire.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import space.nyuki.questionnaire.group.GroupView;

import javax.validation.constraints.NotBlank;

@Data
@Document("sender")
public class BaseUrl {
	@Id
	@JsonProperty(defaultValue = "baseUrl")
	private String id;
	@Field
	@NotBlank(message = "服务器地址不能为空")
	@JsonView(GroupView.Update.class)
	private String url;
}
