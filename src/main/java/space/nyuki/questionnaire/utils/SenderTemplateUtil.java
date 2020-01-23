package space.nyuki.questionnaire.utils;

import space.nyuki.questionnaire.pojo.Member;
import space.nyuki.questionnaire.pojo.QuestionnaireEntity;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SenderTemplateUtil {
	public static String generateMailContent(Member member, QuestionnaireEntity questionnaireEntity,
	                                         String mailTemplate, String baseUrl) {
		return mailTemplate.replace("${name}", member.getName())
				.replace("${title}", questionnaireEntity.getTitle())
				.replace("${from}", getDateString(questionnaireEntity.getFrom()))
				.replace("${to}", getDateString(questionnaireEntity.getTo()))
				.replace("${url}", getUrl(baseUrl, member, questionnaireEntity));
	}

	public static String getDateString(Date date) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return simpleDateFormat.format(date);
	}

	private static String getUrl(String baseUrl, Member member, QuestionnaireEntity questionnaireEntity) {
		String m = member.getEigenvalue();
		String q = questionnaireEntity.getId();
		return baseUrl + "/" + q + "/" + m;
	}

}
