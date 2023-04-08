package src;

public class PresentChain {
    private Present head = new Present(0);

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
            if (curr == null)
                return false;

            synchronized (prev) {
                synchronized (curr) {
                    // Loop again if sync issue and not safe to remove
                    if (validatePresent(prev, curr)) {
                        // Remove safely
                        curr.marked = true;
                        prev.next = curr.next;
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
}
