import java.io.*;
import java.util.*;

// Reservation must be Serializable
class Reservation implements Serializable {
    private static final long serialVersionUID = 1L;

    String reservationId;
    String guestName;
    String roomType;

    public Reservation(String id, String name, String type) {
        this.reservationId = id;
        this.guestName = name;
        this.roomType = type;
    }

    @Override
    public String toString() {
        return reservationId + " | " + guestName + " | " + roomType;
    }
}

// Wrapper class to store full system state
class SystemState implements Serializable {
    private static final long serialVersionUID = 1L;

    Map<String, Integer> inventory;
    List<Reservation> bookings;

    public SystemState(Map<String, Integer> inventory, List<Reservation> bookings) {
        this.inventory = inventory;
        this.bookings = bookings;
    }
}

// Persistence Service
class PersistenceService {

    private static final String FILE_NAME = "system_state.dat";

    // SAVE (Serialization)
    public static void save(SystemState state) {
        try (ObjectOutputStream oos =
                     new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {

            oos.writeObject(state);
            System.out.println("System state saved successfully.");

        } catch (IOException e) {
            System.out.println("Error saving state: " + e.getMessage());
        }
    }

    // LOAD (Deserialization)
    public static SystemState load() {
        try (ObjectInputStream ois =
                     new ObjectInputStream(new FileInputStream(FILE_NAME))) {

            SystemState state = (SystemState) ois.readObject();
            System.out.println("System state loaded successfully.");
            return state;

        } catch (FileNotFoundException e) {
            System.out.println("No previous data found. Starting fresh.");
        } catch (Exception e) {
            System.out.println("Error loading state. Starting safely.");
        }

        return null;
    }
}

// Booking System
class BookingSystem {

    Map<String, Integer> inventory = new HashMap<>();
    List<Reservation> bookings = new ArrayList<>();

    public BookingSystem() {
        // Default inventory
        inventory.put("Standard", 2);
        inventory.put("Deluxe", 1);
    }

    // Restore state
    public void restore(SystemState state) {
        if (state != null) {
            this.inventory = state.inventory;
            this.bookings = state.bookings;
        }
    }

    // Book room
    public void book(String id, String name, String type) {
        if (!inventory.containsKey(type) || inventory.get(type) <= 0) {
            System.out.println("Booking failed for " + name);
            return;
        }

        inventory.put(type, inventory.get(type) - 1);
        Reservation r = new Reservation(id, name, type);
        bookings.add(r);

        System.out.println("Booked: " + r);
    }

    // Display state
    public void display() {
        System.out.println("\n--- Inventory ---");
        System.out.println(inventory);

        System.out.println("\n--- Bookings ---");
        for (Reservation r : bookings) {
            System.out.println(r);
        }
    }

    // Create snapshot
    public SystemState getState() {
        return new SystemState(inventory, bookings);
    }
}

// Main Class
public class Main {
    public static void main(String[] args) {

        BookingSystem system = new BookingSystem();

        // STEP 1: Load previous state
        SystemState loadedState = PersistenceService.load();
        system.restore(loadedState);

        // STEP 2: Continue operations
        system.book("RES101", "Arush", "Deluxe");
        system.book("RES102", "Rahul", "Standard");

        // STEP 3: Show current state
        system.display();

        // STEP 4: Save before exit
        PersistenceService.save(system.getState());
    }
}