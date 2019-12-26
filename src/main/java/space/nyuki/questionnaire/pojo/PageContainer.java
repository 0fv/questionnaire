package space.nyuki.questionnaire.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author ning
 * @createTime 12/25/19 6:51 PM
 * @description
 */
@Data
@JsonView(Object.class)
@ApiModel(description = "传递类")
@AllArgsConstructor
public class PageContainer {
    private Integer page;
    private Long total;
    @JsonProperty("total_pages")
    private int totalPages;
    private Object data;
}