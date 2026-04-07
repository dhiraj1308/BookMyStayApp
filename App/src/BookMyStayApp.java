import java.util.*;

/**
 * ===========================================================================
 * MAIN CLASS - UseCase10BookingCancellation
 * ===========================================================================
 *
 * Use Case 10: Booking Cancellation & Inventory Rollback
 *
 * Description:
 * This class demonstrates how confirmed bookings can be cancelled safely.
 * Inventory is restored and rollback history is maintained using a Stack.
 *
 * @version 10.0
 */

// Supporting Class: Simulates the Inventory Management from previous cases
class RoomInventory {
    private Map<String, Integer> counts = new HashMap<>();

    public RoomInventory() {
        counts.put("Deluxe", 5);
        counts.put("Suite", 2);
    }

    public void updateInventory(String type, int change) {
        counts.put(type, counts.getOrDefault(type, 0) + change);
        System.out.println("Inventory Update: " + type + " is now " + counts.get(type));
    }
}

// Core Logic: The Cancellation Service
class CancellationService {
    private Stack<String> releasedRoomIds = new Stack<>(); // LIFO Rollback structure
    private Map<String, String> activeBookings = new HashMap<>(); // Tracks Reservation -> RoomType

    public void addBooking(String resId, String roomType) {
        activeBookings.put(resId, roomType);
    }

    public void cancelBooking(String resId, RoomInventory inventory) {
        System.out.println("\n--- Processing Cancellation: " + resId + " ---");

        // 1. Validation: Verify reservation existence before rollback
        if (!activeBookings.containsKey(resId)) {
            System.err.println("Failure: Cancellation rejected. Reservation " + resId + " not found.");
            return;
        }

        // 2. Identify and record the state for rollback
        String roomType = activeBookings.get(resId);
        releasedRoomIds.push(resId); // LIFO Rollback Logic

        // 3. Inventory Restoration: Increment count immediately
        inventory.updateInventory(roomType, 1);

        // 4. Controlled Mutation: Update history and remove from active records
        activeBookings.remove(resId);

        System.out.println("Success: System state restored consistently for " + resId);
    }

    public void showRollbackHistory() {
        System.out.println("\n--- Current Rollback History (LIFO) ---");
        if (releasedRoomIds.isEmpty()) {
            System.out.println("No cancellations recorded.");
        } else {
            // Stack displays most recent cancellation at the top
            for (int i = releasedRoomIds.size() - 1; i >= 0; i--) {
                System.out.println("Rollback Point [" + (releasedRoomIds.size() - i) + "]: " + releasedRoomIds.get(i));
            }
        }
    }
}

public class BookMyStayApp {

    /**
     * Application entry point.
     *
     * @param args Command-line arguments
     */
    public static void main(String[] args) {
        // Initialize System Components
        RoomInventory inventory = new RoomInventory();
        CancellationService cancellationService = new CancellationService();

        // Setup: Create some confirmed bookings
        System.out.println("Initial Setup: Registering Bookings...");
        cancellationService.addBooking("RES-001", "Deluxe");
        cancellationService.addBooking("RES-002", "Suite");
        cancellationService.addBooking("RES-003", "Deluxe");

        // --- Flow: Guest initiates cancellation requests ---

        // 1. Valid Cancellation
        cancellationService.cancelBooking("RES-003", inventory);

        // 2. Another Valid Cancellation (Models LIFO behavior)
        cancellationService.cancelBooking("RES-001", inventory);

        // 3. Invalid Cancellation (Safety check)
        cancellationService.cancelBooking("RES-999", inventory);

        // --- Final State Review ---
        cancellationService.showRollbackHistory();

        System.out.println("\n--- System remains stable and consistent ---");
    }
}