import java.util.LinkedList;
import java.util.Queue;

// Represents a guest's booking request
class Reservation {
    String guestName;
    String roomType;

    Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    void process() {
        System.out.println("Processing booking for Guest: " + guestName + ", Room Type: " + roomType);
    }
}

public class BookMyStay {

    public static void main(String[] args) {

        System.out.println("Booking Request Queue\n");

        // Create a queue to hold booking requests
        Queue<Reservation> bookingQueue = new LinkedList<>();

        // Simulate guest booking requests
        bookingQueue.add(new Reservation("Abhi", "Single"));
        bookingQueue.add(new Reservation("Subha", "Double"));
        bookingQueue.add(new Reservation("Vanmathi", "Suite"));

        // Process requests in FIFO order
        while (!bookingQueue.isEmpty()) {
            Reservation request = bookingQueue.poll(); // removes from head
            request.process();
        }
    }
}