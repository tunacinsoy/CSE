/*
 * This program gets inputs as some values of laptops such as coordinates and wireless transmission ranges.
 * Output is the number of hops required to reach from first laptop to i'th laptop.
 * To implement this algorithm, "Graph" Data Structure and "Breadth-First Search" has been used.
 * Author: Tuna Cinsoy
 * 13.04.2020
 */
import java.io.*;
import java.util.Scanner;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Vector;

public class Main {
	
	// Laptop class for creating object instances of given laptops
	static class Laptop {
		double xCoordinateOfTheCurrentLaptop;
		double yCoordinateOfTheCurrentLaptop;
		double wirelessTransmissionRangeOfTheCurrentLaptop;
		int identityOfTheCurrentLaptop;

		public Laptop(double xCoordinateOfTheCurrentLaptop, double yCoordinateOfTheCurrentLaptop,
				double wirelessTransmissionRangeOfTheCurrentLaptop, int identityOfTheCurrentLaptop) {
			this.xCoordinateOfTheCurrentLaptop = xCoordinateOfTheCurrentLaptop;
			this.yCoordinateOfTheCurrentLaptop = yCoordinateOfTheCurrentLaptop;
			this.wirelessTransmissionRangeOfTheCurrentLaptop = wirelessTransmissionRangeOfTheCurrentLaptop;
			this.identityOfTheCurrentLaptop = identityOfTheCurrentLaptop;
		}

	}
	
	// Method for evaluating Overlapping Occasion
	public static boolean DoesWirelessRangeOverlap(Laptop laptop1, Laptop laptop2) {

		double differenceOfXCoordinates = Math
				.abs(laptop1.xCoordinateOfTheCurrentLaptop - laptop2.xCoordinateOfTheCurrentLaptop);
		double differenceOfYCoordinates = Math
				.abs(laptop1.yCoordinateOfTheCurrentLaptop - laptop2.yCoordinateOfTheCurrentLaptop);

		double distanceBetweenTwoLaptops = Math
				.sqrt(Math.pow(differenceOfXCoordinates, 2) + Math.pow(differenceOfYCoordinates, 2));

		if (distanceBetweenTwoLaptops <= laptop1.wirelessTransmissionRangeOfTheCurrentLaptop
				+ laptop2.wirelessTransmissionRangeOfTheCurrentLaptop) {
			return true;
		} else {
			return false;
		}

	}
	
	// Method for doing BFS on our graph
	public static int minimumHopDistanceUsingBFS(Vector<Laptop> edges[], Laptop sourceLaptop, Laptop destinationLaptop,
			int numberOfLaptops) {

		Vector<Boolean> visited = new Vector<Boolean>(numberOfLaptops);

		for (int i = 0; i < numberOfLaptops; i++) {
			visited.addElement(false);
		}

		Vector<Integer> distance = new Vector<Integer>(numberOfLaptops);

		for (int i = 0; i < numberOfLaptops; i++) {
			distance.addElement(0);
		}

		Queue<Integer> queueForBFS = new LinkedList<>();
		distance.setElementAt(0, sourceLaptop.identityOfTheCurrentLaptop);

		queueForBFS.add(sourceLaptop.identityOfTheCurrentLaptop);
		visited.setElementAt(true, sourceLaptop.identityOfTheCurrentLaptop);
		while (!queueForBFS.isEmpty()) {
			int headOfTheQueue = queueForBFS.peek();
			queueForBFS.poll();
			for (int i = 0; i < edges[headOfTheQueue].size(); i++) {
				if (visited.elementAt(edges[headOfTheQueue].get(i).identityOfTheCurrentLaptop)) {
					continue;
				}
				distance.setElementAt(distance.get(headOfTheQueue) + 1,
						edges[headOfTheQueue].get(i).identityOfTheCurrentLaptop);
				queueForBFS.add(edges[headOfTheQueue].get(i).identityOfTheCurrentLaptop);
				visited.setElementAt(true, edges[headOfTheQueue].get(i).identityOfTheCurrentLaptop);
			}
		}
		return distance.get(destinationLaptop.identityOfTheCurrentLaptop);

	}
	
	// Main Method
	public static void main(String[] args) throws FileNotFoundException, IOException {
		// I/O STUFF
		File inputFile = new File(args[0]);
		Scanner scanner = new Scanner(inputFile);
		BufferedWriter writer = new BufferedWriter(new FileWriter("output.txt"));

		// MY VARIABLES
		final int MAX_SIZE = 50;
		int numberOfLaptops = 0;
		double xCoordinateOfTheCurrentLaptop = 0;
		double yCoordinateOfTheCurrentLaptop = 0;
		double wirelessTransmissionRangeOfTheCurrentLaptop = 0;
		double[] xCoordinatesStorage = new double[MAX_SIZE];
		double[] yCoordinatesStorage = new double[MAX_SIZE];
		double[] wirelessTransmissionRangeStorage = new double[MAX_SIZE];
		Laptop[] laptops = new Laptop[MAX_SIZE];

		// GETTING INPUTS
		int validInputArgumentsCount = 0;
		while (scanner.hasNextLine()) {

			String currentLineOfTheInput = scanner.nextLine();

			if (currentLineOfTheInput.charAt(0) == '#') {

				continue;
			}
			if (validInputArgumentsCount == 0) {

				numberOfLaptops = Integer.parseInt(currentLineOfTheInput);
				
				xCoordinatesStorage = new double[numberOfLaptops];
				yCoordinatesStorage = new double[numberOfLaptops];
				wirelessTransmissionRangeStorage = new double[numberOfLaptops];
				laptops = new Laptop[numberOfLaptops];
				validInputArgumentsCount++;
				continue;
			}
			String mathematicalValues[] = currentLineOfTheInput.split("\t");
			xCoordinateOfTheCurrentLaptop = Double.parseDouble(mathematicalValues[0]);
			yCoordinateOfTheCurrentLaptop = Double.parseDouble(mathematicalValues[1]);
			wirelessTransmissionRangeOfTheCurrentLaptop = Double.parseDouble(mathematicalValues[2]);

			
			xCoordinatesStorage[validInputArgumentsCount - 1] = xCoordinateOfTheCurrentLaptop;
			yCoordinatesStorage[validInputArgumentsCount - 1] = yCoordinateOfTheCurrentLaptop;
			wirelessTransmissionRangeStorage[validInputArgumentsCount
					- 1] = wirelessTransmissionRangeOfTheCurrentLaptop;
			laptops[validInputArgumentsCount - 1] = new Laptop(xCoordinateOfTheCurrentLaptop,
					yCoordinateOfTheCurrentLaptop, wirelessTransmissionRangeOfTheCurrentLaptop,
					validInputArgumentsCount - 1);
			validInputArgumentsCount++;
		}

		// Storing laptops in adjacency list
		Vector<Laptop> edges[] = new Vector[numberOfLaptops];
		for (int i = 0; i < edges.length; i++) {
			edges[i] = new Vector<>();
		}
		for (int i = 0; i < edges.length; i++) {
			for (int j = i + 1; j < edges.length; j++) {
				if (DoesWirelessRangeOverlap(laptops[i], laptops[j])) {
					edges[i].add(laptops[j]);
					edges[j].add(laptops[i]);
				}
			}
		}

		// final for printing and writing the values
		for (int i = 0; i < numberOfLaptops; i++) {
			int hopValue = minimumHopDistanceUsingBFS(edges, laptops[0], laptops[i], numberOfLaptops);
			String stringVersionOfHopValue = String.valueOf(hopValue);
			System.out.println(stringVersionOfHopValue);
			writer.write(stringVersionOfHopValue);
			writer.write("\n");
		}

		scanner.close();
		writer.close();
	}

}
