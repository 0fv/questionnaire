package space.nyuki.questionnaire;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import space.nyuki.questionnaire.pojo.MemberGroup;
import space.nyuki.questionnaire.pojo.Questionnaire;
import space.nyuki.questionnaire.service.QuestionnaireService;
import space.nyuki.questionnaire.utils.MapUtil;

import java.util.Map;

/**
 * @author ning
 * @createTime 12/1/19 4:02 PM
 * @description
 */
@SpringBootTest
public class MongoTest {
    @Autowired
    private QuestionnaireService questionnaireService;
    @Autowired
    private MongoTemplate mongoTemplate;


    @Test
    public void test2() {
        Questionnaire questionnaire = new Questionnaire();
        questionnaire.setName("nihaox");
        questionnaire.setIntroduce("buhao");
        questionnaire.setUuid("dee11d4e-63c6-4d90-983c-5c9f1e79e96c");
        Map<String, Object> stringObjectMap = MapUtil.objectToMap(questionnaire);
        System.out.println(stringObjectMap);
    }

    @Test
    public void test3() {
        questionnaireService.deleteQuestionnaire("5de3751ba517897f3d3286b0");
    }

    @Test
    public void test4() {
        Query id = Query.query(Criteria.where("_id").is(new ObjectId("5de3751ba517897f3d3286b0")));
        Questionnaire one = mongoTemplate.findOne(id, Questionnaire.class);
        System.out.println(one);
        one.setIntroduce("sdfsdf");
    }

    @Test
    public void test5() {
        String x = "sfsf";
        String y = "sefsf";
        String q = "sdfsf";

    }
    @Test
    public void test6(){
        MemberGroup id = mongoTemplate.findOne(Query.query(Criteria.where("_id").is("5e2e86dd1e05a076396c14c2")), MemberGroup.class);
        System.out.println(id);
    }
}