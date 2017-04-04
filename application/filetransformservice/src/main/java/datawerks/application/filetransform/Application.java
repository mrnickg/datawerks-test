package main.java.datawerks.application.filetransform;


import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import datawerks.common.db.DBInterfaceFactory;
import datawerks.common.db.PersonRepository;
import datawerks.common.logging.Log;
import datawerks.common.messaging.MessagingService;
import datawerks.common.messaging.mq.RabbitMQProperties;
import datawerks.common.messaging.mq.RabbitMQReceiver;
import datawerks.common.messaging.mq.RabbitMQService;
import datawerks.common.storage.FileSystemStorageService;
import datawerks.common.storage.StorageProperties;
import main.java.datawerks.application.filetransform.service.FileTransformService;

@SpringBootApplication(scanBasePackages = { "datawerks" })
@EnableConfigurationProperties(StorageProperties.class)
@EnableJpaRepositories(basePackages = "datawerks")
@ComponentScan("datawerks")
@EntityScan("datawerks")
public class Application {

	final public static String queueName = "transform-queue";
	
	public static void main(String[] args) {
		new SpringApplicationBuilder(Application.class).web(false).run(args);
	}

	@Bean
	CommandLineRunner init(FileSystemStorageService storageService) {
		return (args) -> {
			storageService.init();
		};
	}

	@Bean
	FileTransformService fileTransformService(MessagingService messageService, DBInterfaceFactory dbInterfaceFactory, PersonRepository personRepository) {
		FileTransformService ts = new FileTransformService(messageService, dbInterfaceFactory, personRepository);
		ts.init();
		return ts;
	}
	
    @Bean
    Queue queue() {
        return new Queue(queueName, false);
    }

    @Bean
    TopicExchange exchange() {
        return new TopicExchange("datawerks-exchange");
    }

    @Bean
    Binding binding(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(queueName);
    }

    @Bean
    SimpleMessageListenerContainer container(ConnectionFactory connectionFactory,
            MessageListenerAdapter listenerAdapter) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(queueName);
        container.setMessageListener(listenerAdapter);
        
        Log.debug("Application created MQ Publisher and Listener", this);
        
        return container;
    }

    @Bean
    MessageListenerAdapter listenerAdapter(RabbitMQReceiver receiver) {
        return new MessageListenerAdapter(receiver, "receiveMessage");
    }
    
    @Bean
    MessagingService messageService(RabbitMQProperties properties, RabbitTemplate template, RabbitMQReceiver receiver) {
    	return new RabbitMQService(properties, template, receiver);
    }
    
}