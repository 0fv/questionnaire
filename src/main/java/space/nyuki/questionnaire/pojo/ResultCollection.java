package space.nyuki.questionnaire.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import space.nyuki.questionnaire.group.GroupView;

import javax.validation.constraints.Future;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * @author ning
 * @createTime 12/2/19 11:28 AM
 * @description 调查结果表
 */
@Document
@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResultCollection{
    @Id
    private String id;
    @Field("clientId")
    //用户id，匿名时通过ip地址限定，非匿名使用提供给用户id进行限定
    private String clientId;
    @Field("name")
    @JsonView({
            GroupView.Gen.class
    })
    @NotNull(
            groups = { GroupView.Gen.class},
            message = "名称不能为空"
    )
    private String name;
    @Field("introduce")
    @JsonView({
            GroupView.Gen.class
    })
    private String introduce;
    @Field("q_id")
    @JsonProperty("q_id")
    @JsonView({
            GroupView.Gen.class
    })
    @NotNull(
            groups = { GroupView.Gen.class},
            message = "id不能为空"
    )
    private String questionnaireId;
    @FutureOrPresent(
            message = "必须大于当前时间",
            groups = { GroupView.Gen.class}
    )
    @Field("start_time")
    @JsonProperty("start_time")
    @JsonView({
            GroupView.Gen.class
    })
    private Date startTime;
    @JsonProperty("end_time")
    @Field("end_time")
    @JsonView({
            GroupView.Gen.class
    })
    private Date endTime;
    @Field("time_limit")
    @JsonProperty("time_limit")
    // 单位 分钟 0 代表无时限
    @JsonView({
            GroupView.Gen.class
    })
    private Integer timeLimit;
    @Field("use_time")
    @JsonProperty("use_time")
    //答题用时 单位分钟，取整
    private Integer useTime;

    @Field("questions")
    @JsonProperty("questions")
    @JsonView({
            GroupView.Input.class
    })
    private List<QuestionCell> questionCells;
    //根据当前和上一个ResultCollection生成infoId
    @Field("info_id")
    @JsonProperty("info_id")
    private String infoId;

}