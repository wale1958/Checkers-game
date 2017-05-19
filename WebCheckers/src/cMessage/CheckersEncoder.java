package cMessage;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

import checkers.common.CheckerBoard;

public class CheckersEncoder implements Encoder.Text<CheckerBoard>{

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void init(EndpointConfig arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String encode(CheckerBoard arg0) throws EncodeException {
		// TODO Auto-generated method stub
		return null;
	}

}
