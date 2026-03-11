import java.util.HashMap;
import java.util.Map; 

abstract class Room{
    protected int numberofbeds;
    protected int squarefeet;
    protected double pricePerNight;

    // constructor used by child classes to initialize common room attributes
    public Room(int numberofbeds, int squarefeet, double pricePerNight){
        this.numberofbeds = numberofbeds;
        this.squarefeet = squarefeet;
        this.pricePerNight = pricePerNight;
    }

    void displayRoomDetails(){
        System.out.println("Beds: " + numberofbeds);
        System.out.println("Size: " + squarefeet + " sqft");
        System.out.println("Price per night: " + pricePerNight);
    }
}

class SingleRoom extends Room{
    public SingleRoom(){ 
        super(1,250,1500.0);
    }
}

class DoubleRoom extends Room{
    public DoubleRoom(){
        super(2,400,2500.0);
    }
}

class SuiteRoom extends Room{
    public SuiteRoom(){
        super(3,750,5000.0);
    }
}

class RoomInventory {

    /**
     * Stores available room count for each room type.
     * Key -> Room type name
     * Value -> Available room count
     */

    private Map<String, Integer> roomAvailability;

    /**
     * Constructor initializes the inventory
     * with default availability values.
     */

    public RoomInventory(){
        roomAvailability = new HashMap<>();
        initializeInventory();
    }

    /**
     * Initializes room availability data.
     * This method centralizes inventory setup
     * instead of using scattered variables.
     */

    private void initializeInventory(){
        roomAvailability.put("Single Room",5);
        roomAvailability.put("Double Room",3);
        roomAvailability.put("Suite Room",2);
    }

    /**
     * Returns the current availability map.
     *
     * @return map of room type to available count
     */

    public Map<String,Integer> getRoomAvailability(){
        return roomAvailability;
    }

    /**
     * Updates availability for a specific room type.
     *
     * @param roomType the room type to update
     * @param count new availability count
     */

    public void updateAvailability(String roomType, int count){
        roomAvailability.put(roomType,count);
    }
}

/**
 * CLASS RoomSearchService
 *
 * Use Case 4: Room Search & Availability Check
 *
 * Description:
 * This class provides search functionality
 * for guests to view available rooms.
 *
 * It reads room availability from inventory
 * and room details from Room objects.
 *
 * No inventory mutation or booking logic
 * is performed in this class.
 *
 * @veUseCase4RoomSearch
 */

class RoomSearchService {

    /**
     * Displays available rooms along with
     * their details and pricing.
     *
     * This method performs read-only access
     * to inventory and room data.
     *
     * @param inventory centralized room inventory
     * @param singleRoom single room definition
     * @param doubleRoom double room definition
     * @param suiteRoom suite room definition
     */

    public void searchAvailableRooms(
            RoomInventory inventory,
            Room singleRoom,
            Room doubleRoom,
            Room suiteRoom) {

        Map<String, Integer> availability = inventory.getRoomAvailability();

        System.out.println("Room Search");

        // Check and display Single Room availability
        if (availability.get("Single Room") > 0) {
            System.out.println("\nSingle Room:");
            singleRoom.displayRoomDetails();
            System.out.println("Available: " + availability.get("Single Room"));
        }

        // Check and display Double Room availability
        if (availability.get("Double Room") > 0) {
            System.out.println("\nDouble Room:");
            doubleRoom.displayRoomDetails();
            System.out.println("Available: " + availability.get("Double Room"));
        }

        // Check and display Suite Room availability
        if (availability.get("Suite Room") > 0) {
            System.out.println("\nSuite Room:");
            suiteRoom.displayRoomDetails();
            System.out.println("Available: " + availability.get("Suite Room"));
        }
    }
}

/**
 * MAIN CLASS UseCase4RoomSearch
 *
 * Use Case 4: Room Search & Availability Check
 *
 * Description:
 * This class demonstrates how guests
 * can view available rooms without
 * modifying inventory data.
 *
 * The system enforces read-only access
 * by design and usage discipline.
 *
 * @version 4.0
 */

public class UseCase4RoomSearch {

    /**
     * Application entry point.
     *
     * @param args Command-line arguments
     */

    public static void main(String[] args) {

        RoomInventory inventory = new RoomInventory();

        Room singleRoom = new SingleRoom();
        Room doubleRoom = new DoubleRoom();
        Room suiteRoom = new SuiteRoom();

        RoomSearchService searchService = new RoomSearchService();

        searchService.searchAvailableRooms(
                inventory,
                singleRoom,
                doubleRoom,
                suiteRoom
        );
    }
}