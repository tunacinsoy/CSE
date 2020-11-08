// 27.12.2019
public class StartingCell extends Cell {
	private final int GIVEN_MONEY = 200;
	private final static int INDEX_VALUE = 0;
	private String name="starting cell";

	StartingCell() {
		super(INDEX_VALUE, null);

	}

	public void MoneyFunc(Player player,Bank bank) {

		int amountOfMoney = player.getAmountOfMoney() + GIVEN_MONEY;
		player.setAmountOfMoney(amountOfMoney);
		bank.outMoney(GIVEN_MONEY);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
