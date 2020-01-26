package space.nyuki.questionnaire.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import space.nyuki.questionnaire.group.GroupView;

import javax.validation.constraints.NotBlank;
import java.util.Date;

@Data
@Document("member_group")
public class MemberGroup {
	@Id
	@JsonView({
			GroupView.View.class,
			GroupView.Update.class
	})
	@NotBlank(
			message = "id不能为空",
			groups = {
					GroupView.Update.class,
			}
	)
	private String id;
	@Field("group_name")
	@JsonProperty("group_name")
	@JsonView({
			GroupView.View.class,
			GroupView.Update.class,
			GroupView.Create.class
	})
	@NotBlank(
			message = "组名不能为空",
			groups = {
					GroupView.Update.class,
					GroupView.Create.class
			}
	)
	private String groupName;
	@Field("created_time")
	@JsonView({
			GroupView.View.class
	})
	@JsonProperty("created_time")
	private Date createdDate;
	@Field("created_account")
	@JsonProperty("created_account")
	@JsonView({
			GroupView.View.class
	})
	private String createdAccount;
	@JsonView({
			GroupView.View.class
	})
	@Field("edited_time")
	@JsonProperty("edited_time")
	private Date editedDate;
	@JsonView({
			GroupView.View.class
	})
	@Field("edited_account")
	@JsonProperty("edited_account")
	private String editedAccount;
	@Field("is_delete")
	private int isDelete;
}
