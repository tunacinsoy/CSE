/*
 * This program aims to simulate a cache memory usage.
 * Author: Tuna Cinsoy
 * 15.06.20
 * NOTE: Works fine only if RAM has enough size, in other words, trace file addresses must be appropriate for RAM.
 * NOTE2: "test1.trace" file (given in Github repository branch) has been used for this particular code as an input file.
 */

import java.util.*;
import java.io.*;

public class Main {
	// Declaring global variables, just to make sure that all of the methods below can access these fields
	static int hitsAtL1I;
	static int hitsAtL1D;
	static int hitsAtL2;
	static int missesAtL1I;
	static int missesAtL1D;
	static int missesAtL2;
	static int evictionsAtL1I;
	static int evictionsAtL1D;
	static int evictionsAtL2;
	static int timeAtOperations;
	static int howManyIndexesAreLocatedInRamArray;
	
	public static void main(String[] args) throws FileNotFoundException {
		// Reading RAM
		String ram = new String();
		File ramFile = new File("ram.txt");
		Scanner scannerOfRamFile = new Scanner(ramFile);
		
		try {
			while (scannerOfRamFile.hasNextLine()) {
				ram = scannerOfRamFile.nextLine();
			}
			
		}catch (Exception error) {
			System.out.println("Some error occurred while reading ram file.");
		}
		
		// Getting inputs for L1 Cache
		int numberOfSetIndexBitsForL1 = Integer.parseInt(args[1]);
		int numberOfSetsForL1 = (int) Math.pow(2, numberOfSetIndexBitsForL1);
		
		int numberOfLinesPerSetForL1 = Integer.parseInt(args[3]);
		
		int numberOfBlockBitsForL1 = Integer.parseInt(args[5]);
		int blockSizeOfL1 = (int) Math.pow(2, numberOfBlockBitsForL1);
		
		// Getting inputs for L2 Cache
		int numberOfSetIndexBitsForL2 = Integer.parseInt(args[7]);
		int numberOfSetsForL2 = (int) Math.pow(2, numberOfSetIndexBitsForL2);
		
		int numberOfLinesPerSetForL2 = Integer.parseInt(args[9]);
		
		int numberOfBlockBitsForL2 = Integer.parseInt(args[11]);
		int blockSizeOfL2 = (int) Math.pow(2, numberOfBlockBitsForL2);
		
		// Name of the trace file
		String nameOfTheTraceFile = args[13];
		
		// Reading trace file
		File traceFile = new File(nameOfTheTraceFile);
		Scanner scannerOfTraceFile = new Scanner(traceFile);
		
		// Doing proper operations to ram, aligning data according to block size input
		String[] ramArray = new String[100000];

		StringBuilder integratedRam = new StringBuilder(ram.replaceAll("\\s+",""));
		
		int howManyHexValuesWillBeUsedInRamArrayIndexes = (8 * blockSizeOfL1) / 4;
		howManyIndexesAreLocatedInRamArray = integratedRam.length() / howManyHexValuesWillBeUsedInRamArrayIndexes;
		//System.out.println("howManyIndexesAreLocatedInRamArray: "+ howManyIndexesAreLocatedInRamArray);
		
		int indexOfRamArray = 0;
		for (int i = 1; i < integratedRam.length(); i++) {
			
			if (i % 16 == 0) {
				ramArray[indexOfRamArray] = integratedRam.substring(i - (howManyHexValuesWillBeUsedInRamArrayIndexes) , i);
				indexOfRamArray++;
			}
			
		}
		
		// Initializing Cache Memory with given inputs, all indexes are "-1" at the beginning
		
		String[][][] L1I = new String[numberOfSetsForL1][numberOfLinesPerSetForL1][4];
		String[][][] L1D = new String[numberOfSetsForL1][numberOfLinesPerSetForL1][4];
		String[][][] L2 = new String[numberOfSetsForL2][numberOfLinesPerSetForL2][4];
		
		for (int i = 0; i < numberOfSetsForL1; i++) {
			for (int j = 0; j < numberOfLinesPerSetForL1; j++) {
				for (int k = 0; k < 4; k++) {
					L1I[i][j][k] = "-1";
				}
			}
		}
		
		
		for (int i = 0; i < numberOfSetsForL1; i++) {
			for (int j = 0; j < numberOfLinesPerSetForL1; j++) {
				for (int k = 0; k < 4; k++) {
					L1D[i][j][k] = "-1";
				}
			}
		}
		
		for (int i = 0; i < numberOfSetsForL2; i++) {
			for (int j = 0; j < numberOfLinesPerSetForL2; j++) {
				for (int k = 0; k < 4; k++) {
					L2[i][j][k] = "-1";
				}
			}
		}
		
		
		try {
			while (scannerOfTraceFile.hasNextLine()) {
				String currentLineOfTraceFile = scannerOfTraceFile.nextLine();
				
				
				if (currentLineOfTraceFile.charAt(0) == 'I') {
					String operationAddress = currentLineOfTraceFile.substring(2, currentLineOfTraceFile.indexOf(','));
					String size = currentLineOfTraceFile.substring(currentLineOfTraceFile.indexOf(',') + 2, currentLineOfTraceFile.length());
					
					OperationsOfInstructionLoad(operationAddress, size, L1I, L2, ramArray,numberOfSetsForL1,numberOfLinesPerSetForL1,numberOfSetsForL2,numberOfLinesPerSetForL2);
				}
				else if (currentLineOfTraceFile.charAt(0) == 'M') {
					String operationAddress = currentLineOfTraceFile.substring(2, currentLineOfTraceFile.indexOf(','));
					String size = currentLineOfTraceFile.substring(currentLineOfTraceFile.indexOf(',') + 2, currentLineOfTraceFile.lastIndexOf(','));
					String data = currentLineOfTraceFile.substring(currentLineOfTraceFile.lastIndexOf(',') + 2, currentLineOfTraceFile.length());
					
					OperationsOfDataModify(operationAddress,size,data,L1D, L2, ramArray, numberOfSetsForL1,numberOfLinesPerSetForL1,numberOfSetsForL2,numberOfLinesPerSetForL2);
				}
				else if (currentLineOfTraceFile.charAt(0) == 'L') {
					String operationAddress = currentLineOfTraceFile.substring(2, currentLineOfTraceFile.indexOf(','));
					String size = currentLineOfTraceFile.substring(currentLineOfTraceFile.indexOf(',') + 2, currentLineOfTraceFile.length());
					
					OperationsOfDataLoad(operationAddress,size, L1D, L2, ramArray, numberOfSetsForL1,numberOfLinesPerSetForL1,numberOfSetsForL2,numberOfLinesPerSetForL2);
				}
				else if (currentLineOfTraceFile.charAt(0) == 'S') {
					String operationAddress = currentLineOfTraceFile.substring(2, currentLineOfTraceFile.indexOf(','));
					String size = currentLineOfTraceFile.substring(currentLineOfTraceFile.indexOf(',') + 2, currentLineOfTraceFile.lastIndexOf(','));
					String data = currentLineOfTraceFile.substring(currentLineOfTraceFile.lastIndexOf(',') + 2, currentLineOfTraceFile.length());
					
					OperationsOfDataStore(operationAddress, size, data, L1D, L2,ramArray, numberOfSetsForL1,numberOfLinesPerSetForL1,numberOfSetsForL2,numberOfLinesPerSetForL2);
				}
				else {
					// If first letter is none of the above, there is an input error
					throw new Exception();
				}
			}
			
		}catch (Exception error) {
			System.out.println("Some error occurred while reading trace file.");
		}
		
		// Last Output of the Algorithm
		
		System.out.println("L1I-hits:" + hitsAtL1I + " L1I-misses:" + missesAtL1I  + " L1I-evictions:" + evictionsAtL1I );
		System.out.println("L1D-hits:" + hitsAtL1D + " L1D-misses:" + missesAtL1D  + " L1D-evictions:" + evictionsAtL1D );
		System.out.println("L2-hits:" + hitsAtL2 + " L2-misses:" + missesAtL2  + " L2-evictions:" + evictionsAtL2 );
		
		
	}	
	public static int hexadecimalToDecimal(String hexadecimalNotation) {
		String hexadecimalLetters = "0123456789ABCDEF";
		hexadecimalNotation = hexadecimalNotation.toUpperCase();
		
		int decimalValue = 0;
		for(int i = 0; i < hexadecimalNotation.length(); i++) {
			char currentChar = hexadecimalNotation.charAt(i);
			int correspondingIndex = hexadecimalLetters.indexOf(currentChar);
			decimalValue = 16*decimalValue + correspondingIndex;
		}
		
		return decimalValue;
	}
	public static void OperationsOfInstructionLoad(String operationAddress, String size, String[][][] L1I,String[][][] L2, String[] ramArray, int numberOfSetsForL1I, int numberOfLinesPerSetForL1I, int numberOfSetsForL2, int numberOfLinesPerSetForL2 ) {
		// Are cache memories empty or not?
				boolean isL1DEmpty = isGivenMemoryEmpty (L1I,numberOfSetsForL1I,numberOfLinesPerSetForL1I);
				boolean isL2Empty =  isGivenMemoryEmpty (L2,numberOfSetsForL2,numberOfLinesPerSetForL2);
				boolean isL1DFull = isGivenMemoryFull (L1I,numberOfSetsForL1I,numberOfLinesPerSetForL1I);
				boolean isL2Full =  isGivenMemoryFull (L2,numberOfSetsForL2,numberOfLinesPerSetForL2);
				String retrievedDataFromRAM;
				
				int memoryIndexThatIWillReach;
				int decimalOfOperationAddress = hexadecimalToDecimal(operationAddress);
				
				if (decimalOfOperationAddress % 8 == 0) {
					memoryIndexThatIWillReach = decimalOfOperationAddress / 8;
				}
				else {
					decimalOfOperationAddress = decimalOfOperationAddress - (decimalOfOperationAddress % 8);
					memoryIndexThatIWillReach = decimalOfOperationAddress / 8;
				}
				
				// IF RAM SIZE IS NOT ENOUGH, MISS WILL OCCUR, WILL RETURN TO MAIN METHOD
				if (memoryIndexThatIWillReach > howManyIndexesAreLocatedInRamArray ) {
					System.out.println("RAM has not enough size to reach that particular address, so miss will occur. Please give appropriate addresses in trace file.");
					missesAtL1I++; 
					missesAtL2++;
					return;
				}
				// If cache memories are completely empty, i.e, Cold miss case
				if (isL1DEmpty == true && isL2Empty == true) {
					
					missesAtL1I++; 
					missesAtL2++;
					timeAtOperations++;
					
					
					
					
					retrievedDataFromRAM = ramArray[memoryIndexThatIWillReach];
				    L1I[0][0][3] = retrievedDataFromRAM;
					L1I[0][0][2] = "1";
					L1I[0][0][1] = Integer.toString(timeAtOperations);
					L1I[0][0][0] = "000000" + Integer.toString(timeAtOperations);
					
					L2[0][0][3] = retrievedDataFromRAM;
					L2[0][0][2] = "1";
					L2[0][0][1] = Integer.toString(timeAtOperations);
					L2[0][0][0] = "000000" + Integer.toString(timeAtOperations);
					return;
					
				}
				
				// Hit case
				String dataToBeCompared = ramArray[memoryIndexThatIWillReach];
				boolean didHitHappenAtL1D = false;
				boolean didHitHappenAtL2 = false;
				for(int i = 0; i < numberOfSetsForL1I; i++ ) {
					for (int j = 0; j < numberOfLinesPerSetForL1I; j++) {
						for (int k = 0; k < 4; k++) {
							if (dataToBeCompared.equals(L1I[i][j][k])) {
								didHitHappenAtL1D = true;
								timeAtOperations++;
								hitsAtL1I++;
							}
						}
					}
				}
				
				for(int i = 0; i < numberOfSetsForL2; i++ ) {
					for (int j = 0; j < numberOfLinesPerSetForL2; j++) {
						for (int k = 0; k < 4; k++) {
							if (dataToBeCompared.equals(L2[i][j][k])) {
								didHitHappenAtL2 = true;
								timeAtOperations++;
								hitsAtL2++;
							}
						}
					}
				}
				
				// Eviction case
				if (isL1DFull == true && isL2Full == true && didHitHappenAtL1D == false && didHitHappenAtL2 == false) {
					timeAtOperations++;
					retrievedDataFromRAM = ramArray[memoryIndexThatIWillReach];
					
					// Going to find the maximum time to find which data will be evicted
					int maxTimeAmongData = Integer.parseInt(L1I[0][0][1]);
					int setIDOfMaxTimeInL1D = 0;
					int lineIDOfMaxTimeInL1D = 0;
					for (int i = 0; i < numberOfSetsForL1I; i++) {
						for (int j = 0; i < numberOfLinesPerSetForL1I; j++ ) {
							if (maxTimeAmongData < Integer.parseInt(L1I[i][j][1])) {
								maxTimeAmongData = Integer.parseInt(L1I[i][j][1]);
								setIDOfMaxTimeInL1D = i;
								lineIDOfMaxTimeInL1D = j;
								
							}
						}
					}
					int setIDOfMaxTimeInL2 = 0;
					int lineIDOfMaxTimeInL2 = 0;
					for (int i = 0; i < numberOfSetsForL2; i++) {
						for (int j = 0; i < numberOfLinesPerSetForL2; j++ ) {
							if (maxTimeAmongData < Integer.parseInt(L2[i][j][1])) {
								maxTimeAmongData = Integer.parseInt(L2[i][j][1]);
								setIDOfMaxTimeInL2 = i;
								lineIDOfMaxTimeInL2 = j;
								
							}
						}
					}
					
					L1I[setIDOfMaxTimeInL1D][lineIDOfMaxTimeInL1D][3] = retrievedDataFromRAM;
					L2[setIDOfMaxTimeInL2][lineIDOfMaxTimeInL2][3] = retrievedDataFromRAM;
					
					L1I[setIDOfMaxTimeInL1D][lineIDOfMaxTimeInL1D][1] = Integer.toString(timeAtOperations);
					L2[setIDOfMaxTimeInL2][lineIDOfMaxTimeInL2][1] = Integer.toString(timeAtOperations);
					
					evictionsAtL1I++;
					evictionsAtL2++;
					
				}
				
				// Miss case
				if (didHitHappenAtL1D == false && didHitHappenAtL2 == false && isL1DFull == false && isL2Full == false) {
					retrievedDataFromRAM = ramArray[memoryIndexThatIWillReach];
					missesAtL1I++;
					missesAtL2++;
					timeAtOperations++;
					
					int setIDOfTheFirstEmptyLocationAtL1D = 0;
					int lineIDOfTheFirstEmptyLocationAtL1D = 0;
					
					outerloop1:
					for (int i = 0; i < numberOfSetsForL1I; i++) {
						for (int j = 0; j < numberOfLinesPerSetForL1I; j++) {
							if(L1I[i][j][0].equals("-1")) {
								setIDOfTheFirstEmptyLocationAtL1D = i;
								lineIDOfTheFirstEmptyLocationAtL1D = j;
								break outerloop1;
							}
						}
						
					}
					int setIDOfTheFirstEmptyLocationAtL2 = 0;
					int lineIDOfTheFirstEmptyLocationAtL2 = 0;
					
					outerloop2:
						for (int i = 0; i < numberOfSetsForL2; i++) {
							for (int j = 0; j < numberOfLinesPerSetForL2; j++) {
								if(L1I[i][j][0].equals("-1")) {
									setIDOfTheFirstEmptyLocationAtL2 = i;
									lineIDOfTheFirstEmptyLocationAtL2 = j;
									break outerloop2;
								}
							}
							
						}
					retrievedDataFromRAM = L1I[setIDOfTheFirstEmptyLocationAtL1D][lineIDOfTheFirstEmptyLocationAtL1D][3];
					retrievedDataFromRAM = L2[setIDOfTheFirstEmptyLocationAtL2][lineIDOfTheFirstEmptyLocationAtL2][3];
					L1I[setIDOfTheFirstEmptyLocationAtL1D][lineIDOfTheFirstEmptyLocationAtL1D][1] = Integer.toString(timeAtOperations);
					L2[setIDOfTheFirstEmptyLocationAtL2][lineIDOfTheFirstEmptyLocationAtL2][1] = Integer.toString(timeAtOperations);
				}
		
		
		
		
	}
	public static void OperationsOfDataLoad(String operationAddress, String size, String[][][] L1D, String[][][] L2, String[] ramArray, int numberOfSetsForL1D, int numberOfLinesPerSetForL1D, int numberOfSetsForL2, int numberOfLinesPerSetForL2) {
		// Are cache memories empty or not?
		boolean isL1DEmpty = isGivenMemoryEmpty (L1D,numberOfSetsForL1D,numberOfLinesPerSetForL1D);
		boolean isL2Empty =  isGivenMemoryEmpty (L2,numberOfSetsForL2,numberOfLinesPerSetForL2);
		boolean isL1DFull = isGivenMemoryFull (L1D,numberOfSetsForL1D,numberOfLinesPerSetForL1D);
		boolean isL2Full =  isGivenMemoryFull (L2,numberOfSetsForL2,numberOfLinesPerSetForL2);
		String retrievedDataFromRAM;
		
		int memoryIndexThatIWillReach;
		int decimalOfOperationAddress = hexadecimalToDecimal(operationAddress);
		
		if (decimalOfOperationAddress % 8 == 0) {
			memoryIndexThatIWillReach = decimalOfOperationAddress / 8;
		}
		else {
			decimalOfOperationAddress = decimalOfOperationAddress - (decimalOfOperationAddress % 8);
			memoryIndexThatIWillReach = decimalOfOperationAddress / 8;
		}
		
		// IF RAM SIZE IS NOT ENOUGH, MISS WILL OCCUR, WILL RETURN TO MAIN METHOD
		if (memoryIndexThatIWillReach > howManyIndexesAreLocatedInRamArray ) {
			System.out.println("RAM has not enough size to reach that address, so miss will occur. Please give appropriate addresses in trace file.");
			missesAtL1D++; 
			missesAtL2++;
			return;
		}
		
		// If cache memories are completely empty, i.e, Cold miss case
		if (isL1DEmpty == true && isL2Empty == true) {
			
			missesAtL1D++; 
			missesAtL2++;
			timeAtOperations++;
			
			
			retrievedDataFromRAM = ramArray[memoryIndexThatIWillReach];
		    L1D[0][0][3] = retrievedDataFromRAM;
			L1D[0][0][2] = "1";
			L1D[0][0][1] = Integer.toString(timeAtOperations);
			L1D[0][0][0] = "000000" + Integer.toString(timeAtOperations);
			
			L2[0][0][3] = retrievedDataFromRAM;
			L2[0][0][2] = "1";
			L2[0][0][1] = Integer.toString(timeAtOperations);
			L2[0][0][0] = "000000" + Integer.toString(timeAtOperations);
			return;
			
		}
		
		// Hit case
		String dataToBeCompared = ramArray[memoryIndexThatIWillReach];
		boolean didHitHappenAtL1D = false;
		boolean didHitHappenAtL2 = false;
		for(int i = 0; i < numberOfSetsForL1D; i++ ) {
			for (int j = 0; j < numberOfLinesPerSetForL1D; j++) {
				for (int k = 0; k < 4; k++) {
					if (dataToBeCompared.equals(L1D[i][j][k])) {
						didHitHappenAtL1D = true;
						timeAtOperations++;
						hitsAtL1D++;
					}
				}
			}
		}
		
		for(int i = 0; i < numberOfSetsForL2; i++ ) {
			for (int j = 0; j < numberOfLinesPerSetForL2; j++) {
				for (int k = 0; k < 4; k++) {
					if (dataToBeCompared.equals(L2[i][j][k])) {
						didHitHappenAtL2 = true;
						timeAtOperations++;
						hitsAtL2++;
					}
				}
			}
		}
		
		// Eviction case
		if (isL1DFull == true && isL2Full == true && didHitHappenAtL1D == false && didHitHappenAtL2 == false) {
			timeAtOperations++;
			retrievedDataFromRAM = ramArray[memoryIndexThatIWillReach];
			
			// Going to find the maximum time to find which data will be evicted
			int maxTimeAmongData = Integer.parseInt(L1D[0][0][1]);
			int setIDOfMaxTimeInL1D = 0;
			int lineIDOfMaxTimeInL1D = 0;
			for (int i = 0; i < numberOfSetsForL1D; i++) {
				for (int j = 0; i < numberOfLinesPerSetForL1D; j++ ) {
					if (maxTimeAmongData < Integer.parseInt(L1D[i][j][1])) {
						maxTimeAmongData = Integer.parseInt(L1D[i][j][1]);
						setIDOfMaxTimeInL1D = i;
						lineIDOfMaxTimeInL1D = j;
						
					}
				}
			}
			int setIDOfMaxTimeInL2 = 0;
			int lineIDOfMaxTimeInL2 = 0;
			for (int i = 0; i < numberOfSetsForL2; i++) {
				for (int j = 0; i < numberOfLinesPerSetForL2; j++ ) {
					if (maxTimeAmongData < Integer.parseInt(L2[i][j][1])) {
						maxTimeAmongData = Integer.parseInt(L2[i][j][1]);
						setIDOfMaxTimeInL2 = i;
						lineIDOfMaxTimeInL2 = j;
						
					}
				}
			}
			
			L1D[setIDOfMaxTimeInL1D][lineIDOfMaxTimeInL1D][3] = retrievedDataFromRAM;
			L2[setIDOfMaxTimeInL2][lineIDOfMaxTimeInL2][3] = retrievedDataFromRAM;
			
			L1D[setIDOfMaxTimeInL1D][lineIDOfMaxTimeInL1D][1] = Integer.toString(timeAtOperations);
			L2[setIDOfMaxTimeInL2][lineIDOfMaxTimeInL2][1] = Integer.toString(timeAtOperations);
			
			evictionsAtL1D++;
			evictionsAtL2++;
			
		}
		
		// Miss case
		if (didHitHappenAtL1D == false && didHitHappenAtL2 == false && isL1DFull == false && isL2Full == false) {
			retrievedDataFromRAM = ramArray[memoryIndexThatIWillReach];
			missesAtL1D++;
			missesAtL2++;
			timeAtOperations++;
			
			int setIDOfTheFirstEmptyLocationAtL1D = 0;
			int lineIDOfTheFirstEmptyLocationAtL1D = 0;
			
			outerloop1:
			for (int i = 0; i < numberOfSetsForL1D; i++) {
				for (int j = 0; j < numberOfLinesPerSetForL1D; j++) {
					if(L1D[i][j][0].equals("-1")) {
						setIDOfTheFirstEmptyLocationAtL1D = i;
						lineIDOfTheFirstEmptyLocationAtL1D = j;
						break outerloop1;
					}
				}
				
			}
			int setIDOfTheFirstEmptyLocationAtL2 = 0;
			int lineIDOfTheFirstEmptyLocationAtL2 = 0;
			
			outerloop2:
				for (int i = 0; i < numberOfSetsForL2; i++) {
					for (int j = 0; j < numberOfLinesPerSetForL2; j++) {
						if(L1D[i][j][0].equals("-1")) {
							setIDOfTheFirstEmptyLocationAtL2 = i;
							lineIDOfTheFirstEmptyLocationAtL2 = j;
							break outerloop2;
						}
					}
					
				}
			retrievedDataFromRAM = L1D[setIDOfTheFirstEmptyLocationAtL1D][lineIDOfTheFirstEmptyLocationAtL1D][3];
			retrievedDataFromRAM = L2[setIDOfTheFirstEmptyLocationAtL2][lineIDOfTheFirstEmptyLocationAtL2][3];
			L1D[setIDOfTheFirstEmptyLocationAtL1D][lineIDOfTheFirstEmptyLocationAtL1D][1] = Integer.toString(timeAtOperations);
			L2[setIDOfTheFirstEmptyLocationAtL2][lineIDOfTheFirstEmptyLocationAtL2][1] = Integer.toString(timeAtOperations);
		}
	
	}
	public static void OperationsOfDataStore(String operationAddress, String size, String data, String[][][] L1D, String[][][] L2, String[] ramArray, int numberOfSetsForL1D, int numberOfLinesPerSetForL1D, int numberOfSetsForL2, int numberOfLinesPerSetForL2) {
		// Are cache memories empty or not?
				boolean isL1DEmpty = isGivenMemoryEmpty (L1D,numberOfSetsForL1D,numberOfLinesPerSetForL1D);
				boolean isL2Empty =  isGivenMemoryEmpty (L2,numberOfSetsForL2,numberOfLinesPerSetForL2);
				boolean isL1DFull = isGivenMemoryFull (L1D,numberOfSetsForL1D,numberOfLinesPerSetForL1D);
				boolean isL2Full =  isGivenMemoryFull (L2,numberOfSetsForL2,numberOfLinesPerSetForL2);
				String retrievedDataFromRAM;
				
				int memoryIndexThatIWillReach;
				int decimalOfOperationAddress = hexadecimalToDecimal(operationAddress);
				
				if (decimalOfOperationAddress % 8 == 0) {
					memoryIndexThatIWillReach = decimalOfOperationAddress / 8;
				}
				else {
					decimalOfOperationAddress = decimalOfOperationAddress - (decimalOfOperationAddress % 8);
					memoryIndexThatIWillReach = decimalOfOperationAddress / 8;
				}
				
				int howManyDataWillBeStored = Integer.parseInt(size) * 8 / 4;
				
				// IF RAM SIZE IS NOT ENOUGH, MISS WILL OCCUR, WILL RETURN TO MAIN METHOD
				if (memoryIndexThatIWillReach > howManyIndexesAreLocatedInRamArray ) {
					System.out.println("RAM has not enough size to reach that address, so miss will occur. Please give appropriate addresses in trace file.");
					missesAtL1D++; 
					missesAtL2++;
					return;
				}
				
				// If cache memories are completely empty, i.e, Cold miss case
				if (isL1DEmpty == true && isL2Empty == true) {
					
					missesAtL1D++; 
					missesAtL2++;
					timeAtOperations++;
					
					// Cold miss happened, just going to change RAM at given address and location
					retrievedDataFromRAM = ramArray[memoryIndexThatIWillReach];
					String changedData = retrievedDataFromRAM.substring(0,howManyDataWillBeStored);
					changedData = data;
					retrievedDataFromRAM = changedData + retrievedDataFromRAM.substring(howManyDataWillBeStored,retrievedDataFromRAM.length());
					ramArray[memoryIndexThatIWillReach] = retrievedDataFromRAM;
					return;
					
				}
				
				// Hit case
				String dataToBeCompared = ramArray[memoryIndexThatIWillReach];
				boolean didHitHappenAtL1D = false;
				boolean didHitHappenAtL2 = false;
				
				outerloop:
				for(int i = 0; i < numberOfSetsForL1D; i++ ) {
					for (int j = 0; j < numberOfLinesPerSetForL1D; j++) {
						for (int k = 0; k < 4; k++) {
							if (dataToBeCompared.equals(L1D[i][j][k])) {
								didHitHappenAtL1D = true;
								
								retrievedDataFromRAM = ramArray[memoryIndexThatIWillReach];
								String changedData = retrievedDataFromRAM.substring(0,howManyDataWillBeStored);
								changedData = data;
								retrievedDataFromRAM = changedData + retrievedDataFromRAM.substring(howManyDataWillBeStored,retrievedDataFromRAM.length());
								ramArray[memoryIndexThatIWillReach] = retrievedDataFromRAM;
								
								L1D[i][j][k] = retrievedDataFromRAM;
								
								timeAtOperations++;
								hitsAtL1D++;
								break outerloop;
								
							}
						}
					}
				}
				
				for(int i = 0; i < numberOfSetsForL2; i++ ) {
					for (int j = 0; j < numberOfLinesPerSetForL2; j++) {
						for (int k = 0; k < 4; k++) {
							if (dataToBeCompared.equals(L2[i][j][k])) {
								didHitHappenAtL2 = true;
								
								retrievedDataFromRAM = ramArray[memoryIndexThatIWillReach];
								String changedData = retrievedDataFromRAM.substring(0,howManyDataWillBeStored);
								changedData = data;
								retrievedDataFromRAM = changedData + retrievedDataFromRAM.substring(howManyDataWillBeStored,retrievedDataFromRAM.length());
								ramArray[memoryIndexThatIWillReach] = retrievedDataFromRAM;
								
								L2[i][j][k] = retrievedDataFromRAM;
								
								timeAtOperations++;
								hitsAtL2++;
							}
						}
					}
				}
				
				// Eviction case
				if (isL1DFull == true && isL2Full == true && didHitHappenAtL1D == false && didHitHappenAtL2 == false) {
					timeAtOperations++;
					// Current info is not in cache memory, so we will just modify the data at RAM
					retrievedDataFromRAM = ramArray[memoryIndexThatIWillReach];
					String changedData = retrievedDataFromRAM.substring(0,howManyDataWillBeStored);
					changedData = data;
					retrievedDataFromRAM = changedData + retrievedDataFromRAM.substring(howManyDataWillBeStored,retrievedDataFromRAM.length());
					ramArray[memoryIndexThatIWillReach] = retrievedDataFromRAM;
					
					
				}
				
				// Miss case
				if (didHitHappenAtL1D == false && didHitHappenAtL2 == false && isL1DFull == false && isL2Full == false) {
					
					// Current info is not in cache memory, so we will just modify the data at RAM
					retrievedDataFromRAM = ramArray[memoryIndexThatIWillReach];
					String changedData = retrievedDataFromRAM.substring(0,howManyDataWillBeStored);
					changedData = data;
					retrievedDataFromRAM = changedData + retrievedDataFromRAM.substring(howManyDataWillBeStored,retrievedDataFromRAM.length());
					ramArray[memoryIndexThatIWillReach] = retrievedDataFromRAM;
					
					missesAtL1D++;
					missesAtL2++;
					timeAtOperations++;
					
				}
	}
	public static void OperationsOfDataModify(String operationAddress, String size, String data, String[][][] L1D, String[][][] L2, String[] ramArray, int numberOfSetsForL1D, int numberOfLinesPerSetForL1D, int numberOfSetsForL2, int numberOfLinesPerSetForL2) {
		// Are cache memories empty or not?
		boolean isL1DEmpty = isGivenMemoryEmpty (L1D,numberOfSetsForL1D,numberOfLinesPerSetForL1D);
		boolean isL2Empty =  isGivenMemoryEmpty (L2,numberOfSetsForL2,numberOfLinesPerSetForL2);
		boolean isL1DFull = isGivenMemoryFull (L1D,numberOfSetsForL1D,numberOfLinesPerSetForL1D);
		boolean isL2Full =  isGivenMemoryFull (L2,numberOfSetsForL2,numberOfLinesPerSetForL2);
		String retrievedDataFromRAM;
		
		int memoryIndexThatIWillReach;
		int decimalOfOperationAddress = hexadecimalToDecimal(operationAddress);
		
		if (decimalOfOperationAddress % 8 == 0) {
			memoryIndexThatIWillReach = decimalOfOperationAddress / 8;
		}
		else {
			decimalOfOperationAddress = decimalOfOperationAddress - (decimalOfOperationAddress % 8);
			memoryIndexThatIWillReach = decimalOfOperationAddress / 8;
		}
		
		int howManyDataWillBeStored = Integer.parseInt(size) * 8 / 4;
		
		// IF RAM SIZE IS NOT ENOUGH, MISS WILL OCCUR, WILL RETURN TO MAIN METHOD
		if (memoryIndexThatIWillReach > howManyIndexesAreLocatedInRamArray ) {
			System.out.println("RAM has not enough size to reach that address, so miss will occur. Please give appropriate addresses in trace file.");
			missesAtL1D++; 
			missesAtL2++;
			return;
		}
		
		// If cache memories are completely empty, i.e, Cold miss case
		if (isL1DEmpty == true && isL2Empty == true) {
			
			missesAtL1D++; 
			missesAtL2++;
			timeAtOperations++;
			
			// Cold miss happened, just going to change RAM at given address and location
			retrievedDataFromRAM = ramArray[memoryIndexThatIWillReach];
			String changedData = retrievedDataFromRAM.substring(0,howManyDataWillBeStored);
			changedData = data;
			retrievedDataFromRAM = changedData + retrievedDataFromRAM.substring(howManyDataWillBeStored,retrievedDataFromRAM.length());
			ramArray[memoryIndexThatIWillReach] = retrievedDataFromRAM;
			return;
			
		}
		
		// Hit case
		String dataToBeCompared = ramArray[memoryIndexThatIWillReach];
		boolean didHitHappenAtL1D = false;
		boolean didHitHappenAtL2 = false;
		
		outerloop:
		for(int i = 0; i < numberOfSetsForL1D; i++ ) {
			for (int j = 0; j < numberOfLinesPerSetForL1D; j++) {
				for (int k = 0; k < 4; k++) {
					if (dataToBeCompared.equals(L1D[i][j][k])) {
						didHitHappenAtL1D = true;
						
						retrievedDataFromRAM = ramArray[memoryIndexThatIWillReach];
						String changedData = retrievedDataFromRAM.substring(0,howManyDataWillBeStored);
						changedData = data;
						retrievedDataFromRAM = changedData + retrievedDataFromRAM.substring(howManyDataWillBeStored,retrievedDataFromRAM.length());
						ramArray[memoryIndexThatIWillReach] = retrievedDataFromRAM;
						
						L1D[i][j][k] = retrievedDataFromRAM;
						
						timeAtOperations++;
						hitsAtL1D++;
						break outerloop;
						
					}
				}
			}
		}
		
		for(int i = 0; i < numberOfSetsForL2; i++ ) {
			for (int j = 0; j < numberOfLinesPerSetForL2; j++) {
				for (int k = 0; k < 4; k++) {
					if (dataToBeCompared.equals(L2[i][j][k])) {
						didHitHappenAtL2 = true;
						
						retrievedDataFromRAM = ramArray[memoryIndexThatIWillReach];
						String changedData = retrievedDataFromRAM.substring(0,howManyDataWillBeStored);
						changedData = data;
						retrievedDataFromRAM = changedData + retrievedDataFromRAM.substring(howManyDataWillBeStored,retrievedDataFromRAM.length());
						ramArray[memoryIndexThatIWillReach] = retrievedDataFromRAM;
						
						L2[i][j][k] = retrievedDataFromRAM;
						
						timeAtOperations++;
						hitsAtL2++;
					}
				}
			}
		}
		
		// Eviction case
		if (isL1DFull == true && isL2Full == true && didHitHappenAtL1D == false && didHitHappenAtL2 == false) {
			timeAtOperations++;
			retrievedDataFromRAM = ramArray[memoryIndexThatIWillReach];
			String changedData = retrievedDataFromRAM.substring(0,howManyDataWillBeStored);
			changedData = data;
			retrievedDataFromRAM = changedData + retrievedDataFromRAM.substring(howManyDataWillBeStored,retrievedDataFromRAM.length());
			ramArray[memoryIndexThatIWillReach] = retrievedDataFromRAM;
			
			// Going to find the maximum time to find which data will be evicted
			int maxTimeAmongData = Integer.parseInt(L1D[0][0][1]);
			int setIDOfMaxTimeInL1D = 0;
			int lineIDOfMaxTimeInL1D = 0;
			for (int i = 0; i < numberOfSetsForL1D; i++) {
				for (int j = 0; i < numberOfLinesPerSetForL1D; j++ ) {
					if (maxTimeAmongData < Integer.parseInt(L1D[i][j][1])) {
						maxTimeAmongData = Integer.parseInt(L1D[i][j][1]);
						setIDOfMaxTimeInL1D = i;
						lineIDOfMaxTimeInL1D = j;
						
					}
				}
			}
			int setIDOfMaxTimeInL2 = 0;
			int lineIDOfMaxTimeInL2 = 0;
			for (int i = 0; i < numberOfSetsForL2; i++) {
				for (int j = 0; i < numberOfLinesPerSetForL2; j++ ) {
					if (maxTimeAmongData < Integer.parseInt(L2[i][j][1])) {
						maxTimeAmongData = Integer.parseInt(L2[i][j][1]);
						setIDOfMaxTimeInL2 = i;
						lineIDOfMaxTimeInL2 = j;
						
					}
				}
			}
			
			L1D[setIDOfMaxTimeInL1D][lineIDOfMaxTimeInL1D][3] = retrievedDataFromRAM;
			L2[setIDOfMaxTimeInL2][lineIDOfMaxTimeInL2][3] = retrievedDataFromRAM;
			
			L1D[setIDOfMaxTimeInL1D][lineIDOfMaxTimeInL1D][1] = Integer.toString(timeAtOperations);
			L2[setIDOfMaxTimeInL2][lineIDOfMaxTimeInL2][1] = Integer.toString(timeAtOperations);
			
			evictionsAtL1D++;
			evictionsAtL2++;
			
		}
		
		// Miss case
		if (didHitHappenAtL1D == false && didHitHappenAtL2 == false && isL1DFull == false && isL2Full == false) {
			
			retrievedDataFromRAM = ramArray[memoryIndexThatIWillReach];
			String changedData = retrievedDataFromRAM.substring(0,howManyDataWillBeStored);
			changedData = data;
			retrievedDataFromRAM = changedData + retrievedDataFromRAM.substring(howManyDataWillBeStored,retrievedDataFromRAM.length());
			ramArray[memoryIndexThatIWillReach] = retrievedDataFromRAM;
			
			missesAtL1D++;
			missesAtL2++;
			timeAtOperations++;
			
			int setIDOfTheFirstEmptyLocationAtL1D = 0;
			int lineIDOfTheFirstEmptyLocationAtL1D = 0;
			
			outerloop1:
			for (int i = 0; i < numberOfSetsForL1D; i++) {
				for (int j = 0; j < numberOfLinesPerSetForL1D; j++) {
					if(L1D[i][j][0].equals("-1")) {
						setIDOfTheFirstEmptyLocationAtL1D = i;
						lineIDOfTheFirstEmptyLocationAtL1D = j;
						break outerloop1;
					}
				}
				
			}
			int setIDOfTheFirstEmptyLocationAtL2 = 0;
			int lineIDOfTheFirstEmptyLocationAtL2 = 0;
			
			outerloop2:
				for (int i = 0; i < numberOfSetsForL2; i++) {
					for (int j = 0; j < numberOfLinesPerSetForL2; j++) {
						if(L1D[i][j][0].equals("-1")) {
							setIDOfTheFirstEmptyLocationAtL2 = i;
							lineIDOfTheFirstEmptyLocationAtL2 = j;
							break outerloop2;
						}
					}
					
				}
			retrievedDataFromRAM = L1D[setIDOfTheFirstEmptyLocationAtL1D][lineIDOfTheFirstEmptyLocationAtL1D][3];
			retrievedDataFromRAM = L2[setIDOfTheFirstEmptyLocationAtL2][lineIDOfTheFirstEmptyLocationAtL2][3];
			L1D[setIDOfTheFirstEmptyLocationAtL1D][lineIDOfTheFirstEmptyLocationAtL1D][1] = Integer.toString(timeAtOperations);
			L2[setIDOfTheFirstEmptyLocationAtL2][lineIDOfTheFirstEmptyLocationAtL2][1] = Integer.toString(timeAtOperations);
		}
	}
	public static boolean isGivenMemoryEmpty (String[][][] cacheMemory, int numberOfArrays, int numberOfRows) {
		boolean isEmpty = true;
		
		for (int i = 0; i < numberOfArrays; i++) {
			for (int j = 0; j < numberOfRows; j++) {
				for (int k = 0; k < 4; k++) {
					if (cacheMemory[i][j][k] != "-1") {
						isEmpty = false;
					}
					
				}
			}
		}
		
		return isEmpty;
	
	}
	public static boolean isGivenMemoryFull (String[][][] cacheMemory, int numberOfArrays, int numberOfRows) {
		boolean isFull = true;
		
		for (int i = 0; i < numberOfArrays; i++) {
			for (int j = 0; j < numberOfRows; j++) {
				for (int k = 0; k < 4; k++) {
					if (cacheMemory[i][j][k] == "-1") {
						isFull = false;
						return isFull;
					}
					
				}
			}
		}
		
		return isFull;
	
	}
}
	
