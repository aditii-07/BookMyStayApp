import java.util.*;

/**
 * Use Case 10: Booking Cancellation & Inventory Rollback
 * @version 10.0
 */

public class UseCase10BookingCancellation {

    static class RoomInventory {

        private Map<String, Integer> rooms;

        public RoomInventory() {
            rooms = new HashMap<>();
            rooms.put("Single", 1);
            rooms.put("Double", 1);
            rooms.put("Suite", 1);
        }

        public void increaseRoom(String roomType) {
            rooms.put(roomType, rooms.getOrDefault(roomType, 0) + 1);
        }

        public int getAvailableRooms(String roomType) {
            return rooms.getOrDefault(roomType, 0);
        }
    }

    static class CancellationService {

        private Stack<String> releasedRoomIds;

        private Map<String, String> reservationRoomTypeMap;

        public CancellationService() {
            releasedRoomIds = new Stack<>();
            reservationRoomTypeMap = new HashMap<>();
        }

        public void registerBooking(String reservationId, String roomType) {
            reservationRoomTypeMap.put(reservationId, roomType);
        }

        public void cancelBooking(String reservationId, RoomInventory inventory) {

            if (!reservationRoomTypeMap.containsKey(reservationId)) {
                System.out.println("Invalid cancellation: Reservation not found.");
                return;
            }

            String roomType = reservationRoomTypeMap.get(reservationId);

            inventory.increaseRoom(roomType);

            releasedRoomIds.push(reservationId);

            reservationRoomTypeMap.remove(reservationId);

            System.out.println("Booking cancelled successfully. Inventory restored for room type: " + roomType);
        }

        public void showRollbackHistory() {
            System.out.println("Rollback History (Most Recent First):");

            while (!releasedRoomIds.isEmpty()) {
                System.out.println("Released Reservation ID: " + releasedRoomIds.pop());
            }
        }
    }

    public static void main(String[] args) {

        System.out.println("Booking Cancellation\n");

        RoomInventory inventory = new RoomInventory();
        CancellationService service = new CancellationService();

        String reservationId = "Single-1";
        service.registerBooking(reservationId, "Single");

        service.cancelBooking(reservationId, inventory);

        service.showRollbackHistory();

        System.out.println("Updated Single Room Availability: "
                + inventory.getAvailableRooms("Single"));
    }
}