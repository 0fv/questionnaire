package space.nyuki.questionnaire.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import space.nyuki.questionnaire.group.GroupView;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Date;
import java.util.List;

/**
 * @author ning
 * @createTime 12/1/19 3:24 PM
 * @description
 */
@Document(collection = "questionnaire")
@Data
@ApiModel("问卷调查表")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Questionnaire {

    @Id
    @JsonProperty("_id")
    @JsonView({
            GroupView.View.class,
            GroupView.Update.class
    })
    @NotNull(
            message = "id不能为空",
            groups = {GroupView.Update.class
            }
    )
    private String id;
    @Field("name")
    @ApiModelProperty(value = "问卷调查大标题", example = "xxx调查")
    @NotNull(
            message = "标题不能为空",
            groups = {GroupView.Create.class
            }
    )
    @JsonView({
            GroupView.View.class,
            GroupView.Update.class,
            GroupView.Create.class
    })
    private String name;
    @Field("introduce")
    @ApiModelProperty(value = "介绍相关内容", example = "为了xxxxxx")
    @JsonView({
            GroupView.View.class,
            GroupView.Update.class,
            GroupView.Create.class
    })
    private String introduce;
    @ApiModelProperty(value = "uuid", example = "1ae52095-e465-4492-8c0b-15399889b9c7")
    @NotNull(
            message = "UUID不能为空",
            groups = {GroupView.Create.class}
    )
    @Pattern(
            regexp = "[0-9|a-z|A-Z]{8}-[0-9|a-z|A-Z]{4}" +
                    "-[0-9|a-z|A-Z]{4}-[0-9|a-z|A-Z]{4}" +
                    "-[0-9|a-z|A-Z]{12}",
            message = "uuid 格式不规范",
            groups = {GroupView.Create.class}
    )
    @JsonView({
            GroupView.Create.class
    })
    @Field("UUID")
    @JsonProperty("UUID")
    private String uuid;
    @ApiModelProperty("创建时间")
    @JsonView(GroupView.View.class)
    @Field("created_time")
    @JsonProperty("created_time")
    private Date createdTime;
    @JsonView(GroupView.View.class)
    @Field("created_account")
    @JsonProperty("created_account")
    private String createdAccount;
    @JsonView(GroupView.View.class)
    @Field("last_modify_account")
    @JsonProperty("last_modify_account")
    private String lastModifyAccount;
    @JsonView({
            GroupView.View.class,
    })
    @Field("modify_time")
    @JsonProperty("modify_time")
    @ApiModelProperty("修改时间")
    private Date modifyDate;
    @Field("question_groups")
    @ApiModelProperty("问题组内容")
    @JsonProperty("question_groups")
    @JsonView({
            GroupView.Update.class
    })
    private List<QuestionGroup> questionGroups;
    @ApiModelProperty("是否已被删除")
    @Field("is_delete")
    @JsonIgnore
    // 1 已删除 0 未删除
    private Integer isDelete;

    @ApiModelProperty("是否正在编辑")
    @Field("is_edit")
    @JsonProperty("is_edit")
    // 1 已完成编辑 0 正在编辑
    private Integer isEdit;

}