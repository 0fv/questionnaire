package space.nyuki.questionnaire.pojo.result;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;

@Data
public class ResultDate implements ResultCell {
	private String title;
	private Date answer;
	private Boolean vdate;
	private Boolean vtime;
	@JsonProperty("must_answer")
	private boolean mustAnswer;
}
