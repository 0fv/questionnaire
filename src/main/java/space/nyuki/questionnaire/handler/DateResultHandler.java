package space.nyuki.questionnaire.handler;

import space.nyuki.questionnaire.pojo.result.ResultCell;
import space.nyuki.questionnaire.pojo.result.ResultDate;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateResultHandler extends ResultStringHandler {
	public DateResultHandler() {
		this.type = ResultDate.class;
	}

	@Override
	protected String getString(ResultCell resultCell) {
		ResultDate resultDate = (ResultDate) resultCell;
		Date answer = resultDate.getAnswer();
		Boolean vdate = resultDate.getVdate();
		Boolean vtime = resultDate.getVtime();
		if (answer == null) {
			return "";
		}
		if (!vdate && !vtime) {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			return simpleDateFormat.format(answer);
		} else if (!vdate) {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
			return simpleDateFormat.format(answer);
		} else if (!vtime) {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
			return simpleDateFormat.format(answer);
		}
		return "";
	}
}
