package cMessage;

/**
 * Used to start up the connection in order for the Server to return all the
 * pieces in their starting position
 * 
 * @author adebowale
 *
 */
public class InitMessage extends Message {

	private String request;

	public InitMessage(String request) {
		this.request = request;
	}

	/**
	 * @return request returns the request message sent by the client
	 */
	public String getRequest() {
		return request;
	}

}
