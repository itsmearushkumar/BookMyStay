import java.util.*;

// Custom Exception (VERY IMPORTANT)
class InvalidBookingException extends Exception {
    public InvalidBookingException(String message) {
        super(message);
    }
}

// Reservation Class
class Reservation {
    private String reservationId;
    private String guestName;
    private String roomType;

    public Reservation(String reservationId, String guestName, String roomType) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
    }

    @Override
    public String toString() {
        return "Reservation ID: " + reservationId +
                ", Guest: " + guestName +
                ", Room: " + roomType;
    }
}

// Booking System with Validation
class BookingSystem {

    // Available rooms
    private Map<String, Integer> inventory = new HashMap<>();

    public BookingSystem() {
        inventory.put("Standard", 2);
        inventory.put("Deluxe", 1);
        inventory.put("Suite", 1);
    }

    // Validate booking (FAIL-FAST)
    private void validate(String roomType) throws InvalidBookingException {

        // Check if room type exists
        if (!inventory.containsKey(roomType)) {
            throw new InvalidBookingException("Invalid room type: " + roomType);
        }

        // Check availability
        if (inventory.get(roomType) <= 0) {
            throw new InvalidBookingException("No rooms available for: " + roomType);
        }
    }

    // Book room
    public Reservation bookRoom(String reservationId, String guestName, String roomType)
            throws InvalidBookingException {

        // Step 1: Validate first (IMPORTANT)
        validate(roomType);

        // Step 2: Safe state update
        inventory.put(roomType, inventory.get(roomType) - 1);

        // Step 3: Create reservation
        return new Reservation(reservationId, guestName, roomType);
    }

    // Display inventory
    public void displayInventory() {
        System.out.println("Current Inventory:");
        for (String type : inventory.keySet()) {
            System.out.println(type + " -> " + inventory.get(type));
        }
    }
}

// Main Class
public class Main {
    public static void main(String[] args) {

        BookingSystem system = new BookingSystem();

        try {
            // Valid booking
            Reservation r1 = system.bookRoom("RES101", "Arush", "Deluxe");
            System.out.println("Booking Success: " + r1);

            // Invalid room type
            Reservation r2 = system.bookRoom("RES102", "Rahul", "Luxury");
            System.out.println(r2);

        } catch (InvalidBookingException e) {
            // Graceful failure handling
            System.out.println("Booking Failed: " + e.getMessage());
        }

        try {
            // Exhaust inventory
            system.bookRoom("RES103", "Priya", "Deluxe");

        } catch (InvalidBookingException e) {
            System.out.println("Booking Failed: " + e.getMessage());
        }

        // System still running safely
        system.displayInventory();
    }
}