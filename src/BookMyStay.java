import java.util.*;

// Reservation Class
class Reservation {
    private String reservationId;
    private String guestName;
    private String roomType;
    private double basePrice;

    public Reservation(String reservationId, String guestName, String roomType, double basePrice) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
        this.basePrice = basePrice;
    }

    public String getReservationId() {
        return reservationId;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }

    public double getBasePrice() {
        return basePrice;
    }

    @Override
    public String toString() {
        return "Reservation ID: " + reservationId +
                ", Guest: " + guestName +
                ", Room: " + roomType +
                ", Price: ₹" + basePrice;
    }
}

// Booking History (stores confirmed bookings)
class BookingHistory {

    // List preserves insertion order
    private List<Reservation> history = new ArrayList<>();

    // Add confirmed booking
    public void addReservation(Reservation reservation) {
        history.add(reservation);
    }

    // Get all bookings
    public List<Reservation> getAllReservations() {
        return history;
    }

    // Display all bookings
    public void displayAll() {
        if (history.isEmpty()) {
            System.out.println("No bookings found.");
            return;
        }

        System.out.println("=== Booking History ===");
        for (Reservation r : history) {
            System.out.println(r);
        }
    }
}

// Report Service (separate from storage)
class BookingReportService {

    // Total revenue
    public double calculateTotalRevenue(List<Reservation> reservations) {
        double total = 0;
        for (Reservation r : reservations) {
            total += r.getBasePrice();
        }
        return total;
    }

    // Count bookings
    public int getTotalBookings(List<Reservation> reservations) {
        return reservations.size();
    }

    // Room type summary
    public void roomTypeSummary(List<Reservation> reservations) {
        Map<String, Integer> map = new HashMap<>();

        for (Reservation r : reservations) {
            map.put(r.getRoomType(), map.getOrDefault(r.getRoomType(), 0) + 1);
        }

        System.out.println("=== Room Type Summary ===");
        for (String type : map.keySet()) {
            System.out.println(type + ": " + map.get(type));
        }
    }

    // Generate full report
    public void generateReport(List<Reservation> reservations) {
        System.out.println("\n=== Booking Report ===");

        System.out.println("Total Bookings: " + getTotalBookings(reservations));
        System.out.println("Total Revenue: ₹" + calculateTotalRevenue(reservations));

        roomTypeSummary(reservations);
    }
}

// Main Class
public class Main {
    public static void main(String[] args) {

        BookingHistory history = new BookingHistory();
        BookingReportService reportService = new BookingReportService();

        // Simulate confirmed bookings (UC6 output)
        Reservation r1 = new Reservation("RES101", "Arush", "Deluxe", 3000);
        Reservation r2 = new Reservation("RES102", "Rahul", "Suite", 5000);
        Reservation r3 = new Reservation("RES103", "Priya", "Deluxe", 3000);

        // Add to history (IMPORTANT STEP)
        history.addReservation(r1);
        history.addReservation(r2);
        history.addReservation(r3);

        // Admin views history
        history.displayAll();

        // Admin generates report
        reportService.generateReport(history.getAllReservations());
    }
}