package space.nyuki.questionnaire.filter;

import lombok.SneakyThrows;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import space.nyuki.questionnaire.token.JWTToken;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

public class TokenFilter extends BasicHttpAuthenticationFilter {
	/**
	 * 查看是否有请求头
	 *
	 * @param request
	 * @param response
	 * @return
	 */
	@Override
	protected boolean isLoginAttempt(ServletRequest request, ServletResponse response) {
		HttpServletRequest hsr = (HttpServletRequest) request;
		String token = hsr.getHeader("token");
		return Objects.nonNull(token);
	}

	/**
	 * 执行登陆，包括鉴权
	 *
	 * @param request
	 * @param response
	 * @return
	 */

	@Override
	protected boolean executeLogin(ServletRequest request, ServletResponse response) {
		try {
			HttpServletRequest hsr = (HttpServletRequest) request;
			String token = hsr.getHeader("token");
			JWTToken jwtToken = new JWTToken(token);
			getSubject(request, response).login(jwtToken);
			return true;
		} catch (AuthenticationException e) {
			return false;
		}
	}

	/**
	 * 尝试登陆，不成功将抛出错误
	 *
	 * @param request
	 * @param response
	 * @param mappedValue
	 * @return
	 */
	@SneakyThrows
	@Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
		if (isLoginAttempt(request, response)) {
			if (executeLogin(request, response)) {
				return true;
			}
		}
		HttpServletRequest r = (HttpServletRequest) request;
		System.out.println(r.getRequestURL().toString());
		r.getRequestDispatcher("/authenticationFailed").forward(request,response);
		return false;
	}
}
