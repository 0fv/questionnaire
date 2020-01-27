package space.nyuki.questionnaire.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import space.nyuki.questionnaire.pojo.Member;
import space.nyuki.questionnaire.utils.MapUtil;

import java.util.List;
import java.util.Map;

@Service
public class MemberService {
	@Autowired
	private MongoTemplate mongoTemplate;
	@Autowired
	private MemberGroupService memberGroupService;

	@Transactional
	public void addData(String gid, Member member, String token) {
		member.setGid(gid);
		mongoTemplate.save(member);
		memberGroupService.updateEditDate(token,gid);
	}

	@Transactional
	public void updateData(String gid,Member member,String token) {
		Update update = new Update();
		String id = member.getId();
		Map<String, Object> map = MapUtil.objectToMap(member);
		map.forEach(update::set);
		mongoTemplate.findAndModify(
				Query.query(Criteria.where("_id").is(id)),
				update,
				Member.class
		);
		memberGroupService.updateEditDate(token,gid);
	}

	@Transactional
	public void deleteData(String gid,String id,String token) {
		mongoTemplate.remove(Query.query(Criteria.where("_id").is(id)), Member.class);
		memberGroupService.updateEditDate(token,gid);
	}

	public List<Member> getData(String gid) {
		return mongoTemplate.find(Query.query(Criteria.where("gid").is(gid)), Member.class);
	}
}
