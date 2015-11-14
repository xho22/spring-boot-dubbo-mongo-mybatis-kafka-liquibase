package com.ubankers.userservice.business.mapper;

import com.ubankers.userservice.business.domain.User;

public interface UserMapper {

	User findById(String id);

	void insert(String name, int age);
}
