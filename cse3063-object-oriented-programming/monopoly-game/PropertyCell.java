// 27.12.2019

public class PropertyCell extends Cell{

	private int rent;
	private int priceOfTheCell;
	private int priceOfTheBuildings;
	private String name;
	private String color;
	private int countOfBuildings = 0;
	private int costOfHouse;
	private int rentforhouse;

	PropertyCell(int id, Player owner, String color,int priceOfTheCell,int rent, String name) {
		super(id, owner);
		this.color = color;
		this.priceOfTheCell = priceOfTheCell;
		this.rent = rent;
		this.name = name;
		super.setCanBeBought(true);
		this.costOfHouse=(priceOfTheCell*3)/4;
		this.rentforhouse=(rent*4)/5;

	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return name;
	}

	public boolean buyingCell(Player person){
		if((person.getPossibilityOfTakingRisk()/100)*person.getAmountOfMoney() >= this.priceOfTheCell){
			return false;
		}
		if(this.getOwner()!=null){
			return false;
		}
		else{
			this.setOwner(person);
			super.setCanBeBought(false);
			person.addToOwnedCells(this);

			return true;
		}

	}

	public int getRent() {
		return rent;
	}

	public void setRent(int rent) {
		this.rent = rent;
	}

	public int getPriceOfTheCell() {
		return priceOfTheCell;
	}

	public void setPriceOfTheCell(int priceOfTheCell) {
		this.priceOfTheCell = priceOfTheCell;
	}

	public int getPriceOfTheBuildings() {
		return priceOfTheBuildings;
	}

	public void setPriceOfTheBuildings(int priceOfTheBuildings) {
		this.priceOfTheBuildings = priceOfTheBuildings;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public int getCountOfBuildings() {
		return countOfBuildings;
	}

	public void setCountOfBuildings(int countOfBuildings) {
		this.countOfBuildings = countOfBuildings;
	}

	public void setName(String name) {
		this.name = name;
	}
	public void increaseBuildingNumber(){

			if (super.getOwner().getAmountOfMoney() > this.costOfHouse+this.costOfHouse && this.countOfBuildings < 3){

				super.getOwner().setAmountOfMoney(super.getOwner().getAmountOfMoney()- this.costOfHouse);
				this.countOfBuildings++;
				System.out.println(super.getOwner().getName() + " has bought a house from "+getName());
			}




	}
	public void payRent(Player player){


        System.out.println(player.getName()+" is on "+super.getOwner().getName()+"'s property!");
        player.setAmountOfMoney(player.getAmountOfMoney()-getRent());
        super.getOwner().setAmountOfMoney(super.getOwner().getAmountOfMoney()+getRent());
        System.out.println(player.getName()+" has paid "+getRent()+"$ to " +super.getOwner().getName()+".");

        if (countOfBuildings != 0){
        	player.setAmountOfMoney(player.getAmountOfMoney() - (countOfBuildings * this.rentforhouse) );
        	super.getOwner().setAmountOfMoney(super.getOwner().getAmountOfMoney() + countOfBuildings * this.rentforhouse);
        	System.out.println(player.getName() + " has paid " + countOfBuildings * rentforhouse + "$ to " + super.getOwner().getName() + " for rent." );
        }


        // Checks for if the owner has all the location cell's color .
        int count = 0;

        for(int i = 0; i < super.getOwner().getOwnedCells().size();i++)
        {
            if(super.getOwner().getOwnedCells().get(i).getColor() == getColor() )
            {
                count++;
            }
        }

        if(count == 3){
        	System.out.println("Whoops! "+super.getOwner().getName()+" has all the towns in this area!");
            player.setAmountOfMoney(player.getAmountOfMoney()-getRent());
            super.getOwner().setAmountOfMoney(super.getOwner().getAmountOfMoney()+getRent());
            System.out.println(player.getName()+" has paid "+getRent()+"$ to " +super.getOwner().getName()+" again.");
        }


	}



}
