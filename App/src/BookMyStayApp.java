import java.util.*;

// ==========================================
// DATA MODELS
// ==========================================

class Reservation {
    private String guestName;
    private String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() { return guestName; }
    public String getRoomType() { return roomType; }
}

class RoomInventory {
    // Conceptual method to update inventory as per Use Case requirements
    public void updateInventory(String roomType, int change) {
        // In a real system, this would decrement available counts in a database
    }
}

// ==========================================
// SERVICE LAYER
// ==========================================

class RoomAllocationService {
    // Stores all allocated room IDs to prevent duplicate assignments.
    private Set<String> allocatedRoomIds;

    // Stores assigned room IDs by room type (Key -> Room Type, Value -> Set of IDs)
    private Map<String, Set<String>> assignedRoomsByType;

    /**
     * Initializes allocation tracking structures.
     */
    public RoomAllocationService() {
        this.allocatedRoomIds = new HashSet<>();
        this.assignedRoomsByType = new HashMap<>();
    }

    /**
     * Confirms a booking request by assigning a unique room ID and updating inventory.
     */
    public void allocateRoom(Reservation reservation, RoomInventory inventory) {
        String type = reservation.getRoomType();

        // Generate unique ID (e.g., Single-1)
        String roomId = generateRoomId(type);

        // Track the allocation to prevent double-booking
        allocatedRoomIds.add(roomId);
        assignedRoomsByType.putIfAbsent(type, new HashSet<>());
        assignedRoomsByType.get(type).add(roomId);

        // Update inventory immediately
        inventory.updateInventory(type, -1);

        // Print output in the specific format requested
        System.out.println("Booking confirmed for Guest: " + reservation.getGuestName() +
                ", Room ID: " + roomId);
    }

    /**
     * Generates a unique room ID based on the room type and current count.
     */
    private String generateRoomId(String roomType) {
        // Get the current count for this specific room type
        int count = assignedRoomsByType.getOrDefault(roomType, new HashSet<>()).size() + 1;
        String newId = roomType + "-" + count;

        // Safety check: if ID somehow exists, increment until unique
        while (allocatedRoomIds.contains(newId)) {
            count++;
            newId = roomType + "-" + count;
        }
        return newId;
    }
}

// ==========================================
// MAIN EXECUTION CLASS
// ==========================================

public class BookMyStayApp {

    public static void main(String[] args) {
        System.out.println("Room Allocation Processing");

        // 1. Initialize Service and Inventory
        RoomAllocationService service = new RoomAllocationService();
        RoomInventory inventory = new RoomInventory();

        // 2. Setup FIFO Queue for booking requests
        Queue<Reservation> bookingRequests = new LinkedList<>();

        // 3. Add requests in order (Abhi, Subha, Vanmathi)
        bookingRequests.add(new Reservation("Abhi", "Single"));
        bookingRequests.add(new Reservation("Subha", "Single"));
        bookingRequests.add(new Reservation("Vanmathi", "Suite"));

        // 4. Process the queue (First-In, First-Out)
        while (!bookingRequests.isEmpty()) {
            Reservation request = bookingRequests.poll();
            service.allocateRoom(request, inventory);
        }
    }
}
