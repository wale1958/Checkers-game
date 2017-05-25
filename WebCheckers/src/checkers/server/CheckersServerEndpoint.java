package checkers.server;

import java.io.IOException;
import java.util.logging.*;

import javax.websocket.*;

import cMessage.*;

import javax.websocket.server.ServerEndpoint;

import checkers.common.SquarePlayer;

/**
 * What does this server do when it receives a message from a client?
 * 
 * @author sdexter72
 *
 */

@ServerEndpoint(value = "/poke", decoders = { MessageDecoder.class }, encoders = { BoardMessageEncoder.class,
		InitMessageEncoder.class, PlayerMoveEncoder.class })
public class CheckersServerEndpoint {
	private static int count = 0;
	private Logger logger = Logger.getLogger(this.getClass().getName());
	private static CheckersModel model;
	private static SquarePlayer currentPlayer;
	private static String playerOneID;
	private static String playerTwoID;
	String notice = "*";

	@OnOpen
	public void onOpen(Session peer) {
		if (count == 2) {
			try {
				peer.close(new CloseReason(CloseReason.CloseCodes.CANNOT_ACCEPT, ""));
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {

			logger.info("Connected ... " + Integer.toString(count) + " .... " + peer.getId());
			count++;
			if (count == 1) {
				model = new CheckersModel();
				model.newGame();
				notice = "Waiting for player Two to connect...";
				currentPlayer = SquarePlayer.PlayerTwo;
				playerOneID = peer.getId();
				System.out.println(model.toString());
			} else {
				playerTwoID = peer.getId();
				currentPlayer = SquarePlayer.PlayerOne;
				notice = "Player One's turn";
			}
		}
	}

	@OnMessage
	public void onMessage(Session peer, Message msg) throws EncodeException {
		// logger.log(Level.FINE, "Message {0} from {1}", new Object[]{msg,
		// peer.getId()});
		logger.log(Level.INFO, "Message {0} from {1}", new Object[] { msg, peer.getId() });
		BoardMessage msg2 = null;

		if (msg instanceof InitMessage) {
			System.out.println(((InitMessage) msg).getRequest());
			// msg2 = new MoveMessage(model.getBoard());
		}
		if (msg instanceof PlayerMove) {
			System.out.println(currentPlayer);
			System.out.println("from:" + ((PlayerMove) msg).getFromRow() + "-" + ((PlayerMove) msg).getFromCol());
			System.out.println("To: " + ((PlayerMove) msg).getToRow() + "-" + ((PlayerMove) msg).getToCol());
			System.out.println(
					model.canMoveFrom(currentPlayer, ((PlayerMove) msg).getFromRow(), ((PlayerMove) msg).getFromCol()));
			System.out.println(model.getBoard().get(((PlayerMove) msg).getFromRow(), ((PlayerMove) msg).getFromCol()));
			System.out.println(peer.getId());
			System.out.println(playerOneID);
			System.out.println(playerTwoID);

			if (peer.getId() == playerOneID) {
				if (currentPlayer == SquarePlayer.PlayerOne) {
					if (model.canMoveFrom(currentPlayer, ((PlayerMove) msg).getFromRow(),
							((PlayerMove) msg).getFromCol())) {
						if ((model.move(((PlayerMove) msg).getFromRow(), ((PlayerMove) msg).getFromCol(),
								((PlayerMove) msg).getToRow(), ((PlayerMove) msg).getToCol()))) {
							if (model.gameOver()) {
								notice = "GameOver, connection has been closed";
							} else {
								currentPlayer = SquarePlayer.PlayerTwo;
								notice = "Player two's turn to play";
								System.out.println("Move made , switching to player two.");
							}
						} else {
							notice = "Error! Invalid move by Player One";
							System.out.println("Error! Invalid move");
							// msg2 = new MoveMessage(model.getBoard());

						}
					} else {
						notice = "Player One: retry your turn again";
						System.out.println("Not PlayerOne's peice");
					}
				} else {
					notice = "Not Player One's turn. Player Two: make a move";
					System.out.println("Not PlayerOne's turn");
				}

			} else if (peer.getId() == playerTwoID) {
				if (currentPlayer == SquarePlayer.PlayerTwo) {
					if (model.canMoveFrom(currentPlayer, ((PlayerMove) msg).getFromRow(),
							((PlayerMove) msg).getFromCol())) {
						if ((model.move(((PlayerMove) msg).getFromRow(), ((PlayerMove) msg).getFromCol(),
								((PlayerMove) msg).getToRow(), ((PlayerMove) msg).getToCol()))) {
							if (model.gameOver()) {
								notice = "GameOver, connection has been closed";
							} else {
								currentPlayer = SquarePlayer.PlayerOne;
								notice = "It is player one's turn to play";
								System.out.println("Move made , switching to player one.");
							}
						} else {
							notice = "Error! Invalid move by Player Two";
							System.out.println("Error! Invalid move");

						}
					} else {
						notice = "Player Two: retry your turn again";
						System.out.println("Not PlayerTwo's peice");
					}
				} else {
					notice = "Not Player Two's turn. \n Player One: make a move";
					System.out.println("Not PlayerTwo's turn");
				}

			} else {
				System.out.println("Unknown Player" + peer.getId());
			}

		} // play move instance
		msg2 = new BoardMessage(model.getBoard(), notice);

		for (Session other : peer.getOpenSessions()) {
			try {
				other.getBasicRemote().sendObject(msg2);
				if (notice == "GameOver, connection has been closed") {
						other.close();
				}
			} catch (IOException ex) {
				Logger.getLogger(CheckersServerEndpoint.class.getName()).log(Level.SEVERE, null, ex);
			}
		}

	}

	@OnClose
	public void onClose(Session session, CloseReason closeReason) {
		logger.info(String.format("Session %s closed because of %s", session.getId(), closeReason));
	}

}
