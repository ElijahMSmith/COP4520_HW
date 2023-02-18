public class Labyrinth {
    boolean cupcakeReady = false;

    public synchronized void enter(GuestThread guest) {
        // System.out.printf("Guest %d entering with hasEaten = %b and cupcakeReady =
        // %b\n", guest.id, guest.hasEaten(),
        // cupcakeReady);
        // if (guest instanceof SpecialGuestThread)
        // System.out.printf("\tSpecial guest knows %d guests have entered total\n",
        // ((SpecialGuestThread) guest).getTotalEntrants());

        if (guest.hasEaten() && !(guest instanceof SpecialGuestThread))
            return;

        if (cupcakeReady && !guest.hasEaten()) {
            guest.eatCupcake();
            cupcakeReady = false;
            // System.out.printf("\tGuest %d just ate the cupcake!\n", guest.id);
        }

        if (guest instanceof SpecialGuestThread && !cupcakeReady) {
            SpecialGuestThread specialGuest = (SpecialGuestThread) guest;
            cupcakeReady = true;
            specialGuest.countNewEntrant(guest.id);
        }
    }
}
