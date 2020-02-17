package space.nyuki.questionnaire.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;
import java.util.List;

/**
 * @author ning
 * @createTime 12/2/19 11:28 AM
 * @description 调查结果表
 */
@Document
@Data
@NoArgsConstructor
@ApiModel("问卷调查结果")
public class ResultCollection {
	@Id
	private String id;
	@Field("finger_print")
	private String fingerPrint;
	@Field("entity_id")
	@JsonProperty("entity_id")
	private String entityId;
	@Field("submit_date")
	@JsonProperty("submit_date")
	private Date submitDate;
	@Field("submit_result_groups")
	@JsonProperty("submit_result_groups")
	private List<SubmitResultGroup> submitResultGroups;
	@Field("name")
	@JsonProperty("name")
	private String name;
}