package space.nyuki.questionnaire.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import space.nyuki.questionnaire.factory.TransFactory;
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
	public TransData getData() {
		return TransFactory.getSuccessResponse(questionCollectionService.getQuestionCollection());
	}

	@GetMapping("{id}")
	public TransData getDataById(@PathVariable(name = "id") String id) {
		return TransFactory.getSuccessResponse(questionCollectionService.getQuestionCollectionById(id));
	}

	@PostMapping
	public TransData addData(@RequestBody QuestionCollection questionCollection,
	                         @RequestHeader(name = "token") String token,
	                         BindingResult result) {
		ValidUtil.valid(result);
		questionCollectionService.add(questionCollection, token);
		return TransFactory.getSuccessResponse();
	}

	@PutMapping
	public TransData update(@RequestBody QuestionCollection questionCollection,
	                        @RequestHeader(name = "token") String token,
			                        BindingResult result) {
		ValidUtil.valid(result);
		questionCollectionService.update(questionCollection,token);
		return TransFactory.getSuccessResponse();
	}

	@DeleteMapping("{id}")
	public TransData delete(@PathVariable(name = "id") String id) {
		questionCollectionService.delete(id);
		return TransFactory.getSuccessResponse();
	}

}
