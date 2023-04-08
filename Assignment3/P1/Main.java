import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import src.PresentChain;
import src.ServantThread;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        /*
         * Four servants do any of three actions in no particular order:
         * 
         * 1. Take present from unordered bag and add to chain in correct location
         * (ordered according to tag num)
         * 
         * 2. Write thank you card to guest and remove present from the chain
         *
         * 3. When minotaur requests, check if a gift with particular tag is present in
         * chain or not
         * 
         * Alternate adding gifts to ordered chain and writing thank you cards (1 & 2)
         * 
         * 1 thread per servant, 500k presents from guests
         */

        final int NUM_PRESENTS = 500000;
        final int NUM_SERVANTS = 4;

        // Shuffle tags order
        int[] tags = new int[NUM_PRESENTS];
        for (int i = 0; i < tags.length; i++) {
            tags[i] = i + 1;
        }
        for (int i = 0; i < NUM_PRESENTS * 4; i++) {
            int rand = (int) (Math.random() * NUM_PRESENTS);
            int temp = tags[i];
            tags[i] = tags[rand];
            tags[rand] = temp;
        }

        PresentChain chain = new PresentChain();
        LinkedList<Integer> presentBag = new LinkedList<Integer>();
        for (int i = 0; i < NUM_PRESENTS; i++)
            presentBag.add(tags[i]);

        long start = System.currentTimeMillis();
        System.out.println("Releasing the minotaur's workers.");

        ExecutorService es = Executors.newCachedThreadPool();
        ServantThread[] servants = new ServantThread[NUM_SERVANTS];
        for (int i = 0; i < NUM_SERVANTS; i++) {
            servants[i] = new ServantThread(presentBag, chain, i);
            es.execute(servants[i]);
        }

        es.shutdown();
        boolean success = es.awaitTermination(2, TimeUnit.MINUTES);
        long end = System.currentTimeMillis();

        if (!success)
            System.out.println("Not all workers finished by 2m timeout.");
        else
            System.out.println("Present bag depleted and all thank-you notes written!");

        System.out.printf("\nExecution time: %d\n\n", (end - start));
        System.out.println("Servant actions report:");
        System.out.println("===========================================");
        System.out.println("ID\t\tPresents Added\tPresents Removed\tSearches Made\t");
        for (int i = 0; i < NUM_SERVANTS; i++)
            System.out.println(servants[i].getActionsReport());
    }
}