package com.ubankers.userservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.system.ApplicationPidFileWriter;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

/**
 * 启动方法
 * 
 * @author yuanxiang 20151102
 */
@Configuration
@ComponentScan
@EnableAutoConfiguration
@ImportResource("classpath*:/spring/*.xml")
public class Application {

	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(
				Application.class);
		application.addListeners(
				new ApplicationPidFileWriter("app.pid"));
		application.run(args);

	}

}
