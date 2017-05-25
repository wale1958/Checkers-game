package cMessage;

import javax.json.Json;
import javax.json.JsonObject;
import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

/**
 * Encodes PlayerMove
 * 
 * @author adebowale
 *
 */
public class PlayerMoveEncoder implements Encoder.Text<PlayerMove> {

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void init(EndpointConfig arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public String encode(PlayerMove pMsg) throws EncodeException {
		// TODO Auto-generated method stub
		JsonObject jsonPlayerMove = Json.createObjectBuilder().add("type", "PlayerMove")
				.add("fromCol", pMsg.getFromCol()).add("fromRow", pMsg.getFromRow()).add("toCol", pMsg.getToCol())
				.add("toRow", pMsg.getToRow()).build();

		return jsonPlayerMove.toString();

	}

}
