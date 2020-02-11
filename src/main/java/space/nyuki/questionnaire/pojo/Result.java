package space.nyuki.questionnaire.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import space.nyuki.questionnaire.pojo.answer.AnswerCell;

import java.util.List;

@Data
public class Result {
	private String title;
	@JsonProperty("answer_cells")
	private List<AnswerCell> answerCells;
	@JsonProperty("must_answer")
	private int mustAnswer;
}
