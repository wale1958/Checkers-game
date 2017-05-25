package cMessage;

import checkers.common.SquarePlayer;

public class PlayerMove extends Message{
	int fromRow;
	int fromCol;
	int toRow;
	int toCol;
	//SquarePlayer player;

	public PlayerMove(int fromRow, int fromCol, int toRow, int toCol) {

		this.fromRow = fromRow;
		this.fromCol= fromCol;
		this.toRow=toRow;
		this.toCol= toCol;
		//this.player=player;

	}

	public int getFromRow() {
		return fromRow;
	}

	public int getFromCol() {
		return fromCol;
	}

	public int getToRow() {
		return toRow;
	}

	public int getToCol() {
		return toCol;
	}
	/*public SquarePlayer getPlayer(){
		return player;
	}*/

}
