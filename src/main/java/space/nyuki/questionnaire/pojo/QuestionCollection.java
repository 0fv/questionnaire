package space.nyuki.questionnaire.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import space.nyuki.questionnaire.group.GroupView;
import space.nyuki.questionnaire.pojo.answer.AnswerCell;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Document(collection = "question_collection")
@Data
@ApiModel("问题收藏")
public class QuestionCollection {
	@Id
	@JsonProperty("id")
	@JsonView({
			GroupView.View.class,
			GroupView.Update.class,
			GroupView.Input.class
	})
	@NotNull(
			message = "id不能为空",
			groups = {GroupView.Update.class
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
			GroupView.Input.class
	})

	private String title;
	@Field("answer_cells")
	@JsonProperty("answer_cells")
	@NotNull(
			message = "内容不能为空",
			groups = {
					GroupView.Create.class,
			}
	)
	@JsonView({
			GroupView.Create.class,
			GroupView.Input.class
	})

	private List<AnswerCell> answerCells;
	@JsonView({
			GroupView.Create.class,
			GroupView.View.class,
			GroupView.Input.class
			})

	@NotNull(
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
