package space.nyuki.questionnaire.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;
import java.util.List;

@Data
@Document("questionnaire_entity")
public class QuestionnaireEntity {
	@Id
	private String id;
	@Field
	private String title;
	@Field
	private String introduce;
	@Field
	private Date from;
	@Field
	private Date to;
	@Field("question_group")
	@JsonProperty("question_group")
	private List<QuestionGroup> questionGroups;
	@Field("is_anonymous")
	@JsonProperty("is_anonymous")
	private Integer isAnonymous;
	@JsonProperty("member_group")
	@Field("member_group")
	private String memberGroup;
	@Field("is_finish")
	@JsonProperty("is_finish")
	private int isFinish;
	@Field("is_delete")
	@JsonProperty("is_delete")
	private int isDelete;


}
