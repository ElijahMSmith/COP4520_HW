import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class CrystalVase {
    public static void main(String[] args) throws InterruptedException {
        /*
         * Create all guest threads
         * Wait for a second to let them queue up
         * Start the first guest in the queue
         * Wait for all threads to have stopped (executor pool again?)
         */

        int N = 0;
        Scanner sc = new Scanner(System.in);
        System.out.println("How many guests?");
        while (true) {
            try {
                N = sc.nextInt();
                if (N > 1)
                    break;
            } catch (Exception e) {
                System.out.println("Please provide a valid number of guests (>= 1).");
            }
        }
        sc.close();

        Queue<GuestThread> vaseRoom = new LinkedList<GuestThread>();
        ExecutorService es = Executors.newCachedThreadPool();

        long start = System.currentTimeMillis();
        GuestThread[] allGuests = new GuestThread[N];
        for (int i = 0; i < N; i++) {
            allGuests[i] = new GuestThread(vaseRoom);
            es.execute(allGuests[i]);
        }

        es.shutdown();
        boolean success = es.awaitTermination(2, TimeUnit.MINUTES);

        long end = System.currentTimeMillis();

        System.out.println("--------------------");
        if (!success)
            System.out.println("Not all guests finished viewing before 2m timeout.");
        else
            System.out.println("All guests are now finished viewing the vase!");

        double sum = 0;
        for (int i = 0; i < N; i++) {
            sum += allGuests[i].getTimesVisited();
            System.out.printf("Guest %d viewed the vase %d times.\n", i, allGuests[i].getTimesVisited());
        }
        System.out.printf("Average number of views per guest: %.2f.\n", sum / N);
        System.out.printf("Execution time: %d\n", (end - start));
    }
}