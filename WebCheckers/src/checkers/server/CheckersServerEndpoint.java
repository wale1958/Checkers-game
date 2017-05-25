package checkers.server;

import java.io.IOException;
import java.util.logging.*;

import javax.websocket.*;

import cMessage.*;

import javax.websocket.server.ServerEndpoint;

import checkers.common.SquarePlayer;

/**
 * CheckersServerEndpoint listens for 3 different types of messages and makes
 * decisions based on which one was received
 * 
 * @author Adebowale Ojetola
 *
 */

@ServerEndpoint(value = "/checkers", decoders = { MessageDecoder.class }, encoders = { BoardMessageEncoder.class,
		InitMessageEncoder.class, PlayerMoveEncoder.class })
public class CheckersServerEndpoint {
	private static int count = 0;
	private Logger logger = Logger.getLogger(this.getClass().getName());
	private static CheckersModel model; // holds the master version of the board
	private static SquarePlayer currentPlayer; // used to determine who's turn
												// it is to play
	private static String playerOneID; // used to prevent a player from making a
										// move on behalf of his opponent
	private static String playerTwoID;
	String notice = ""; // message displayed on the title of the GUI

	/**
	 * Creates the game to be played
	 * 
	 * @param peer
	 *            client trying to establish connection
	 */
	@OnOpen
	public void onOpen(Session peer) {
		if (count == 2) { // if two players are already playing
			try {
				peer.close(new CloseReason(CloseReason.CloseCodes.CANNOT_ACCEPT, "")); // reject
																						// other
																						// connection
																						// attempts
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {

			logger.info("Connected ... " + Integer.toString(count) + " .... " + peer.getId());
			count++;
			if (count == 1) {// otherwise, if this is the first player to try to
								// connect
				model = new CheckersModel(); // create the server's version of
												// the model
				model.newGame(); // set the pieces
				notice = "Waiting for player Two to connect...";
				currentPlayer = SquarePlayer.PlayerTwo;// block the first player
														// from making a move
				playerOneID = peer.getId(); // store it's ID
			} else {// otherwise, if this is the second player to connect
				playerTwoID = peer.getId(); // store the ID
				currentPlayer = SquarePlayer.PlayerOne; // give playerOne back
														// his turn
				notice = "Player One's turn"; // notify the players
			}
		}
	}

	/**
	 * Creates a board message to be sent to the client every time a move is
	 * attempted and "valid"(not more than 2 clients allowed) clients ask to
	 * play
	 * 
	 * @param peer
	 *            client that sends a message
	 * @param msg
	 *            message sent by client
	 * @throws EncodeException
	 */
	@OnMessage
	public void onMessage(Session peer, Message msg) throws EncodeException {
		logger.log(Level.FINE, "Message {0} from {1}", new Object[] { msg, peer.getId() });
		BoardMessage msg2 = null;

		if (msg instanceof InitMessage) {
			System.out.println(((InitMessage) msg).getRequest());
		}
		if (msg instanceof PlayerMove) {

			if (peer.getId() == playerOneID) {// if it is playerOne's GUI trying
												// to make a move
				if (currentPlayer == SquarePlayer.PlayerOne) {// if it is player
																// one's turn
					if (model.canMoveFrom(currentPlayer, ((PlayerMove) msg).getFromRow(),
							((PlayerMove) msg).getFromCol())) {// if it is a
																// player one's
																// piece to be
																// moved
						if ((model.move(((PlayerMove) msg).getFromRow(), ((PlayerMove) msg).getFromCol(),
								((PlayerMove) msg).getToRow(), ((PlayerMove) msg).getToCol()))) {// if
																									// it
																									// is
																									// a
																									// valid
																									// move,
																									// do
																									// it
							if (model.gameOver()) {// if the move ended the game
								notice = "GameOver. Congrats PlayerOne! connection has been closed"; // notify
								// the
								// users
							} else {
								currentPlayer = SquarePlayer.PlayerTwo; // otherwise
																		// switch
																		// the
																		// turns
								notice = "Player two's turn to play";
							}
						} else {// otherwise if the move was invalid
							notice = "Error! Invalid move by Player One";
						}
					} else {// otherwise if the piece wasn't a playerOne piece.
							// Note that the mousePressed and MousedRealeased
							// aren't accurate so the user is encouraged to try
							// again
						notice = "Player One: retry your turn again";
					}
				} else {// otherwise if playerOne tries to make a move at
						// playerTwo's turn
					notice = "Not Player One's turn. Player Two: make a move";
				}

				// Do the same check for playerTwo
			} else if (peer.getId() == playerTwoID) {
				if (currentPlayer == SquarePlayer.PlayerTwo) {
					if (model.canMoveFrom(currentPlayer, ((PlayerMove) msg).getFromRow(),
							((PlayerMove) msg).getFromCol())) {
						if ((model.move(((PlayerMove) msg).getFromRow(), ((PlayerMove) msg).getFromCol(),
								((PlayerMove) msg).getToRow(), ((PlayerMove) msg).getToCol()))) {
							if (model.gameOver()) {
								notice = "GameOver. Congrats PlayerTwo! connection has been closed";
							} else {
								currentPlayer = SquarePlayer.PlayerOne;
								notice = "It is player one's turn to play";
							}
						} else {
							notice = "Error! Invalid move by Player Two";
						}
					} else {
						notice = "Player Two: retry your turn again";
					}
				} else {
					notice = "Not Player Two's turn. \n Player One: make a move";
				}

			} else {
				System.out.println("Unknown Player" + peer.getId());
			}

		} // Weather a successful move was made or not, relay the board and the
			// notice to the client
		msg2 = new BoardMessage(model.getBoard(), notice);

		for (Session other : peer.getOpenSessions()) {
			try {
				other.getBasicRemote().sendObject(msg2);
				if (model.gameOver()) {// if the game is over
					other.close(); // close the session
				}
			} catch (IOException ex) {
				Logger.getLogger(CheckersServerEndpoint.class.getName()).log(Level.SEVERE, null, ex);
			}
		}

	}

	/**
	 * handles the message provided when a session is closed
	 * 
	 * @param session
	 *            current Session running
	 * @param closeReason
	 *            reason the session was clossed
	 */
	@OnClose
	public void onClose(Session session, CloseReason closeReason) {
		logger.info(String.format("Session %s closed because of %s", session.getId(), closeReason));
	}

}
