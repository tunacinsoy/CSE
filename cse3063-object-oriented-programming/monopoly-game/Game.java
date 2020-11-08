// 27.12.2019
import java.util.ArrayList;

public class Game {
    private ArrayList<Player> players = new ArrayList<Player>();
    private Board board;
    private int initialMoney;
    private int howManyTaxedCells;
    private int taxedMoneyAmount;
    private int turnNumber = 0;
    private Bank bank ;
    private int moveNumbers = 0;
    Player current;

    Game(int initialMoney, int howManyTaxedCells, String[] playerNames, int taxedMoneyAmount, int possibilityOfTakingRiskValues[],String[] cell_inputs) {
        this.initialMoney = initialMoney;
        this.taxedMoneyAmount = taxedMoneyAmount;
        this.howManyTaxedCells = howManyTaxedCells;
        bank = Bank.getBank();

        for(int i = 0; i < playerNames.length; i++){

            players.add(new Player(playerNames[i],possibilityOfTakingRiskValues[i],initialMoney,i));
            if(i == playerNames.length-1){players.get(i).setNextTurn(0);continue;}
            players.get(i).setNextTurn(i+1);
        }
        //board.assignAllPieces(players);
        board = Board.getBoard(howManyTaxedCells,playerNames.length, taxedMoneyAmount, players,cell_inputs); // Singleton Pattern
        for(int i  = 0; i < playerNames.length; i++){
            players.get(i).setPiece(board.getPieces()[i]);
        }

        System.out.println("Players:");
        for (int i = 0; i < players.size(); i++) {
            System.out.println( (i+1) +". " +  players.get(i).getName());
        }
        System.out.println();

    }


    public void onTurn(){
        turnNumber = current.getTurnNumber();
        int beforeCell = players.get(turnNumber).getCellLocation();
        if(players.get(turnNumber).isInJail())
        {
            int diceInJail = board.getDice().rollDices();
            if(diceInJail % 2 == 0 )
            {
                System.out.println(players.get(turnNumber).getName() +" is free because the player rolled even number.");
                players.get(turnNumber).setWaitedTurnsInJail(2);

            }
            players.get(turnNumber).setWaitedTurnsInJail(players.get(turnNumber).getWaitedTurnsInJail()+1);;
            if(players.get(turnNumber).getWaitedTurnsInJail() == 3)
            {
                players.get(turnNumber).setWaitedTurnsInJail(0);
                players.get(turnNumber).setInJail(false);
            }


            PassTheDice(current); return;

        }

        System.out.println(players.get(turnNumber).getName() +" is rolling dice right now...");
        players.get(turnNumber).setCellLocation(board.getDice().rollDices());


        players.get(turnNumber).getPiece().move(board.getCells());

        System.out.println(players.get(turnNumber).getName() + "'s " +players.get(turnNumber).getPiece().getShape() + " is being moved right now ...");
        players.get(turnNumber).setCellFinal(board.getCells()[players.get(turnNumber).getCellLocation()]);

        System.out.println("After moving, " + players.get(turnNumber).getName() + " is currently on Cell " + players.get(turnNumber).getCellFinal().getCellId()+", "+board.getCells()[players.get(turnNumber).getCellLocation()].getName());

        cardcell(turnNumber);

///----------------  HANDLE OF CELL LOCATION  -----------------------///

        if(board.getCells()[players.get(turnNumber).getCellLocation()].getOwner() != null && (board.getCells()[players.get(turnNumber).getCellLocation()].getOwner() != players.get(turnNumber))){
        	board.getCells()[players.get(turnNumber).getCellLocation()].payRent(players.get(turnNumber));
        }
        else if(board.getCells()[players.get(turnNumber).getCellLocation()].getCanBeBought() == true)
        {
        	board.getCells()[players.get(turnNumber).getCellLocation()].buyingCell(players.get(turnNumber));

        }
        else if ((board.getCells()[players.get(turnNumber).getCellLocation()].getOwner() == players.get(turnNumber)))
        {


        	board.getCells()[players.get(turnNumber).getCellLocation()].increaseBuildingNumber();
        	
        }


        if(board.checkJail(board.getCells()[players.get(turnNumber).getCellLocation()]) == 1)
        {
            System.out.println("JAIL!!!");
            players.get(turnNumber).setInJail(true);
            if (players.get(turnNumber).isDoesHaveJailGetawayCard() == true){
                System.out.println(players.get(turnNumber).getName() + " has Jail Getaway Card!");
                players.get(turnNumber).setInJail(false);
                players.get(turnNumber).setDoesHaveJailGetawayCard(false);
                System.out.println(players.get(turnNumber).getName() + " is no longer in Jail!");
            }
            PassTheDice(current); return;
        }

        int afterCell = players.get(turnNumber).getCellLocation();
        if(beforeCell - players.get(turnNumber).getCellFinal().getCellId() > 0){
            board.getCells()[0].MoneyFunc(players.get(turnNumber), bank); // will give money to player
        }

        if (players.get(turnNumber).getCellFinal().getCellId() != 0){
            players.get(turnNumber).getCellFinal().MoneyFunc(players.get(turnNumber), bank); // will take money from player
        }
        System.out.println(players.get(turnNumber).getName() + " has money amount of "+players.get(turnNumber).getAmountOfMoney() +" $");
        moveNumbers++;

        PassTheDice(current);
        return;




    }
    public void PassTheDice(Player current){


        int firstsize = players.size();
        int secondsize = players.size();

        if(current.isBankrupt(current) && current.getOwnedCells().size() != 0){

        	while (current.getAmountOfMoney() < 0){
        		if (current.getOwnedCells().size() == 0){
        			
        			break;
        		}
        		while (current.getAmountOfMoney() < 0){
        			if(current.getOwnedCells().get(0).getCountOfBuildings() == 0)
        			{
        				break;
        			}
        			current.setAmountOfMoney(current.getAmountOfMoney() + current.getOwnedCells().get(0).getCountOfBuildings() * 100);
        			System.out.println(current.getName() + " has sold house at " + current.getOwnedCells().get(0).getName());
        			current.getOwnedCells().get(0).setCountOfBuildings(current.getOwnedCells().get(0).getCountOfBuildings() - 1);

        		}

        		if (current.getAmountOfMoney() >= 0){
        			break;
        		}
        		current.setAmountOfMoney(current.getAmountOfMoney() + current.getOwnedCells().get(0).getPriceOfTheCell() * 3 / 4 );
        		current.getOwnedCells().get(0).setCanBeBought(true);
        		current.getOwnedCells().get(0).setOwner(null);
        		System.out.println(current.getName() + " has sold " +  current.getOwnedCells().get(0).getName() + " to prevent bankrupting.");
        		current.getOwnedCells().remove(0);

        	}
        	current.setBankrupt(false);

        }

        if(current.isBankrupt(current))
        {

            if(current.getTurnNumber() == players.size()-1)
            {
                players.get(current.getTurnNumber()-1).setNextTurn(0);
            }
            System.out.println("**********  "+players.get(current.getTurnNumber()).getName()+" has bankrupted and left the game ..."+"  **********");
            System.out.println("Dices are given automatically to "+players.get(current.getNextTurn()).getName());
            System.out.println(moveNumbers);
            System.out.println("Bank money: " + bank.getTotalMoney());

            players.remove(current.getTurnNumber());
            secondsize = players.size();
            if(current.getNextTurn() == players.size())
            {
                current.setNextTurn(current.getNextTurn()-1);
            }
            for(int i=current.getTurnNumber() ; i<players.size();i++){
                players.get(i).setTurnNumber(players.get(i).getTurnNumber()-1);

                if(i == players.size()-1)
                {
                    players.get(i).setNextTurn(0);
                    break;
                }

                players.get(i).setNextTurn(players.get(i).getNextTurn()-1);

            }

        }
        if(players.size()== 1)
        {	System.out.println();
            System.out.println(players.get(0).getName()+" has won the game !");
            System.out.println("Game is over !");System.exit(1);;
        }
        if(firstsize == secondsize)
        {
            System.out.println(players.get(current.getTurnNumber()).getName()+" has given the dices to "+players.get(current.getNextTurn()).getName());
            System.out.println("Total number of moves: "+moveNumbers);
            System.out.println("Bank money: " + bank.getTotalMoney());
        }
        System.out.println();
        this.current = (players.get(current.getNextTurn()));
        return;
    }


    public int getTaxedMoneyAmount() {
        return taxedMoneyAmount;
    }


    public void setTaxedMoneyAmount(int taxedMoneyAmount) {
        this.taxedMoneyAmount = taxedMoneyAmount;
    }


    public void Play(){
        current = players.get(0);
    	while(players.size()!= 1 && bank.getTotalMoney() > 0)
        	{
        		onTurn();
        	}
    	if(bank.getTotalMoney() <= 0)
    	{
    		Player winner = null;
    		for(int a = 0; a<players.size();a++)
    		{
    			if(a == 0)
    			{
    				winner = players.get(0);
    				continue;
    			}
    			else
    			{
    				if(players.get(a).getAmountOfMoney() > winner.getAmountOfMoney())
    				{
    					winner = players.get(a);
    				}
    			}
    		}
    		System.out.println(winner.getName() + " has won the game because bank's money amount is less than 0 !!");
    		System.out.println(winner.getName()+" is the winner of the game with the amount of money: "+ winner.getAmountOfMoney() +" !!!");
    		System.out.println("Game is over !!!");
    	}


    }

    public void cardcell(int turnNumber){

        if (board.getCells()[players.get(turnNumber).getCellLocation()].getName() == "Chance Card Cell"){
            int randomValue = (int)(Math.random() * 24);
            if(board.getCells()[players.get(turnNumber).getCellLocation()].getChanceCards()[randomValue].getCardID() == 0) {
                board.getCells()[players.get(turnNumber).getCellLocation()].getChanceCards()[randomValue].ActionOfMoveCard(board.getCells()[players.get(turnNumber).getCellLocation()].getChanceCards()[randomValue].getActionOfTheCard(),board.getDice(), players.get(turnNumber));
                players.get(turnNumber).getPiece().move(board.getCells());
            }
            else if (board.getCells()[players.get(turnNumber).getCellLocation()].getChanceCards()[randomValue].getCardID() == 1){
                board.getCells()[players.get(turnNumber).getCellLocation()].getChanceCards()[randomValue].ActionOfMoveWithoutDiceCard(board.getCells()[players.get(turnNumber).getCellLocation()].getChanceCards()[randomValue].getActionOfTheCard(), players.get(turnNumber), -3);
                players.get(turnNumber).getPiece().move(board.getCells());
            }
            else if (board.getCells()[players.get(turnNumber).getCellLocation()].getChanceCards()[randomValue].getCardID() == 2){
                board.getCells()[players.get(turnNumber).getCellLocation()].getChanceCards()[randomValue].ActionOfJailGetawayCard(board.getCells()[players.get(turnNumber).getCellLocation()].getChanceCards()[randomValue].getActionOfTheCard(), players.get(turnNumber));
            }
            else if(board.getCells()[players.get(turnNumber).getCellLocation()].getChanceCards()[randomValue].getCardID() == 3){
                board.getCells()[players.get(turnNumber).getCellLocation()].getChanceCards()[randomValue].ActionOfDirectlyToJailCard(board.getCells()[players.get(turnNumber).getCellLocation()].getChanceCards()[randomValue].getActionOfTheCard(), players.get(turnNumber));
            }
            else if(board.getCells()[players.get(turnNumber).getCellLocation()].getChanceCards()[randomValue].getCardID() == 4) {
                board.getCells()[players.get(turnNumber).getCellLocation()].getChanceCards()[randomValue].ActionOfMoveWithoutDiceCard(board.getCells()[players.get(turnNumber).getCellLocation()].getChanceCards()[randomValue].getActionOfTheCard(), players.get(turnNumber), 3);
                players.get(turnNumber).getPiece().move(board.getCells());
            }
            else if(board.getCells()[players.get(turnNumber).getCellLocation()].getChanceCards()[randomValue].getCardID() == 5) {
                board.getCells()[players.get(turnNumber).getCellLocation()].getChanceCards()[randomValue].ActionOfGetMoneyFromBankCard(board.getCells()[players.get(turnNumber).getCellLocation()].getChanceCards()[randomValue].getActionOfTheCard(), players.get(turnNumber), bank);
            }
        }
        else if (board.getCells()[players.get(turnNumber).getCellLocation()].getName() == "Community Card Cell") {
            int randomValue = (int)(Math.random() * 20);
            if (board.getCells()[players.get(turnNumber).getCellLocation()].getCommunityCards()[randomValue].getCardID() == 0) {
                board.getCells()[players.get(turnNumber).getCellLocation()].getCommunityCards()[randomValue].ActionOfPayAllPlayer25$(board.getCells()[players.get(turnNumber).getCellLocation()].getCommunityCards()[randomValue].getActionOfTheCard(), players.get(turnNumber), players);
            }
            else if(board.getCells()[players.get(turnNumber).getCellLocation()].getCommunityCards()[randomValue].getCardID() == 1) {
                board.getCells()[players.get(turnNumber).getCellLocation()].getCommunityCards()[randomValue].ActionOfPay15$toBankAccordingToNumberOfPlayers(board.getCells()[players.get(turnNumber).getCellLocation()].getCommunityCards()[randomValue].getActionOfTheCard(), players.get(turnNumber),bank, players);
            }
            else if(board.getCells()[players.get(turnNumber).getCellLocation()].getCommunityCards()[randomValue].getCardID() == 2) {
                board.getCells()[players.get(turnNumber).getCellLocation()].getCommunityCards()[randomValue].ActionOfPay20$ToAllPlayersBecauseGambling(board.getCells()[players.get(turnNumber).getCellLocation()].getCommunityCards()[randomValue].getActionOfTheCard(), players.get(turnNumber), players);
            }
            else if(board.getCells()[players.get(turnNumber).getCellLocation()].getCommunityCards()[randomValue].getCardID() == 3) {
                board.getCells()[players.get(turnNumber).getCellLocation()].getCommunityCards()[randomValue].ActionOfTake20$FromAllPlayersCard(board.getCells()[players.get(turnNumber).getCellLocation()].getCommunityCards()[randomValue].getActionOfTheCard(), players.get(turnNumber), players);
            }
            else if(board.getCells()[players.get(turnNumber).getCellLocation()].getCommunityCards()[randomValue].getCardID() == 4) {
                board.getCells()[players.get(turnNumber).getCellLocation()].getCommunityCards()[randomValue].ActionOfTake50$AccordingToDiceValue(board.getCells()[players.get(turnNumber).getCellLocation()].getCommunityCards()[randomValue].getActionOfTheCard(),bank, players.get(turnNumber), board.getDice());
            }


        }
	}


}
