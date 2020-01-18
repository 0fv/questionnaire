package space.nyuki.questionnaire.controller;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import space.nyuki.questionnaire.factory.TransFactory;
import space.nyuki.questionnaire.group.GroupView;
import space.nyuki.questionnaire.pojo.QuestionGroupCollection;
import space.nyuki.questionnaire.pojo.TransData;
import space.nyuki.questionnaire.service.QuestionGroupCollectionService;
import space.nyuki.questionnaire.utils.ValidUtil;

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
	@JsonView(GroupView.Input.class)
	public TransData getDataById(@PathVariable(name = "id") String id) {
		return TransFactory.getSuccessResponse(questionGroupCollectionService.getQuestionGroupCollectionById(id));
	}

	@PostMapping

	public TransData addData(
			@JsonView({
					GroupView.Create.class
			})
			@RequestBody QuestionGroupCollection collection,
			@RequestHeader(name = "token") String token,
			BindingResult result) {
		ValidUtil.valid(result);
		questionGroupCollectionService.addData(collection, token);
		return TransFactory.getSuccessResponse();
	}

	@PutMapping
	public TransData update(
			@JsonView({
					GroupView.Input.class
			})
			@RequestBody QuestionGroupCollection collection,
			@RequestHeader(name = "token") String token,
			BindingResult result) {
		ValidUtil.valid(result);
		questionGroupCollectionService.updateData(collection, token);
		return TransFactory.getSuccessResponse();
	}

	@DeleteMapping("{id}")
	public TransData delete(@PathVariable(name = "id") String id) {
		questionGroupCollectionService.deleteData(id);
		return TransFactory.getSuccessResponse();
	}
}
