package space.nyuki.questionnaire.controller;

import com.fasterxml.jackson.annotation.JsonView;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import space.nyuki.questionnaire.exception.AuthenticationFailedException;
import space.nyuki.questionnaire.factory.TransFactory;
import space.nyuki.questionnaire.group.GroupView;
import space.nyuki.questionnaire.pojo.TransData;
import space.nyuki.questionnaire.pojo.User;
import space.nyuki.questionnaire.service.UserService;
import space.nyuki.questionnaire.token.JWTToken;
import space.nyuki.questionnaire.utils.JWTUtil;

import java.util.HashMap;
import java.util.Map;

@RestController
public class LoginController {
	@Autowired
	private UserService userService;

	@PostMapping("/login")
	@JsonView(GroupView.LoginInfo.class)
	public TransData login(
			@Validated(GroupView.Login.class)
			@JsonView(GroupView.Login.class)
			@RequestBody User user,
			BindingResult bindingResult
	) {
		String username = user.getUsername();
		String password = user.getPasswd();
		JWTToken token = new JWTToken(JWTUtil.sign(username, password));
		Subject subject = SecurityUtils.getSubject();
		subject.login(token);
		User userInfo = userService.getLoginInfo(username);
		Map<String, Object> loginInfo = new HashMap<>();
		loginInfo.put("token", token.getCredentials().toString());
		loginInfo.put("user", userInfo);
		return TransFactory.getSuccessResponse(loginInfo);
	}
	@PostMapping("/login/token")
	public TransData tokenLogin(@RequestBody Map<String,String> token){
		String token1 = token.get("token");
		Subject subject = SecurityUtils.getSubject();
		subject.login(new JWTToken(token1));
		String username = JWTUtil.getUsername(token1);
		User userInfo = userService.getLoginInfo(username);
		Map<String, Object> loginInfo = new HashMap<>();
		loginInfo.put("token", token1);
		loginInfo.put("user", userInfo);
		return TransFactory.getSuccessResponse(loginInfo);


	}

	@RequestMapping(value = "/authenticationFailed",
			method = {RequestMethod.GET, RequestMethod.DELETE, RequestMethod.POST, RequestMethod.PUT})
	public void authenticationFailed() {
		throw new AuthenticationFailedException();
	}
}
