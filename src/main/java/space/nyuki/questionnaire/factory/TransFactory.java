package space.nyuki.questionnaire.factory;

import space.nyuki.questionnaire.pojo.TransData;

/**
 * @author ning
 * @createTime 12/1/19 12:58 PM
 * @description get status
 */
public class TransFactory {
    public static TransData getSuccessResponse(){
        return TransData.builder()
                .code(200)
                .msg("success")
                .build();
    }
    public static TransData getSuccessResponse(Object data){
        return getSuccessResponse()
                .toBuilder()
                .data(data)
                .build();
    }
    public static TransData getFailedResponse(Integer code,String msg){
        return TransData.builder()
                .code(code)
                .msg(msg)
                .build();
    }
}