//26.12.2019
//Chance Card actions are handled in here
import java.util.*;
public class ChanceCard extends Card
{

	public ChanceCard(String nameOfCard,String actionOfTheCard, int cardID) {
		super.setNameOfTheCard(nameOfCard);
		super.setActionOfTheCard(actionOfTheCard);
		super.setCardID(cardID);
	}

	public void ActionOfMoveCard ( String actionOfCard, Dice dice, Player moverPlayer){
			System.out.println("Chance Card has drawn by " + moverPlayer.getName() + "!");
			System.out.println(actionOfCard);
			int numberOfMoves = dice.rollDices();
			moverPlayer.setCellLocation(moverPlayer.getCellLocation()+numberOfMoves);

			System.out.println(moverPlayer.getName() + " moved " + numberOfMoves + " steps because of Move Card");
	}
	public void ActionOfMoveWithoutDiceCard (String actionOfCard,Player moverPlayer, int numberOfMove){
			System.out.println("Chance Card has drawn by " + moverPlayer.getName() + "!");
			System.out.println(actionOfCard);
			moverPlayer.setCellLocation(moverPlayer.getCellLocation() + numberOfMove);
			if (numberOfMove > 0){
				System.out.println(moverPlayer.getName() + " moved 3 " + " steps forwards because of Move Card");
			}
			else if(numberOfMove < 0){
				System.out.println(moverPlayer.getName() + " moved 3 " + " steps backwards because of Move Card");
			}

	}

	public void ActionOfPayToBankCard (String actionOfCard, Player senderPlayer, Bank bank, int amountOfMoney){

		System.out.println("Chance Card has drawn by " + senderPlayer.getName() + "!");
		System.out.println(actionOfCard);
		senderPlayer.setAmountOfMoney(senderPlayer.getAmountOfMoney()-amountOfMoney);
		bank.setTotalMoney(bank.getTotalMoney()+amountOfMoney);
		System.out.println(senderPlayer.getName() + " paid " + amountOfMoney + " to bank because of Chance Card.");

	}

	public void ActionOfPayToSinglePlayerCard (String actionOfCard, Player senderPlayer, Player receiverPlayer, int amountOfMoney){
		System.out.println("Chance Card has drawn by " + senderPlayer.getName() + "!");
		System.out.println(actionOfCard);
		senderPlayer.setAmountOfMoney(senderPlayer.getAmountOfMoney() - amountOfMoney);
		receiverPlayer.setAmountOfMoney(receiverPlayer.getAmountOfMoney() + amountOfMoney);
		System.out.println(senderPlayer.getName() + " paid " + amountOfMoney + " to " + receiverPlayer.getName() + " because of Chance Card.");

	}
	public void ActionOfPayToAllPlayersCard (String actionOfCard, Player senderPlayer, ArrayList<Player> allPlayers, int amountOfMoney){
		System.out.println("Chance Card has drawn by " + senderPlayer.getName() + "!");
		System.out.println(actionOfCard);

		for (int i = 0; i < allPlayers.size(); i++){
			if (senderPlayer == allPlayers.get(i)) {
				continue;
			}
			senderPlayer.setAmountOfMoney(senderPlayer.getAmountOfMoney() - amountOfMoney);
			allPlayers.get(i).setAmountOfMoney(allPlayers.get(i).getAmountOfMoney() + amountOfMoney);
			System.out.println(senderPlayer.getName() + "has paid " + amountOfMoney + " to " + allPlayers.get(i).getName() + " because of Chance Card.");
		}

	}
	public void ActionOfDirectlyToJailCard (String actionOfCard, Player guiltyPlayer) {
		System.out.println("Chance Card has drawn by " + guiltyPlayer.getName() + "!");
		System.out.println(actionOfCard);

		guiltyPlayer.setCellLocation(19);
		System.out.println(guiltyPlayer.getName() + "'s is in jail because of Chance Card.");

	}
	public void ActionOfJailGetawayCard (String actionOfCard, Player luckyPlayer){
		System.out.println("Chance Card has drawn by " + luckyPlayer.getName() + "!");
		System.out.println(actionOfCard);

		luckyPlayer.setDoesHaveJailGetawayCard(true);
		System.out.println(luckyPlayer.getName() + "has Jail Getaway Card! This card will be used in case of being in jail.");
	}
	public void ActionOfGetMoneyFromBankCard (String actionOfCard, Player luckyPlayer, Bank bank) {
		System.out.println("Chance Card has drawn by " + luckyPlayer.getName() + "!");
		System.out.println(actionOfCard);

		luckyPlayer.setAmountOfMoney(luckyPlayer.getAmountOfMoney() + 100);
		bank.setTotalMoney(bank.getTotalMoney() - 1000);
		System.out.println(luckyPlayer.getName() + " got 100$ from Bank because of Chance Card!");
	}


}
