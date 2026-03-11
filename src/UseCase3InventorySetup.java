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
 * MAIN CLASS UseCase3InventorySetup
 *
 * Use Case 3: Centralized Room Inventory Management
 *
 * Description:
 * This class demonstrates how room availability
 * is managed using a centralized inventory.
 *
 * Room objects are used to retrieve pricing
 * and room characteristics.
 *
 * No booking or search logic is introduced here.
 */

class UseCase3InventorySetup {

    public static void main(String[] args){

        
        // Application entry point.


        System.out.println("Hotel Room Inventory Status");
        System.out.println();

        RoomInventory inventory = new RoomInventory();

        SingleRoom s = new SingleRoom();
        DoubleRoom d = new DoubleRoom();
        SuiteRoom su = new SuiteRoom();

        System.out.println("Single Room:");
        s.displayRoomDetails();
        System.out.println("Available Rooms: " + inventory.getRoomAvailability().get("Single Room"));
        System.out.println();

        System.out.println("Double Room:");
        d.displayRoomDetails();
        System.out.println("Available Rooms: " + inventory.getRoomAvailability().get("Double Room"));
        System.out.println();

        System.out.println("Suite Room:");
        su.displayRoomDetails();
        System.out.println("Available Rooms: " + inventory.getRoomAvailability().get("Suite Room"));
    }
}