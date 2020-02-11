package space.nyuki.questionnaire.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.*;
import java.util.Date;
import java.util.List;

@Data
public class QuestionnaireCreate {
	@NotBlank(message = "问卷id不能为空")
	@JsonProperty("questionnaire_id")
	private String questionnaireId;
	@NotBlank(message = "名称不能为空")
	@JsonProperty("name")
	private String name;
	@Max(value = 1, message = "匿名参数错误")
	@Min(value = 0, message = "匿名参数错误")
	@JsonProperty("is_anonymous")
	private Integer isAnonymous;
	@JsonProperty("member_id")
	private List<String> memberId;
	@JsonProperty("send_mail_time")
	private Date sendMailTime;
	@NotNull(message = "开始时间不能为空")
	private Date from;
	@Future(message ="结束时间必须大于当前时间")
	private Date to;
	@Max(value = 2, message = "匿名参数错误")
	@Min(value = 0, message = "匿名参数错误")
	private Integer pagination;
	@Min(value = 1,message = "分页数量必须大于0")
	@JsonProperty("page_size")
	private Integer pageSize;
}
