package com.ubankers.userservice.business.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.ubankers.userservice.business.domain.UserMongo;
@Repository
public class UserRepository {
	
	Logger logger = LoggerFactory.getLogger(this.getClass());  
	
	@Autowired
	private MongoTemplate mongoTemplate;
	
	public UserMongo findById(Long userId){
		Criteria c =Criteria.where("id").is(userId);
		Query q = new Query(c);
		logger.debug("findById q:{}",q);
		UserMongo userMongo=  this.mongoTemplate.findOne(q, UserMongo.class, "testuser");
		return userMongo;
	}
	
	public void save(UserMongo userMongo){
		this.mongoTemplate.save(userMongo, "testuser");
	}
	
	public void delete(Long userId){
		Criteria c =Criteria.where("id").is(userId);
		Query q = new Query(c);
		logger.info("delete q:{}",q);
		this.mongoTemplate.remove(q, UserMongo.class,"testuser");
	}
}
