// 27.12.2019
public class RegularCell extends Cell {
	private String name = "Free Car Park";

	RegularCell(int id, Player owner){
		super(id,owner);
		super.setCanBeBought(false);
	}
	public void MoneyFunc(Player player,Bank bank){
		System.out.println("User can relax in this cell! VACATION TIME!");
		// Will be implemented in subclass of Cell -> "PayMoneyCell"
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}


}
