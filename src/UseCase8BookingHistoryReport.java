import java.util.*;

/**
 * Use Case 8: Booking History & Reporting
 *
 * Description:
 * This program demonstrates how confirmed bookings
 * are stored and reported.
 *
 * The system maintains an ordered audit trail
 * of reservations.
 *
 * @version 8.0
 */

public class UseCase8BookingHistoryReport {

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

    static class BookingHistory {

        private List<Reservation> confirmedReservations;

        public BookingHistory() {
            confirmedReservations = new ArrayList<>();
        }

        public void addReservation(Reservation reservation) {
            confirmedReservations.add(reservation);
        }

        public List<Reservation> getConfirmedReservations() {
            return confirmedReservations;
        }
    }

    static class BookingReportService {

        public void generateReport(BookingHistory history) {

            System.out.println("Booking History Report");

            for (Reservation r : history.getConfirmedReservations()) {
                System.out.println(
                    "Guest: " + r.getGuestName() +
                    ", Room Type: " + r.getRoomType()
                );
            }
        }
    }

    public static void main(String[] args) {

        System.out.println("Booking History and Reporting\n");

        // Create booking history
        BookingHistory history = new BookingHistory();

        // Add confirmed reservations
        history.addReservation(new Reservation("Abhi", "Single"));
        history.addReservation(new Reservation("Subha", "Double"));
        history.addReservation(new Reservation("Vanmathi", "Suite"));

        // Generate report
        BookingReportService reportService = new BookingReportService();
        reportService.generateReport(history);
    }
}