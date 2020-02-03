package space.nyuki.questionnaire.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import space.nyuki.questionnaire.group.GroupView;

import java.util.Date;
import java.util.List;

@Data
@Document("questionnaire_entity")
public class QuestionnaireEntity {
	@Id
	@JsonView(GroupView.View.class)
	private String id;
	@Field
	@JsonView(GroupView.View.class)
	private String title;
	@Field
	@JsonView(GroupView.View.class)
	private String introduce;
	@Field("created_account")
	@JsonView(GroupView.View.class)
	@JsonProperty("created_account")
	private String createdAccount;
	@Field
	@JsonView(GroupView.View.class)
	private Date from;
	@Field
	@JsonView(GroupView.View.class)
	private Date to;
	@Field("question_group")
	@JsonProperty("question_group")
	private List<QuestionGroup> questionGroups;
	@Field("is_anonymous")
	@JsonProperty("is_anonymous")
	@JsonView(GroupView.View.class)
	//0 匿名 //1 不匿名
	private Integer isAnonymous;
	@Field("member_group_name")
	@JsonProperty("member_group_name")
	private List<String> memberGroupName;
	@Field
	private List<Member> members;
	@Field
	//0 不分页，//1 按组分页 //2 按pageSize条数分页
	private Integer pagination;
	@Field("page_size")
	@JsonProperty("page_size")
	private Integer pageSize;
	@Field("is_finish")
	@JsonProperty("is_finish")
	private int isFinish;
	@Field("is_delete")
	@JsonProperty("is_delete")
	private int isDelete;


}
