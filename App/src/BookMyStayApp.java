import java.io.*;
import java.util.*;

/**
 * ===========================================================================
 * MAIN CLASS - UseCase12DataPersistenceRecovery
 * ===========================================================================
 *
 * Use Case 12: Data Persistence & System Recovery
 *
 * Description:
 * This class demonstrates how system state
 * can be restored after an application restart.
 *
 * Inventory data is loaded from a file
 * before any booking operations occur.
 *
 * @version 12.0
 */
public class BookMyStayApp {

    private static final String DATA_FILE = "inventory_state.txt";

    /**
     * Application entry point.
     *
     * @param args Command-line arguments
     */
    public static void main(String[] args) {
        System.out.println("--- System Initializing ---");

        // 1. Initialize System State
        RoomInventory inventory = new RoomInventory();
        PersistenceService persistence = new PersistenceService();

        // 2. System Startup Flow: Restore state from persistent storage
        // This transitions the app from in-memory only to a durable system.
        persistence.loadState(inventory, DATA_FILE);

        // 3. Display current state after recovery
        System.out.println("Current Inventory after recovery:");
        inventory.display();

        // 4. Simulate booking operation (Modify state)
        System.out.println("\nProcessing a new booking...");
        inventory.updateRoomCount("Deluxe", -1);

        // 5. System Shutdown Flow: Save current state to file
        // Ensures that critical data survives until the next restart.
        persistence.saveState(inventory, DATA_FILE);

        System.out.println("\n--- System Shutdown Complete ---");
    }
}

/**
 * Persistence Service handles the serialization and deserialization
 * of system data to a physical file.
 */
class PersistenceService {

    /**
     * Saves the inventory state to a file.
     * Uses Serialization concepts by converting Map data to text lines.
     */
    public void saveState(RoomInventory inventory, String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Map.Entry<String, Integer> entry : inventory.getRooms().entrySet()) {
                writer.write(entry.getKey() + ":" + entry.getValue());
                writer.newLine();
            }
            System.out.println("Success: System state persisted to " + filePath);
        } catch (IOException e) {
            System.err.println("Error: Failed to save system state. " + e.getMessage());
        }
    }

    /**
     * Loads the inventory state from a file.
     * Implements Failure Tolerance by checking if file exists.
     */
    public void loadState(RoomInventory inventory, String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            System.out.println("Notice: No persistence file found. Starting with default inventory.");
            inventory.initializeDefault();
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts.length == 2) {
                    inventory.setRoomCount(parts[0], Integer.parseInt(parts[1]));
                }
            }
            System.out.println("Success: System state restored from " + filePath);
        } catch (IOException | NumberFormatException e) {
            System.err.println("Warning: Could not recover state. Starting fresh. " + e.getMessage());
            inventory.initializeDefault();
        }
    }
}

/**
 * Represents the in-memory Room Inventory.
 */
class RoomInventory {
    private Map<String, Integer> rooms = new HashMap<>();

    public void initializeDefault() {
        rooms.put("Deluxe", 10);
        rooms.put("Suite", 5);
    }

    public void setRoomCount(String type, int count) { rooms.put(type, count); }

    public void updateRoomCount(String type, int change) {
        rooms.put(type, rooms.getOrDefault(type, 0) + change);
    }

    public Map<String, Integer> getRooms() { return rooms; }

    public void display() {
        rooms.forEach((type, count) -> System.out.println(" - " + type + ": " + count));
    }
}