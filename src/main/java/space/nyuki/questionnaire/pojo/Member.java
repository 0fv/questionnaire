package space.nyuki.questionnaire.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import space.nyuki.questionnaire.group.GroupView;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@Document("member")
public class Member {
	@Id
	@NotBlank(
			message = "id不能为空",
			groups = GroupView.Update.class
	)
	@JsonView(GroupView.View.class)
	private String id;
	@Field
	private String gid;
	@Field
	@NotBlank(
			message = "姓名不能为空",
			groups = GroupView.Create.class
	)
	@JsonView(GroupView.View.class)
	private String name;
	@Field
	@Email(
			message = "请输入正确的邮件地址",
			groups = GroupView.Create.class
	)
	@JsonView(GroupView.View.class)
	private String email;
	@Field("additional_info")
	@JsonProperty("additional_info")
	@JsonView(GroupView.View.class)
	private String additionalInfo;
}
