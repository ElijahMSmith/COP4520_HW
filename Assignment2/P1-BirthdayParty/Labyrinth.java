public class Labyrinth {
    boolean cupcakeReady = false;

    public void enter(GuestThread guest) {
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
        }

        if (guest instanceof SpecialGuestThread && !cupcakeReady) {
            SpecialGuestThread specialGuest = (SpecialGuestThread) guest;
            cupcakeReady = true;
            specialGuest.countNewEntrant();
        }
    }
}
