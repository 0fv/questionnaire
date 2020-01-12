package space.nyuki.questionnaire.utils;

import java.util.UUID;

/**
 * @author ning
 * @createTime 12/1/19 3:27 PM
 * @description 生成uuid
 */
public class UUIDUtil {
	public static String getUUID32() {
		return UUID.randomUUID().toString().replace("-", "").toLowerCase();

	}
}