package space.nyuki.questionnaire.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import space.nyuki.questionnaire.exception.CanNotEditException;
import space.nyuki.questionnaire.exception.ElementNotFoundException;
import space.nyuki.questionnaire.pojo.Questionnaire;
import space.nyuki.questionnaire.utils.MapUtil;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static org.springframework.data.mongodb.core.query.Query.query;

/**
 * @author ning
 * @createTime 12/1/19 3:42 PM
 * @description 处理问卷调查表(表体)
 */
@Service
public class QuestionnaireService {
    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * 创建问卷调查表
     *
     * @param questionnaire
     */
    @Transactional
    public void createQuestionnaire(Questionnaire questionnaire) {
        String uuid = questionnaire.getUuid();
        System.out.println(uuid);
        Questionnaire q = mongoTemplate.findOne(query((Criteria.where("uuid").is(uuid))),
                Questionnaire.class, "questionnaire");
        System.out.println("q = " + q);
        if (Objects.isNull(q)) {
            questionnaire.setCreatedTime(new Timestamp(System.currentTimeMillis()));
            questionnaire.setQuestionGroups(null);
            questionnaire.setIsDelete(0);
            questionnaire.setIsEdit(0);
            mongoTemplate.save(questionnaire);
        }

    }


    /**
     * 删除问卷调查
     *
     * @param id
     */

    @Transactional
    public void deleteQuestionnaire(String id) {
        Update update = new Update();
        update.set("is_delete", 1);
        update.set("modify_time", new Date());
        mongoTemplate.findAndModify(query(Criteria.where("_id").is(id))
                , update, Questionnaire.class);
    }

    /**
     * 修改问卷调查
     *
     * @param questionnaire
     */
    @Transactional
    public void alterQuestionnaire(Questionnaire questionnaire) {
        Questionnaire q = getQuestionnaireById(questionnaire.getId());
        Map<String, Object> map = MapUtil.objectToMap(questionnaire);
        Update update = new Update();
        map.forEach(update::set);
        update.set("modify_time", new Date());
        mongoTemplate.findAndModify(
                query(Criteria.where("_id").is(questionnaire.getId())),
                update,
                Questionnaire.class);
    }

    /**
     * 更新是否可编辑状态
     *
     * @param id
     * @param isEdit
     */
    @Transactional
    public void editChange(String id, Integer isEdit) {
        Update update = new Update();
        update.set("is_edit", isEdit);
        mongoTemplate.findAndModify(
                query(Criteria.where("_id").is(id)),
                update,
                Questionnaire.class
        );
    }

    /**
     * 查询现有问卷调查
     *
     * @param page
     * @param pageSize
     * @return
     */

    public List<Questionnaire> getQuestionnaire(Integer page, Integer pageSize) {
        Query query = new Query();
        query.skip((page - 1) * pageSize);
        query.limit(pageSize);
        query.addCriteria(Criteria.where("is_delete").is(0));
        return mongoTemplate.find(query, Questionnaire.class);
    }

    /**
     * 分是否可编辑进行查询
     *
     * @param page
     * @param pageSize
     * @param isEdit
     * @return
     */
    public List<Questionnaire> getQuestionnaire(Integer page, Integer pageSize, Integer isEdit) {
        Query query = new Query();
        query.skip((page - 1) * pageSize);
        query.limit(pageSize);
        query.addCriteria(Criteria.where("is_delete").is(0));
        query.addCriteria(Criteria.where("is_edit").is(isEdit));
        return mongoTemplate.find(query, Questionnaire.class);
    }

    /**
     * 通过id查找问卷，并验证是否能够修改
     * @param id
     * @return
     */

    public Questionnaire getQuestionnaireById(String id) {
        Questionnaire questionnaire = mongoTemplate.findOne(
                query(Criteria.where("_id").is(id)),
                Questionnaire.class
        );
        if (questionnaire.getIsEdit() == 1) {
            throw new CanNotEditException();
        }
        if (Objects.nonNull(questionnaire)) {
            return questionnaire;
        } else {
            throw new ElementNotFoundException(id);
        }
    }

    /**
     * 设置更新时间
     * @param update
     */
    private void setUpdateTime(Update update) {
        update.set("modify_time", new Date());
    }

    /**
     * 更新问卷内容
     * @param id
     * @param update
     */
    public void setQuestionnaireUpdate(String id, Update update) {
        setUpdateTime(update);
        mongoTemplate.findAndModify(
                Query.query(Criteria.where("_id").is(id)),
                update,
                Questionnaire.class
        );
    }

    public void reverseDeleteQuestionnaire(String id) {
        Update update = new Update();
        update.set("is_delete", 0);
        update.set("modify_time", new Date());
        mongoTemplate.findAndModify(query(Criteria.where("_id").is(id))
                , update, Questionnaire.class);
    }
}