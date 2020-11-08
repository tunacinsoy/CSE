// 27.12.2019
public class PayMoneyCell extends Cell {
	private int amountMoneyToBeTaken;
	private String name = "paymoneycell";

	PayMoneyCell(int id, Player owner, int amountMoneyToBeTaken) {
		super(id, owner);
		this.amountMoneyToBeTaken = amountMoneyToBeTaken;
	}
	@Override
	public void MoneyFunc(Player player,Bank bank){
		int amountOfMoney = player.getAmountOfMoney()-amountMoneyToBeTaken;
			player.setAmountOfMoney(amountOfMoney);
			bank.inMoney(amountMoneyToBeTaken);

	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAmountMoneyToBeTaken() {
		return amountMoneyToBeTaken;
	}
	public void setAmountMoneyToBeTaken(int amountMoneyToBeTaken) {
		this.amountMoneyToBeTaken = amountMoneyToBeTaken;
	}

}
