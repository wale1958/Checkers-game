package cMessage;

import java.util.StringTokenizer;

import checkers.common.CheckerBoard;
import checkers.common.Square;
import checkers.common.SquarePlayer;
import sun.util.locale.StringTokenIterator;

public class BoardMessage extends Message {

	CheckerBoard board;
	String notice;

	public BoardMessage(CheckerBoard board, String notice) {
		this.board = board;
		this.notice=notice;
	}

	public String getNotice(){
		return notice;
	}
	public CheckerBoard getBoard() {
		return board;
	}

	public void buildBoard(String in) {
		board = new CheckerBoard();
		StringTokenizer tok = new StringTokenizer(in, "|", false);
		this.notice=tok.nextToken();
		while (tok.hasMoreTokens()) {
			String piece = tok.nextToken();
			StringTokenizer tok2 = new StringTokenizer(piece, ":", false);
			int r=Integer.parseInt(tok2.nextToken());
			int c=Integer.parseInt(tok2.nextToken());
			System.out.println(piece);
			
			board.set(r, c,tok2.nextToken().equalsIgnoreCase("1") ? SquarePlayer.PlayerOne: SquarePlayer.PlayerTwo);
			if(tok2.nextToken()=="K"){
				board.makeKing(r, c);
			}
			}

		}
}
