package space.nyuki.questionnaire;

import org.junit.jupiter.api.Test;
import space.nyuki.questionnaire.pojo.Permission;
import space.nyuki.questionnaire.pojo.User;
import space.nyuki.questionnaire.utils.MapUtil;

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
    @Test
    public void test2(){
        User user = new User();
        user.setId("d");
        Permission permission = new Permission();
        user.setPermission(permission);
        System.out.println(MapUtil.objectToMap(user));
    }
}