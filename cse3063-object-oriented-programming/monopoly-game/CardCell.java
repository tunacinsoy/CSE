// 27.12.2019
public class CardCell extends Cell {
	private String name;
	private ChanceCard[] chancecards = new ChanceCard[20];
	private CommunityCard[] communitycards = new CommunityCard[20];
	CardCell(int id){
		super(id,null);

		}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public ChanceCard[] getChancecards() {
		return chancecards;
	}
	public void setChancecards(ChanceCard[] chancecards) {
		this.chancecards = chancecards;
	}
	public CommunityCard[] getCommunitycards() {
		return communitycards;
	}
	public void setCommunitycards(CommunityCard[] communitycards) {
		this.communitycards = communitycards;
	}





}
