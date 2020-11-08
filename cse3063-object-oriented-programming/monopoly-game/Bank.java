import java.util.ArrayList;
/*
 * Bank class, implemented with Singleton Design Pattern
 * 27.12.19
*/ 
public class Bank {
	private int totalMoney;
	private static Bank bank; // Singleton Design Pattern

	private Bank() {
		totalMoney = 1000000;
	}

	public void outMoney(int amount) {
		totalMoney -= amount;
	}

	public void inMoney(int amount) {
		totalMoney += amount;

	}

	public int getTotalMoney() {
		return totalMoney;
	}

	public void setTotalMoney(int totalMoney) {
		this.totalMoney = totalMoney;
	}
	
	public static Bank getBank(){
		if(bank == null)
		{
			bank = new Bank();
		}
		return bank;
	}
	
}
