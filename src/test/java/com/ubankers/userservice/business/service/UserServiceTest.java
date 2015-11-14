package com.ubankers.userservice.business.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ubankers.userservice.Application;
import com.ubankers.userservice.business.domain.User;
import com.ubankers.userservice.business.domain.UserMongo;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class,locations = {"classpath*:/spring/*.xml"})
@WebIntegrationTest({ "server.port:0" })
public class UserServiceTest {

	@Autowired
	private UserService userService;

	@Before
	public void setUp() {
	}

	@Test
	public void testFindById() {
		System.out.println("testFindById start");
		Assert.assertNotNull(userService);
		User user = userService.findOne2("1");
		Assert.assertNotNull(user);
		Assert.assertEquals(user.getAge().longValue(), 26l);

		System.out.println("testFindById over");
	}
	@Test
	public void testMongo() {
		Long id = 1l;
		System.out.println("testMongo start");
		// mongo test
		userService.saveToMongo(id, "ttt", 34);
		UserMongo userMongo = userService.findOneFromMongo(1l);
		System.out.println(userMongo == null);
		Assert.assertNotNull(userMongo);
		userService.deleteMongo(id);
		
		System.out.println("testMongo over");
	}

}
