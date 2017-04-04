package datawerks.common.messaging.mq.messages;

import datawerks.common.messaging.Message;

public class TransformMessage implements Message {

	private String fileLocation;
	
	public TransformMessage() {
	}
	
	public String getFileLocation() {
		return fileLocation;
	}
	
	public void setFileLocation(String fileLocation) {
		this.fileLocation = fileLocation;
	}

	@Override
	public String getRawContent() {
		return fileLocation != null ? fileLocation : "";
	}
	
}
