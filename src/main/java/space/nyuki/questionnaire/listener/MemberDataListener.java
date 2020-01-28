package space.nyuki.questionnaire.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import org.springframework.data.mongodb.core.MongoTemplate;
import space.nyuki.questionnaire.exception.FormatNotCorrectException;
import space.nyuki.questionnaire.group.GroupView;
import space.nyuki.questionnaire.pojo.Member;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class MemberDataListener extends AnalysisEventListener<Member> {
	private MongoTemplate mongoTemplate;
	private String gid;

	public MemberDataListener(MongoTemplate mongoTemplate, String gid) {
		this.mongoTemplate = mongoTemplate;
		this.gid = gid;
	}

	private List<Member> list = new ArrayList<>();

	@Override
	public void invoke(Member member, AnalysisContext analysisContext) {
		member.setGid(gid);
		ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
		Validator validator = validatorFactory.getValidator();
		Set<ConstraintViolation<Member>> validate = validator.validate(member, GroupView.Create.class);
		validate.stream().filter(t -> Objects.nonNull(t.getMessage())).forEach(t -> {
			throw new FormatNotCorrectException(" " + (list.size() + 2) + "行" + " " + t.getMessage());
		});
		list.add(member);
	}

	@Override
	public void doAfterAllAnalysed(AnalysisContext analysisContext) {
		save();
	}

	public void save() {
		list.forEach(mongoTemplate::save);
	}
}

