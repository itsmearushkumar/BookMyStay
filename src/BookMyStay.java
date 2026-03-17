import java.util.*;

// Reservation Class
class Reservation {
    private String reservationId;
    private String guestName;
    private String roomType;
    private String roomId;
    private boolean isCancelled;

    public Reservation(String reservationId, String guestName, String roomType, String roomId) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
        this.roomId = roomId;
        this.isCancelled = false;
    }

    public String getReservationId() {
        return reservationId;
    }

    public String getRoomType() {
        return roomType;
    }

    public String getRoomId() {
        return roomId;
    }

    public boolean isCancelled() {
        return isCancelled;
    }

    public void cancel() {
        isCancelled = true;
    }

    @Override
    public String toString() {
        return "Reservation ID: " + reservationId +
                ", Guest: " + guestName +
                ", RoomType: " + roomType +
                ", RoomID: " + roomId +
                ", Status: " + (isCancelled ? "CANCELLED" : "CONFIRMED");
    }
}

// Booking System
class BookingSystem {

    private Map<String, Integer> inventory = new HashMap<>();
    private Map<String, Reservation> bookings = new HashMap<>();

    // Stack for rollback (LIFO)
    private Stack<String> releasedRooms = new Stack<>();

    public BookingSystem() {
        inventory.put("Standard", 2);
        inventory.put("Deluxe", 1);
        inventory.put("Suite", 1);
    }

    // Booking
    public Reservation bookRoom(String resId, String name, String type) {

        if (!inventory.containsKey(type) || inventory.get(type) <= 0) {
            System.out.println("Booking Failed for " + resId);
            return null;
        }

        // Generate simple room ID
        String roomId = type.substring(0, 2).toUpperCase() + inventory.get(type);

        inventory.put(type, inventory.get(type) - 1);

        Reservation r = new Reservation(resId, name, type, roomId);
        bookings.put(resId, r);

        System.out.println("Booked: " + r);
        return r;
    }

    // Cancellation Service
    public void cancelBooking(String reservationId) {

        // Step 1: Validate existence
        if (!bookings.containsKey(reservationId)) {
            System.out.println("Cancellation Failed: Reservation not found.");
            return;
        }

        Reservation r = bookings.get(reservationId);

        // Step 2: Check already cancelled
        if (r.isCancelled()) {
            System.out.println("Cancellation Failed: Already cancelled.");
            return;
        }

        // Step 3: Record room for rollback (STACK)
        releasedRooms.push(r.getRoomId());

        // Step 4: Restore inventory
        inventory.put(r.getRoomType(), inventory.get(r.getRoomType()) + 1);

        // Step 5: Mark cancelled
        r.cancel();

        // Step 6: Update history (we keep it but mark cancelled)
        System.out.println("Cancellation Successful: " + r);
    }

    // Display inventory
    public void displayInventory() {
        System.out.println("\nInventory:");
        for (String type : inventory.keySet()) {
            System.out.println(type + " -> " + inventory.get(type));
        }
    }

    // Show rollback stack
    public void showRollbackStack() {
        System.out.println("\nRollback Stack (Recently Released Rooms): " + releasedRooms);
    }
}

// Main Class
public class Main {
    public static void main(String[] args) {

        BookingSystem system = new BookingSystem();

        // Step 1: Book rooms
        system.bookRoom("RES101", "Arush", "Deluxe");
        system.bookRoom("RES102", "Rahul", "Standard");

        // Step 2: Cancel booking
        system.cancelBooking("RES101");

        // Step 3: Try invalid cancellation
        system.cancelBooking("RES999");

        // Step 4: Duplicate cancellation
        system.cancelBooking("RES101");

        // Step 5: Check system state
        system.displayInventory();
        system.showRollbackStack();
    }
}