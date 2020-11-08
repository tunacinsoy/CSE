// 27.12.2019

public class CommunityCardCell extends CardCell{

	private int amountOfMoney;
	private String name;


	public CommunityCardCell(int id,CommunityCard[]cards)
	{
		super(id);
		super.setCommunitycards(cards);
		super.setName("Community Card Cell");
		super.setCanBeBought(false);
	}

	public CommunityCard[] getCommunityCards(){
		return super.getCommunitycards();
	}



}
