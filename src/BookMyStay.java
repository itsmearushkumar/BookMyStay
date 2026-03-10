import java.util.HashMap;

abstract class Room3 {
    int beds;
    int size;
    double price;

    Room3(int beds, int size, double price) {
        this.beds = beds;
        this.size = size;
        this.price = price;
    }

    void display(String type, int available) {
        System.out.println(type + " Room:");
        System.out.println("Beds: " + beds);
        System.out.println("Size: " + size + " sqft");
        System.out.println("Price per night: " + price);
        System.out.println("Available Rooms: " + available);
        System.out.println();
    }
}

class SingleRoom3 extends Room3 {
    SingleRoom3() {
        super(1, 250, 1500.0);
    }
}

class DoubleRoom3 extends Room3 {
    DoubleRoom3() {
        super(2, 400, 2500.0);
    }
}

class SuiteRoom3 extends Room3 {
    SuiteRoom3() {
        super(3, 750, 5000.0);
    }
}

class RoomInventory3 {
    HashMap<String, Integer> inventory = new HashMap<>();

    RoomInventory3() {
        inventory.put("Single", 5);
        inventory.put("Double", 3);
        inventory.put("Suite", 2);
    }

    int getAvailability(String type) {
        return inventory.get(type);
    }
}

public class BookMyStay {

    public static void main(String[] args) {

        System.out.println("Hotel Room Inventory Status\n");

        RoomInventory3 inventory = new RoomInventory3();

        Room3 single = new SingleRoom3();
        Room3 doubleRoom = new DoubleRoom3();
        Room3 suite = new SuiteRoom3();

        single.display("Single", inventory.getAvailability("Single"));
        doubleRoom.display("Double", inventory.getAvailability("Double"));
        suite.display("Suite", inventory.getAvailability("Suite"));
    }
}