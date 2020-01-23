package space.nyuki.questionnaire;

import lombok.var;
import org.junit.jupiter.api.Test;

public class TemplateTest {
	String test = "<html>\n" +
			"<body>\n" +
			"    <h3>${User}</h3>\n" +
			"</body>\n" +
			"</html>";

	@Test
	public void test() {
		var d = test.replace("${}", "ning").replace("<html>", "sdf");
		System.out.println(d);
	}
}
