package datawerks.common.messaging.mq;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import datawerks.common.logging.Log;
import datawerks.common.messaging.MessageListener;

@Component
public class RabbitMQReceiver {

	private ArrayList<MessageListener> subscribers;
	
	@Autowired
	public RabbitMQReceiver() {
		subscribers = new ArrayList<MessageListener>();
	}
	
    public void receiveMessage(Object message) {
    	Log.info("MQReceiver received message from MQ", this);
    	for (MessageListener l : subscribers) {
    		Log.info("Notifying listener of MQ Message", this);
    		l.onMessage(message.toString());
    	}
    }

    public void subscribe(String queue, MessageListener l) {
    	subscribers.add(l);
    }
    
    public void unsubscribe(String queue, MessageListener l) {
    	subscribers.remove(l);
    }
}
