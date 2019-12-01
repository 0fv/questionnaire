package space.nyuki.questionnaire.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import space.nyuki.questionnaire.factory.TransFactory;
import space.nyuki.questionnaire.group.GroupView;
import space.nyuki.questionnaire.pojo.QuestionCell;
import space.nyuki.questionnaire.pojo.TransData;
import space.nyuki.questionnaire.pojo.answer.AnswerCell;
import space.nyuki.questionnaire.service.QuestionTemplateService;

import java.util.Map;

import static space.nyuki.questionnaire.utils.ValidUtil.valid;

/**
 * @author ning
 * @createTime 12/1/19 2:25 PM
 * @description
 */
@RestController
@RequestMapping("/questionTemplate")
public class QuestionTemplateController {
    @Autowired
    private QuestionTemplateService questionTemplateService;
    @GetMapping
    public TransData getName(){
        Map<String, AnswerCell> answerCells = questionTemplateService.getAnswerCells();
        System.out.println(answerCells);
        return TransFactory.getSuccessResponse(answerCells);
    }
    @PostMapping("/valid")
    public TransData validChoice(
            @Validated(GroupView.Input.class) @RequestBody QuestionCell questionCell,
            BindingResult bindingResult){
        valid(bindingResult);
        return TransFactory.getSuccessResponse();
    }
}