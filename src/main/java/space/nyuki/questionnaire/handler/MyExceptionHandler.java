package space.nyuki.questionnaire.handler;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import space.nyuki.questionnaire.exception.ElementNotFoundException;
import space.nyuki.questionnaire.exception.FormatNotCorrectException;
import space.nyuki.questionnaire.exception.TemplateNotFoundException;
import space.nyuki.questionnaire.factory.TransFactory;
import space.nyuki.questionnaire.pojo.TransData;

/**
 * @author ning
 * @createTime 12/1/19 1:59 PM
 * @description
 */

@RestControllerAdvice
public class MyExceptionHandler {
    @Value("${web.status.templateNotFound.code}")
    private Integer templateNotFoundCode;
    @Value("${web.status.templateNotFound.msg}")
    private String templateNotFoundMsg;
    @Value("${web.status.formatError.code}")
    private Integer formatErrorCode;
    @Value("${web.status.elementNotFound.code}")
    private Integer elementNotFoundCode;
    @Value("${web.status.elementNotFound.msg}")
    private String elementNotFoundMsg;
    @ExceptionHandler({
            TemplateNotFoundException.class,
            HttpMessageNotReadableException.class})
    public TransData templateNotFoundException(){
        return TransFactory.getFailedResponse(templateNotFoundCode,templateNotFoundMsg);
    }
    @ExceptionHandler(FormatNotCorrectException.class)
    public TransData formatNotCorrectException(FormatNotCorrectException e){
        return TransFactory.getFailedResponse(formatErrorCode,e.getMessage());
    }
    @ExceptionHandler({
            ElementNotFoundException.class
    })
    public TransData elementNotFoundException(ElementNotFoundException e){
        return TransFactory.getFailedResponse(
                elementNotFoundCode,elementNotFoundMsg+":"+e.getMessage());
    }

}