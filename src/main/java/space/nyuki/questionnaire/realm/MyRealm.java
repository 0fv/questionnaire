package space.nyuki.questionnaire.realm;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import space.nyuki.questionnaire.pojo.User;
import space.nyuki.questionnaire.service.UserService;
import space.nyuki.questionnaire.token.JWTToken;
import space.nyuki.questionnaire.utils.JWTUtil;

import java.util.Objects;

public class MyRealm extends AuthorizingRealm {

	@Autowired
	UserService userService;

	@Override
	public boolean supports(AuthenticationToken token) {
		return token instanceof JWTToken;
	}

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
		String username = JWTUtil.getUsername(principalCollection.toString());
		return userService.getAuthorizationInfo(username);
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
		String token = (String) authenticationToken.getCredentials();
		String username = JWTUtil.getUsername(token);
		User user = userService.getUserByUserName(username);
		if (Objects.isNull(user)) {
			throw new AuthenticationException();
		}
		if (!JWTUtil.verify(token, username, user.getPasswd())) {
			throw new AuthenticationException();
		}
		return new SimpleAuthenticationInfo(token, token, "myRealm");
	}

	@Override
	public boolean isPermitted(PrincipalCollection principals, String permission) {
		String username = JWTUtil.getUsername(principals.toString());
		User user = userService.getUserByUserName(username);
		return user.getIsSuper() == 1||super.isPermitted(principals,permission);
	}
}
