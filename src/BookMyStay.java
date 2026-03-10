import java.util.HashMap;

abstract class Room4 {
    int beds;
    int size;
    double price;

    Room4(int beds, int size, double price) {
        this.beds = beds;
        this.size = size;
        this.price = price;
    }

    void display(String type, int available) {
        System.out.println(type + " Room:");
        System.out.println("Beds: " + beds);
        System.out.println("Size: " + size + " sqft");
        System.out.println("Price per night: " + price);
        System.out.println("Available: " + available);
        System.out.println();
    }
}

class SingleRoom4 extends Room4 {
    SingleRoom4() { super(1, 250, 1500.0); }
}

class DoubleRoom4 extends Room4 {
    DoubleRoom4() { super(2, 400, 2500.0); }
}

class SuiteRoom4 extends Room4 {
    SuiteRoom4() { super(3, 750, 5000.0); }
}

class RoomInventory4 {
    HashMap<String, Integer> inventory = new HashMap<>();

    RoomInventory4() {
        inventory.put("Single", 5);
        inventory.put("Double", 3);
        inventory.put("Suite", 2);
    }

    int getAvailability(String type) {
        return inventory.getOrDefault(type, 0);
    }
}

public class BookMyStay {
    public static void main(String[] args) {

        System.out.println("Room Search\n");

        RoomInventory4 inventory = new RoomInventory4();

        Room4 single = new SingleRoom4();
        Room4 doubleRoom = new DoubleRoom4();
        Room4 suite = new SuiteRoom4();

        single.display("Single", inventory.getAvailability("Single"));
        doubleRoom.display("Double", inventory.getAvailability("Double"));
        suite.display("Suite", inventory.getAvailability("Suite"));
    }
}