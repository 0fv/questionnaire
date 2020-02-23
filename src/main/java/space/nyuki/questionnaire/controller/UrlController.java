package space.nyuki.questionnaire.controller;

import com.fasterxml.jackson.annotation.JsonView;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import space.nyuki.questionnaire.factory.TransFactory;
import space.nyuki.questionnaire.group.GroupView;
import space.nyuki.questionnaire.pojo.BaseUrl;
import space.nyuki.questionnaire.pojo.TransData;
import space.nyuki.questionnaire.service.BaseUrlService;

@RestController
@RequestMapping("/url")
@RequiresPermissions("mail_management:w")
public class UrlController {
	@Autowired
	private BaseUrlService baseUrlService;

	@GetMapping
	@RequiresPermissions("mail_management:r")
	public TransData getData() {
		return TransFactory.getSuccessResponse(baseUrlService.getBaseUrl());
	}

	@PutMapping
	public TransData updateData(
			@RequestBody
			@Validated
			@JsonView(GroupView.Update.class)
					BaseUrl baseUrl,
			BindingResult result
	) {
		baseUrlService.setUrl(baseUrl);
		return TransFactory.getSuccessResponse();
	}
}
