/*
 * This program takes inputs as signed, unsigned or fractional; and converts them to hexadecimal notation.
 * Aim is being familiar with bit representations.
 * Author: Tuna Cinsoy
 * Last Updated: 08.04.2020 (Final touches)
 * 
 */
import java.io.*;
import java.util.Scanner; 

public class Core {
	
	// Method for declaring the input number is zero or not
	public static boolean isZero(String input) {
		int x = input.compareTo("0");
		if (x == 0) {
			return true;
		} else {
			return false;
		}

	}
	
	// Method for declaring the input number is unsigned or not
	public static boolean isUnsigned(String input) {

		for (int i = 0; i < input.length(); i++) {

			if (input.charAt(i) == 'u') {
				return true;
			}

		}
		return false;

	}
	
	// Method for declaring the input number is unsigned or not
	public static boolean isSigned(String input) {

		for (int i = 0; i < input.length(); i++) {

			if (input.charAt(i) == 'u' || input.charAt(i) == '.') {
				return false;
			}

		}
		return true;

	}
	
	// Method for declaring the input number is fractional or not
	public static boolean isFractional(String input) {

		for (int i = 0; i < input.length(); i++) {

			if (input.charAt(i) == '.') {
				return true;
			}

		}
		return false;

	}
	
	// Method for evaluating 0 input
	public static String evaluateZero(String input, String byteOrdering) {
		String binaryRepresentation = "0000000000000000";
		String hexadecimalNotation = evaluateHexadecimal(binaryRepresentation, 16);

		if (byteOrdering.compareTo("Little Endian") == 0) {
			String temp = hexadecimalNotation.substring(2, 4);
			String temp2 = hexadecimalNotation.substring(0, 2);
			temp = temp + " " + temp2;
			return temp;
		} else if (byteOrdering.compareTo("Big Endian") == 0) {
			String temp = hexadecimalNotation.substring(2, 4);
			String temp2 = hexadecimalNotation.substring(0, 2);
			temp2 = temp2 + " " + temp;
			return temp2;
		}

		return "Oopsé! Something Went Wrong! Please check for typos in byte ordering :)";
	}
	
	// Method for evaluating unsigned inputs
	public static String evaluateUnsigned(String input, String byteOrdering) {
		int indexOfULetter = 0;
		for (int i = 0; i < input.length(); i++) {
			if (input.charAt(i) == 'u') {
				indexOfULetter = i;
			}
		}
		int unsignedInteger = Integer.parseInt(input.substring(0, indexOfULetter));

		String binaryRepresentation = "";
		while (unsignedInteger > 0) {
			binaryRepresentation = ((unsignedInteger % 2) == 0 ? "0" : "1") + binaryRepresentation;
			unsignedInteger = unsignedInteger / 2;
		}
		int howManyZeros = 16 - binaryRepresentation.length();

		String fullOfZeros = "";
		for (int i = 0; i < howManyZeros; i++) {
			fullOfZeros = fullOfZeros + "0";
		}
		binaryRepresentation = fullOfZeros + binaryRepresentation;
		String hexadecimalNotation = evaluateHexadecimal(binaryRepresentation, 16);

		if (byteOrdering.compareTo("Little Endian") == 0) {
			String temp = hexadecimalNotation.substring(2, 4);
			String temp2 = hexadecimalNotation.substring(0, 2);
			temp = temp + " " + temp2;
			return temp;
		} else if (byteOrdering.compareTo("Big Endian") == 0) {
			String temp = hexadecimalNotation.substring(2, 4);
			String temp2 = hexadecimalNotation.substring(0, 2);
			temp2 = temp2 + " " + temp;
			return temp2;
		}
		return "Oopsé! Something Went Wrong! Please check for typos in byte ordering :)";

	}
	
	// Method for evaluating signed inputs
	public static String evaluateSigned(String input, String byteOrdering) {
	
		
		
		int unsignedInteger = Integer.parseInt(input.substring(1, input.length()));
		
		

		String binaryRepresentation = "";
		while (unsignedInteger > 0) {
			binaryRepresentation = ((unsignedInteger % 2) == 0 ? "0" : "1") + binaryRepresentation;
			unsignedInteger = unsignedInteger / 2;
		}
		int howManyZeros = 16 - binaryRepresentation.length();

		String fullOfZeros = "";
		for (int i = 0; i < howManyZeros; i++) {
			fullOfZeros = fullOfZeros + "0";
		}
		binaryRepresentation = fullOfZeros + binaryRepresentation;
		StringBuffer str = new StringBuffer(binaryRepresentation);

		// 2's Complement Operation
		int n = str.length();

		int i;
		for (i = n - 1; i >= 0; i--)
			if (str.charAt(i) == '1')
				break;

		if (i == -1)
			return "1" + str;

		for (int k = i - 1; k >= 0; k--) {

			if (str.charAt(k) == '1')
				str.replace(k, k + 1, "0");
			else
				str.replace(k, k + 1, "1");
		}

		String hexadecimalNotation = evaluateHexadecimal(str.toString(), 16);

		if (byteOrdering.compareTo("Little Endian") == 0) {
			String temp = hexadecimalNotation.substring(2, 4);
			String temp2 = hexadecimalNotation.substring(0, 2);
			temp = temp + " " + temp2;
			return temp;
		} else if (byteOrdering.compareTo("Big Endian") == 0) {
			String temp = hexadecimalNotation.substring(2, 4);
			String temp2 = hexadecimalNotation.substring(0, 2);
			temp2 = temp2 + " " + temp;
			return temp2;
		}
		return "Oopsé! Something Went Wrong! Please check for typos in byte ordering :)";

	}

	// Method for evaluating fractional inputs
	public static String evaluateFractional(String input, String byteOrdering, int floatingPointSize) {

		
		int exponentPart;
		int fractionPart;
		int eValue;
		int exponent;
		int bias;
		String signBit = "";
		String finalBinaryExpression = "";

		String binaryRepresentationBeforeOperations = ""; // 29.109375 = 11101.000111
		String properVersionForMantissa = "";

		if (floatingPointSize == 1) {
			exponentPart = 4;
			fractionPart = 3;
			binaryRepresentationBeforeOperations = floatingPointNumberToBinary(input, 32);

			int locationOfDotBeforeOperations = binaryRepresentationBeforeOperations.indexOf('.');

			properVersionForMantissa = movingDotToLeft(binaryRepresentationBeforeOperations);

			int locationOfDotAtMantissa = properVersionForMantissa.indexOf('.');

			// DENORMALIZED CASE HANDLING
			if (properVersionForMantissa.charAt(0) == '0') {
				eValue = locationOfDotAtMantissa - properVersionForMantissa.indexOf("1");
			} else {
				eValue = locationOfDotBeforeOperations - locationOfDotAtMantissa;
			}

			bias = (int) (Math.pow(2, 3) - 1);

			exponent = bias + eValue;

			String binaryRepresentationOfExponent = "";
			String afterDotPart = "";
			if (exponent == 0) {
				binaryRepresentationOfExponent = "0000";
				afterDotPart = properVersionForMantissa.substring(properVersionForMantissa.indexOf('1'),
						properVersionForMantissa.length());

			} else {
				afterDotPart = properVersionForMantissa.substring(properVersionForMantissa.indexOf('.') + 1,
						properVersionForMantissa.length());
			}
			while (exponent > 0) {
				binaryRepresentationOfExponent = ((exponent % 2) == 0 ? "0" : "1") + binaryRepresentationOfExponent;
				exponent = exponent / 2;
			}

			if (input.charAt(0) != '-') {
				signBit = signBit + "0";

			} else {
				signBit = signBit + "1";
			}

			finalBinaryExpression = signBit + binaryRepresentationOfExponent;

			// --------FRACTION PART---------------------------------

			int i = afterDotPart.length() - 1;

			// Trimming 0s from right of the fraction
			while (afterDotPart.charAt(i) == '0') {

				afterDotPart = afterDotPart.substring(0, i);
				i--;

				if (afterDotPart.length() == fractionPart) {
					break;
				}

			}

			// ROUNDING TO NEAREST EVEN
			while (afterDotPart.length() != fractionPart) {
				int x = afterDotPart.length() - 1;
				String valueToBeAdded = "1";
				afterDotPart = sum(afterDotPart, valueToBeAdded);

				afterDotPart = afterDotPart.substring(0, x);

			}

			finalBinaryExpression = finalBinaryExpression + afterDotPart;
			String hexadecimalNotation = evaluateHexadecimal(finalBinaryExpression, 8 * floatingPointSize);
			return hexadecimalNotation;

		} else if (floatingPointSize == 2) {
			exponentPart = 6;
			fractionPart = 9;

			binaryRepresentationBeforeOperations = floatingPointNumberToBinary(input, 32);

			int locationOfDotBeforeOperations = binaryRepresentationBeforeOperations.indexOf('.');

			properVersionForMantissa = movingDotToLeft(binaryRepresentationBeforeOperations);

			int locationOfDotAtMantissa = properVersionForMantissa.indexOf('.');

			if (properVersionForMantissa.charAt(0) == '0') {
				eValue = locationOfDotAtMantissa - properVersionForMantissa.indexOf("1");

			} else {
				eValue = locationOfDotBeforeOperations - locationOfDotAtMantissa;
			}

			bias = (int) (Math.pow(2, 5) - 1);

			exponent = bias + eValue;

			String binaryRepresentationOfExponent = "";
			String afterDotPart = "";
			if (exponent == 0) {
				binaryRepresentationOfExponent = "000000";
				afterDotPart = properVersionForMantissa.substring(properVersionForMantissa.indexOf('1'),
						properVersionForMantissa.length());

			} else {
				afterDotPart = properVersionForMantissa.substring(properVersionForMantissa.indexOf('.') + 1,
						properVersionForMantissa.length());
			}
			while (exponent > 0) {
				binaryRepresentationOfExponent = ((exponent % 2) == 0 ? "0" : "1") + binaryRepresentationOfExponent;
				exponent = exponent / 2;

			}

			if (binaryRepresentationOfExponent.length() != exponentPart) {

				binaryRepresentationOfExponent = binaryRepresentationOfExponent + "0";
			}

			if (input.charAt(0) != '-') {
				signBit = signBit + "0";

			} else {
				signBit = signBit + "1";
			}

			finalBinaryExpression = signBit + binaryRepresentationOfExponent;

			// --------FRACTION PART---------------------------------

			int i = afterDotPart.length() - 1;

			// Trimming 0s from right of the fraction
			while (afterDotPart.charAt(i) == '0') {

				afterDotPart = afterDotPart.substring(0, i);
				i--;

				if (afterDotPart.length() == fractionPart) {
					break;
				}

			}

			// ROUNDING TO NEAREST EVEN
			while (afterDotPart.length() != fractionPart) {
				int x = afterDotPart.length() - 1;
				String valueToBeAdded = "1";
				afterDotPart = sum(afterDotPart, valueToBeAdded);

				afterDotPart = afterDotPart.substring(0, x);

			}

			finalBinaryExpression = finalBinaryExpression + afterDotPart;

			String hexadecimalNotation = evaluateHexadecimal(finalBinaryExpression, 8 * floatingPointSize);

			if (byteOrdering.compareTo("Little Endian") == 0) {
				String temp = hexadecimalNotation.substring(2, 4);
				String temp2 = hexadecimalNotation.substring(0, 2);
				temp = temp + " " + temp2;
				return temp;
			} else if (byteOrdering.compareTo("Big Endian") == 0) {
				String temp = hexadecimalNotation.substring(2, 4);
				String temp2 = hexadecimalNotation.substring(0, 2);
				temp2 = temp2 + " " + temp;
				return temp2;
			}
			else {
				return "Oopsé! Something Went Wrong! Please check for typos in byte ordering :)";
			}

		} else if (floatingPointSize == 3) {
			exponentPart = 8;
			fractionPart = 15;
			binaryRepresentationBeforeOperations = floatingPointNumberToBinary(input, 32);

			int locationOfDotBeforeOperations = binaryRepresentationBeforeOperations.indexOf('.');

			properVersionForMantissa = movingDotToLeft(binaryRepresentationBeforeOperations);

			int locationOfDotAtMantissa = properVersionForMantissa.indexOf('.');

			if (properVersionForMantissa.charAt(0) == '0') {
				eValue = locationOfDotAtMantissa - properVersionForMantissa.indexOf("1");
			} else {
				eValue = locationOfDotBeforeOperations - locationOfDotAtMantissa;
			}

			bias = (int) (Math.pow(2, 7) - 1);

			exponent = bias + eValue;
			String binaryRepresentationOfExponent = "";
			String afterDotPart = "";
			if (exponent == 0) {
				binaryRepresentationOfExponent = "00000000";
				afterDotPart = properVersionForMantissa.substring(properVersionForMantissa.indexOf('1'),
						properVersionForMantissa.length());
				System.out.println("afterDotPart -> " + afterDotPart);
			} else {
				afterDotPart = properVersionForMantissa.substring(properVersionForMantissa.indexOf('.') + 1,
						properVersionForMantissa.length());
			}
			while (exponent > 0) {
				binaryRepresentationOfExponent = ((exponent % 2) == 0 ? "0" : "1") + binaryRepresentationOfExponent;
				exponent = exponent / 2;
			}
			if (binaryRepresentationOfExponent.length() != exponentPart) {
				binaryRepresentationOfExponent = binaryRepresentationOfExponent + "0";
			}

			if (input.charAt(0) != '-') {
				signBit = signBit + "0";

			} else {
				signBit = signBit + "1";
			}

			finalBinaryExpression = signBit + binaryRepresentationOfExponent;

			// --------FRACTION PART---------------------------------

			int i = afterDotPart.length() - 1;

			// Trimming 0s from right of the fraction
			while (afterDotPart.charAt(i) == '0') {

				afterDotPart = afterDotPart.substring(0, i);
				i--;

				if (afterDotPart.length() == fractionPart) {
					break;
				}

			}

			// ROUNDING TO NEAREST EVEN
			while (afterDotPart.length() != fractionPart - 2) {
				int x = afterDotPart.length() - 1;
				String valueToBeAdded = "1";
				afterDotPart = sum(afterDotPart, valueToBeAdded);

				afterDotPart = afterDotPart.substring(0, x);

			}
			afterDotPart = afterDotPart + "00";
			finalBinaryExpression = finalBinaryExpression + afterDotPart;
			String hexadecimalNotation = evaluateHexadecimal(finalBinaryExpression, 24);

			if (byteOrdering.compareTo("Little Endian") == 0) {
				String temp = hexadecimalNotation.substring(2, 4);
				String temp2 = hexadecimalNotation.substring(0, 2);
				String temp3 = hexadecimalNotation.substring(4, 6);
				temp3 = temp3 + " " + temp + " " + temp2;

				return temp3;

			} else if (byteOrdering.compareTo("Big Endian") == 0) {
				String temp = hexadecimalNotation.substring(2, 4);
				String temp2 = hexadecimalNotation.substring(0, 2);
				String temp3 = hexadecimalNotation.substring(4, 6);
				temp2 = temp2 + " " + temp + " " + temp3;
				return temp2;
			}
			else {
				return "Oopsé! Something Went Wrong! Please check for typos in byte ordering :)";
			}

		} else if (floatingPointSize == 4) {
			exponentPart = 10;
			fractionPart = 21;
			binaryRepresentationBeforeOperations = floatingPointNumberToBinary(input, 32);

			int locationOfDotBeforeOperations = binaryRepresentationBeforeOperations.indexOf('.');

			properVersionForMantissa = movingDotToLeft(binaryRepresentationBeforeOperations);

			int locationOfDotAtMantissa = properVersionForMantissa.indexOf('.');

			if (properVersionForMantissa.charAt(0) == '0') {
				eValue = locationOfDotAtMantissa - properVersionForMantissa.indexOf("1");
			} else {
				eValue = locationOfDotBeforeOperations - locationOfDotAtMantissa;
			}

			bias = (int) (Math.pow(2, 9) - 1);

			exponent = bias + eValue;
			String binaryRepresentationOfExponent = "";
			String afterDotPart = "";
			if (exponent == 0) {
				binaryRepresentationOfExponent = "0000000000";
				afterDotPart = properVersionForMantissa.substring(properVersionForMantissa.indexOf('1'),
						properVersionForMantissa.length());
				System.out.println("afterDotPart -> " + afterDotPart);
			} else {
				afterDotPart = properVersionForMantissa.substring(properVersionForMantissa.indexOf('.') + 1,
						properVersionForMantissa.length());
			}
			while (exponent > 0) {
				binaryRepresentationOfExponent = ((exponent % 2) == 0 ? "0" : "1") + binaryRepresentationOfExponent;
				exponent = exponent / 2;
			}
			if (binaryRepresentationOfExponent.length() != exponentPart) {
				binaryRepresentationOfExponent = binaryRepresentationOfExponent + "0";
			}

			if (input.charAt(0) != '-') {
				signBit = signBit + "0";

			} else {
				signBit = signBit + "1";
			}

			finalBinaryExpression = signBit + binaryRepresentationOfExponent;

			// --------FRACTION PART---------------------------------

			int i = afterDotPart.length() - 1;

			// Trimming 0s from right of the fraction
			while (afterDotPart.charAt(i) == '0') {

				afterDotPart = afterDotPart.substring(0, i);
				i--;

				if (afterDotPart.length() == fractionPart) {
					break;
				}

			}

			// ROUNDING TO NEAREST EVEN
			while (afterDotPart.length() != fractionPart - 8) {
				int x = afterDotPart.length() - 1;
				String valueToBeAdded = "1";
				afterDotPart = sum(afterDotPart, valueToBeAdded);

				afterDotPart = afterDotPart.substring(0, x);

			}
			afterDotPart = afterDotPart + "00000000";
			finalBinaryExpression = finalBinaryExpression + afterDotPart;
			String hexadecimalNotation = evaluateHexadecimal(finalBinaryExpression, 32);

			if (byteOrdering.compareTo("Little Endian") == 0) {
				String temp = hexadecimalNotation.substring(2, 4);
				String temp2 = hexadecimalNotation.substring(0, 2);
				String temp3 = hexadecimalNotation.substring(4, 6);
				String temp4 = hexadecimalNotation.substring(6, 8);
				temp4 = temp4 + " " + temp3 + " " + temp + " " + temp2;

				return temp4;

			} else if (byteOrdering.compareTo("Big Endian") == 0) {
				String temp = hexadecimalNotation.substring(2, 4);
				String temp2 = hexadecimalNotation.substring(0, 2);
				String temp3 = hexadecimalNotation.substring(4, 6);
				String temp4 = hexadecimalNotation.substring(6, 8);
				temp2 = temp2 + " " + temp + " " + temp3 + " " + temp4;
				return temp2;
			}
			else {
				return "Oopsé! Something Went Wrong! Please check for typos in byte ordering :)";
			}
		}

		return "Invalid input. Please enter 1, 2, 3 or 4 for floating point size";
	}
	
	// Method for bitwise sum operation
	public static String sum(String b1, String b2) {
		int len1 = b1.length();
		int len2 = b2.length();
		int carry = 0;
		String res = "";

		int maxLen = Math.max(len1, len2);
		for (int i = 0; i < maxLen; i++) {

			int p = i < len1 ? b1.charAt(len1 - 1 - i) - '0' : 0;
			int q = i < len2 ? b2.charAt(len2 - 1 - i) - '0' : 0;
			int tmp = p + q + carry;
			carry = tmp / 2;
			res = tmp % 2 + res;
		}
		return (carry == 0) ? res : "1" + res;
	}
	
	// Converting floatingPointSize input to integer
	public static int floatingPointSizeToInteger(String input) {

		String[] splittedVersion = input.split(" ");

		int returnValue = Integer.parseInt(splittedVersion[0]);

		return returnValue;
	}
	
	// Method for evaluating hexadecimal outputs
	public static String evaluateHexadecimal(String input, int howManyBits) {
		StringBuilder number = new StringBuilder();
		String cluster = "";
		for (int i = 0; i < howManyBits; i += 4) {
			cluster = input.substring(i, i + 4);
			switch (cluster) {
			case "0000":
				number.append("0");
				break;
			case "0001":
				number.append("1");
				break;
			case "0010":
				number.append("2");
				break;
			case "0011":
				number.append("3");
				break;
			case "0100":
				number.append("4");
				break;
			case "0101":
				number.append("5");
				break;
			case "0110":
				number.append("6");
				break;
			case "0111":
				number.append("7");
				break;
			case "1000":
				number.append("8");
				break;
			case "1001":
				number.append("9");
				break;
			case "1010":
				number.append("A");
				break;
			case "1011":
				number.append("B");
				break;
			case "1100":
				number.append("C");
				break;
			case "1101":
				number.append("D");
				break;
			case "1110":
				number.append("E");
				break;
			case "1111":
				number.append("F");
				break;
			}
		}
		return number.toString();
	}
	
	// Method for evaluating floating point number's binary representation
	public static String floatingPointNumberToBinary (String input, int precision) {
		
		
		float floatingPointNumber = Float.parseFloat(input);
		
		String binaryRepresentation = "";
		
		int integerPartOfFloatingPointNumber = (int) floatingPointNumber;
		
		float fractionPartOfFloatingPointNumber = floatingPointNumber - integerPartOfFloatingPointNumber;
		
		if (integerPartOfFloatingPointNumber < 0) {
			integerPartOfFloatingPointNumber = Math.abs(integerPartOfFloatingPointNumber);
		}
		else if(integerPartOfFloatingPointNumber == 0) {
			binaryRepresentation = "0";
		}

		while (integerPartOfFloatingPointNumber > 0) {
			binaryRepresentation = ((integerPartOfFloatingPointNumber % 2) == 0 ? "0" : "1") + binaryRepresentation;
			integerPartOfFloatingPointNumber = integerPartOfFloatingPointNumber / 2;
		}
		
		binaryRepresentation = binaryRepresentation + ".";
		
		fractionPartOfFloatingPointNumber = Math.abs(fractionPartOfFloatingPointNumber);
		
		String fractionPartBinaryRepresentation = "";
		while (precision-- > 0) {
			// Find next bit in fraction
			fractionPartOfFloatingPointNumber *= 2;
			int currentFractionBit = (int) fractionPartOfFloatingPointNumber;
			if (currentFractionBit == 1) {
				fractionPartOfFloatingPointNumber -= currentFractionBit;
				fractionPartBinaryRepresentation += (char) (1 + '0');
			} else {
				fractionPartBinaryRepresentation += (char) (0 + '0');
			}
		}

		binaryRepresentation = binaryRepresentation + fractionPartBinaryRepresentation;

		return binaryRepresentation;
	}
	
	// Method for moving dot to left to find out mantissa
	public static String movingDotToLeft (String input) {

		int locationOfDot = input.indexOf('.');
		String finalMantissaVersion = "";
		String first = input.substring(0, 1);
		String second = input.substring(1, locationOfDot);
		String third = input.substring(locationOfDot + 1, input.length());

		finalMantissaVersion = first + "." + second + third;

		return finalMantissaVersion;

	}

	public static void main(String[] args) throws FileNotFoundException, IOException  {
		
		File inputFile = new File("C:\\Users\\dell\\OneDrive\\Masaüstü\\input.txt");
		Scanner sc = new Scanner(inputFile);

		BufferedWriter writer = new BufferedWriter(new FileWriter("C:\\Users\\dell\\OneDrive\\Masaüstü\\output.txt"));

		Scanner sc2 = new Scanner(System.in);
		System.out.print("Please Enter the Byte Ordering (Little Endian or Big Endian): ");
		String byteOrdering = sc2.nextLine();
		System.out.print("Please Enter the Floating Point Size (1, 2, 3 or 4 bytes): ");
		String floatingPointSize = sc2.nextLine(); // Getting input as a string since there is "bytes" word
		int floatingPoint = floatingPointSizeToInteger(floatingPointSize); // Converting string to int to use for
																			// fractional evaluation

		while (sc.hasNextLine()) {

			String currentInput = sc.nextLine();
			String hexadecimalNotationOfCurrentInput;

			boolean isZero = isZero(currentInput);
			boolean isUnsigned = isUnsigned(currentInput);
			boolean isSigned = isSigned(currentInput);
			boolean isFractional = isFractional(currentInput);

			if (isZero == true) {

				hexadecimalNotationOfCurrentInput = evaluateZero(currentInput, byteOrdering);
				System.out.println(hexadecimalNotationOfCurrentInput);
				writer.write(hexadecimalNotationOfCurrentInput);
				writer.write("\n");
			} else if (isUnsigned == true) {
				hexadecimalNotationOfCurrentInput = evaluateUnsigned(currentInput, byteOrdering);
				System.out.println(hexadecimalNotationOfCurrentInput);
				writer.write(hexadecimalNotationOfCurrentInput);
				writer.write("\n");
			} else if (isSigned == true) {
				hexadecimalNotationOfCurrentInput = evaluateSigned(currentInput, byteOrdering);
				System.out.println(hexadecimalNotationOfCurrentInput);
				writer.write(hexadecimalNotationOfCurrentInput);
				writer.write("\n");
			} else if (isFractional == true) {
				hexadecimalNotationOfCurrentInput = evaluateFractional(currentInput, byteOrdering, floatingPoint);
				System.out.println(hexadecimalNotationOfCurrentInput);
				writer.write(hexadecimalNotationOfCurrentInput);
				writer.write("\n");
			}

		}
		sc.close();
		sc2.close();
		writer.close();

	}

}
