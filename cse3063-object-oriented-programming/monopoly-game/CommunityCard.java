// Community Card actions are declared in here
// 06.12.2019

import java.util.ArrayList;

public class CommunityCard extends Card {

	public CommunityCard(String nameOfTheCard, String actionOfTheCard, int cardID) {
		super.setNameOfTheCard(nameOfTheCard);
		super.setActionOfTheCard(actionOfTheCard);
		super.setCardID(cardID);
	}

	public void ActionOfPayAllPlayer25$(String actionOfCard, Player senderPlayer, ArrayList<Player> allPlayers) {
		System.out.println("Community Card has drawn by " + senderPlayer.getName() + "!");
		System.out.println(actionOfCard);

		for (int i = 0; i < allPlayers.size(); i++) {
			if (senderPlayer == allPlayers.get(i)) {
				continue;
			}
			senderPlayer.setAmountOfMoney(senderPlayer.getAmountOfMoney() - 25);
			allPlayers.get(i).setAmountOfMoney(allPlayers.get(i).getAmountOfMoney() + 25);
			System.out.println(senderPlayer.getName() + " has paid " + 25 + "$ to " + allPlayers.get(i).getName()
					+ " because of Community Card.");
		}

	}

	public void ActionOfPay15$toBankAccordingToNumberOfPlayers(String actionOfCard, Player senderPlayer, Bank bank,
			ArrayList<Player> allPlayers) {
		System.out.println("Community Card has drawn by " + senderPlayer.getName() + "!");
		System.out.println(actionOfCard);

		senderPlayer.setAmountOfMoney(senderPlayer.getAmountOfMoney() - (15 * (allPlayers.size() - 1)));
		bank.setTotalMoney(bank.getTotalMoney() + (15 * (allPlayers.size() - 1)));
		System.out.println(senderPlayer.getName() + " paid " + (15 * (allPlayers.size() - 1))
				+ "$ to bank because of Community Card.");

	}

	public void ActionOfPay20$ToAllPlayersBecauseGambling(String actionOfCard, Player senderPlayer,
			ArrayList<Player> allPlayers) {
		System.out.println("Community Card has drawn by " + senderPlayer.getName() + "!");
		System.out.println(actionOfCard);

		for (int i = 0; i < allPlayers.size(); i++) {
			if (senderPlayer == allPlayers.get(i)) {
				continue;
			}
			senderPlayer.setAmountOfMoney(senderPlayer.getAmountOfMoney() - 20);
			allPlayers.get(i).setAmountOfMoney(allPlayers.get(i).getAmountOfMoney() + 20);
			System.out.println(senderPlayer.getName() + " has paid " + 20 + "$ to " + allPlayers.get(i).getName()
					+ " because of Community Card.");
		}
	}

	public void ActionOfTake20$FromAllPlayersCard(String actionOfCard, Player senderPlayer,
			ArrayList<Player> allPlayers) {
		System.out.println("Community Card has drawn by " + senderPlayer.getName() + "!");
		System.out.println(actionOfCard);

		for (int i = 0; i < allPlayers.size(); i++) {
			if (senderPlayer == allPlayers.get(i)) {
				continue;
			}
			senderPlayer.setAmountOfMoney(senderPlayer.getAmountOfMoney() + 20);
			allPlayers.get(i).setAmountOfMoney(allPlayers.get(i).getAmountOfMoney() - 20);
			System.out.println(allPlayers.get(i).getName() + "has paid " + 20 + "$ to " + senderPlayer.getName()
					+ " because of Community Card.");
		}
	}

	public void ActionOfTake50$AccordingToDiceValue(String actionOfCard, Bank bank, Player senderPlayer, Dice dice) {
		System.out.println("Community Card has drawn by " + senderPlayer.getName() + "!");
		System.out.println(actionOfCard);
		int diceValue = dice.rollDices();
		System.out.println("Dice value of " + senderPlayer.getName() + " is " + diceValue);

		if (diceValue > 8) {
			senderPlayer.setAmountOfMoney(senderPlayer.getAmountOfMoney() + 50);
			bank.setTotalMoney(bank.getTotalMoney() - 50);
			System.out
					.println("Bank has paid " + 50 + "$ to " + senderPlayer.getName() + " because of Community Card.");
		} else if (diceValue <= 8) {
			System.out.println("There will be no transaction because the sum of face values are less than 8.");

		}

	}
}