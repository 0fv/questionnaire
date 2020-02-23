package space.nyuki.questionnaire.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import space.nyuki.questionnaire.group.GroupView;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@Document(collection = "user")
@ApiModel("用户管理")
public class User {
	@Id
	@JsonProperty("id")
	@JsonView({
			GroupView.View.class,
			GroupView.Update.class,
			GroupView.LoginInfo.class,
			GroupView.UpdatePass.class,
			GroupView.GetById.class,
			GroupView.UpdateAccess.class
	})
	@NotBlank(
			message = "id不能为空",
			groups = {
					GroupView.Update.class,
					GroupView.UpdateAccess.class,
					GroupView.UpdatePass.class
			}
	)
	private String id;
	@NotBlank(
			message = "用户名不能为空",
			groups = {
					GroupView.Create.class,
					GroupView.Login.class
			}
	)
	@JsonView({
			GroupView.View.class,
			GroupView.Create.class,
			GroupView.Login.class,
			GroupView.LoginInfo.class,
			GroupView.GetById.class
			})
	private String username;
	@NotBlank(
			message = "密码不能为空",
			groups = {
					GroupView.Create.class,
					GroupView.Login.class,
					GroupView.UpdatePass.class
			}
	)
	@JsonView({
			GroupView.Create.class,
			GroupView.Login.class,
			GroupView.UpdatePass.class,
	})
	private String passwd;
	@JsonView({
			GroupView.View.class

	})
	@JsonProperty("created_time")
	@Field("created_time")
	private Date createdTime;
	@JsonView({
			GroupView.View.class,
			GroupView.LoginInfo.class
	})
	@JsonProperty("last_login")
	@Field("last_login")
	private Date lastLogin;

	@JsonView({
			GroupView.LoginInfo.class,
			GroupView.Create.class,
			GroupView.GetById.class,
			GroupView.UpdateAccess.class
	})
	@NotNull(
			message = "权限设置不能为空",
			groups = {
					GroupView.Create.class,
					GroupView.UpdateAccess.class
			}
	)
	private Permission permission;

	@JsonProperty("is_delete")
	@Field("is_delete")
	// 1 已删除 0 未删除
	private int isDelete;
	@JsonProperty("is_super")
	@Field("is_super")
	@JsonView({
			GroupView.LoginInfo.class,
			GroupView.View.class,
			GroupView.UpdateAccess.class,
			GroupView.Create.class,
			GroupView.GetById.class
	})
	// 1 管理员 0 不是
	private int isSuper;
}

