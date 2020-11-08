public class InsertionSort extends Sort {

    @Override
    public int sort(int[] input) {
        for (int i = 1; i < input.length; i++) {
            int key = input[i];
            int j = i - 1;

            while (j >= 0 && input[j] > key) {
                increaseCount();
                input[j+1] = input[j];
                j--;
            }
            increaseCount();
            input[j+1] = key;
        }
        int medianPosition = input.length / 2;

        return input[medianPosition];
    }

}
