package space.nyuki.questionnaire.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import space.nyuki.questionnaire.group.GroupView;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Data
@Document("sender")
public class MailInfo {
	@Id
	@JsonProperty(defaultValue = "mailInfo")
	private String id;
	@Field
	@JsonView(GroupView.Update.class)
	private String protocol;
	@Field
	@NotBlank(message = "stmp服务器地址不能为空")
	@JsonView(GroupView.Update.class)
	private String host;
	@Field
	@Min(value = 1,message = "端口号在0～60035之间")
	@Max(value = 65535,message = "端口号在0～60035之间")
	@JsonView(GroupView.Update.class)
	private Integer port;
	@Field
	@NotBlank(message = "用户名不能为空")
	@JsonView(GroupView.Update.class)
	private String username;
	@Field
	@NotBlank(message = "密码不能为空")
	@JsonView(GroupView.Update.class)
	private String password;
	@Field
	@NotBlank(message = "发送者不能为空")
	@JsonView(GroupView.Update.class)
	private String from;
	@Field
	@NotBlank(message = "标题不能为空")
	@JsonView(GroupView.Update.class)
	private String subject;
	@Field
	@NotBlank(message = "模板不能为空")
	@JsonView(GroupView.Update.class)
	private String template;
}
