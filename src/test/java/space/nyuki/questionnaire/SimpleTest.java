package space.nyuki.questionnaire;

import org.junit.jupiter.api.Test;

/**
 * @author ning
 * @createTime 12/23/19 3:50 PM
 * @description
 */

public class SimpleTest {
    @Test
    public void StringTest(){
        String s = "IndexOutOfBoundsException";
        int length = s.length();
        String v = "Exception";
        int length1 = v.length();
        System.out.println(length1);
        int x = length - length1;
        System.out.println(s.substring(0, x));

    }
}