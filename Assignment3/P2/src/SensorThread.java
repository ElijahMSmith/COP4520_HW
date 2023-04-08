package src;

public class SensorThread extends Thread {
    int[][] log;
    TempList hottest;
    TempList coldest;

    public final int ID;
    private static int ID_COUNTER = 0;

    public SensorThread(int[][] log, TempList hottest, TempList coldest) {
        this.log = log;
        this.hottest = hottest;
        this.coldest = coldest;

        this.ID = ID_COUNTER;
        ID_COUNTER++;
    }

    @Override
    public void run() {
        for (int min = 0; min < 60; min++) {
            // -100F to 70F
            int randTemp = (int) (Math.random() * 171 - 100);
            log[ID][min] = randTemp;
            hottest.offerTemp(randTemp);
            coldest.offerTemp(randTemp);
        }
    }
}
