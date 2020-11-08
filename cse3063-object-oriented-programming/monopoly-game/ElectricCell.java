//27.12.2019

public class ElectricCell extends PayMoneyCell {

	public ElectricCell(int id ,int amountMoneyToBeTaken) {
		super(id,null,amountMoneyToBeTaken);
		super.setName("Electric Company");
		super.setCanBeBought(false);

	}
	public void MoneyFunc(Player player,Bank bank){
		int amountOfMoney = player.getAmountOfMoney()-super.getAmountMoneyToBeTaken();
			player.setAmountOfMoney(amountOfMoney);
			bank.inMoney(super.getAmountMoneyToBeTaken());
			System.out.println(player.getName()+" has paid "+super.getAmountMoneyToBeTaken()+"$ to the Electric Company.");

	}



}
