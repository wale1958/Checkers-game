package cMessage;

/**
 * Holds from where and to where coordinates of a move attempt
 * 
 * @author adebowale
 *
 */
public class PlayerMove extends Message {
	int fromRow;
	int fromCol;
	int toRow;
	int toCol;

	public PlayerMove(int fromRow, int fromCol, int toRow, int toCol) {

		this.fromRow = fromRow;
		this.fromCol = fromCol;
		this.toRow = toRow;
		this.toCol = toCol;

	}

	/**
	 * @return the row a piece is to be moved from
	 */
	public int getFromRow() {
		return fromRow;
	}

	/**
	 * @return the column a piece should be moved frok
	 */
	public int getFromCol() {
		return fromCol;
	}

	/**
	 * @return The row a piece should be moved to
	 */
	public int getToRow() {
		return toRow;
	}

	/**
	 * @return The column a piece should be moved to
	 */
	public int getToCol() {
		return toCol;
	}

}
