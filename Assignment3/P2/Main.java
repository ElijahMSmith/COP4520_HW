import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import src.SensorThread;
import src.TempList;

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

    public static void main(String[] args) throws InterruptedException {
        int NUM_THREADS = 8;
        int[][] log = new int[NUM_THREADS][60];
        TempList hottest = new TempList(true);
        TempList coldest = new TempList(false);

        ExecutorService es = Executors.newCachedThreadPool();
        for (int i = 0; i < NUM_THREADS; i++) {
            es.execute(new SensorThread(log, hottest, coldest));
        }

        es.shutdown();
        boolean success = es.awaitTermination(1, TimeUnit.MINUTES);

        if (success)
            System.out.println("Simulation ended successfully!");
        else
            System.out.println("Simulation failed to end before 1m timeout.");

        System.out.println("\n============ Full Log ============");
        System.out.println("\tT1\tT2\tT3\tT4\tT5\tT6\tT7\tT8\n");
        for (int j = 0; j < 60; j++) {
            System.out.print("M" + j + "\t");
            for (int i = 0; i < NUM_THREADS; i++)
                System.out.print(log[i][j] + "\t");
            System.out.println();
        }

        System.out.println("\nTop 5 hottest temperatures: " + hottest);
        System.out.println("\nTop 5 coldest temperatures: " + coldest);

        int maxDiff = -1;
        int maxID = -1;
        int maxStartMin = -1;

        for (int tID = 0; tID < NUM_THREADS; tID++) {
            for (int min = 0; min < 50; min++) {
                int diff = Math.abs(log[tID][min] - log[tID][min + 10]);
                if (diff > maxDiff) {
                    maxDiff = diff;
                    maxID = tID;
                    maxStartMin = min;
                }
            }
        }

        int sTemp = log[maxID][maxStartMin];
        int fTemp = log[maxID][maxStartMin + 10];
        System.out.printf(
                "\nMax temperature difference was recorded by sensor %d:\n%dF at minute %d -> %dF at minute %d (total %ddeg)\n",
                maxID, sTemp, maxStartMin, fTemp, maxStartMin + 10, fTemp - sTemp);
    }
}