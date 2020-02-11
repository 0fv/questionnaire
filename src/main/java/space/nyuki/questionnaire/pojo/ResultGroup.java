package space.nyuki.questionnaire.pojo;

import lombok.Data;

import java.util.List;

@Data
public class ResultGroup {
	private String title;
	private List<Result> results;
}
