package src;

import java.util.LinkedList;

public class ServantThread extends Thread {

    private LinkedList<Integer> presentBag;
    private PresentChain chain;
    private int id;

    private int presentsAdded = 0;
    private int notesWritten = 0;
    private int searchesMade = 0;

    public ServantThread(LinkedList<Integer> presentBag, PresentChain chain, int id) {
        this.presentBag = presentBag;
        this.chain = chain;
        this.id = id;
    }

    private boolean addPresentToChain() {
        Present nextPresent;
        synchronized (presentBag) {
            if (presentBag.isEmpty())
                return false;
            nextPresent = new Present(presentBag.poll());
        }
        return chain.insertPresent(nextPresent);
    }

    private boolean writeThankYou() {
        return chain.removeFirstPresent();
    }

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
            bagEmpty = addPresentToChain();
            if (!bagEmpty)
                presentsAdded++;

            // Random decide to search
            if (shouldRandomlySearch())
                searchForRandomPresent();

            // Write thank you
            chainEmpty = writeThankYou();
            if (!chainEmpty)
                notesWritten++;

            // Check if safe to exit
            if (bagEmpty && chainEmpty)
                return;
        }
    }

    public String getActionsReport() {
        return String.format("%d\t\t%d\t%d\t%d", id, presentsAdded, notesWritten, searchesMade);
    }
}