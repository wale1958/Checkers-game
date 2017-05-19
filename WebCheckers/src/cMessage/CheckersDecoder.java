package cMessage;

import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

import checkers.common.CheckerBoard;

public class CheckersDecoder implements Decoder.Text<CheckerBoard>{

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void init(EndpointConfig arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public CheckerBoard decode(String arg0) throws DecodeException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean willDecode(String arg0) {
		// TODO Auto-generated method stub
		return false;
	}

}
