package com.ubankers.userservice.business.domain;

import java.io.Serializable;

import org.springframework.data.annotation.Id;

public class UserMongo implements Serializable {

	private static final long serialVersionUID = 2L;

	@Id
	private Long id;

	private String name;

	private Integer age;

	public Long getId() {
		return id;
	}


	public void setId( Long id ) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName( String name ) {
		this.name = name;
	}


	public Integer getAge() {
		return age;
	}


	public void setAge( Integer age ) {
		this.age = age;
	}
}
