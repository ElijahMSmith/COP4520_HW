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

    // Based on: https://en.wikipedia.org/wiki/Primality_test
    // Check number for divisibility by 2, 3, and all 6k +/- 1
    private boolean checkIsPrime(int val) {
        if (val <= 3)
            return val > 1;

        if (val % 2 == 0 || val % 3 == 0)
            return false;

        int cap = ((int) Math.sqrt(val)) + 1;

        for (int i = 5; i < cap; i += 6) {
            if (val % i == 0 || val % (i + 2) == 0)
                return false;
        }

        return true;
    }
}
