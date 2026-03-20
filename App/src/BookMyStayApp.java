import java.util.HashMap;
import java.util.Map;

// 1. Removed 'public' so it can stay in this file
class Room {
    private String type;
    private double price;

    public Room(String type, double price) {
        this.type = type;
        this.price = price;
    }

    public String getRoomType() { return type; }
    public double getRoomPrice() { return price; }
}

// 2. Removed 'public'
class RoomInventory {
    private Map<String, Integer> inventory = new HashMap<>();

    public void updateAvailability(String type, int count) {
        inventory.put(type, count);
    }

    public Map<String, Integer> getRoomAvailability() {
        return inventory;
    }
}

// 3. Removed 'public'
class RoomSearchService {
    public void searchAvailableRooms(RoomInventory inventory, Room singleRoom, Room doubleRoom, Room suiteRoom) {
        Map<String, Integer> availability = inventory.getRoomAvailability();
        System.out.println("=== Available Rooms ===");

        if (availability.getOrDefault("Single", 0) > 0) {
            displayRoom(singleRoom, availability.get("Single"));
        }
        if (availability.getOrDefault("Double", 0) > 0) {
            displayRoom(doubleRoom, availability.get("Double"));
        }
        if (availability.getOrDefault("Suite", 0) > 0) {
            displayRoom(suiteRoom, availability.get("Suite"));
        }
    }

    private void displayRoom(Room room, int count) {
        System.out.println("Type: " + room.getRoomType() + " | Price: $" + room.getRoomPrice() + " | Available: " + count);
    }
}

// 4. This class is PUBLIC because it matches the filename 'BookMyStayApp.java'
public class BookMyStayApp {
    public static void main(String[] args) {
        // Setup Room Definitions
        Room single = new Room("Single Standard", 188.55);
        Room doubleR = new Room("Double Deluxe", 158.01);
        Room suite = new Room("Executive Suite", 300.01);

        // Setup Inventory
        RoomInventory inventory = new RoomInventory();
        inventory.updateAvailability("Single", 5);
        inventory.updateAvailability("Double", 2);
        inventory.updateAvailability("Suite", 0);

        // Execute Search
        RoomSearchService service = new RoomSearchService();
        service.searchAvailableRooms(inventory, single, doubleR, suite);
    }
}
