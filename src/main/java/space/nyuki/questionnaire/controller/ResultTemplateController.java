package space.nyuki.questionnaire.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import space.nyuki.questionnaire.factory.TransFactory;
import space.nyuki.questionnaire.pojo.TransData;
import space.nyuki.questionnaire.service.ResultService;

@RestController
@RequestMapping("result")
public class ResultTemplateController {
	@Autowired
	private ResultService resultService;
	@GetMapping("template/{id}")
	public TransData getData(@PathVariable("id") String id) {
		return TransFactory.getSuccessResponse(resultService.getTemplate(id));
	}
}
