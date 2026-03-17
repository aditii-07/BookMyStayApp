import java.io.*;
import java.util.*;

/**
 * Use Case 12: Data Persistence & System Recovery
 * @version 12.0
 */

public class UseCase12DataPersistenceRecovery {

    static class RoomInventory {

        private Map<String, Integer> rooms;

        public RoomInventory() {
            rooms = new HashMap<>();
            rooms.put("Single", 5);
            rooms.put("Double", 3);
            rooms.put("Suite", 2);
        }

        public void setRoom(String type, int count) {
            rooms.put(type, count);
        }

        public int getRoom(String type) {
            return rooms.getOrDefault(type, 0);
        }

        public Map<String, Integer> getAllRooms() {
            return rooms;
        }

        public void display() {
            System.out.println("Current Inventory:");
            System.out.println("Single: " + getRoom("Single"));
            System.out.println("Double: " + getRoom("Double"));
            System.out.println("Suite: " + getRoom("Suite"));
        }
    }

    static class FilePersistenceService {

        public void saveInventory(RoomInventory inventory, String filePath) {

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {

                for (Map.Entry<String, Integer> entry : inventory.getAllRooms().entrySet()) {
                    writer.write(entry.getKey() + "-" + entry.getValue());
                    writer.newLine();
                }

                System.out.println("Inventory saved successfully.");

            } catch (IOException e) {
                System.out.println("Error saving inventory.");
            }
        }

      
        public void loadInventory(RoomInventory inventory, String filePath) {

            File file = new File(filePath);

            if (!file.exists()) {
                System.out.println("No valid inventory data found. Starting fresh.");
                return;
            }

            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {

                String line;
                boolean validData = false;

                while ((line = reader.readLine()) != null) {

                    String[] parts = line.split("-");

                    if (parts.length == 2) {
                        String type = parts[0];
                        int count = Integer.parseInt(parts[1]);

                        inventory.setRoom(type, count);
                        validData = true;
                    }
                }

                if (!validData) {
                    System.out.println("No valid inventory data found. Starting fresh.");
                }

            } catch (IOException | NumberFormatException e) {
                System.out.println("Error loading inventory. Starting fresh.");
            }
        }
    }

    public static void main(String[] args) {

        System.out.println("System Recovery\n");

        String filePath = "inventory.txt";

        RoomInventory inventory = new RoomInventory();
        FilePersistenceService persistence = new FilePersistenceService();

        persistence.loadInventory(inventory, filePath);

        inventory.display();

        persistence.saveInventory(inventory, filePath);
    }
}