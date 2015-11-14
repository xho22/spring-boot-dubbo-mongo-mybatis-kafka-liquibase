package com.ubankers.userservice.configuration.mq.kafka;

import java.util.Collections;
import java.util.Map;
import java.util.Properties;

import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.expression.common.LiteralExpression;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.kafka.core.BrokerAddress;
import org.springframework.integration.kafka.core.BrokerAddressListConfiguration;
import org.springframework.integration.kafka.core.ConnectionFactory;
import org.springframework.integration.kafka.core.DefaultConnectionFactory;
import org.springframework.integration.kafka.core.Partition;
import org.springframework.integration.kafka.inbound.KafkaMessageDrivenChannelAdapter;
import org.springframework.integration.kafka.listener.KafkaMessageListenerContainer;
import org.springframework.integration.kafka.listener.KafkaTopicOffsetManager;
import org.springframework.integration.kafka.listener.OffsetManager;
import org.springframework.integration.kafka.outbound.KafkaProducerMessageHandler;
import org.springframework.integration.kafka.serializer.common.StringDecoder;
import org.springframework.integration.kafka.support.KafkaProducerContext;
import org.springframework.integration.kafka.support.ProducerConfiguration;
import org.springframework.integration.kafka.support.ProducerFactoryBean;
import org.springframework.integration.kafka.support.ProducerMetadata;
import org.springframework.integration.kafka.support.ZookeeperConnect;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.PollableChannel;
import org.springframework.messaging.support.GenericMessage;

import com.ubankers.userservice.Application;
/**
 * 初始化kafka
 * @author yuanxiang
 * 20151104
 */
@Configuration
@EnableConfigurationProperties
public class ApplicationKafkaConfigurations {

	@Value("${kafka.topic}")
	private String topic;

	@Value("${kafka.messageKey}")
	private String messageKey;

	@Value("${kafka.broker.address}")
	private String brokerAddress;

	@Value("${kafka.zookeeper.connect}")
	private String zookeeperConnect;
	/**
	 * 测试kafka
	 * 启动时请把com.ubankers.userservice.configuration.kafka.consumer.KafkaConsumerStarter的postConstruct注释掉
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		ConfigurableApplicationContext context
				= new SpringApplicationBuilder(Application.class)
				.web(false)
				.run(args);
		MessageChannel toKafka = context.getBean("toKafka", MessageChannel.class);
		System.out.println("发送消息");
		for (int i = 0; i < 10; i++) {
			toKafka.send(new GenericMessage<>("test3" + i));
		}
		
		System.out.println("接收消息");
		PollableChannel fromKafka = context.getBean("fromKafka", PollableChannel.class);
		Message<?> received = null;
		int i = 1;
		while(true){
			received = fromKafka.receive(1000);
			System.out.println(received);
			if(i++>1000)break;
		}
//		do{
//			received = fromKafka.receive(10000);
//			System.out.println(received);
//			
//		}while(received!=null);
		
		context.close();
		System.exit(0);
	}
	
	@ServiceActivator(inputChannel = "toKafka")
	@Bean
	public MessageHandler handler() throws Exception {
		KafkaProducerMessageHandler handler = new KafkaProducerMessageHandler(producerContext());
		handler.setTopicExpression(new LiteralExpression(this.topic));
		handler.setMessageKeyExpression(new LiteralExpression(this.messageKey));
		return handler;
	}
	
	@Bean
	public ConnectionFactory kafkaBrokerConnectionFactory() throws Exception {
		return new DefaultConnectionFactory(kafkaConfiguration());
	}

	@Bean
	public org.springframework.integration.kafka.core.Configuration kafkaConfiguration() {
		BrokerAddressListConfiguration configuration = new BrokerAddressListConfiguration(
				BrokerAddress.fromAddress(this.brokerAddress));
		configuration.setSocketTimeout(500);
		return configuration;
	}

	@Bean
	public KafkaProducerContext producerContext() throws Exception {
		KafkaProducerContext kafkaProducerContext = new KafkaProducerContext();
		ProducerMetadata<String, String> producerMetadata = new ProducerMetadata<>(this.topic, String.class,
				String.class, new StringSerializer(), new StringSerializer());
		Properties props = new Properties();
		props.put("linger.ms", "1000");
		ProducerFactoryBean<String, String> producer =
				new ProducerFactoryBean<>(producerMetadata, this.brokerAddress, props);
		ProducerConfiguration<String, String> config =
				new ProducerConfiguration<>(producerMetadata, producer.getObject());
		Map<String, ProducerConfiguration<?, ?>> producerConfigurationMap =
				Collections.<String, ProducerConfiguration<?, ?>>singletonMap(this.topic, config);
		kafkaProducerContext.setProducerConfigurations(producerConfigurationMap);
		return kafkaProducerContext;
	}

	@Bean
	public OffsetManager offsetManager() {
		return new KafkaTopicOffsetManager(new ZookeeperConnect(this.zookeeperConnect), "si-offsets");
	}

	@Bean
	public KafkaMessageListenerContainer container(OffsetManager offsetManager) throws Exception {
		final KafkaMessageListenerContainer kafkaMessageListenerContainer = new KafkaMessageListenerContainer(
				kafkaBrokerConnectionFactory(), new Partition(this.topic, 0));
		kafkaMessageListenerContainer.setOffsetManager(offsetManager);
		kafkaMessageListenerContainer.setMaxFetch(100);
		kafkaMessageListenerContainer.setConcurrency(1);
		return kafkaMessageListenerContainer;
	}

	@Bean
	public KafkaMessageDrivenChannelAdapter adapter(KafkaMessageListenerContainer container) {
		KafkaMessageDrivenChannelAdapter kafkaMessageDrivenChannelAdapter =
				new KafkaMessageDrivenChannelAdapter(container);
		StringDecoder decoder = new StringDecoder();
		kafkaMessageDrivenChannelAdapter.setKeyDecoder(decoder);
		kafkaMessageDrivenChannelAdapter.setPayloadDecoder(decoder);
		kafkaMessageDrivenChannelAdapter.setOutputChannel(fromKafka());
		return kafkaMessageDrivenChannelAdapter;
	}

	@Bean
	public PollableChannel fromKafka() {
		return new QueueChannel();
	}

	@Bean
	public TopicCreator topicCreator() {
		return new TopicCreator(this.topic, this.zookeeperConnect);
	}
}
