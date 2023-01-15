import java.util.LinkedList;
import java.util.Queue;

public class Counter {

    public static final int MAX = 100000000;

    private int count = 2;
    private int primesFound = 0;
    private long sumPrimes = 0;

    private Queue<Integer> lastTen = new LinkedList<>();

    public synchronized int getNextCount() {
        int temp = count;
        count = count + 1;
        return temp;
    }

    public synchronized void notePrime(int newPrime) {
        primesFound++;
        sumPrimes += newPrime;
        lastTen.add(newPrime);
        if (lastTen.size() > 10)
            lastTen.poll();
    }

    public void printResults(long executionTime) {
        System.out.println(executionTime + " " + primesFound + " " + sumPrimes);
        while (!lastTen.isEmpty())
            System.out.print(lastTen.poll() + (!lastTen.isEmpty() ? " " : ""));
    }
}
