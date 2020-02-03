package space.nyuki.questionnaire.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import space.nyuki.questionnaire.pojo.BaseUrl;

@Service
public class BaseUrlService {
	private BaseUrl url;
	@Autowired
	private MongoTemplate mongoTemplate;

	public void init() {
		BaseUrl b = mongoTemplate.findOne
				(Query.query(Criteria.where("_id").is("url")), BaseUrl.class);
		this.url = b;
	}

	@Transactional
	public void setUrl(BaseUrl baseUrl) {
		this.url.setId("url");
		this.url = baseUrl;
		mongoTemplate.save(baseUrl);
	}

	public BaseUrl getBaseUrl() {
		return url;
	}
}
