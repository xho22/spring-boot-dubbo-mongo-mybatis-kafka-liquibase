package com.ubankers.userservice.business.service;

import java.util.concurrent.Future;

import com.ubankers.userservice.business.domain.User;

public interface IUserService {

	public Future<Void> execute();

	public Future<User> findOne(String id);

	public Future<Void> addUser(String name);
	
	public User findOne2( String id );
	
	public User findOne3( Long id );

}
