public class SpecialGuestThread extends GuestThread {
    private int totalEntrants = 0;

    public SpecialGuestThread(Labyrinth labyrinth) {
        super(labyrinth);
    }

    public int getTotalEntrants() {
        return totalEntrants;
    }

    public void countNewEntrant(int id) {
        totalEntrants++;
        // System.out.printf("Special Guest has now noticed %d unique guests enter\n",
        // totalEntrants);
        if (totalEntrants == BirthdayParty.NUM_GUESTS)
            BirthdayParty.FINISHED = true;
    }

}
