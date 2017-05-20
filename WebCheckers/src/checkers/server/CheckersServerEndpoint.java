package checkers.server;

import java.util.logging.Logger;

import javax.websocket.CloseReason;
import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import cMessage.MoveMessageDecoder;
import cMessage.MoveMessageEncoder;
import checkers.common.CheckerBoard;

@ServerEndpoint(value = "/server",decoders = { MoveMessageDecoder.class }, encoders = {MoveMessageEncoder.class })
public class CheckersServerEndpoint {
	private Logger logger = Logger.getLogger(this.getClass().getName());

	 

    @OnOpen
    public void onOpen(Session peer) {
        logger.info("Connected ... " + peer.getId());
    }

 
    @OnMessage
    public void onMessage(Session peer, CheckerBoard board) throws EncodeException {

      //  logger.log(Level.FINE, "Message {0} from {1}", new Object[]{board, peer.getId()});

/*

        for (Session other : peer.getOpenSessions()) {

            try {

                other.getBasicRemote().sendObject(msg);

            } catch (IOException ex) {

                Logger.getLogger(PokeServerEndpoint.class.getName()).log(Level.SEVERE, null, ex);

            }



        }
*/
    }

 

    @OnClose
    public void onClose(Session session, CloseReason closeReason) {
        logger.info(String.format("Session %s closed because of %s", session.getId(), closeReason));

    }

    

 

}

