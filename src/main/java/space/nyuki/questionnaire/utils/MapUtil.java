package space.nyuki.questionnaire.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;
import java.util.Objects;

/**
 * @author ning
 * @createTime 12/1/19 5:10 PM
 * @description 将pojo 转为map形式 去除空值，方便更新
 */
public class MapUtil {
	public static Map<String, Object> objectToMap(Object o) {
		ObjectMapper m = new ObjectMapper();
		Map<String, Object> props = m.convertValue(o, Map.class);
		props.values().removeIf(Objects::isNull);
		return props;
	}
}