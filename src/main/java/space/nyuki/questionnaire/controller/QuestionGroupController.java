package space.nyuki.questionnaire.controller;

import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import space.nyuki.questionnaire.factory.TransFactory;
import space.nyuki.questionnaire.group.GroupView;
import space.nyuki.questionnaire.pojo.QuestionGroup;
import space.nyuki.questionnaire.pojo.TransData;
import space.nyuki.questionnaire.service.QuestionGroupService;
import space.nyuki.questionnaire.utils.ValidUtil;

/**
 * @author ning
 * @createTime 12/23/19 7:57 PM
 * @description 问题组控制器
 */
@Api(tags = "问卷调查问题组管理")
@RestController
@RequestMapping("/questionGroups")
public class QuestionGroupController {
    @Autowired
    private QuestionGroupService questionGroupService;

    @ApiOperation("获取所有问题组")
    @GetMapping("{id}")
    public TransData getQuestionGroup(@PathVariable(name = "id") String id) {
        return TransFactory.getSuccessResponse(questionGroupService.getQuestionGroups(id));
    }


    @ApiOperation("创建问题组")
    @PostMapping("{id}")
    @JsonView(GroupView.Create.class)
    public TransData createQuestionGroup(@PathVariable(name = "id") String id,
                                         @RequestBody QuestionGroup questionGroup,
                                         BindingResult bindingResult) {
        ValidUtil.valid(bindingResult);
        questionGroupService.addQuestionGroup(id, questionGroup);
        return TransFactory.getSuccessResponse();
    }

    @ApiOperation("修改现有问题组")
    @PutMapping("{id}/{gid}")
    @JsonView(GroupView.Update.class)
    public TransData alterQuestionGroup(
            @PathVariable(name = "id") String id,
            @PathVariable(name = "gid") Integer gid,
            @RequestBody QuestionGroup questionGroup,
            BindingResult bindingResult) {
        ValidUtil.valid(bindingResult);
        questionGroupService.alterQuestionGroup(id, gid, questionGroup);
        return TransFactory.getSuccessResponse();
    }

    @ApiOperation("交换问题组位置")
    @PutMapping("{id}/{gidA}/{gidB}")
    public TransData swapQuestionGroup(
        @PathVariable(name = "id") String id,
        @PathVariable(name = "gidA")Integer gidA,
        @PathVariable(name = "gidB") Integer gidB
    ){
        questionGroupService.swapQuestionGroup(id,gidA,gidB);
        return TransFactory.getSuccessResponse();
    }

    @ApiOperation("删除问题组")
    @DeleteMapping("{id}/{gid}")
    public TransData deleteQuestionGroup(@PathVariable(name = "id") String id,
                                         @PathVariable(name = "gid") Integer gid) {
        questionGroupService.deleteQuestionGroup(id, gid);
        return TransFactory.getSuccessResponse();
    }
}