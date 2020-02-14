package space.nyuki.questionnaire.service;

import com.alibaba.excel.EasyExcel;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import space.nyuki.questionnaire.exception.CanNotCreateException;
import space.nyuki.questionnaire.exception.ElementNotFoundException;
import space.nyuki.questionnaire.listener.MemberExcelDataListener;
import space.nyuki.questionnaire.pojo.Member;
import space.nyuki.questionnaire.pojo.MemberGroup;
import space.nyuki.questionnaire.utils.MapUtil;

import java.io.File;
import java.net.MalformedURLException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class MemberService {
	@Autowired
	private MongoTemplate mongoTemplate;
	@Autowired
	private MemberGroupService memberGroupService;
	@Value("${temp-file.dir}")
	private String dir;

	@Transactional
	public void addData(String gid, Member member, String token) {
		member.setGid(gid);
		mongoTemplate.save(member);
		memberGroupService.updateEditDate(token, gid);
	}

	@Transactional
	public void updateData(String gid, Member member, String token) {
		Update update = new Update();
		String id = member.getId();
		Map<String, Object> map = MapUtil.objectToMap(member);
		map.forEach(update::set);
		mongoTemplate.findAndModify(
				Query.query(Criteria.where("_id").is(id)),
				update,
				Member.class
		);
		memberGroupService.updateEditDate(token, gid);
	}

	@Transactional
	public void deleteData(String gid, String id, String token) {
		mongoTemplate.remove(Query.query(Criteria.where("_id").is(id)), Member.class);
		memberGroupService.updateEditDate(token, gid);
	}

	public List<Member> getData(String gid) {
		return mongoTemplate.find(Query.query(Criteria.where("gid").is(gid)), Member.class);
	}

	@SneakyThrows
	public Resource getTemplateFile() {
		ClassPathResource resource = new ClassPathResource("人员模板.xlsx");
		return new UrlResource(resource.getURL());
	}

	@SneakyThrows
	@Transactional
	public void uploadData(String id, String token, MultipartFile file) {
		EasyExcel.read(file.getInputStream(), Member.class, new MemberExcelDataListener(mongoTemplate, id)).sheet().doRead();
		memberGroupService.updateEditDate(token, id);
	}

	public Resource exportData(String gid) throws MalformedURLException {
		List<Member> data = getData(gid);
		MemberGroup memberGroup = memberGroupService.getDataById(gid);
		if (Objects.isNull(memberGroup)) {
			throw new ElementNotFoundException();
		}
		String groupName = memberGroup.getGroupName();
		String fileDir = dir + File.separator + gid;
		String filename = fileDir + File.separator + groupName.trim() + ".xlsx";
		File file = new File(fileDir);
		if (!file.exists()) {
			boolean mkdir = file.mkdir();
			if (!mkdir) {
				throw new CanNotCreateException();
			}
		}
		EasyExcel.write(filename, Member.class).sheet("人员").doWrite(data);
		return new UrlResource(new File(filename).toURI().toASCIIString());
	}

	public List<Member> getMembersById(List<String> memberId) {
		List<MemberGroup> memberGroup = mongoTemplate.find(Query.query(Criteria.where("_id").in(memberId)), MemberGroup.class);
		return memberGroup.stream()
				.map(MemberGroup::getId)
				.map(this::getData)
				.flatMap(Collection::stream)
				.collect(Collectors.toCollection(ArrayList::new));
	}
}
