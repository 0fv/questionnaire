package space.nyuki.questionnaire.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
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
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TransData {
    private Integer code;
    private String msg;
    private Object data;
    @Tolerate
    TransData() {}
}