package src;

import java.util.LinkedList;

public class ServantThread extends Thread {

    private LinkedList<Integer> presentBag;
    private PresentChain chain;

    private int presentsAdded = 0;
    private int notesWritten = 0;
    private int searchesMade = 0;

    public ServantThread(LinkedList<Integer> presentBag, PresentChain chain) {
        this.presentBag = presentBag;
        this.chain = chain;
    }

    /*
     * Grabs the next random present from the bag
     * Returns true if the present was successfully added to the chain
     * Returns false if bag is empty and no more presents can be added
     */
    private boolean addPresentToChain() {
        Present nextPresent;
        synchronized (presentBag) {
            if (presentBag.isEmpty())
                return false;
            nextPresent = new Present(presentBag.poll());
        }
        return chain.insertPresent(nextPresent);
    }

    /*
     * Returns true if a present was removed
     * Returns false if the chain is empty and there are no presents to remove
     */
    private boolean writeThankYou() {
        return chain.removeFirstPresent();
    }

    /*
     * Picks a random tag and checks if the present is currently in the chain
     */
    private boolean searchForRandomPresent() {
        searchesMade++;
        int tag = (int) (Math.random() * 500000 + 1);
        return chain.containsPresent(tag);
    }

    private boolean shouldRandomlySearch() {
        return Math.random() < .25;
    }

    @Override
    public void run() {
        while (true) {
            boolean bagEmpty = false;
            boolean chainEmpty = false;

            // Random decide to search
            if (shouldRandomlySearch())
                searchForRandomPresent();

            // Add to chain
            bagEmpty = !addPresentToChain();
            if (!bagEmpty)
                presentsAdded++;

            // Random decide to search
            if (shouldRandomlySearch())
                searchForRandomPresent();

            // Write thank you
            chainEmpty = !writeThankYou();
            if (!chainEmpty)
                notesWritten++;

            // Check if safe to exit
            if (bagEmpty && chainEmpty)
                return;
        }
    }

    public int[] getActionsReport() {
        return new int[] { presentsAdded, notesWritten, searchesMade };
    }
}