package space.nyuki.questionnaire.utils;

import org.springframework.validation.BindingResult;
import space.nyuki.questionnaire.exception.FormatNotCorrectException;

import java.util.Objects;

/**
 * @author ning
 * @createTime 12/1/19 1:56 PM
 * @description
 */
public class ValidUtil {
    public static void valid(BindingResult result){
        if (result.hasErrors()) {
            throw new FormatNotCorrectException(Objects.requireNonNull(result.getFieldError()).getDefaultMessage());
        }
    }
}