package space.nyuki.questionnaire.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import space.nyuki.questionnaire.group.GroupView;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Document(collection = "question_collection")
@Data
@ApiModel("问题收藏")
public class QuestionCollection {
	@Id
	@JsonProperty("_id")
	@JsonView({
			GroupView.View.class,
			GroupView.Update.class
	})
	@NotNull(
			message = "id不能为空",
			groups = {GroupView.Update.class
			}
	)
	private String id;
	@Field("created_time")
	@JsonProperty("created_time")
	private Date createdDate;
	@Field("created_account")
	@JsonProperty("created_account")
	private String createdAccount;

	@Field("edited_time")
	@JsonProperty("edited_time")
	private Date editedDate;

	@Field("edited_account")
	@JsonProperty("edited_account")
	private String editedAccount;

	@Field("question_cell")
	@JsonProperty("question_cell")
	@NotNull(
			message = "问题不能为空",
			groups = {
					GroupView.Create.class,
					GroupView.Update.class
			}
	)
	private QuestionCell questionCell;

	private String classification;
	private String type;
	@Field("is_delete")
	@JsonProperty("is_delete")
	private int isDelete;
}
