import java.util.Scanner;
import java.util.HashMap;
import java.util.Map;

/**
 * ===========================================================================
 * USE CASE 9: ERROR HANDLING & VALIDATION
 * ===========================================================================
 * This code demonstrates:
 * 1. Fail-Fast Design (Detecting errors early)
 * 2. Guarding System State (Preventing invalid inventory changes)
 * 3. Custom Exceptions (Clear, domain-specific error reporting)
 */

// 1. CUSTOM EXCEPTION: Represents domain-specific booking errors
class InvalidBookingException extends Exception {
    public InvalidBookingException(String message) {
        super(message);
    }
}

// 2. ROOM INVENTORY: Stores current state of rooms
class RoomInventory {
    private Map<String, Integer> inventory = new HashMap<>();

    public RoomInventory() {
        // Initial setup: 2 Deluxe rooms, 0 Suites (to test "No Availability")
        inventory.put("Deluxe", 2);
        inventory.put("Suite", 0);
    }

    public boolean isValidRoomType(String type) {
        return inventory.containsKey(type);
    }

    public int getAvailableCount(String type) {
        return inventory.getOrDefault(type, 0);
    }
}

// 3. BOOKING QUEUE: Handles successful requests
class BookingRequestQueue {
    public void addRequest(String guest, String room) {
        System.out.println("Processing... [Queueing request for " + guest + " in " + room + "]");
    }
}

// 4. RESERVATION VALIDATOR: The "Guard" that prevents invalid states
class ReservationValidator {
    public void validate(String guestName, String roomType, RoomInventory inventory)
            throws InvalidBookingException {

        // Check for empty inputs (Input Validation)
        if (guestName == null || guestName.trim().isEmpty()) {
            throw new InvalidBookingException("Guest name cannot be empty.");
        }

        // Check if the room type even exists (System Constraints)
        if (!inventory.isValidRoomType(roomType)) {
            throw new InvalidBookingException("Invalid room type: " + roomType);
        }

        // Check for availability (Guarding System State)
        if (inventory.getAvailableCount(roomType) <= 0) {
            throw new InvalidBookingException("No availability for room type: " + roomType);
        }

        // If logic reaches here, state is valid!
    }
}

// 5. MAIN APPLICATION CLASS
public class BookMyStayApp {

    public static void main(String[] args) {
        // Display application header
        System.out.println("--- Hotel Booking Validation System ---");

        Scanner scanner = new Scanner(System.in);

        // Initialize required components
        RoomInventory inventory = new RoomInventory();
        ReservationValidator validator = new ReservationValidator();
        BookingRequestQueue bookingQueue = new BookingRequestQueue();

        try {
            // Collect input from the Guest (Actor)
            System.out.print("Enter Guest Name: ");
            String guestName = scanner.nextLine();

            System.out.print("Enter Room Type (Deluxe/Suite): ");
            String roomType = scanner.nextLine();

            // STEP 1: Structured Validation (Fail-Fast Design)
            // The validator throws an exception if ANYTHING is wrong.
            validator.validate(guestName, roomType, inventory);

            // STEP 2: Logic processing (Only occurs if validation passes)
            bookingQueue.addRequest(guestName, roomType);
            System.out.println("SUCCESS: Your booking has been processed safely.");

        } catch (InvalidBookingException e) {
            // STEP 3: Handle domain-specific validation errors (Graceful Failure)
            // Instead of crashing, we show a meaningful failure message.
            System.out.println("\n[!] BOOKING FAILED: " + e.getMessage());
            System.out.println("Status: The system prevented an invalid state change.");

        } catch (Exception e) {
            // Catch-all for any unexpected system errors
            System.out.println("Unexpected Error: " + e.getMessage());

        } finally {
            // STEP 4: Cleanup resources
            scanner.close();
            System.out.println("--- Session Closed ---");
        }
    }
}