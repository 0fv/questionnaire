package space.nyuki.questionnaire.controller;

import com.fasterxml.jackson.annotation.JsonView;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import space.nyuki.questionnaire.factory.TransFactory;
import space.nyuki.questionnaire.group.GroupView;
import space.nyuki.questionnaire.pojo.QuestionnaireEntity;
import space.nyuki.questionnaire.pojo.TransData;
import space.nyuki.questionnaire.service.QuestionnaireEntityService;

@RestController
@RequestMapping("questionnaireEntity")
@RequiresPermissions({"questionnaire:w","result_show:w"})
public class QuestionnaireEntityController {
	@Autowired
	private QuestionnaireEntityService questionnaireEntityService;

	@GetMapping
	@JsonView(GroupView.View.class)
	@RequiresPermissions({"questionnaire:r","result_show:r"})
	public TransData getData(@RequestParam(value = "isFinish", required = false, defaultValue = "0") int isFinish) {
		return TransFactory.getSuccessResponse(questionnaireEntityService.getData(isFinish));
	}

	@GetMapping("{id}/{mId}")
	@JsonView(GroupView.InputView.class)
	public TransData getDataById(@PathVariable(name = "id") String id, @PathVariable(name = "mId") String mId) {
		return TransFactory.getSuccessResponse(questionnaireEntityService.getDataById2(id, mId));
	}
	@GetMapping("{id}")
	@JsonView(GroupView.InputView.class)
	public TransData getDataById2(@PathVariable(name = "id") String id) {
		return TransFactory.getSuccessResponse(questionnaireEntityService.getDataById1(id,true));
	}

	@DeleteMapping("{id}")
	public TransData deleteData(@PathVariable(name = "id") String id) {
		questionnaireEntityService.deleteData(id);
		return TransFactory.getSuccessResponse();
	}

	@PutMapping("toFinish/{id}")
	@RequiresPermissions({"questionnaire:w"})
	public TransData updateFinish(@PathVariable(name = "id") String id) {
		questionnaireEntityService.updateFinish(id);
		return TransFactory.getSuccessResponse();
	}

	@PutMapping("delay")
	@RequiresPermissions({"questionnaire:w","result_show:w"})
	public TransData updateFinishTime(@RequestBody
	                                  @Validated
	                                  @JsonView(GroupView.UpdateTime.class)
			                                  QuestionnaireEntity questionnaireEntity,
	                                  BindingResult bindingResult) {
		questionnaireEntityService.updateDate(questionnaireEntity);
		return TransFactory.getSuccessResponse();

	}
}
