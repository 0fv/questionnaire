package space.nyuki.questionnaire.exception;

/**
 * @author ning
 * @createTime 12/1/19 1:57 PM
 * @description
 */
public class FormatNotCorrectException extends RuntimeException {
    public FormatNotCorrectException() {
        super();
    }

    public FormatNotCorrectException(String message) {
        super(message);
    }
}