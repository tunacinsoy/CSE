// 27.12.2019
import java.util.ArrayList;

public class Player {
	private String name = new String();
	private int turnNumber;
	private int nextTurn;
	private int amountOfMoney;
	private Piece piece;
	private int cycleNumber;
	private int cellLocation;
	private Cell cellFinal;
	private boolean isBankrupt;
	private boolean isInJail;
	private ArrayList<Cell> ownedCells ;
	private int waitedTurnsInJail = 0;
	private int possibilityOfTakingRisk;
	private boolean doesHaveJailGetawayCard;


	Player(String name, int possibilityOfTakingRisk, int initialMoney, int turnNumber) {
		setName(name);
		setPossibilityOfTakingRisk(possibilityOfTakingRisk);
		setAmountOfMoney(initialMoney);
		setTurnNumber(turnNumber);
		this.cellLocation = 0;
		this.possibilityOfTakingRisk = possibilityOfTakingRisk ;
		ownedCells = new ArrayList<Cell>();

	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getTurnNumber() {
		return turnNumber;
	}

	public void setTurnNumber(int turnNumber) {
		this.turnNumber = turnNumber;

	}

	public int getAmountOfMoney() {
		return amountOfMoney;
	}

	public void setAmountOfMoney(int amountOfMoney) {
		this.amountOfMoney = amountOfMoney;
	}

	public Piece getPiece() {
		return piece;
	}

	public void setPiece(Piece piece) {
		this.piece = piece;
	}

	public int getCycleNumber() {
		return cycleNumber;
	}

	public void setCycleNumber(int cycleNumber) {
		this.cycleNumber = cycleNumber;
	}

	public int getCellLocation() {

		return cellLocation;
	}

	public void setCellLocation(int cellLocation) {

		this.cellLocation += cellLocation;
		if(this.cellLocation>39){this.cellLocation %= 40;}
		//System.out.println("Sum of face values : "+cellLocation);

	}

	public void setBankrupt(boolean isBankrupt) {
		this.isBankrupt = isBankrupt;
	}

	public boolean isInJail() {
		return isInJail;
	}

	public void setInJail(boolean isInJail) {
		this.isInJail = isInJail;
	}

	public ArrayList<Cell> getOwnedCells() {
		return ownedCells;
	}

	public void setOwnedCells(ArrayList<Cell> ownedCells) {
		this.ownedCells = ownedCells;
	}

	public int getWaitedTurnsInJail() {
		return waitedTurnsInJail;
	}

	public void setWaitedTurnsInJail(int waitedTurnsInJail) {
		this.waitedTurnsInJail = waitedTurnsInJail;
	}

	public int getPossibilityOfTakingRisk() {
		return possibilityOfTakingRisk;
	}

	public void setPossibilityOfTakingRisk(int possiblyOfTakingRisk) {
		this.possibilityOfTakingRisk = possiblyOfTakingRisk;
	}

	public Cell getCellFinal() {
		return cellFinal;
	}

	public void setCellFinal(Cell cellFinal) {
		if(this.cellLocation >= 39){
			this.cellLocation %= 40;
		}

		this.cellFinal = cellFinal;
	}

	public int getNextTurn() {
		return nextTurn;
	}

	public void setNextTurn(int nextTurn) {
		this.nextTurn = nextTurn;
	}

	public boolean isBankrupt(Player player) {

		if (player.getAmountOfMoney() < 0) {
			this.isBankrupt = true;
			return true;
		}

		else {
			this.isBankrupt = false;
			return false;
		}

	}



	public boolean isBankrupt() {
		return isBankrupt;
	}

	public boolean isDoesHaveJailGetawayCard() {
		return doesHaveJailGetawayCard;
	}

	public void setDoesHaveJailGetawayCard(boolean doesHaveJailGetawayCard) {
		this.doesHaveJailGetawayCard = doesHaveJailGetawayCard;

	}

	public void addToOwnedCells(Cell cell){
		ownedCells.add(cell);

	}
	public void removeFromOwnedCells(Cell cell){
		ownedCells.remove(cell);

	}




}
