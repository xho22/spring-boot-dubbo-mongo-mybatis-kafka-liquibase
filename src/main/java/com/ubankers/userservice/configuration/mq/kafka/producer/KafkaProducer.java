package com.ubankers.userservice.configuration.mq.kafka.producer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Component;

import com.ubankers.userservice.configuration.mq.MqSend;
/**
 * kafka启动
 * @author yuanxiang
 *	20151104
 */
@Component("kafkaProducer")
public class KafkaProducer implements MqSend{
	
	Logger logger = LoggerFactory.getLogger(this.getClass());  
	
	@Autowired
	@Qualifier("toKafka")
	public MessageChannel toKafka;
	
	@Override
	public void send(String message) {
		logger.info("发送消息:{}",message);
		toKafka.send(new GenericMessage<>(message));
	}
	
}
