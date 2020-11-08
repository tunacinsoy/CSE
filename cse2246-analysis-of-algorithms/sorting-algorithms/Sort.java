import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public abstract class Sort {
    private long count;
    private long exchange;
    protected  String fileName;

    public abstract int sort(int[] input);



    public void increaseCount() {
        count++;
    }

    public void increaseCount(long c) {
        count = count + c;
    }

    public long getCount() {
        count = count + exchange;
        exchange = 0;
        return count;
    }


    public void resetCount() {
        count = 0;
        exchange = 0;
    }

    public void increaseExchange() {
        exchange++;
    }
}
