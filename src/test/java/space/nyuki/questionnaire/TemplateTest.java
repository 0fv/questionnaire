package space.nyuki.questionnaire;

import lombok.var;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;

@SpringBootTest
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
	@Test
	public void test3() throws IOException {
		ClassPathResource resource = new ClassPathResource("人员模板.xlsx");
		System.out.println(resource.getPath().toString());
	}
}
