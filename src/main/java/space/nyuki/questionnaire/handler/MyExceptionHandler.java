package space.nyuki.questionnaire.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import space.nyuki.questionnaire.exception.ElementNotFoundException;
import space.nyuki.questionnaire.exception.FormatNotCorrectException;
import space.nyuki.questionnaire.exception.TemplateNotFoundException;
import space.nyuki.questionnaire.factory.TransFactory;
import space.nyuki.questionnaire.pojo.TransData;

import java.util.Arrays;
import java.util.Objects;

/**
 * @author ning
 * @createTime 12/1/19 1:59 PM
 * @description
 */

@RestControllerAdvice

public class MyExceptionHandler {

    @Autowired
    private Environment env;
    @ExceptionHandler(Exception.class)
    public TransData templateNotFoundException(Exception e){
        System.out.println("catch exception");
        String exceptionName = removeExceptionWord(e.getClass().getName());
        String code = env.getProperty(exceptionName + ".code");
        System.out.println("+++++++++++++");
        System.out.println(exceptionName);
        System.out.println("+++++++++++++");
        if (Objects.isNull(code)){
            return TransFactory.getFailedResponse(5001,"未知错误："+e.getMessage());
        }
        int c = Integer.parseInt(code);
        String msg = env.getProperty(exceptionName + ".msg");
        if (Objects.isNull(msg)) {
            return TransFactory.getFailedResponse(c,e.getMessage());
        }
        return TransFactory.getFailedResponse(c,msg+" "+e.getMessage());
    }
    private String removeExceptionWord(String exceptionName){
        String[] split = exceptionName.split("\\.");
        String eName = split[split.length-1];
        return eName.substring(0,eName.length()-9);
    }


}