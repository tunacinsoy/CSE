// 27.12.2019
public class Dice {
	private int faceValue1;
	private int faceValue2;

	public int rollDices() {
		faceValue1 = (int) (Math.random() * 6 + 1);
		faceValue2 = (int) (Math.random() * 6 + 1);
		System.out.println("Face value of 1st Dice : " + faceValue1+" Face Value of 2nd Dice : "+faceValue2);
		return (faceValue1 + faceValue2);
	}
}
