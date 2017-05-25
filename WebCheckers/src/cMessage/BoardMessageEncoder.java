package cMessage;

import javax.json.*;
import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

import checkers.common.CheckerBoard;

/**
 * Encodes BoardMessage
 * 
 * @author adebowale
 *
 */
public class BoardMessageEncoder implements Encoder.Text<BoardMessage> {

	@Override
	public String encode(BoardMessage msg) throws EncodeException {
		// Rather than sending the whole board, I decided to make a string
		// separated by tokens to hold the value of the pieces that are not
		// empty
		String temp = "";
		for (int i = 0; i < msg.getBoard().BOARD_SIZE; i++) {
			for (int j = 0; j < msg.getBoard().BOARD_SIZE; j++) {// for
																	// everything
																	// on the
																	// board
				if (msg.getBoard().squareIsOccupied(i, j)) {// if the
															// coordinates are
															// not empty
					temp = temp + "|" + Integer.toString(i) + ":" + Integer.toString(j) + ":"// add
																								// the
																								// details
																								// of
																								// that
																								// coordinate
							+ (msg.getBoard().isPlayerOne(i, j) ? "1" : "2") + ":"
							+ (msg.getBoard().isKing(i, j) ? "k" : "p");
				}
			}
		}
		temp = "|" + msg.getNotice() + temp;
		JsonObject jsonBoardMessage = Json.createObjectBuilder().add("type", "mainBoard").add("board", temp).build();

		return jsonBoardMessage.toString();
	}

	@Override
	public void destroy() {
	}

	@Override
	public void init(EndpointConfig arg0) {
	}

}
