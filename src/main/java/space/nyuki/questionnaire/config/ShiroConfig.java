package space.nyuki.questionnaire.config;

import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import space.nyuki.questionnaire.filter.TokenFilter;
import space.nyuki.questionnaire.realm.MyRealm;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {
	@Bean
	public Realm getRealm() {
		return new MyRealm();
	}

	@Bean("securityManager")
	public DefaultWebSecurityManager getDefaultWebSecurityManager(Realm realm) {
		DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
		defaultWebSecurityManager.setRealm(realm);
		DefaultSubjectDAO defaultSubjectDAO = new DefaultSubjectDAO();
		DefaultSessionStorageEvaluator defaultSessionStorageEvaluator = new DefaultSessionStorageEvaluator();
		defaultSessionStorageEvaluator.setSessionStorageEnabled(false);
		defaultSubjectDAO.setSessionStorageEvaluator(defaultSessionStorageEvaluator);
		defaultWebSecurityManager.setSubjectDAO(defaultSubjectDAO);
		return defaultWebSecurityManager;
	}

	@Bean("shiroFilter")
	public ShiroFilterFactoryBean getShiroFilterFactoryBean(DefaultWebSecurityManager defaultWebSecurityManager) {
		ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
		shiroFilterFactoryBean.setSecurityManager(defaultWebSecurityManager);
		HashMap<String, Filter> filterHashMap = new HashMap<>();
		filterHashMap.put("jwt", new TokenFilter());
		shiroFilterFactoryBean.setFilters(filterHashMap);
		Map<String, String> rules = new HashMap<>();
		rules.put("/login", "anon");
		rules.put("/swagger-ui.html", "anon");
		rules.put("/swagger-resources/**", "anon");
		rules.put("/webjars/**", "anon");
		rules.put("/v2/**", "anon");
		rules.put("/csrf", "anon");
		//to-do change to jwt3
		rules.put("/**", "anon");
		shiroFilterFactoryBean.setLoginUrl("/login");
		shiroFilterFactoryBean.setFilterChainDefinitionMap(rules);
		return shiroFilterFactoryBean;
	}

	/**
	 * shiro 生命周期处理器
	 *
	 * @return
	 */
	@Bean(name = "lifecycleBeanPostProcessor")
	public static LifecycleBeanPostProcessor getLifecycleBeanPostProcessor() {
		return new LifecycleBeanPostProcessor();
	}

	/**
	 * 开启代理对象
	 *
	 * @return
	 */
	@Bean
	@DependsOn("lifecycleBeanPostProcessor")
	public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
		DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
		// 强制使用cglib，防止重复代理和可能引起代理出错的问题
		defaultAdvisorAutoProxyCreator.setProxyTargetClass(true);
		return defaultAdvisorAutoProxyCreator;
	}

	@Bean("lifecycleBeanPostProcessor")
	public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
		return new LifecycleBeanPostProcessor();
	}

	@Bean
	public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(DefaultWebSecurityManager securityManager) {
		AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
		advisor.setSecurityManager(securityManager);
		return advisor;
	}
}
