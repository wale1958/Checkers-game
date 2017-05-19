package checkers.client;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.concurrent.CountDownLatch;

import javax.enterprise.inject.spi.DeploymentException;
import javax.websocket.ClientEndpoint;
import javax.websocket.CloseReason;
import javax.websocket.Endpoint;
import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;

import org.glassfish.tyrus.client.ClientManager;

import com.sun.org.apache.xerces.internal.util.URI;

@ClientEndpoint
public class CheckersClientEndpoint {
	private static CountDownLatch latch;
	
	@OnOpen
    public void onOpen(Session session) {
/*
    	   logger.info("Connected ... " + session.getId());

           try {

               session.getBasicRemote().sendText("start");

           } catch (IOException e) {

               throw new RuntimeException(e);

           }
*/
    }

 

    @OnMessage
    public String onMessage(String message, Session session) {
		return message;
/*
    	   BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));

           try {

               logger.info("Received ...." + message);

               String userInput = bufferRead.readLine();

               return userInput;

           } catch (IOException e) {

               throw new RuntimeException(e);

           }
           */

    }

 

    @OnClose
    public void onClose(Session session, CloseReason closeReason) {
/*
        logger.info(String.format("Session %s close because of %s", session.getId(), closeReason));

        latch.countDown();
*/
    }
    
    public static void main(String [] args){
    	go();
    }
    
    public static void go(){
    	Session peer;
    	 latch = new CountDownLatch(1);

		ClientManager client = ClientManager.createClient();

		try {

			peer = client.connectToServer(CheckersClientEndpoint.class, new URI("ws://localhost:8025/WebCheckers/server"));

			//createAndShowGUI(peer);

			latch.await();

		} catch (DeploymentException | URISyntaxException

				| InterruptedException | IOException e) {

			throw new RuntimeException(e);

		}
    }
}
