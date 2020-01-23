package space.nyuki.questionnaire.pojo;

import lombok.Data;

@Data
public class Member {
	private String name;
	private Long phone;
	private String email;
	private String additionalInfo;
	private String eigenvalue;
}
