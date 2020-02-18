package space.nyuki.questionnaire.handler;

import space.nyuki.questionnaire.pojo.result.ResultCell;
import space.nyuki.questionnaire.pojo.result.ResultChoice;

import java.util.List;

public class ChoiceResultHandler extends ResultStringHandler {
	public ChoiceResultHandler() {
		this.type = ResultChoice.class;
	}

	@Override
	protected String getString(ResultCell resultCell) {
		ResultChoice resultChoice = (ResultChoice) resultCell;
		List<String> answer = resultChoice.getAnswer();
		StringBuilder result = new StringBuilder();
		for (String s : answer) {
			result.append(s).append(" ");
		}
		return result.toString();
	}
}
