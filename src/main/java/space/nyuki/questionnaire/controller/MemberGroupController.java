package space.nyuki.questionnaire.controller;

import com.fasterxml.jackson.annotation.JsonView;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import space.nyuki.questionnaire.factory.TransFactory;
import space.nyuki.questionnaire.group.GroupView;
import space.nyuki.questionnaire.pojo.MemberGroup;
import space.nyuki.questionnaire.pojo.TransData;
import space.nyuki.questionnaire.service.MemberGroupService;

@RestController
@RequestMapping("memberGroup")
@RequiresPermissions("inquiry_crew:w")
public class MemberGroupController {
	@Autowired
	private MemberGroupService memberGroupService;

	@GetMapping
	@RequiresPermissions("inquiry_crew:r")
	@JsonView(GroupView.View.class)
	public TransData getData() {
		return TransFactory.getSuccessResponse(
				memberGroupService.getData()
		);
	}

	@PutMapping
	public TransData updateData(
			@RequestHeader(name = "token")
					String token,
			@RequestBody
			@JsonView(GroupView.Update.class)
			@Validated(GroupView.Update.class)
					MemberGroup memberGroup,
			BindingResult result
	) {
		memberGroupService.updateData(memberGroup, token);
		return TransFactory.getSuccessResponse();
	}

	@PostMapping
	public TransData addData(
			@RequestHeader(name = "token")
					String token,
			@RequestBody
			@JsonView(GroupView.Create.class)
			@Validated(GroupView.Create.class)
					MemberGroup memberGroup,
			BindingResult result
	) {
		memberGroupService.addData(memberGroup, token);
		return TransFactory.getSuccessResponse();
	}

	@DeleteMapping("{id}")
	public TransData deleteData(
			@PathVariable(value = "id")
					String id
	) {
		memberGroupService.deleteData(id);
		return TransFactory.getSuccessResponse();
	}
}
