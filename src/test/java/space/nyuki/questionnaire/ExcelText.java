package space.nyuki.questionnaire;

import com.alibaba.excel.EasyExcel;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import space.nyuki.questionnaire.pojo.Member;
import space.nyuki.questionnaire.service.MemberService;

import java.util.List;

@SpringBootTest
public class ExcelText {
	@Autowired
	private MemberService memberService;

	@Test
	public void test1() {
		String fileName = "/tmp/人员模板.xlsx";
//		EasyExcel.read(fileName, Member.class, new MemberDataListener("adfasdfasdfasdf")).sheet().doRead();
	}

	@Test
	public void test2() {
		List<Member> data = memberService.getData("5e2e86dd1e05a076396c14c2");
		EasyExcel.write("/tmp/fsdf/sdfsdf.xlsx", Member.class).sheet("人员").doWrite(data);
	}
}
