package src;

public class TempList {
    private boolean hottest;
    private int[] list;

    int size = 0;
    int MAX_SIZE = 5;

    public TempList(boolean hottest) {
        this.hottest = hottest;
        list = new int[5];
    }

    public synchronized void offerTemp(int reading) {
        // Find insertion location
        int index = 0;
        while (index < size && ((!hottest && list[index] < reading) || (hottest && list[index] > reading)))
            index++;

        // If past the end, not part of top 5
        if (index >= 5)
            return;

        // Inc size if not already full
        if (size < 5)
            size++;

        // Insert and bump all other readings down
        int prev = reading;
        while (index < 5) {
            int temp = list[index];
            list[index] = prev;
            prev = temp;
            index++;
        }
    }

    @Override
    public String toString() {
        StringBuilder build = new StringBuilder();
        for (int i = 0; i < size; i++)
            build.append(list[i] + " ");
        return build.toString().trim();
    }
}
