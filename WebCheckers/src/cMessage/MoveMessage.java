package cMessage;

public class MoveMessage extends Message {
	int fromRow;
	int fromCol;
	int toRow;
	int toCol;

	public MoveMessage(int fromRow, int fromCol, int toRow, int toCol) {

		this.fromRow = fromRow;
		this.fromCol= fromCol;
		this.toRow=toRow;
		this.toCol= toCol;

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
}
