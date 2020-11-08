import java.util.ArrayList;
import java.util.Collections;
import java.util.*;

/*
 * Implemented with Singleton Design Pattern just like as Bank class, also includes Factory Design Pattern
 * 27.12.19
*/
public class Board {
	private static Board board;
	private Dice dice = new Dice();
	private int noOfPlayers;
	private Piece[] pieces = new Piece[8];
	private Cell[] cells = new Cell[40];
	private CommunityCard[] communitycards = new CommunityCard[20];
	private ChanceCard[] chancecards = new ChanceCard[24];
	private static ArrayList<Cell> jails = new ArrayList<Cell>();

	private String[] pieceTypes = { "Car", "Hat", "Racket", " Cat", "Shoe", "Ship", "Pac-Man", "Trumpet" };

	private Board(int noOfPayingCells, int noOfPlayers, int amountOfMoneyToBeTaken, ArrayList<Player> players,
			String[] cell_inputs) {
		
		// Pieces are created and assigned to each player
		for (int i = 0; i < noOfPlayers; i++) {
			pieces[i] = new Piece(pieceTypes[i]);
		}

		this.noOfPlayers = noOfPlayers;
		assignAllPieces(players);
		
		// Cards are created and shuffled 
		setCommunityCardArray();
		setChanceCardArray();
		List<Card> tobeshuffled = Arrays.asList(communitycards);
		Collections.shuffle(tobeshuffled);
		tobeshuffled.toArray(communitycards);
		tobeshuffled = Arrays.asList(chancecards);
		Collections.shuffle(tobeshuffled);
		tobeshuffled.toArray(chancecards);
		
		// Filling board with cells, information of cells are coming from .json file, applied Factory Design Pattern
		for (int k = 0; k < 40; k++) {
			String cellType = null;
			int id = 0;
			Player owner = null;
			String color = null;
			int cellPrice = 0;
			int cellRent = 0;
			String name = null;

			String[] inputs = cell_inputs[k].split(" ", 10);

			cellType = inputs[0];
			id = Integer.valueOf(inputs[1]);
			color = inputs[3];
			cellPrice = Integer.parseInt(inputs[4]);
			cellRent = Integer.parseInt(inputs[5]);
			name = inputs[6].replace("_", " ");
			for (String a : inputs) {
				System.out.print(cellType);
			}
			System.out.println();

			cells[k] = GetCellFactory.GetCell(cellType, id, owner, color, cellPrice, cellRent, name, communitycards,
					chancecards);

		}

	}

	private void setCommunityCardArray() {
		for (int i = 0; i < communitycards.length / 5; i++) {
			communitycards[i] = new CommunityCard("Community Card", "You lost the bet! Pay 25$ to each player!", 0);
		}
		for (int i = 4; i < 4 + (communitycards.length / 5); i++) {
			communitycards[i] = new CommunityCard("Community Card",
					"Government Regulations! Pay 15$ tax per player to bank!", 1);
		}
		for (int i = 8; i < 8 + (communitycards.length / 5); i++) {
			communitycards[i] = new CommunityCard("Community Card", "Blackjack was not good! Pay 20$ to each player!",
					2);
		}
		for (int i = 12; i < 12 + (communitycards.length / 5); i++) {
			communitycards[i] = new CommunityCard("Community Card", "BAZINGAAAAA!! Get 20$ from each player!", 3);
		}
		for (int i = 16; i < 16 + (communitycards.length / 5); i++) {
			communitycards[i] = new CommunityCard("Community Card",
					"Jackpot hits! Roll the dice again, if the sum of face values are higher than 6, take 50$ from the bank!",
					4);
		}
	}

	private void setChanceCardArray() {
		for (int i = 0; i < chancecards.length / 6; i++) {
			chancecards[i] = new ChanceCard("Chance Card",
					"Let's lose some weight! Roll the dice again and move to that location!", 0);
		}
		for (int i = 4; i < (4 + (chancecards.length / 6)); i++) {
			chancecards[i] = new ChanceCard("Chance Card", "Luck has never been your friend! Move 3 cells backwards!",
					1);
		}
		for (int i = 8; i < 8 + chancecards.length / 6; i++) {
			chancecards[i] = new ChanceCard("Chance Card",
					"Bail has been paid by an Anonymous! You can get out of the jail with this card!", 2);
		}
		for (int i = 12; i < 12 + chancecards.length / 6; i++) {
			chancecards[i] = new ChanceCard("Chance Card", "Cops! Go directly to jail!", 3);
		}
		for (int i = 16; i < 16 + chancecards.length / 6; i++) {
			chancecards[i] = new ChanceCard("Chance Card", "Luck is with you my friend! Move 3 cells forwards!", 4);
		}
		for (int i = 20; i < 20 + chancecards.length / 6; i++) {
			chancecards[i] = new ChanceCard("Chance Card", "Heritage from Egypt! Get 100$ from bank!", 5);
		}

	}

	public void assignAllPieces(ArrayList<Player> players) {

		for (int i = 0; i < noOfPlayers; i++) {
			pieces[i].setOwner((players.get(i)));
		}

	}

	public void removePiece(Piece pieceToRemove) {
		pieceToRemove.setOwner(null);
	}

	public int getNoOfPlayers() {
		return noOfPlayers;
	}

	public void setNoOfPlayers(int noOfPlayers) {
		this.noOfPlayers = noOfPlayers;
	}

	public Piece[] getPieces() {
		return pieces;
	}

	public void setPieces(Piece[] pieces) {
		this.pieces = pieces;
	}

	public Cell[] getCells() {
		return cells;
	}

	public void setCells(Cell[] cells) {
		this.cells = cells;
	}

	public String[] getPieceTypes() {
		return pieceTypes;
	}

	public void setPieceTypes(String[] pieceTypes) {
		this.pieceTypes = pieceTypes;
	}

	public Dice getDice() {
		return dice;
	}

	public void setDice(Dice dice) {
		this.dice = dice;
	}

	public static Board getBoard(int noOfPayingCells, int noOfPlayers, int amountOfMoneyToBeTaken,
			ArrayList<Player> players, String[] cell_inputs) {
		if (board == null) {
			board = new Board(noOfPayingCells, noOfPlayers, amountOfMoneyToBeTaken, players, cell_inputs);
		}
		return board;
	}

	public int checkJail(Cell cell) {
		int check = 0;
		for (int i = 0; i < jails.size(); i++) {
			if (cell.getCellId() == jails.get(i).getCellId()) {
				check = 1;
			}
		}

		return check;

	}

	public static void addJail(Cell cell) {
		jails.add(cell);

	}
}
