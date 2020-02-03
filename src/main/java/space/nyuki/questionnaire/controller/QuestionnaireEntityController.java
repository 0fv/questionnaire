package space.nyuki.questionnaire.controller;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import space.nyuki.questionnaire.factory.TransFactory;
import space.nyuki.questionnaire.group.GroupView;
import space.nyuki.questionnaire.pojo.TransData;
import space.nyuki.questionnaire.service.QuestionnaireEntityService;

@RestController
@RequestMapping("questionnaireEntity")
public class QuestionnaireEntityController {
	@Autowired
	private QuestionnaireEntityService questionnaireEntityService;

	@GetMapping
	@JsonView(GroupView.View.class)
	public TransData getData(@RequestParam(value = "isFinish", required = false, defaultValue = "0") int isFinish) {
		return TransFactory.getSuccessResponse(questionnaireEntityService.getData(isFinish));
	}

	@DeleteMapping("{id}")
	public TransData deleteData(@PathVariable(name = "id") String id) {
		questionnaireEntityService.deleteData(id);
		return TransFactory.getSuccessResponse();
	}

	@PutMapping("{id}")
	public TransData updateFinish(@PathVariable(name = "id") String id,
	                              @RequestParam(value = "isFinish") int isFinish) {
		questionnaireEntityService.updateFinish(id, isFinish);
		return TransFactory.getSuccessResponse();
	}
}
