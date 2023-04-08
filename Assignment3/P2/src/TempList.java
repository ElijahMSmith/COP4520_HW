package src;

public class TempList {
    TempNode head = new TempNode(Integer.MIN_VALUE);
    private boolean hottest;

    int size = 0;
    int MAX_SIZE = 5;

    public TempList(boolean hottest) {
        this.hottest = hottest;
    }

    // TODO: This doesn't work
    public synchronized void offerTemp(int reading) {
        TempNode prev = head;
        TempNode curr = head.next;

        // Look for proper insertion slot
        while (curr != null && ((!hottest && reading > curr.val) || (hottest && reading < curr.val))) {
            prev = curr;
            curr = curr.next;
        }

        // If reached end of a full list, it isn't part of top 5
        if (curr == null && size == MAX_SIZE)
            return;

        size++;
        TempNode newNode = new TempNode(reading);
        prev.next = newNode;
        newNode.next = curr;

        if (size > 5) {
            // Keep going until curr is the very tail element of the list
            // Not a NPE because curr is not null (we return early if so)
            while (curr.next != null) {
                prev = curr;
                curr = curr.next;
            }

            // Remove this tail element to go back to size 5
            prev.next = null;
            size--;
        }
    }

    @Override
    public String toString() {
        TempNode curr = head.next;
        StringBuilder build = new StringBuilder();
        while (curr != null) {
            build.append(curr.val + ", ");
            curr = curr.next;
        }
        return build.substring(build.length() - 2).toString();
    }
}
