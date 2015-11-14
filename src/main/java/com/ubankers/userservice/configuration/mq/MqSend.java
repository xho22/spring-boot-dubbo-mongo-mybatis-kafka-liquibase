package com.ubankers.userservice.configuration.mq;
/**
 * 发消息
 * @author yuanxiang
 * 20151106
 */
public interface MqSend {

	public void send(String message);
	
}
