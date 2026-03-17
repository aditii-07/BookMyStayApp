import java.util.*;

/**
 * Use Case 9: Error Handling & Validation
 * @version 9.0
 */

public class UseCase9ErrorHandlingValidation {

    // ==========================
    // Custom Exception
    // ==========================
    static class InvalidBookingException extends Exception {
        public InvalidBookingException(String message) {
            super(message);
        }
    }

    // ==========================
    // Reservation (from UC5)
    // ==========================
    static class Reservation {
        private String guestName;
        private String roomType;

        public Reservation(String guestName, String roomType) {
            this.guestName = guestName;
            this.roomType = roomType;
        }

        public String getGuestName() {
            return guestName;
        }

        public String getRoomType() {
            return roomType;
        }
    }

    // ==========================
    // BookingRequestQueue (from UC5)
    // ==========================
    static class BookingRequestQueue {
        private Queue<Reservation> queue;

        public BookingRequestQueue() {
            queue = new LinkedList<>();
        }

        public void addRequest(Reservation r) {
            queue.offer(r);
        }
    }

    // ==========================
    // RoomInventory (Required)
    // ==========================
    static class RoomInventory {

        private Set<String> validRoomTypes;

        public RoomInventory() {
            validRoomTypes = new HashSet<>();
            validRoomTypes.add("Single");
            validRoomTypes.add("Double");
            validRoomTypes.add("Suite");
        }

        public boolean isValidRoomType(String roomType) {
            return validRoomTypes.contains(roomType); // STRICT (case-sensitive)
        }
    }

    // ==========================
    // ReservationValidator
    // ==========================
    static class ReservationValidator {

        public void validate(
                String guestName,
                String roomType,
                RoomInventory inventory
        ) throws InvalidBookingException {

            if (guestName == null || guestName.trim().isEmpty()) {
                throw new InvalidBookingException("Guest name cannot be empty.");
            }

            // STRICT CASE-SENSITIVE CHECK
            if (!inventory.isValidRoomType(roomType)) {
                throw new InvalidBookingException("Invalid room type selected.");
            }
        }
    }

    // ==========================
    // Main Method
    // ==========================
    public static void main(String[] args) {

        System.out.println("Booking Validation");

        Scanner scanner = new Scanner(System.in);

        RoomInventory inventory = new RoomInventory();
        ReservationValidator validator = new ReservationValidator();
        BookingRequestQueue bookingQueue = new BookingRequestQueue();

        try {
            // Input
            System.out.print("Enter guest name: ");
            String guestName = scanner.nextLine();

            System.out.print("Enter room type (Single/Double/Suite): ");
            String roomType = scanner.nextLine();

            // NO normalization → STRICT behavior
            validator.validate(guestName, roomType, inventory);

            // If valid → create reservation and add to queue
            Reservation reservation = new Reservation(guestName, roomType);
            bookingQueue.addRequest(reservation);

            System.out.println("Booking request added successfully.");

        } catch (InvalidBookingException e) {
            System.out.println("Booking failed: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }
}