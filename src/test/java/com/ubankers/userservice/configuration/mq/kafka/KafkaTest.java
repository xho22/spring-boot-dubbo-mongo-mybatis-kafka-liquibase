package com.ubankers.userservice.configuration.mq.kafka;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ubankers.userservice.Application;
import com.ubankers.userservice.configuration.mq.MqSend;
/**
 * 初始化kafka
 * @author yuanxiang
 * 20151104
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebIntegrationTest({ "server.port:0" })
public class KafkaTest {
	@Autowired
	@Qualifier("kafkaProducer")
	MqSend mqSend;
	@Test
	public void testSendKafka(){
		System.out.println("test testSendKafka");
		mqSend.send("test data");
		System.out.println("test testSendKafka over");
	}
	
}
