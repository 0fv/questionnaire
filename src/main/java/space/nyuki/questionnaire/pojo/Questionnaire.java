package space.nyuki.questionnaire.pojo;

import com.fasterxml.jackson.annotation.*;
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
    @JsonView({
            GroupView.View.class,
            GroupView.Update.class,
            GroupView.Create.class
    })
    private String introduce;
    @NotNull(
            message="UUID不能为空",
            groups = {GroupView.Create.class}
    )
    @Pattern(
            regexp = "[0-9|a-z|A-Z]{8}-[0-9|a-z|A-Z]{4}"+
            "-[0-9|a-z|A-Z]{4}-[0-9|a-z|A-Z]{4}"+
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
    @JsonView(GroupView.View.class)
    @Field("created_time")
    @JsonProperty("created_time")
    private Date createdTime;
    @JsonView({
            GroupView.View.class,
    })
    @Field("modify_time")
    @JsonProperty("modify_time")
    private Date modifyDate;
    @Field("questions")
    @JsonProperty("questions")
    @JsonView({
            GroupView.Update.class
    })
    private List<QuestionCell> questionCells;
    @Field("is_delete")
    @JsonIgnore
    // 1 已删除 0 未删除
    private Integer isDelete;

}