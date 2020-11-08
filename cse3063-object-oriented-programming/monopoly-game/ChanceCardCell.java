// 27.12.2019
public class ChanceCardCell extends CardCell {

	public ChanceCardCell(int id,ChanceCard[]cards) {
			super(id);
			super.setChancecards(cards);
			super.setName("Chance Card Cell");
	}
	public ChanceCard[] getChanceCards(){
		return super.getChancecards();
	}

}
