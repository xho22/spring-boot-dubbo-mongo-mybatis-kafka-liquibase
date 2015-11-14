package com.ubankers.userservice.configuration.mq;
/**
 * 收消息
 * @author yuanxiang
 * 20151106
 */
public interface MqReceive extends Runnable{

	public void receive();
	
}
