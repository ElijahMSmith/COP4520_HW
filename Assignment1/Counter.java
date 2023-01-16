import java.util.LinkedList;
import java.util.ListIterator;

public class Counter {

    public static final int MAX = 100000000;

    private int count = 2;
    private int primesFound = 0;
    private long sumPrimes = 0;

    // Store top 10 primes in descending order since most elements will fit on front
    // Reduces the amount of iterations required on average to find the fit location
    private LinkedList<Integer> lastTenDescending = new LinkedList<>();

    // Synchronized so only one thread ever gets current count value
    public synchronized int getNextCount() {
        int temp = count;
        count = count + 1;
        return temp;
    }

    // Add a newly-found prime to the top 10 and increment sums
    // Synchronized so lastTenDescending list is modified safely
    public synchronized void notePrime(int newPrime) {
        primesFound++;
        sumPrimes += newPrime;

        ListIterator<Integer> it = lastTenDescending.listIterator();
        boolean inserted = false;
        while (it.hasNext()) {
            int nextPrime = it.next();
            // Find the placement in the descending list
            if (newPrime > nextPrime) {
                // Insert it in front of the current element since it's greater
                if (it.hasPrevious()) {
                    // Not the first element in the list, .previous() works
                    it.previous();
                    it.add(newPrime);
                } else {
                    // First element in list, .previous() would error
                    lastTenDescending.addFirst(newPrime);
                }

                inserted = true;
                break;
            }
        }

        // If we made it through the list, it's the smallest and fits on the end
        if (!inserted)
            lastTenDescending.addLast(newPrime);

        // Keep it no more than 10 elements
        if (lastTenDescending.size() > 10)
            lastTenDescending.removeLast();
    }

    public void printResults(long executionTime) {
        System.out.println(executionTime + " " + primesFound + " " + sumPrimes);

        // Print top 10 in ascending order, the reverse order of the list
        while (!lastTenDescending.isEmpty())
            System.out.print(lastTenDescending.removeLast() + (!lastTenDescending.isEmpty() ? " " : ""));
    }
}
