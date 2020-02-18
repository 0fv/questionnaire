package space.nyuki.questionnaire.pojo;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
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
	@ExcelIgnore
	@JsonView(GroupView.View.class)
	private String id;
	@Field
	@ExcelIgnore
	private String gid;
	@Field
	@NotBlank(
			message = "姓名不能为空",
			groups = GroupView.Create.class
	)
	@JsonView(GroupView.View.class)
	@ExcelProperty(index = 0,value = "姓名")
	private String name;
	@Field
	@Email(
			message = "请输入正确的邮件地址",
			groups = GroupView.Create.class
	)
	@JsonView(GroupView.View.class)
	@ExcelProperty(index = 1,value = "邮件地址")
	private String email;
	@Field("additional_info")
	@JsonProperty("additional_info")
	@ExcelProperty(index=2,value ="附加信息")
	@JsonView(GroupView.View.class)
	private String additionalInfo;
}
