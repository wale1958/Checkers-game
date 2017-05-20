package cMessage;

import java.io.StringReader;

import javax.json.Json;
import javax.json.JsonObject;
import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;


public class MoveMessageDecoder implements Decoder.Text<Message> {

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void init(EndpointConfig arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public Message decode(String msg) throws DecodeException {
		// TODO Auto-generated method stub
		JsonObject jsonObject = Json.createReader(new StringReader(msg)).readObject();

		//if (jsonObject.getString("type").equals("move")) {
			MoveMessage message = new MoveMessage(((MoveMessage) jsonObject).getFromRow(),
					((MoveMessage) jsonObject).getFromCol(), ((MoveMessage) jsonObject).getToRow(),
					((MoveMessage) jsonObject).getToCol());
			return message;

			/*
		} else if(true){
			
		} else
			throw new DecodeException(msg, "Neither poke nor prod.");

			 	*/
	}

	@Override
	public boolean willDecode(String arg0) {
		// TODO Auto-generated method stub
		return false;
	}

}
