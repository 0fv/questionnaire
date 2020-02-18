package space.nyuki.questionnaire.handler;

import space.nyuki.questionnaire.pojo.result.ResultCell;
import space.nyuki.questionnaire.pojo.result.ResultComment;

public class CommentResultHandler extends ResultStringHandler {
	public CommentResultHandler() {
		this.type = ResultComment.class;
	}

	@Override
	protected String getString(ResultCell resultCell) {
		ResultComment comment = (ResultComment) resultCell;
		String answer = comment.getAnswer();
		if (answer == null) {
			return "";
		} else {
			return answer;
		}
	}
}
