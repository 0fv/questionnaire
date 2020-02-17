package space.nyuki.questionnaire.pojo.result;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class ResultChoice implements ResultCell {
	private String title;
	private List<String> answer;
	@JsonProperty("must_answer")
	private boolean mustAnswer;
	@JsonProperty("is_multi")
	private boolean isMulti;
}
