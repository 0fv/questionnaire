package space.nyuki.questionnaire.controller;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import space.nyuki.questionnaire.factory.TransFactory;
import space.nyuki.questionnaire.group.GroupView;
import space.nyuki.questionnaire.pojo.QuestionCell;
import space.nyuki.questionnaire.pojo.Questionnaire;
import space.nyuki.questionnaire.pojo.ResultCollection;
import space.nyuki.questionnaire.pojo.TransData;
import space.nyuki.questionnaire.service.QuestionnaireService;
import space.nyuki.questionnaire.utils.ValidUtil;

import java.util.Date;

/**
 * @author ning
 * @createTime 12/1/19 4:13 PM
 * @description
 */
@RestController
@RequestMapping("/questionnaire")
public class QuestionnaireController {
    @Autowired
    private QuestionnaireService questionnaireService;

    @PostMapping
    @JsonView(GroupView.Create.class)
    public TransData createQuestionnaire(
            @Validated(GroupView.Create.class)
            @RequestBody Questionnaire questionnaire,
            BindingResult bindingResult) {
        ValidUtil.valid(bindingResult);
        questionnaireService.createQuestionnaire(questionnaire);
        return TransFactory.getSuccessResponse();
    }

    @DeleteMapping("{id}")
    public TransData deleteQuestionnaire(@PathVariable String id) {
        questionnaireService.deleteQuestionnaire(id);
        return TransFactory.getSuccessResponse();
    }

    @PutMapping
    @JsonView(GroupView.Update.class)
    public TransData alterQuestionnaire(
            @Validated(GroupView.Update.class)
            @RequestBody Questionnaire questionnaire,
            BindingResult bindingResult) {
        ValidUtil.valid(bindingResult);
        questionnaireService.alterQuestionnaire(questionnaire);
        return TransFactory.getSuccessResponse();
    }

    @GetMapping
    @JsonView(GroupView.View.class)
    public TransData getQuestionnaire(
            @RequestParam(name = "page", required = false, defaultValue = "1") Integer page,
            @RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize
    ) {
        return TransFactory.getSuccessResponse(questionnaireService.getQuestionnaire(page, pageSize));
    }

    @GetMapping("{id}")
    public TransData getQuestions(
            @PathVariable("id") String id
    ) {
        return TransFactory.getSuccessResponse(questionnaireService.getQuestions(id));
    }

    @PostMapping("{id}")
    public TransData addQuestion(
            @PathVariable("id") String id,
            @Validated(GroupView.Create.class)
            @RequestBody QuestionCell questionCell,
            BindingResult bindingResult) {
        ValidUtil.valid(bindingResult);
        questionnaireService.addQuestion(id, questionCell);
        return TransFactory.getSuccessResponse();
    }

    @PutMapping("{qid}/{cid}")
    public TransData alterQuestion(
            @PathVariable("qid") String qid,
            @PathVariable("cid") Integer cid,
            @Validated(GroupView.Create.class)
            @RequestBody QuestionCell questionCell,
            BindingResult bindingResult
    ) {
        ValidUtil.valid(bindingResult);
        questionnaireService.alterQuestion(qid, cid, questionCell);
        return TransFactory.getSuccessResponse();
    }

    @DeleteMapping("{qid}/{cid}")
    public TransData deleteQuestion(
            @PathVariable("qid") String qid,
            @PathVariable("cid") Integer cid
    ){
        questionnaireService.deleteQuestion(qid,cid);
        return TransFactory.getSuccessResponse();
    }
    @JsonView(GroupView.Gen.class)
    @PostMapping("start")
    public TransData startInvestigation(
            @Validated(GroupView.Gen.class)
            @RequestBody ResultCollection resultCollection,
            BindingResult bindingResult
            ){
        ValidUtil.valid(bindingResult);
        questionnaireService.startInvestigation(resultCollection);
        return TransFactory.getSuccessResponse();
    }


}