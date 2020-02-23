package space.nyuki.questionnaire.controller;

import com.fasterxml.jackson.annotation.JsonView;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import space.nyuki.questionnaire.factory.TransFactory;
import space.nyuki.questionnaire.group.GroupView;
import space.nyuki.questionnaire.pojo.TransData;
import space.nyuki.questionnaire.pojo.User;
import space.nyuki.questionnaire.service.UserService;

@RestController
@RequestMapping("/user")
@RequiresPermissions("account_management:w")
public class UserController {
	@Autowired
	private UserService userService;

	@GetMapping
	@JsonView(GroupView.View.class)
	@RequiresPermissions("account_management:r")
	public TransData getUsers() {
		return TransFactory.getSuccessResponse(userService.getUsers());
	}

	@GetMapping("{id}")
	@JsonView(GroupView.GetById.class)
	public TransData getUserById(@PathVariable(name = "id") String id) {
		return TransFactory.getSuccessResponse(userService.getUserById(id));
	}

	@PostMapping
	public TransData addUser(
			@JsonView(GroupView.Create.class)
			@Validated(GroupView.Create.class)
			@RequestBody User user,
			BindingResult bindingResult
	) {
		userService.createUser(user);
		return TransFactory.getSuccessResponse();
	}

	@PutMapping("password")
	public TransData updatePassword(
			@JsonView(GroupView.UpdatePass.class)
			@Validated(GroupView.UpdatePass.class)
			@RequestBody User user,
			BindingResult bindingResult
	) {
		userService.updateUser(user);
		return TransFactory.getSuccessResponse();
	}

	@PutMapping("permission")
	public TransData updatePermission(
			@JsonView(GroupView.UpdateAccess.class)
			@Validated(GroupView.UpdateAccess.class)
			@RequestBody User user,
			BindingResult bindingResult
	) {
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
