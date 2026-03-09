abstract class Room{
    protected int numberofbeds;
    protected int squarefeet;
    protected double pricePerNight;

    // constructor used by child classes to initialize common room attributes
    public Room(int numberofbeds, int squarefeet, double pricePerNight){
        this.numberofbeds= numberofbeds;
        this.squarefeet = squarefeet;
        this.pricePerNight = pricePerNight;
    }

    void displayRoomDetails(){
        System.out.println("beds: "+ numberofbeds);
        System.out.println("Size: "+ squarefeet);
        System.out.println("Price per night: "+pricePerNight);
        }
}

class SingleRoom extends Room{
    int available =5;

    public SingleRoom(){ super(1,250,1500.0);}

    @Override
    void displayRoomDetails() {
        System.out.println("single room:");
        super.displayRoomDetails();
        System.out.println("Available: "+available);
        System.out.println();

    }
}

class DoubleRoom extends Room{
    int available = 3;

    public DoubleRoom(){super(2, 400, 2500.0);}

    @Override
    void displayRoomDetails() {
        System.out.println("Double room:");
        super.displayRoomDetails();
        System.out.println("Available :"+available);
        System.out.println();

    }
}

class SuiteRoom extends Room{
    int available=2;
    public SuiteRoom(){super(3, 750, 5000.0);}

    @Override
    void displayRoomDetails() {
        System.out.println("Suite room:");
        super.displayRoomDetails();
        System.out.println("Available: "+available);
        System.out.println();
    }
}


public class BookMyStay {

    public static void main(String[] args){
        System.out.println("Hotel Room Initialization");
        System.out.println();

        SingleRoom s = new SingleRoom();
        DoubleRoom d = new DoubleRoom();
        SuiteRoom su = new SuiteRoom();

        s.displayRoomDetails();
        d.displayRoomDetails();
        su.displayRoomDetails();
    }
}
