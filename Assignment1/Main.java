import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) throws FileNotFoundException, InterruptedException {
        Counter counter = new Counter();

        ExecutorService es = Executors.newCachedThreadPool();
        for (int i = 0; i < 8; i++)
            es.execute(new Worker(counter));

        long startTime = System.currentTimeMillis();

        es.shutdown();
        es.awaitTermination(1, TimeUnit.MINUTES);

        long endTime = System.currentTimeMillis();
        
        System.setOut(new PrintStream(new File("primes.txt")));
        counter.printResults(endTime - startTime);
    }

}
