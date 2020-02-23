package space.nyuki.questionnaire.controller;

import com.fasterxml.jackson.annotation.JsonView;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import space.nyuki.questionnaire.factory.TransFactory;
import space.nyuki.questionnaire.group.GroupView;
import space.nyuki.questionnaire.pojo.MailInfo;
import space.nyuki.questionnaire.pojo.TransData;
import space.nyuki.questionnaire.service.MailSendScheduleService;
import space.nyuki.questionnaire.service.MailSenderService;

@RestController
@RequestMapping("mail")
@RequiresPermissions("mail_management:w")
public class MailController {
	@Autowired
	private MailSenderService mailSenderService;
	@Autowired
	private MailSendScheduleService mailSendScheduleService;

	@GetMapping
	@RequiresPermissions("mail_management:r")
	public TransData getData() {
		return TransFactory.getSuccessResponse(mailSenderService.getMailInfo());
	}

	@PutMapping
	public TransData updateData(@RequestBody
	                            @Validated
	                            @JsonView(GroupView.Update.class)
			                            MailInfo mailInfo,
	                            BindingResult result
	) {
		mailSenderService.setMailInfo(mailInfo);
		return TransFactory.getSuccessResponse();
	}

	@GetMapping("log")
	@RequiresPermissions("mail_management:r")
	public TransData getLogData() {
		return TransFactory.getSuccessResponse(mailSenderService.getLogData());
	}

	@GetMapping("schedule")
	@RequiresPermissions("mail_management:r")
	@JsonView(GroupView.View.class)
	public TransData getSchedule() {
		return TransFactory.getSuccessResponse(mailSenderService.getScheduleData());
	}

	@PostMapping("{id}")
	public TransData sendNow(@PathVariable(name = "id") String id) {
		mailSendScheduleService.sendNow(id);
		return TransFactory.getSuccessResponse();
	}
}
