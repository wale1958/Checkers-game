package cMessage;

import java.util.StringTokenizer;

import checkers.common.CheckerBoard;
import checkers.common.Square;
import checkers.common.SquarePlayer;
import sun.util.locale.StringTokenIterator;

/**
 * Relays the board from the server to the client
 * 
 * @author adebowale
 *
 */
public class BoardMessage extends Message {

	CheckerBoard board;
	String notice;

	public BoardMessage(CheckerBoard board, String notice) {
		this.board = board;
		this.notice = notice;
	}

	/**
	 * @return message to be relayed to the users
	 */
	public String getNotice() {
		return notice;
	}

	/**
	 * @return board to be sent by the server
	 */
	public CheckerBoard getBoard() {
		return board;
	}

	/**
	 * The board is in encoded in string format and so this method converts that
	 * string representation into a CheckerBoard representation of the board
	 * 
	 * @param in
	 *            string representation of the board created in
	 *            {@link BoardMessageEncoder}
	 */
	public void buildBoard(String in) {
		board = new CheckerBoard();
		StringTokenizer tok = new StringTokenizer(in, "|", false);
		this.notice = tok.nextToken();
		while (tok.hasMoreTokens()) {
			String piece = tok.nextToken();
			StringTokenizer tok2 = new StringTokenizer(piece, ":", false);
			int r = Integer.parseInt(tok2.nextToken());
			int c = Integer.parseInt(tok2.nextToken());
			System.out.println(piece);

			board.set(r, c, tok2.nextToken().equalsIgnoreCase("1") ? SquarePlayer.PlayerOne : SquarePlayer.PlayerTwo);
			if (tok2.nextToken() == "K") {
				board.makeKing(r, c);
			}
		}

	}
}
