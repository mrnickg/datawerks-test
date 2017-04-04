package datawerks.common.messaging;

public interface MessagingService {

	public void init();
	
	public boolean publish(String queue, Message message);
	
	public void subscribe(String queue, MessageListener l);
	
	public void unsubscribe(String queue, MessageListener l);
	
}
