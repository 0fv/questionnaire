package space.nyuki.questionnaire.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import space.nyuki.questionnaire.factory.TransFactory;
import space.nyuki.questionnaire.pojo.QuestionCell;
import space.nyuki.questionnaire.pojo.TransData;
import space.nyuki.questionnaire.service.QuestionService;
import space.nyuki.questionnaire.utils.ValidUtil;

/**
 * @author ning
 * @createTime 12/23/19 10:16 PM
 * @description 问题控制器
 */
@Api(tags = "问题管理器")
@RestController
@RequestMapping("/question")
public class QuestionController {
    @Autowired
    private QuestionService questionService;

    @ApiOperation("添加问题")
    @PostMapping("{id}/{gid}")
    public TransData createQuestion(
            @PathVariable(name = "id") String id,
            @PathVariable(name = "gid") Integer gid,
            @RequestBody QuestionCell cell,
            BindingResult bindingResult) {
        ValidUtil.valid(bindingResult);
        questionService.addQuestion(id, gid, cell);
        return TransFactory.getSuccessResponse();
    }

    @ApiOperation("修改在某个问题方框")
    @PutMapping("{id}/{gid}/{qid}")
    public TransData alterQuestion(
            @PathVariable(name = "id") String id,
            @PathVariable(name = "gid") Integer gid,
            @PathVariable(name = "qid") Integer qid,
            @RequestBody QuestionCell cell,
            BindingResult bindingResult
    ){
        ValidUtil.valid(bindingResult);
        questionService.alterQuestion(id,gid,qid,cell);
        return TransFactory.getSuccessResponse();
    }
    @ApiOperation("删除在某个问题方框")
    @DeleteMapping("{id}/{gid}/{qid}")
    public TransData deleteQuestion(
            @PathVariable(name = "id") String id,
            @PathVariable(name = "gid") Integer gid,
            @PathVariable(name = "qid") Integer qid
    ){
        questionService.deleteQuestion(id,gid,qid);
        return TransFactory.getSuccessResponse();
    }

}