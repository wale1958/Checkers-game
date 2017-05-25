package checkers.server;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.glassfish.tyrus.server.*;

public class CheckersServer {

	/**
	 * run the server
	 * 
	 * @param args
	 *            not used
	 */
	public static void main(String[] args) {
		runServer();
	}

	/**
	 * Creates an Infinite loop for the server to be stopped in the console
	 */
	public static void runServer() {

		Server server = new Server("localhost", 8025, "/websockets", null, CheckersServerEndpoint.class);

		try {
			server.start();
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			System.out.print("Please press a key to stop the server.");
			reader.readLine();
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			server.stop();
		}
	}
}