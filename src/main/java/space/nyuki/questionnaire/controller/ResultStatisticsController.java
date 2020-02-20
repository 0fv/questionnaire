package space.nyuki.questionnaire.controller;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import space.nyuki.questionnaire.factory.TransFactory;
import space.nyuki.questionnaire.group.GroupView;
import space.nyuki.questionnaire.pojo.ChoiceStatisticsCollection;
import space.nyuki.questionnaire.pojo.TransData;
import space.nyuki.questionnaire.service.ResultStatisticsService;

@RestController
@RequestMapping("statistic")
public class ResultStatisticsController {
	@Autowired
	private ResultStatisticsService resultStatisticsService;

	@GetMapping("{id}")
	@JsonView(GroupView.View.class)
	public TransData getDataById(@PathVariable("id") String id) {
		ChoiceStatisticsCollection dataById = resultStatisticsService.getDataById(id);
		return TransFactory.getSuccessResponse(dataById);
	}
}
