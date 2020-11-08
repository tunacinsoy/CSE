import java.util.Arrays;
public class MedianOfMedians extends Sort{

    public int sort(int[] input){
        int k = input.length /2;
        select(input, 0, input.length-1, k);
        return input[k];
    }
    public int select(int[] input, int left, int right, int n){

        while(true){
            increaseCount();
            if (left == right){
                return left;
            }
            int pivotIndex = pivot(input, left, right);
            pivotIndex = partition(input, left, right, pivotIndex, n);

            increaseCount();
            if (n == pivotIndex) {
                return n;
            }
            else if (n < pivotIndex) {
                right = pivotIndex - 1;
            }
            else {
                left = pivotIndex + 1;
            }

        }
    }

    public int partition(int[] input, int left, int right, int pivotIndex, int n) {
        int pivotValue = input[pivotIndex];
        InputUtility.swap(input,pivotIndex, right);
        int storeIndex = left;

        for (int i = left; i <= right - 1; i++){
            increaseCount();
            if (input[i] < pivotValue) {
                InputUtility.swap(input, storeIndex, i);
                storeIndex++;
            }
        }
        int storeIndexEq = storeIndex;
        for (int i = storeIndex; i <= right - 1; i++) {
            increaseCount();
            if (input[i] == pivotValue) {
                InputUtility.swap(input, storeIndexEq, i);
                storeIndexEq++;
            }

        }
        InputUtility.swap(input,right,storeIndexEq);
        increaseCount();
        if (n < storeIndex) {
            return storeIndex;
        }
        if (n <= storeIndexEq) {
            return n;
        }
        return storeIndexEq;
    }

    public int pivot (int[] input, int left, int right) {
        if (right - left < 5) {
            return partition5(input, left,right);
        }
        for (int i = left; i <= right; i=i+5 ) {
            int subRight = i + 4;
            increaseCount();
            if (subRight > right) {
                subRight = right;
            }
            int median5 = partition5(input,i, subRight);
            InputUtility.swap(input, median5, left + ((i - left) / 5) );
        }

        int mid = (right - left) / 10 + left + 1;
        return select(input, left, left + (right - left) / 5, mid);
    }

    public int partition5 (int[] input, int left, int right){
        int i = left + 1;
        while (i <= right) {
            int j = i;
            while (j > left && input[j-1] > input[j] ) {
                InputUtility.swap(input, j-1,j);
                j = j - 1;
            }
            i = i + 1;
        }
        return ((left + right) / 2);
    }

}
