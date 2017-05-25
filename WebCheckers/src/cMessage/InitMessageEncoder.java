package cMessage;

import javax.json.*;
import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

/**
 * Encodes the {@link InitMessage} class
 * @author adebowale
 *
 */
public class InitMessageEncoder implements Encoder.Text<InitMessage> {

	@Override
	public String encode(InitMessage msg) throws EncodeException {
		JsonObject jsonInitMessage = Json.createObjectBuilder()
				.add("type","Init")
                .add("Request", msg.getRequest())
                .build();

        return jsonInitMessage.toString();
    }	

	@Override
	public void destroy() {
	}

	@Override
	public void init(EndpointConfig arg0) {
	}

}
