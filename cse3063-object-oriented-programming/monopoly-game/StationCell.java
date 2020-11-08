// 27.12.19
public class StationCell extends Cell {

	private int priceOfTheCell;
	private int rent;
	private String name;

	StationCell(int id, Player owner, int priceOfTheCell, String name, int rent){
		super(id,owner);
		this.name = name;
		this.priceOfTheCell = priceOfTheCell;
		this.rent = rent;
		super.setCanBeBought(true);

	}
	public String getName(){
		return name;
	}
	public boolean buyingCell(Player person){
		if((person.getPossibilityOfTakingRisk()/100)*person.getAmountOfMoney() < this.priceOfTheCell){
			return false;
		}
		if(this.getOwner()!=null){
			return false;
		}
		else{
			this.setOwner(person);

			return true;
		}
	}


}
