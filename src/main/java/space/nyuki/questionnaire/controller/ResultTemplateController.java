package space.nyuki.questionnaire.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import space.nyuki.questionnaire.factory.TransFactory;
import space.nyuki.questionnaire.pojo.ResultTemplate;
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

	@PostMapping
	public TransData getResult(@RequestBody ResultTemplate resultTemplate) {
		System.out.println(resultTemplate);
		return TransFactory.getSuccessResponse();
	}
}
