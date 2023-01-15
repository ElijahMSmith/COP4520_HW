public class Worker implements Runnable {
    private Counter counter;

    public Worker(Counter counter) {
        this.counter = counter;
    }

    @Override
    public void run() {
        int next = counter.getNextCount();
        while (next <= Counter.MAX) {
            if (checkIsPrime(next))
                counter.notePrime(next);
            next = counter.getNextCount();
        }
    }

    private boolean checkIsPrime(int val) {
        int sqrt = (int) Math.sqrt(val);
        for (int i = 2; i <= sqrt; i++)
            if (val % i == 0)
                return false;
        return true;
    }
}
