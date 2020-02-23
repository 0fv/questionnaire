package space.nyuki.questionnaire.service;

import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import space.nyuki.questionnaire.exception.UserExistsException;
import space.nyuki.questionnaire.pojo.User;
import space.nyuki.questionnaire.utils.MD5Util;
import space.nyuki.questionnaire.utils.MapUtil;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class UserService {
	@Autowired
	private MongoTemplate mongoTemplate;

	@Transactional
	public void createUser(User user) {
		User userByUserName = getUserByUserName(user.getUsername());
		if (Objects.nonNull(userByUserName)) {
			throw new UserExistsException();
		}
		user.setCreatedTime(new Date());
		user.setPasswd(MD5Util.saltMd5(user.getPasswd()));
		mongoTemplate.save(user);
	}


	@Transactional
	public void deleteUser(String id) {
		Update update = new Update();
		update.set("is_delete", 1);
		mongoTemplate.findAndModify(
				Query.query(Criteria.where("_id").is(id)),
				update,
				User.class
		);
	}

	@Transactional
	public void updateUser(User user) {
		String id = user.getId();
		Map<String, Object> stringObjectMap = MapUtil.objectToMap(user);
		Update update = new Update();
		String passwd = (String) stringObjectMap.get("passwd");
		if (Objects.nonNull(passwd)) {
			stringObjectMap.put("passwd", MD5Util.saltMd5(passwd));
		}
		stringObjectMap.forEach(update::set);
		mongoTemplate.findAndModify(
				Query.query(Criteria.where("_id").is(id)),
				update,
				User.class
		);
	}

	@Transactional
	public User getLoginInfo(String username) {
		User user = this.getUserByUserName(username);
		Update update = new Update();
		update.set("last_login", new Date());
		mongoTemplate.findAndModify(
				Query.query(Criteria.where("_id").is(user.getId())),
				update,
				User.class
		);
		return user;

	}

	public List<User> getUsers() {
		return mongoTemplate.find(
				Query.query(Criteria.where("is_delete").is(0)),
				User.class
		);
	}

	public User getUserByUserName(String username) {
		return mongoTemplate.findOne(
				Query.query(Criteria.where("username").is(username).and("is_delete").is(0)), User.class);
	}


	/**
	 * 获取用户权限
	 *
	 * @param username
	 * @return
	 */
	public AuthorizationInfo getAuthorizationInfo(String username) {
		User user = this.getUserByUserName(username);
		SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
		MapUtil.objectToMap(user.getPermission()).forEach((k, v) -> {
			simpleAuthorizationInfo.addStringPermission(k+":"+v);
		});
		return simpleAuthorizationInfo;
	}

	public User getUserById(String id) {
		return mongoTemplate.findOne(Query.query(Criteria.where("_id").is(id)), User.class);
	}
}
