package space.nyuki.questionnaire.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import space.nyuki.questionnaire.pojo.*;
import space.nyuki.questionnaire.pojo.answer.AnswerCell;
import space.nyuki.questionnaire.pojo.answer.Choice;
import space.nyuki.questionnaire.pojo.answer.Comment;
import space.nyuki.questionnaire.pojo.answer.InquiryDate;
import space.nyuki.questionnaire.pojo.result.ResultCell;
import space.nyuki.questionnaire.pojo.result.ResultChoice;
import space.nyuki.questionnaire.pojo.result.ResultComment;
import space.nyuki.questionnaire.pojo.result.ResultDate;
import space.nyuki.questionnaire.utils.JWTUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class QuestionnaireEntityCreateService {
	@Autowired
	private MemberService memberService;
	@Autowired
	private MemberGroupService memberGroupService;
	@Autowired
	private MailSenderService mailSenderService;
	@Autowired
	private MailSendScheduleService mailSendScheduleService;
	@Autowired
	private QuestionnaireEntityService questionnaireEntityService;

	@Transactional
	@Async("taskExecutor")
	public void createNewInstance(QuestionnaireCreate create, Questionnaire questionnaire, String token) {
		String username = JWTUtil.getUsername(token);
		QuestionnaireEntity questionnaireEntity = new QuestionnaireEntity();
		questionnaireEntity.setTitle(create.getName());
		questionnaireEntity.setCreatedAccount(username);
		questionnaireEntity.setFrom(create.getFrom());
		questionnaireEntity.setTo(create.getTo());
		questionnaireEntity.setIntroduce(questionnaire.getIntroduce());
		questionnaireEntity.setIsAnonymous(create.getIsAnonymous());
		questionnaireEntity.setPagination(create.getPagination());
		questionnaireEntity.setQuestionGroups(questionnaire.getQuestionGroups());
		setIndexAndSubmitTemplate(questionnaireEntity);
		if (create.getPagination() == 2) {
			questionnaireEntity.setPageSize(create.getPageSize());
		}
		if (create.getIsAnonymous() == 1) {
			List<String> memberId = create.getMemberId();
			List<Member> members = memberService.getMembersById(memberId);
			List<String> memberGroupNameList = memberGroupService.getGroupName(memberId);
			questionnaireEntity.setMembers(members);

			questionnaireEntity.setMemberGroupName(memberGroupNameList);
			Date sendMailTime = create.getSendMailTime();
			QuestionnaireEntity q = questionnaireEntityService.save(questionnaireEntity);
			if (sendMailTime.before(new Date())) {
				mailSenderService.sendMail(members, q);
			} else {
				mailSendScheduleService.addSchedule(members, memberGroupNameList, q, sendMailTime);
			}
		} else {
			questionnaireEntityService.save(questionnaireEntity);
		}
	}

	private void setIndexAndSubmitTemplate(QuestionnaireEntity questionnaireEntity) {
		List<QuestionGroup> groups = questionnaireEntity.getQuestionGroups();
		SubmitResult submitResult = new SubmitResult();
		List<SubmitResultGroup> submitResultGroups = new ArrayList<>();
		for (int i = 0; i < groups.size(); i++) {
			SubmitResultGroup submitResultGroup = new SubmitResultGroup();
			QuestionGroup questionGroup = groups.get(i);
			submitResultGroup.setGroupTitle(questionGroup.getTitle());
			List<QuestionCell> questionCells = questionGroup.getQuestionCells();
			List<ResultCell> resultCells = new ArrayList<>();
			for (int a = 0; a < questionCells.size(); a++) {
				QuestionCell questionCell = questionCells.get(a);
				questionCell.setIndex(Arrays.asList(i, a));
				List<AnswerCell> answerCells = questionCell.getAnswerCells();
				AnswerCell answerCell = answerCells.get(0);
				ResultCell resultCell = resultCellAdapter(answerCell);
				assert resultCell != null;
				resultCell.setTitle(questionCell.getTitle());
				resultCell.setMustAnswer(questionCell.getMustAnswer() == 1);
				resultCells.add(resultCell);
				for (int b = 0; b < answerCells.size(); b++) {
					answerCells.get(b).setIndex(Arrays.asList(i, a, b));
				}
			}
			submitResultGroup.setResultCells(resultCells);
			submitResultGroups.add(submitResultGroup);
		}
		submitResult.setSubmitResultGroups(submitResultGroups);
		questionnaireEntity.setSubmitResultTemplate(submitResult);
	}

	private ResultCell resultCellAdapter(AnswerCell answerCell) {
		Class<? extends AnswerCell> type = answerCell.getClass();
		if (type.equals(Choice.class)) {
			Choice choice = (Choice) answerCell;
			ResultChoice resultChoice = new ResultChoice();
			resultChoice.setAnswer(new ArrayList<>());
			resultChoice.setMulti(choice.getIsMulti());
			return resultChoice;
		} else if (type.equals(Comment.class)) {
			return new ResultComment();
		} else if (type.equals(InquiryDate.class)) {
			InquiryDate date = (InquiryDate) answerCell;
			ResultDate resultDate = new ResultDate();
			resultDate.setVdate(date.getVdate());
			resultDate.setVtime(date.getVtime());
			return resultDate;
		} else {
			return null;
		}
	}
}
