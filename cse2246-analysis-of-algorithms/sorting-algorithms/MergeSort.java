import java.util.Arrays;

public class MergeSort extends Sort {


    @Override
    public int sort(int[] input) {
        int size = input.length;
        int median = 0;

        int[] left;
        int[] right;
        if (size > 1) {

            left = Arrays.copyOf(input, size / 2 );
            right = Arrays.copyOfRange(input, size / 2 , size);
            sort(left);
            increaseCount();
            sort(right);
            increaseCount();
            merge(left, right, input);
        }
        if(size != 0) {
            median = input[size/2];
        }
        return median;
    }

    private void merge(int[] left, int[] right, int[] input) {
        int i = 0;
        int j = 0;
        int k = 0;

        int leftSize = left.length;
        int rightSize = right.length;

        while (i < leftSize && j < rightSize) {
            if (left[i] <= right[j]){
                input[k] = left[i];
                i++;
            } else {
                input[k] = right[j];
                j++;
            }
            increaseCount();
            k++;
        }

        if(i == leftSize) {
            for(int x = j; x < rightSize ; x++) {
                input[k] = right[x];
                k++;
                increaseCount();
            }
        } else {
            for(int x = i; x < leftSize; x++) {
                input[k] = left[x];
                k++;
                increaseCount();
            }
        }
    }

}
