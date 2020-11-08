/*
 * This program aims to build a 2-dimensional Field Tiredness Map with given inputs from a text file.
 * Author: Tuna Cinsoy
 * 21.12.19
 */


import java.io.File;
import java.util.Scanner;

public class FieldTirednessMap {

	public static void main(String[] args) throws Exception {
		File inputFile = new File(
				"C:\\Users\\dell\\Desktop\\eclipse-workspace\\Field_Tiredness_Map\\src\\fieldInfo.txt"); // Creating file object
		Scanner sc = new Scanner(inputFile);

		// Initializing all variables which are going to be used in upcoming algorithms
		int[][] tirednessMap = new int[0][0]; // first double array created with first input
		int[][] newTirednessMap = new int[0][0]; // dummy double array, will be used in comparison with tirednessMap
		int plantingCount;
		int currentYear = 0;
		int sizeX = 0;
		int sizeY = 0;
		int startXCoord;
		int startYCoord;
		int endXCoord;
		int endYCoord;
		int countOfLines = 1;
		int countOfYear = 1;

		while (sc.hasNextLine()) {

			if (countOfLines == 1) { // If the countOfLines is equal to 1, that means I am currently reading the first line
				sizeX = Integer.parseInt(sc.next()); // Getting input sizeX and sizeY according to given value
				sizeY = Integer.parseInt(sc.next());
				tirednessMap = new int[sizeY][sizeX]; // sizeY to the bottom, sizeX to the right
				for (int j = 0; j < sizeY; j++) {
					for (int k = 0; k < sizeX; k++) {
						tirednessMap[j][k] = 0; // Initializing the essential array with 0's
					}
				}
				countOfLines++; // Increasing countOfLines by 1, so we can continue reading
			}
			int nextToken = Integer.parseInt(sc.next());

			if (nextToken % 1000 != 0) { // If the next input key that I got has 4 digits, it means that the modular operation result will be different than 0
				if (countOfYear == 1) {
					currentYear = nextToken; // If currentYear is 1, then I am currently in firstYear's information block
					plantingCount = Integer.parseInt(sc.next());
					for (int i = 0; i < plantingCount; i++) { // Until integer i is equal to plantingCount, we get informations about plants
						startXCoord = Integer.parseInt(sc.next());
						startYCoord = Integer.parseInt(sc.next());
						endXCoord = Integer.parseInt(sc.next());
						endYCoord = Integer.parseInt(sc.next());

						for (int j = startYCoord; j <= endYCoord; j++) { // Filling the essential tirednessMap
							for (int k = startXCoord; k <= endXCoord; k++) {
								tirednessMap[j][k]++;
							}
						}
					}
					countOfYear++;
				} else { // If currentYear is not 1 which means that we will create dummy tirednessMap to store new inputs
					newTirednessMap = new int[sizeY][sizeX];
					for (int j = 0; j < sizeY; j++) {
						for (int k = 0; k < sizeX; k++) {
							newTirednessMap[j][k] = 0; // Initializing dummy newTirednessMap with 0's
						}
					}
					currentYear = nextToken;
					plantingCount = Integer.parseInt(sc.next());
					for (int i = 0; i < plantingCount; i++) {
						startXCoord = Integer.parseInt(sc.next());
						startYCoord = Integer.parseInt(sc.next());
						endXCoord = Integer.parseInt(sc.next());
						endYCoord = Integer.parseInt(sc.next());

						for (int j = startYCoord; j <= endYCoord; j++) {
							for (int k = startXCoord; k <= endXCoord; k++) {
								newTirednessMap[j][k]++; // Increasing the corresponding indexes just like we did at tirednessMap
							}
						}
					}

					for (int i = 0; i < sizeY; i++) { // Crucial part, in here we are comparing tirednessMap with newTirednessMap
						for (int j = 0; j < sizeX; j++) {
							if (newTirednessMap[i][j] == 0 && tirednessMap[i][j] != 0) { // If the corresponding index of newTirednessMap is zero, then original tirednessMap should decrease its corresponding value
								tirednessMap[i][j]--;
							} else if (newTirednessMap[i][j] != 0) { // If the corresponding index of newTirednessMap is different than zero, original tirednessMap should be increased
								tirednessMap[i][j] = newTirednessMap[i][j] + tirednessMap[i][j];
							}
						}
					}

				}
				if (currentYear == 2017) { // If the currentYear is 2017, we break the while loop
					break;
				}
			}

			countOfLines++; // Increasing the count of lines by 1, which is just for reaching other lines in text
		}

		for (int j = 0; j < sizeY; j++) { // Prints out tirednessMap, which is our original one
			for (int k = 0; k < sizeX; k++) {
				System.out.print(tirednessMap[j][k]);
			}

			System.out.println();
		}

	}

}
