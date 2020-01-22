package space.nyuki.questionnaire.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import space.nyuki.questionnaire.utils.ValidUtil;

@Aspect
@Component
public class ValidAspect {
	@Pointcut(value = "execution(* space.nyuki.questionnaire.controller.*.*(..))&&args(..,result)")
	public void valid(BindingResult result){}
	@Before(value = "valid(result)", argNames = "result")
	public void ValidValue(BindingResult result){
		ValidUtil.valid(result);
	}
}
