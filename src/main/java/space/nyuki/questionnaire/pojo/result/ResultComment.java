package space.nyuki.questionnaire.pojo.result;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ResultComment implements ResultCell {
	private String title;
	private String answer;
	@JsonProperty("must_answer")
	private boolean mustAnswer;}
