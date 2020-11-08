import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class InputUtility {

    public static int[] generateRandomInput(int inputSize) {
        Random rnd = new Random();
        int[] input = new int[inputSize];
        for(int i = 0; i < inputSize; i++) {
            input[i] = rnd.nextInt(1000);
        }
        return input;
    }

    public static int[] generateReverseOrderedInput(int inputSize) {
        int[] input = generateOrderedInput(inputSize);
        int temp;
        for(int i = 0; i < Math.floor(inputSize/2.0); i++) {
            temp = input[i];
            input[i] = input[inputSize-i-1];
            input[inputSize-i-1] = temp;
        }
        return input;
    }

    public static int[] generateOrderedInput(int inputSize) {
        int[] input = generateRandomInput(inputSize);
        Arrays.sort(input);
        return input;
    }

    public static int[] generatePercentageOrderedInput(int inputSize, int percentage) {
        int[] input = generateOrderedInput(inputSize);
        int tempSize = ((inputSize * percentage)/100);
        Random rnd = new Random();
        int j;
        for (int i = 0; i < inputSize - tempSize; i++) {
            j = tempSize + rnd.nextInt(inputSize - tempSize);
            input[tempSize+i]=input[j];
        }
        return input;
    }

    public static void swap(int[] input, int index1, int index2) {
        int temp = input[index1];
        input[index1] = input[index2];
        input[index2] = temp;

    }

    public static void printInput(int[] input) {
        for (int value : input) {
            System.out.print(value + " ");
        }
        System.out.println();
    }

    public static void  writeFile(String fileName, String str) {
        String fl = "./out/".concat(fileName);
        File file = new File(fl);
        FileWriter fw;
        BufferedWriter bw;

        try {
            if(!file.exists()) {
                file.createNewFile();
            }
            fw = new FileWriter(file, true);
            bw = new BufferedWriter(fw);
            bw.write(str);
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
