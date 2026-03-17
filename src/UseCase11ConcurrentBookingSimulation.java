import java.util.*;

/**
 * Use Case 11: Concurrent Booking Simulation
 * @version 11.0
 */

public class UseCase11ConcurrentBookingSimulation {

    static class Reservation {
        private String guestName;
        private String roomType;

        public Reservation(String guestName, String roomType) {
            this.guestName = guestName;
            this.roomType = roomType;
        }

        public String getGuestName() { return guestName; }
        public String getRoomType() { return roomType; }
    }

    static class BookingRequestQueue {
        private Queue<Reservation> queue = new LinkedList<>();

        public void addRequest(Reservation r) {
            queue.offer(r);
        }

        public Reservation getNextRequest() {
            return queue.poll();
        }

        public boolean hasPendingRequests() {
            return !queue.isEmpty();
        }
    }

    static class RoomInventory {

        private Map<String, Integer> rooms = new HashMap<>();

        public RoomInventory() {
            rooms.put("Single", 2);
            rooms.put("Double", 1);
            rooms.put("Suite", 1);
        }

        public boolean allocate(String roomType) {
            int count = rooms.getOrDefault(roomType, 0);
            if (count > 0) {
                rooms.put(roomType, count - 1);
                return true;
            }
            return false;
        }

        public int getAvailable(String roomType) {
            return rooms.getOrDefault(roomType, 0);
        }

        public void display() {
            System.out.println("Remaining Inventory:");
            System.out.println("Single: " + getAvailable("Single"));
            System.out.println("Double: " + getAvailable("Double"));
            System.out.println("Suite: " + getAvailable("Suite"));
        }
    }

    static class RoomAllocationService {

        private Map<String, Integer> counters = new HashMap<>();

        public RoomAllocationService() {
            counters.put("Single", 0);
            counters.put("Double", 0);
            counters.put("Suite", 0);
        }

        public void allocateRoom(Reservation r, RoomInventory inventory) {

            if (inventory.allocate(r.getRoomType())) {

                int id = counters.get(r.getRoomType()) + 1;
                counters.put(r.getRoomType(), id);

                String roomId = r.getRoomType() + "-" + id;

                System.out.println(
                    "Booking confirmed for Guest: "
                    + r.getGuestName()
                    + ", Room ID: "
                    + roomId
                );
            }
        }
    }

    static class ConcurrentBookingProcessor implements Runnable {

        private BookingRequestQueue bookingQueue;
        private RoomInventory inventory;
        private RoomAllocationService allocationService;

        public ConcurrentBookingProcessor(
                BookingRequestQueue bookingQueue,
                RoomInventory inventory,
                RoomAllocationService allocationService
        ) {
            this.bookingQueue = bookingQueue;
            this.inventory = inventory;
            this.allocationService = allocationService;
        }

@Override
public void run() {

    while (true) {

        Reservation reservation;

        synchronized (bookingQueue) {
            if (!bookingQueue.hasPendingRequests()) {
                break;
            }
            reservation = bookingQueue.getNextRequest();
        }

        if (reservation == null) continue;

        synchronized (allocationService) {
            allocationService.allocateRoom(reservation, inventory);
        }
    }
}
    }

    public static void main(String[] args) {

        System.out.println("Concurrent Booking Simulation\n");

        BookingRequestQueue bookingQueue = new BookingRequestQueue();
        RoomInventory inventory = new RoomInventory();
        RoomAllocationService allocationService = new RoomAllocationService();

        bookingQueue.addRequest(new Reservation("Abhi", "Single"));
        bookingQueue.addRequest(new Reservation("Vanmathi", "Double"));
        bookingQueue.addRequest(new Reservation("Kural", "Suite"));
        bookingQueue.addRequest(new Reservation("Subha", "Single"));

        Thread t1 = new Thread(
                new ConcurrentBookingProcessor(bookingQueue, inventory, allocationService)
        );

        Thread t2 = new Thread(
                new ConcurrentBookingProcessor(bookingQueue, inventory, allocationService)
        );

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            System.out.println("Thread execution interrupted.");
        }

        inventory.display();
    }
}