package datawerks.common.messaging.mq;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;

import datawerks.common.logging.Log;
import datawerks.common.messaging.Message;
import datawerks.common.messaging.MessageListener;
import datawerks.common.messaging.MessagingService;

@Service
@EnableConfigurationProperties(RabbitMQProperties.class)
public class RabbitMQService implements MessagingService {

	private RabbitTemplate template;
	private RabbitMQReceiver receiver;
	
	@Autowired
	public RabbitMQService(RabbitMQProperties properties, RabbitTemplate template, RabbitMQReceiver receiver) {
		this.template = template;
		this.receiver = receiver;
	}
	
	@Override
	public void init() {
		

	}

	@Override
	public boolean publish(String queue, Message message) {
		Log.info("MQPublisher publishing message", this);
		template.convertAndSend(queue, message.getRawContent());
		return true;
	}

	@Override
	public void subscribe(String queue, MessageListener l) {
		receiver.subscribe(queue, l);
	}

	@Override
	public void unsubscribe(String queue, MessageListener l) {
		receiver.unsubscribe(queue, l);
	}

}
