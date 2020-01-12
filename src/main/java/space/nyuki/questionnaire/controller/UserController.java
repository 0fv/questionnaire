package space.nyuki.questionnaire.controller;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import space.nyuki.questionnaire.factory.TransFactory;
import space.nyuki.questionnaire.group.GroupView;
import space.nyuki.questionnaire.pojo.TransData;
import space.nyuki.questionnaire.pojo.User;
import space.nyuki.questionnaire.service.UserService;
import space.nyuki.questionnaire.utils.ValidUtil;

@RestController
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserService userService;

	@GetMapping
	@JsonView(GroupView.View.class)
	public TransData getUsers() {
		return TransFactory.getSuccessResponse(userService.getUsers());
	}

	@GetMapping("{id}")
	@JsonView(GroupView.Get.class)
	public TransData getUserById(@PathVariable(name = "id") String id) {
		return TransFactory.getSuccessResponse(userService.getUserById(id));
	}

	@PostMapping
	public TransData addUser(
			@RequestBody User user,
			BindingResult bindingResult
	) {
		ValidUtil.valid(bindingResult);
		userService.createUser(user);
		return TransFactory.getSuccessResponse();
	}

	@PutMapping
	public TransData alterUser(
			@RequestBody User user,
			BindingResult bindingResult
	) {
		ValidUtil.valid(bindingResult);
		System.out.println(user);
		userService.updateUser(user);
		return TransFactory.getSuccessResponse();
	}

	@DeleteMapping("{id}")
	public TransData deleteUser(
			@PathVariable(name = "id", required = true) String id
	) {
		userService.deleteUser(id);
		return TransFactory.getSuccessResponse();
	}
}
