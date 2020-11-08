// 27.12.2019
public class Piece {

	private String shape = new String();
	private Player owner;
	private Cell cellId;


	Piece(String shape){
		this.shape = shape;
	}
	public void setOwner(Player owner){
		this.owner = owner;
	}

	public String getShape(){
		return shape;
	}
	public void move(Cell[] cells){
		int initialPosition = owner.getCellLocation();
		cellId = cells[initialPosition];
	}


}
