package space.nyuki.questionnaire.controller;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import space.nyuki.questionnaire.factory.TransFactory;
import space.nyuki.questionnaire.group.GroupView;
import space.nyuki.questionnaire.pojo.QuestionGroupCollection;
import space.nyuki.questionnaire.pojo.TransData;
import space.nyuki.questionnaire.service.QuestionGroupCollectionService;

@RequestMapping("/questionGroupCollection")
@RestController
public class QuestionGroupCollectionController {
	@Autowired
	private QuestionGroupCollectionService questionGroupCollectionService;

	@GetMapping
	@JsonView({GroupView.View.class})
	public TransData getData() {
		return TransFactory.getSuccessResponse(questionGroupCollectionService.getQuestionGroupCollection());
	}

	@GetMapping("{id}")
	@JsonView(GroupView.GetById.class)
	public TransData getDataById(@PathVariable(name = "id") String id) {
		return TransFactory.getSuccessResponse(questionGroupCollectionService.getQuestionGroupCollectionById(id));
	}

	@PostMapping

	public TransData addData(
			@RequestHeader(name = "token") String token,
			@JsonView(GroupView.Create.class)
			@Validated(GroupView.Create.class)
			@RequestBody QuestionGroupCollection collection,
			BindingResult result) {
		questionGroupCollectionService.addData(collection, token);
		return TransFactory.getSuccessResponse();
	}

	@PutMapping
	public TransData update(
			@RequestHeader(name = "token") String token,
			@JsonView(GroupView.Update.class)
			@Validated(GroupView.Update.class)
			@RequestBody QuestionGroupCollection collection,
			BindingResult result) {
		questionGroupCollectionService.updateData(collection, token);
		return TransFactory.getSuccessResponse();
	}

	@DeleteMapping("{id}")
	public TransData delete(@PathVariable(name = "id") String id) {
		questionGroupCollectionService.deleteData(id);
		return TransFactory.getSuccessResponse();
	}
}
