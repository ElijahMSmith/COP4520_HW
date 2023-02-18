import java.util.Scanner;

public class BirthdayParty {

    public static volatile boolean FINISHED = false;
    public static volatile int NUM_GUESTS;

    public static void main(String[] args) throws InterruptedException {
        Scanner sc = new Scanner(System.in);
        System.out.println("How many guests?");
        while (true) {
            try {
                NUM_GUESTS = sc.nextInt();
                if (NUM_GUESTS > 1)
                    break;
            } catch (Exception e) {
                System.out.println("Please provide a valid number of guests (>= 1).");
            }
        }
        sc.close();

        Labyrinth labyrinth = new Labyrinth();

        GuestThread[] allGuests = new GuestThread[NUM_GUESTS];
        for (int i = 0; i < NUM_GUESTS; i++)
            allGuests[i] = i == 0 ? new SpecialGuestThread(labyrinth) : new GuestThread(labyrinth);

        for (int i = 0; i < NUM_GUESTS; i++)
            allGuests[i].start();

        while (!FINISHED) {
            int random = (int) (Math.random() * NUM_GUESTS);
            //System.out.printf("%d\n", random);
            GuestThread thisGuest = allGuests[random];
            synchronized (thisGuest) {
                thisGuest.notify();
            }
        }

        System.out.println("--------------------------\nGuests now know everyone has entered the labyrinth!");
        for (int i = 0; i < NUM_GUESTS; i++)
            System.out.println("Guest " + i + " entered " + allGuests[i].getTimesEntered() + " times");

        System.exit(0);
    }
}