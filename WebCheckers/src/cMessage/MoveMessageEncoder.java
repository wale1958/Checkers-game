package cMessage;

import javax.json.Json;
import javax.json.JsonObject;
import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

import checkers.common.CheckerBoard;

public class MoveMessageEncoder implements Encoder.Text<MoveMessage> {

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void init(EndpointConfig arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public String encode(MoveMessage mMsg) throws EncodeException {
		// TODO Auto-generated method stub
		JsonObject jsonMoveMessage = Json.createObjectBuilder()
				.add("type", "move")
				.add("fromCol", mMsg.getFromCol())
				.add("fromRow", mMsg.getFromRow())
				.add("toCol", mMsg.getToCol())
				.add("toRow", mMsg.getToRow())
				.build();

		return jsonMoveMessage.toString();

	}

}
