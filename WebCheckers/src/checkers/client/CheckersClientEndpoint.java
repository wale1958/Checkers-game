/*package checkers.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.CountDownLatch;
import java.util.logging.Logger;

import javax.websocket.*;

import org.glassfish.tyrus.client.ClientManager;

import cMessage.MoveMessageDecoder;
import cMessage.MoveMessageEncoder;

@ClientEndpoint(decoders = { MoveMessageDecoder.class }, encoders = { MoveMessageEncoder.class })
public class CheckersClientEndpoint {

	private static CountDownLatch latch;
	private Logger logger = Logger.getLogger(this.getClass().getName());
	private static CheckersClient checkersClient;
	
	@OnOpen
	public void onOpen(Session session) {

		logger.info("Connected ... " + session.getId());
		try {
			session.getBasicRemote().sendText("start");
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

	}

	@OnMessage
	public String onMessage(String message, Session session) {
		// return message;

		BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));

		try {
			logger.info("Received ...." + message.toString());
			String userInput = bufferRead.readLine();
			return userInput;
			
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

	}

	@OnClose
	public void onClose(Session session, CloseReason closeReason) {
		logger.info(String.format("Session %s close because of %s", session.getId(), closeReason));

		latch.countDown();

	}

	public static void main(String[] args) throws javax.websocket.DeploymentException {
		Session peer;
		latch = new CountDownLatch(1);

		ClientManager client = ClientManager.createClient();

		try {

			peer = client.connectToServer(CheckersClientEndpoint.class,
					new URI("ws://localhost:8025/WebCheckers/server"));
			checkersClient= new CheckersClient(peer);
			latch.await();

		} catch (DeploymentException | URISyntaxException

				| InterruptedException | IOException e) {

			throw new RuntimeException(e);

		}
	}


}*/
