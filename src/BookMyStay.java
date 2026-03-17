import java.util.*;

// Booking Request
class BookingRequest {
    String guestName;
    String roomType;

    public BookingRequest(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }
}

// Shared Booking System
class BookingSystem {

    // Shared inventory
    private Map<String, Integer> inventory = new HashMap<>();

    // Shared booking queue
    private Queue<BookingRequest> queue = new LinkedList<>();

    public BookingSystem() {
        inventory.put("Deluxe", 1);   // Only 1 room to show conflict clearly
    }

    // Add request (multiple threads can call)
    public synchronized void addRequest(BookingRequest req) {
        queue.add(req);
    }

    // Get request safely
    public synchronized BookingRequest getRequest() {
        return queue.poll();
    }

    // CRITICAL SECTION (IMPORTANT)
    public synchronized void processBooking(BookingRequest req) {

        if (req == null) return;

        String type = req.roomType;

        if (inventory.get(type) > 0) {

            System.out.println(Thread.currentThread().getName()
                    + " is booking for " + req.guestName);

            // Simulate delay (causes race condition if not synchronized)
            try { Thread.sleep(100); } catch (Exception e) {}

            inventory.put(type, inventory.get(type) - 1);

            System.out.println("SUCCESS: Room booked for " + req.guestName);

        } else {
            System.out.println("FAILED: No rooms for " + req.guestName);
        }
    }

    public void displayInventory() {
        System.out.println("Final Inventory: " + inventory);
    }
}

// Worker Thread
class BookingProcessor extends Thread {

    private BookingSystem system;

    public BookingProcessor(BookingSystem system, String name) {
        super(name);
        this.system = system;
    }

    @Override
    public void run() {

        BookingRequest req;

        while ((req = system.getRequest()) != null) {
            system.processBooking(req);
        }
    }
}

// Main Class
public class Main {
    public static void main(String[] args) {

        BookingSystem system = new BookingSystem();

        // Simulate multiple guests booking at same time
        system.addRequest(new BookingRequest("Arush", "Deluxe"));
        system.addRequest(new BookingRequest("Rahul", "Deluxe"));
        system.addRequest(new BookingRequest("Priya", "Deluxe"));

        // Create multiple threads (concurrent users)
        BookingProcessor t1 = new BookingProcessor(system, "Thread-1");
        BookingProcessor t2 = new BookingProcessor(system, "Thread-2");

        // Start threads
        t1.start();
        t2.start();

        // Wait for threads to finish
        try {
            t1.join();
            t2.join();
        } catch (Exception e) {}

        // Final state
        system.displayInventory();
    }
}