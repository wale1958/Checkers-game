package cMessage;

import javax.json.*;
import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

import checkers.common.CheckerBoard;

public class BoardMessageEncoder implements Encoder.Text<BoardMessage> {

	@Override
	public String encode(BoardMessage msg) throws EncodeException {
		String temp = "";
		for (int i = 0; i < msg.getBoard().BOARD_SIZE; i++) {
			for (int j = 0; j < msg.getBoard().BOARD_SIZE; j++) {
				if (msg.getBoard().squareIsOccupied(i, j)) {
					temp = temp + "|" + Integer.toString(i) + ":" + Integer.toString(j) +":"
							+ (msg.getBoard().isPlayerOne(i, j) ? "1" : "2") + ":"
							+ (msg.getBoard().isKing(i, j) ? "k" : "p");
				}
			}
		}
		temp="|"+msg.getNotice()+temp;
		JsonObject jsonPokeMessage = Json.createObjectBuilder().add("type", "mainBoard").add("board", temp).build();

		return jsonPokeMessage.toString();
	}

	@Override
	public void destroy() {
	}

	@Override
	public void init(EndpointConfig arg0) {
	}

	/*
	 * String[] arr;
	 * 
	 * public MoveMessage(CheckerBoard board) { String[] arr; int z=0; arr = new
	 * String[(board.BOARD_SIZE / 2) * 6]; for (int i = 0; i < board.BOARD_SIZE;
	 * i++) { for (int j = 0; j < board.BOARD_SIZE; j++) { if
	 * (board.squareIsOccupied(i, j)) { arr[z] = Integer.toString(i) + ":" +
	 * Integer.toString(j) + (board.isPlayerOne(i, j) ? "1" : "2") + ":" +
	 * (board.isKing(i, j)? "p": "k"); z++; } } } }
	 */
}
