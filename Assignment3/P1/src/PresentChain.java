package src;

public class PresentChain {
    private Present head;

    private final int MIN_SENT = 0;
    private final int MAX_SENT = 500001;

    private int[] addLog = new int[500001];
    private int[] removeLog = new int[500001];

    public PresentChain() {
        head = new Present(MIN_SENT);
        head.next = new Present(MAX_SENT);
    }

    /*
     * Inserts a present into the chain at the correct numerical location
     * Returns true if the present was successfully added
     * Returns false if a present with the same tag already exists
     */
    public boolean insertPresent(Present newPresent) {
        int tag = newPresent.tag;
        while (true) {
            Present prev = head;
            Present curr = head.next;
            while (curr.tag < tag) {
                prev = curr;
                curr = curr.next;
            }
            synchronized (prev) {
                synchronized (curr) {
                    if (validatePresent(prev, curr)) {
                        if (curr.tag == tag)
                            return false;
                        else {
                            newPresent.next = curr;
                            prev.next = newPresent;
                            addLog[newPresent.tag]++;
                            return true;
                        }
                    }
                }
            }
        }
    }

    /*
     * Attempts to remove the present at the front of the chain
     * Returns true if a present was removed
     * Returns false if no presents in the chain to remove
     */
    public boolean removeFirstPresent() {
        while (true) {
            Present prev = head;
            Present curr = head.next;

            // If no present on list, let worker move on to the next task
            if (curr.tag == MAX_SENT)
                return false;

            synchronized (prev) {
                synchronized (curr) {
                    // Loop again if sync issue and not safe to remove
                    if (validatePresent(prev, curr)) {
                        // Remove safely
                        curr.marked = true;
                        prev.next = curr.next;
                        removeLog[curr.tag]++;
                        return true;
                    }
                }
            }
        }
    }

    /*
     * Checks if a present with a particular numerical tag is in the chain
     */
    public boolean containsPresent(int tag) {
        Present current = head;
        while (current.tag < tag)
            current = current.next;
        return current.tag == tag && !current.marked;
    }

    /*
     * Validates if it is safe to insert between prev and curr or to remove curr
     */
    private boolean validatePresent(Present prev, Present curr) {
        return !prev.marked && !curr.marked && prev.next == curr;
    }

    public boolean allPresentsAddedRemovedOnce() {
        for (int i = 1; i < 500001; i++) {
            if (addLog[i] != 1) {
                System.out.println("Present " + i + " added to chain " + addLog[i] + " times!");
                return false;
            } else if (removeLog[i] != 1) {
                System.out.println("Present " + i + " removed from chain " + removeLog[i] + " times!");
                return false;
            }
        }
        return true;
    }
}
