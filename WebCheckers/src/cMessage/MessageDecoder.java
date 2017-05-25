package cMessage;

import java.io.StringReader;

import javax.json.*;
import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

/**
 * Decodes BoardMessage, InitMessage, and PlayerMoveMessage
 * 
 * @author adebowale
 *
 */
public class MessageDecoder implements Decoder.Text<Message> {

	@Override
	public Message decode(String msg) throws DecodeException {
		JsonObject jsonObject = Json.createReader(new StringReader(msg)).readObject();

		if (jsonObject.getString("type").equals("mainBoard")) {
			BoardMessage message = new BoardMessage(null, "");
			message.buildBoard(jsonObject.getString("board"));// convert from
																// the string
																// representation
																// to
																// CheckerBoard
																// representation
																// of the board
			return message;
		} else if (jsonObject.getString("type").equals("Init")) {
			InitMessage message = new InitMessage(jsonObject.getString("Request"));
			return message;
		} else if (jsonObject.getString("type").equals("PlayerMove")) {
			PlayerMove pMove = new PlayerMove(jsonObject.getInt("fromRow"), jsonObject.getInt("fromCol"),
					jsonObject.getInt("toRow"), jsonObject.getInt("toCol"));
			return pMove;
		} else
			throw new DecodeException(msg, "Non valid message");

	}

	/**
	 * Check to see if incoming message is valid JSON
	 */

	@Override
	public boolean willDecode(String msg) {
		try {
			Json.createReader(new StringReader(msg)).readObject();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public void destroy() {
	}

	@Override
	public void init(EndpointConfig arg0) {
	}

}
