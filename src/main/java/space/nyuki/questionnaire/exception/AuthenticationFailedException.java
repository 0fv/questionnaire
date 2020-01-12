package space.nyuki.questionnaire.exception;

public class AuthenticationFailedException extends RuntimeException{
	public AuthenticationFailedException(String message) {
		super(message);
	}

	public AuthenticationFailedException() {
		super();
	}
}
