package checkers.client;

import checkers.common.*;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.concurrent.CountDownLatch;

import javax.enterprise.inject.spi.DeploymentException;
import javax.swing.*;
import javax.websocket.*;
import javax.websocket.CloseReason;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;

import org.glassfish.tyrus.client.ClientManager;

import com.sun.org.apache.xerces.internal.util.URI;

@ClientEndpoint
public class CheckersClient extends JFrame implements MouseListener {
    private static CountDownLatch latch;

	private CheckerboardCanvas cbCanvas; // the 'view' of the checkerboard
	private CheckerBoard board; // the client's part of the 'model'

	private int canvasTopInset; // distance Canvas is placed from the top

	private int fromRow, fromCol; // Where the checker is moving from

	private SquarePlayer currentPlayer;

	public CheckersClient() {

		super("NetCheckers");
		setSize(450, 450);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		board = new CheckerBoard(); // Create the 'model'

		cbCanvas = new CheckerboardCanvas(board);
		add(cbCanvas); // Add the view to this frame

		addMouseListener(this); // Have this program listen for mouse events

		setVisible(true);
		canvasTopInset = getInsets().top; // may be needed to get accurate
											// mouse-click location

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
		// System.err.println("Mouse down: " + fromRow + "-" + fromCol);
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
	public static void main(String[] args) {
        latch = new CountDownLatch(1);
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				CheckersClient game = new CheckersClient();

				// TODO: does any other setup need to happen?
				Session peer;

				ClientManager client = ClientManager.createClient();

				try {

					peer = client.connectToServer(CheckersClient.class, new URI("ws://localhost:8025/WebCheckers/server"));

					//createAndShowGUI(peer);

					latch.await();

				} catch (DeploymentException | URISyntaxException

						| InterruptedException | IOException e) {

					throw new RuntimeException(e);

				}

			}
		});
	}
}