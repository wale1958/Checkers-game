package checkers.client;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.CountDownLatch;
import java.util.logging.Logger;

import javax.swing.JFrame;
import javax.websocket.ClientEndpoint;
import javax.websocket.CloseReason;
import javax.websocket.DeploymentException;
import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;

import org.glassfish.tyrus.client.ClientManager;

import checkers.common.CheckerBoard;
import checkers.common.SquarePlayer;
import cMessage.InitMessage;
import cMessage.InitMessageEncoder;
import cMessage.Message;
import cMessage.BoardMessage;
import cMessage.MessageDecoder;
import cMessage.BoardMessageEncoder;
import cMessage.PlayerMove;
import cMessage.PlayerMoveEncoder;

/**
 * This class combines GUI setup code and WebSockets client-endpoint code. 
 * 
 * @author Adebowale Ojetola
 *
 */

@ClientEndpoint(decoders = { MessageDecoder.class }, encoders = { BoardMessageEncoder.class, InitMessageEncoder.class,
		PlayerMoveEncoder.class })
public class CheckersClient extends JFrame implements MouseListener {
	private CheckerboardCanvas cbCanvas; // the 'view' of the checkerboard
	private CheckerBoard board; // the client's part of the 'model'
	private int canvasTopInset; // distance Canvas is placed from the top
	private int fromRow, fromCol; // Where the checker is moving from
	private SquarePlayer currentPlayer;
	// private Session session;
	private CheckersClient game;
	public static Session session;
	static Session peer;

	public CheckersClient() {
		super("NetCheckers");
		setSize(450, 450);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("a");

		board = new CheckerBoard(); // Create the 'model'

		cbCanvas = new CheckerboardCanvas(board);
		add(cbCanvas); // Add the view to this frame

		addMouseListener(this); // Have this program listen for mouse events

		setVisible(false);
		canvasTopInset = getInsets().top; // may be needed to get accurate
											// mouse-click location

	}

	private static CountDownLatch latch;
	private Logger logger = Logger.getLogger(this.getClass().getName());

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
	public void onMessage(Session session, Message message) {

		if (game == null) {
			game = new CheckersClient();
			game.setVisible(true);
		}
		logger.info("Received ...." + message.toString());

		if (message instanceof BoardMessage) {
			board = ((BoardMessage) message).getBoard();
			game.setTitle(((BoardMessage) message).getNotice());

			for (int i = 0; i < game.board.BOARD_SIZE; i++) {
				for (int j = 0; j < game.board.BOARD_SIZE; j++) {
					if (board.squareIsOccupied(i, j)) {
						game.board.set(i, j, board.isPlayerOne(i, j) ? SquarePlayer.PlayerOne : SquarePlayer.PlayerTwo);
						if ((board.isKing(i, j))) {
							game.board.makeKing(i, j);
						}
					} else {
						game.board.set(i, j, SquarePlayer.Empty);
					}
				}
			}
			game.repaint();

			System.out.println(((BoardMessage) message).getBoard().toString());

		} else if (message instanceof InitMessage) {
			System.out.println(message);
		}
	}

	@OnClose
	public void onClose(Session session, CloseReason closeReason) {
		logger.info(String.format("Session %s close because of %s", session.getId(), closeReason));
		latch.countDown();
	}

	public static void main(String[] args) {
		latch = new CountDownLatch(1);

		ClientManager client = ClientManager.createClient();
		try {
			peer = client.connectToServer(CheckersClient.class, new URI("ws://localhost:8025/websockets/poke"));
			createAndShowGUI(peer);
			latch.await();

		} catch (DeploymentException | URISyntaxException | InterruptedException | IOException e) {
			throw new RuntimeException(e);
		}
	}

	private static void createAndShowGUI(Session session) {
		doInit(session);
	}

	/**
	 * Gets the pixel location of the mouse click and turns it into the row and
	 * column in the grid of the checker location.
	 * 
	 * @param e
	 *            the mouse event that just occurred
	 */

	public void mousePressed(MouseEvent e) {
		fromRow = cbCanvas.getRow(e.getY() - canvasTopInset);
		fromCol = cbCanvas.getCol(e.getX());

		// for debugging
		System.err.println("Mouse down: " + fromRow + "-" + fromCol);
	}

	/**
	 * 
	 * Attempts to move piece from mouse-press location to mouse-release
	 * location.
	 * 
	 * @param e
	 *            the mouse event that just occurred
	 */

	public void mouseReleased(MouseEvent e) {

		int toRow = cbCanvas.getRow(e.getY() - canvasTopInset); // adjust
																// coordinates
																// to account
																// for the
																// checkerboard's
																// location
		int toCol = cbCanvas.getCol(e.getX());

		// TODO: what happens here?
		sendMove(fromRow, fromCol, toRow, toCol); // send the move to the server
		// for debugging
		System.err.println("Mouse down: " + toRow + "-" + toCol);
	}

	private static void sendMove(int fromRow, int fromCol, int toRow, int toCol) {

		PlayerMove pMove = new PlayerMove(fromRow, fromCol, toRow, toCol);
		// for debugging
		System.out.println(peer);
		System.out.println(pMove);

		try {
			peer.getBasicRemote().sendObject(pMove);
		} catch (IOException | EncodeException e) {
			System.err.println("Problem with sending a Player-Move message");
		}
	}

	// not used
	public void mouseDragged(MouseEvent e) {
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	public void mouseClicked(MouseEvent e) {
	}

	public void mouseMoved(MouseEvent e) {
	}

	private static void doInit(Session session) {
		InitMessage initmsg = new InitMessage("Let's play");
		try {
			session.getBasicRemote().sendObject(initmsg);
		} catch (IOException | EncodeException e) {
			System.err.println("Problem with sending a Init-message.");
		}
	}

}

