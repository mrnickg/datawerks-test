package datawerks.application.fileupload;


import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import datawerks.common.db.DBInterfaceFactory;
import datawerks.common.db.DatabaseProperties;
import datawerks.common.messaging.MessagingService;
import datawerks.common.messaging.mq.RabbitMQProperties;
import datawerks.common.messaging.mq.RabbitMQReceiver;
import datawerks.common.messaging.mq.RabbitMQService;
import datawerks.common.storage.FileSystemStorageService;
import datawerks.common.storage.StorageProperties;

@SpringBootApplication(scanBasePackages = { "datawerks" })
@EnableConfigurationProperties(StorageProperties.class)
public class Application {

	final public static String queueName = "transform-queue";
	
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	CommandLineRunner init(FileSystemStorageService storageService) {
		return (args) -> {
            storageService.init();
		};
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
    MessagingService messageService(RabbitMQProperties properties, RabbitTemplate template) {
    	return new RabbitMQService(properties, template, null);
    }
    
}