package main.java.datawerks.application.filetransform.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;

import datawerks.common.db.DBInterfaceFactory;
import datawerks.common.db.PersonRepository;
import datawerks.common.messaging.MessageListener;
import datawerks.common.messaging.MessagingService;
import main.java.datawerks.application.filetransform.Application;

@Service
public class FileTransformService implements MessageListener {

    private final MessagingService messageService;
    private final DBInterfaceFactory dbInterfaceFactory;

    private PersonRepository personRepository;
    
    private TaskExecutor taskExecutor;
    
    @Autowired
    public FileTransformService(MessagingService messageService, DBInterfaceFactory dbInterfaceFactory, PersonRepository personRepository) {
        this.messageService = messageService;    
        this.dbInterfaceFactory = dbInterfaceFactory;
        this.personRepository = personRepository;
    }
    
    public void init() {
    	messageService.subscribe(Application.queueName, this);
    	taskExecutor = new SimpleAsyncTaskExecutor();
    }

	@Override
	public void onMessage(String message) {
		taskExecutor.execute(new TransformExecutor(message, dbInterfaceFactory.getInterface(), personRepository));
	}
	
	
}
