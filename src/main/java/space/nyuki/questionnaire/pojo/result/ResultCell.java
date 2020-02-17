package space.nyuki.questionnaire.pojo.result;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonView;

@JsonTypeInfo(
		use = JsonTypeInfo.Id.NAME,
		property = "type"
)
@JsonSubTypes({
		@JsonSubTypes.Type(value = ResultChoice.class, name = "choice"),
		@JsonSubTypes.Type(value = ResultComment.class, name = "comment"),
		@JsonSubTypes.Type(value = ResultDate.class, name = "date")

})
@JsonView(Object.class)
@JsonInclude
public interface ResultCell {
	void setTitle(String title);
	void setMustAnswer(boolean flag);
}
