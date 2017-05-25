package cMessage;

public class InitMessage extends Message {

	private String request;

	public InitMessage(String request) {
		this.request = request;
	}
	
	public String getRequest() { return request; }

}
