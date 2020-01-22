package space.nyuki.questionnaire.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import space.nyuki.questionnaire.group.GroupView;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Data
@Document(collection = "question_group_collection")
public class QuestionGroupCollection {
	@Id
	@JsonProperty("id")
	@JsonView({
			GroupView.View.class,
			GroupView.GetById.class,
			GroupView.Update.class
	})
	@NotNull(
			message = "id不能为空",
			groups = {
					GroupView.Update.class
			}
	)
	private String id;
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

	@JsonView({
			GroupView.Create.class,
			GroupView.View.class,
			GroupView.Update.class,
			GroupView.GetById.class
	})
	@NotBlank(
			message = "标题不能为空",
			groups = {
					GroupView.Create.class,
					GroupView.Update.class
			}
	)
	private String title;
	@Field("question_cells")
	@JsonProperty("question_cells")
	@NotNull(
			message = "内容不能为空",
			groups = {
					GroupView.Create.class,
			}
	)
	@JsonView({
			GroupView.Create.class,
			GroupView.GetById.class,
			GroupView.Update.class
	})

	private List<QuestionCell> questionCells;
	@JsonView({
			GroupView.Create.class,
			GroupView.View.class,
			GroupView.Update.class,
			GroupView.GetById.class
	})

	@NotBlank(
			message = "分类不能为空",
			groups = {
					GroupView.Create.class,
					GroupView.Update.class
			}
	)
	private String classification;
	@Field("is_delete")
	@JsonProperty("is_delete")
	private int isDelete;
}
