package com.ubankers.userservice.configuration.mq.kafka;

import java.util.Properties;

import kafka.admin.AdminUtils;
import kafka.common.TopicExistsException;
import kafka.utils.ZKStringSerializer$;

import org.I0Itec.zkclient.ZkClient;
import org.springframework.context.SmartLifecycle;

public class TopicCreator implements SmartLifecycle {

	private final String topic;

	private final String zkConnect;

	private volatile boolean running;

	public TopicCreator(String topic, String zkConnect) {
		this.topic = topic;
		this.zkConnect = zkConnect;
	}

	@Override
	public void start() {
		ZkClient client = new ZkClient(this.zkConnect, 10000, 10000, ZKStringSerializer$.MODULE$);
		try {
			AdminUtils.createTopic(client, this.topic, 1, 1, new Properties());
		}
		catch (TopicExistsException e) {
		}
		this.running = true;
	}

	@Override
	public void stop() {
	}

	@Override
	public boolean isRunning() {
		return this.running;
	}

	@Override
	public int getPhase() {
		return Integer.MIN_VALUE;
	}

	@Override
	public boolean isAutoStartup() {
		return true;
	}

	@Override
	public void stop(Runnable callback) {
		callback.run();
	}

}
