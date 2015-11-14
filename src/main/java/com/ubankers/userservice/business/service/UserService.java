package com.ubankers.userservice.business.service;

import java.util.concurrent.Future;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import com.ubankers.userservice.business.domain.User;
import com.ubankers.userservice.business.domain.UserMongo;
import com.ubankers.userservice.business.mapper.UserMapper;
import com.ubankers.userservice.business.repository.UserRepository;

@Service("userService")
public class UserService implements IUserService{

	Logger logger = LoggerFactory.getLogger(this.getClass());  

	@Autowired
	private UserMapper userMapper;
	@Autowired
	private UserRepository userRepository;

	@Override
	@Async
	public Future<Void> execute() {
		return new AsyncResult<Void>(null);
	}

	@Override
	@Async
	public Future<User> findOne( String id ) {
		User user = userMapper.findById(id);
		return new AsyncResult<User>(user);
	}
	@Override
	public User findOne2( String id ) {
		return userMapper.findById(id);
	}
	
	@Override
	public User findOne3( Long id ) {
		String id_str = id+"";
		return userMapper.findById(id_str);
	}
	
	public void saveToMongo( Long id ,String name,Integer age) {
		
		logger.debug("test debug log");
		logger.info("test info log");
		
		UserMongo userMongo = new UserMongo();
		userMongo.setId(id);
		userMongo.setName(name);
		userMongo.setAge(age);
		userRepository.save(userMongo);
	}
	
	public UserMongo findOneFromMongo( Long id ) {
		return userRepository.findById(id);
	}

	public void deleteMongo(Long id){
		userRepository.delete(id);
	}
	
	@Override
	@Async
	public Future<Void> addUser( String name ) {
		userMapper.insert(StringUtils.defaultString(name, "testname"), 10);
		return new AsyncResult<Void>(null);
	}

}
