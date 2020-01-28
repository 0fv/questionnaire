package space.nyuki.questionnaire.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;

import java.io.IOException;

@SpringBootTest
class MemberServiceTest {
	@Autowired
	MemberService memberService;

	@Test
	void exportData() throws IOException {
		Resource resource = memberService.exportData("5e2e86dd1e05a076396c14c2");
	}
}