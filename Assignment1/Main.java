import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) throws FileNotFoundException, InterruptedException {
        long startTime = System.currentTimeMillis();
        Counter counter = new Counter();

        ExecutorService es = Executors.newCachedThreadPool();
        for (int i = 0; i < 8; i++)
            es.execute(new Worker(counter));

        es.shutdown();
        es.awaitTermination(1, TimeUnit.MINUTES);

        System.setOut(new PrintStream(new File("primes.txt")));
        counter.printResults(System.currentTimeMillis() - startTime);
    }

}
