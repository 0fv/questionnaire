package space.nyuki.questionnaire.exception;

/**
 * @author ning
 * @createTime 12/1/19 10:03 PM
 * @description
 */
public class ElementNotFoundException extends RuntimeException {
    public ElementNotFoundException() {
        super();
    }

    public ElementNotFoundException(String message) {
        super(message);
    }
}