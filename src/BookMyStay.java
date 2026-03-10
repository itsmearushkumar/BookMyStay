import java.util.*;

// Represents a guest's booking request
class Reservation6 {
    String guestName;
    String roomType;

    Reservation6(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }
}

// Manages room inventory and allocation
class RoomInventory6 {
    HashMap<String, Integer> inventory = new HashMap<>();
    HashMap<String, Set<String>> allocatedRooms = new HashMap<>();

    RoomInventory6() {
        // Initial inventory counts
        inventory.put("Single", 2);
        inventory.put("Double", 2);
        inventory.put("Suite", 1);

        // Initialize allocated room sets
        allocatedRooms.put("Single", new HashSet<>());
        allocatedRooms.put("Double", new HashSet<>());
        allocatedRooms.put("Suite", new HashSet<>());
    }

    // Allocate a room if available
    String allocateRoom(String roomType) {
        int available = inventory.getOrDefault(roomType, 0);
        if (available <= 0) {
            return null; // No rooms available
        }

        Set<String> allocated = allocatedRooms.get(roomType);
        int roomNumber = 1;

        // Generate next unique room ID
        while (allocated.contains(roomType + "-" + roomNumber)) {
            roomNumber++;
        }

        String roomID = roomType + "-" + roomNumber;
        allocated.add(roomID);

        // Decrement inventory
        inventory.put(roomType, available - 1);

        return roomID;
    }
}

public class BookMyStay {

    public static void main(String[] args) {

        System.out.println("Room Allocation Processing\n");

        // Booking request queue (FIFO)
        Queue<Reservation6> bookingQueue = new LinkedList<>();
        bookingQueue.add(new Reservation6("Abhi", "Single"));
        bookingQueue.add(new Reservation6("Subha", "Single"));
        bookingQueue.add(new Reservation6("Vanmathi", "Suite"));

        RoomInventory6 inventory = new RoomInventory6();

        // Process each booking request
        while (!bookingQueue.isEmpty()) {
            Reservation6 request = bookingQueue.poll();
            String allocatedRoom = inventory.allocateRoom(request.roomType);
            if (allocatedRoom != null) {
                System.out.println("Booking confirmed for Guest: " + request.guestName
                        + ", Room ID: " + allocatedRoom);
            } else {
                System.out.println("No available room for Guest: " + request.guestName
                        + ", Room Type: " + request.roomType);
            }
        }
    }
}