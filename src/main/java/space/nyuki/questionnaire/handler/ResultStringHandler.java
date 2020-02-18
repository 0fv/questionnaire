package space.nyuki.questionnaire.handler;

import space.nyuki.questionnaire.pojo.result.ResultCell;

public abstract class ResultStringHandler {
	protected Class<? extends ResultCell> type;
	protected ResultStringHandler nextHandler;

	public void setNextHandler(ResultStringHandler resultStringHandler) {
		this.nextHandler = resultStringHandler;
	}

	public String getResultString(ResultCell resultCell) {
		Class<? extends ResultCell> type = resultCell.getClass();
		if (type.equals(this.type)) {
			return getString(resultCell);
		} else {
			return this.nextHandler.getResultString(resultCell);
		}
	}

	abstract protected String getString(ResultCell resultCell);
}
