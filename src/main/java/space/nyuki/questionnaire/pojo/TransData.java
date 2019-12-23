package space.nyuki.questionnaire.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Tolerate;

/**
 * @author ning
 * @createTime 12/1/19 12:55 PM
 * @description transfer data to front end
 */

@Builder(toBuilder = true)
@Data
@JsonView(Object.class)
@ApiModel(description = "传递类")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TransData {
    @ApiModelProperty(value = "代码", example = "200")
    private Integer code;
    @ApiModelProperty(value = "信息", example = "success")
    private String msg;
    @ApiModelProperty(value = "数据", example = "data")
    private Object data;
    @Tolerate
    TransData() {}
}