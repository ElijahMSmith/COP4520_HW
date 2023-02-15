public class GuestThread extends Thread {

    private static int ID_COUNTER = 0;

    private boolean hasEaten = false;
    private int timesEntered = 0;
    public final int id;

    public Labyrinth labyrinth;

    public GuestThread(Labyrinth labyrinth) {
        this.labyrinth = labyrinth;
        this.id = ID_COUNTER++;
    }

    @Override
    public void run() {
        while (true) {
            synchronized (this) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    return;
                }
                labyrinth.enter(this);
                timesEntered++;
            }

        }
    }

    public boolean hasEaten() {
        return hasEaten;
    }

    public void eatCupcake() {
        this.hasEaten = true;
    }

    public int getTimesEntered() {
        return timesEntered;
    }
}
