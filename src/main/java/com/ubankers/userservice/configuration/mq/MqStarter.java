package com.ubankers.userservice.configuration.mq;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
/**
 * 收消息启动线程
 * @author yuanxiang
 * 20151106
 */
@Component
@ConfigurationProperties(value = "mq")
public class MqStarter {
	@Autowired
	@Qualifier("kafkaConsumer")
	MqReceive mqReceive;
	
	private boolean enabled;
	
	@PostConstruct
	public void init() {
		//利用kafka
		if(enabled){
			Thread t=new Thread(mqReceive); 
			t.start();
		}
	}
	
}
