package space.nyuki.questionnaire.controller;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import space.nyuki.questionnaire.factory.TransFactory;
import space.nyuki.questionnaire.group.GroupView;
import space.nyuki.questionnaire.pojo.QuestionCollection;
import space.nyuki.questionnaire.pojo.TransData;
import space.nyuki.questionnaire.service.QuestionCollectionService;
import space.nyuki.questionnaire.utils.ValidUtil;

@RequestMapping("/questionCollection")
@RestController
public class QuestionCollectionController {
	@Autowired
	private QuestionCollectionService questionCollectionService;

	@GetMapping
	@JsonView(GroupView.View.class)
	public TransData getData() {
		return TransFactory.getSuccessResponse(questionCollectionService.getQuestionCollection());
	}

	@GetMapping("{id}")
	@JsonView(GroupView.Input.class)
	public TransData getDataById(@PathVariable(name = "id") String id) {
		return TransFactory.getSuccessResponse(questionCollectionService.getQuestionCollectionById(id));
	}

	@PostMapping

	public TransData addData(
			@JsonView({
					GroupView.Create.class
			})
			@RequestBody QuestionCollection questionCollection,
			@RequestHeader(name = "token") String token,
			BindingResult result) {
		ValidUtil.valid(result);
		questionCollectionService.add(questionCollection, token);
		return TransFactory.getSuccessResponse();
	}

	@PutMapping
	public TransData update(
			@JsonView({
					GroupView.Input.class
			})
			@RequestBody QuestionCollection questionCollection,
	                        @RequestHeader(name = "token") String token,
	                        BindingResult result) {
		ValidUtil.valid(result);
		questionCollectionService.update(questionCollection, token);
		return TransFactory.getSuccessResponse();
	}

	@DeleteMapping("{id}")
	public TransData delete(@PathVariable(name = "id") String id) {
		questionCollectionService.delete(id);
		return TransFactory.getSuccessResponse();
	}

}
