import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import src.ServantThread;

public class Main {

    /*
     * Design a data structure such that:
     * - Compile report at end of every hour with:
     * - Top 5 highest temps that hour
     * - Top 5 lowest temps that hour
     * - The 10m interval of time when largest temp difference was observed
     * - Temperature readings taken every minute, gen random number -100F to 70F
     * - Use 8 threads, one for each temp sensor
     */

    /*
     * x60:
     * - Run 8 threads to generate temp (-100F to 70F) and log to matrix
     * - Coarse-lock top 5 highest list and check if we should bump something off
     * - Coast-lock top 5 lowest list and do same
     * 
     * Print top 5 lists
     * Run through matrices and check each sensor at each minute
     * - Get temp 10m ahead and get difference (abs value)
     * - Print max of these
     */

    public static void main(String[] args) throws InterruptedException {
        int NUM_THREADS = 8;
        int[][] log = new int[8][60];

        ExecutorService
            for(int i = 0; i < )
        
    }
}