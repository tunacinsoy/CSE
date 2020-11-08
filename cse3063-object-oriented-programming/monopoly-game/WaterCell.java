
public class WaterCell extends PayMoneyCell {

	public WaterCell(int id, int amountMoneyToBeTaken) {
		super(id,null,amountMoneyToBeTaken);
		super.setName("Water Works");
		super.setCanBeBought(false);

	}
	public void MoneyFunc(Player player,Bank bank){
		int amountOfMoney = player.getAmountOfMoney()-super.getAmountMoneyToBeTaken();
			player.setAmountOfMoney(amountOfMoney);
			bank.inMoney(super.getAmountMoneyToBeTaken());
			System.out.println(player.getName()+" has paid "+super.getAmountMoneyToBeTaken()+"$ to the Water Works.");

	}

}
