package space.nyuki.questionnaire.controller;

import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import space.nyuki.questionnaire.factory.TransFactory;
import space.nyuki.questionnaire.group.GroupView;
import space.nyuki.questionnaire.pojo.Questionnaire;
import space.nyuki.questionnaire.pojo.TransData;
import space.nyuki.questionnaire.service.QuestionnaireService;
import space.nyuki.questionnaire.utils.ValidUtil;

import java.util.Objects;

/**
 * @author ning
 * @createTime 12/1/19 4:13 PM
 * @description
 */

@Api(tags = "问卷调查表创建管理")
@RestController
@RequestMapping("/questionnaire")
public class QuestionnaireController {
    @Autowired
    private QuestionnaireService questionnaireService;

    @ApiOperation("创建问卷调查表")
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

    @ApiOperation("删除问卷调查表")
    @DeleteMapping("{id}")
    public TransData deleteQuestionnaire(@PathVariable String id) {
        questionnaireService.deleteQuestionnaire(id);
        return TransFactory.getSuccessResponse();
    }

    @ApiOperation("还原被删除问卷调查表")
    @GetMapping("reverse/{id}")
    public TransData reverseDeletedQuestionnaire(@PathVariable String id) {
        questionnaireService.reverseDeleteQuestionnaire(id);
        return TransFactory.getSuccessResponse();
    }

    @ApiOperation("修改问卷调查表")
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

    @ApiOperation("列出问卷调查表")
    @GetMapping
    @JsonView(GroupView.View.class)
    public TransData getQuestionnaire(
            @RequestParam(name = "page", required = false) Integer page,
            @RequestParam(name = "pageSize", required = false) Integer pageSize,
            @RequestParam(name = "isEdit", required = false, defaultValue = "-1") Integer isEdit
    ) {
        if (Objects.isNull(page)) {
            if (isEdit.equals(-1)) {
                return TransFactory.getSuccessResponse(questionnaireService.getQuestionnaire());
            } else {
                return TransFactory.getSuccessResponse(questionnaireService.getQuestionnaire(isEdit));
            }
        }
        if (isEdit.equals(-1)) {
            return TransFactory.getSuccessResponse(questionnaireService.getQuestionnaire(page, pageSize));
        } else {
            return TransFactory.getSuccessResponse(questionnaireService.getQuestionnaire(page, pageSize, isEdit));
        }

    }

    @ApiOperation("更改编辑状态")
    @GetMapping("edit/")
    public TransData changeEditStatus(
            @RequestParam(name = "id", required = true) String id,
            @RequestParam(name = "isEdit", required = true) Integer isEdit
    ) {
        questionnaireService.editChange(id, isEdit);
        return TransFactory.getSuccessResponse();
    }


}