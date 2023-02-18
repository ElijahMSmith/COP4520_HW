import java.util.Queue;

public class GuestThread extends Thread {

    private static int ID_COUNTER = 0;
    private int id = 0;

    private final int DELAY_TO_QUEUE = 15;
    private final int MAX_TIME_IN = 30;
    private final double RUN_AGAIN_THRESHOLD = .5;

    private int timesVisited = 0;
    private boolean runAgain = true;
    private Queue<GuestThread> vaseRoom;

    public GuestThread(Queue<GuestThread> vaseRoom) {
        this.vaseRoom = vaseRoom;
        this.id = ID_COUNTER++;
    }

    public int id() {
        return id;
    }

    public int getTimesVisited() {
        return timesVisited;
    }

    /*
     * 
     * Choose a random offset for when they will next enter the room
     * Wait that long
     * Join the queue to enter the room and wait
     * When predecessor finished, will be notified to wake up
     * 
     * When entering the room, wait a random amount of time in the room
     * When leaving, notify the next guest thread waiting
     * Randomly decide to queue up again or to leave
     * 
     */

    @Override
    public void run() {

        while (runAgain) {
            // Wait some amount of time before adding to the queue
            int wait = (int) (Math.random() * DELAY_TO_QUEUE);

            try {
                sleep(wait);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Adds itself to the queue and checks if they are at the front
            boolean shouldWait = true;
            synchronized (vaseRoom) {
                shouldWait = !vaseRoom.isEmpty();
                vaseRoom.add(this);
            }

            // System.out.printf("Guest %d queueing up (new queue size = %d)\n", id,
            // vaseRoom.size());

            // If they aren't at the front, have them wait to be notified
            synchronized (this) {
                if (shouldWait) {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

            // System.out.printf("Guest %d entering room\n", id);

            // Visit the room for a random amount of time
            timesVisited++;
            int stay = (int) (Math.random() * MAX_TIME_IN);

            try {
                sleep(stay);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            GuestThread nextGuest;
            synchronized (vaseRoom) {
                // System.out.printf("Guest %d leaving room\n", id);
                // Remove itself from the queue
                vaseRoom.poll();
                nextGuest = vaseRoom.peek();
            }

            // Notify the next guest in line, if there is one and they are waiting
            if (nextGuest != null) {
                synchronized (nextGuest) {
                    nextGuest.notify();
                    // System.out.printf("Notifying guest %d\n", nextGuest.id);
                }
            }

            // Decide whether to re-queue after a delay
            runAgain = Math.random() < RUN_AGAIN_THRESHOLD;

            // System.out.printf("Guest %d requeing ? %b\n", id, runAgain);
        }
    }
}
