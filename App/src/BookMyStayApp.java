import java.util.*;

/**
 * ===========================================================================
 * MAIN CLASS - UseCase11ThreadSafety
 * ===========================================================================
 *
 * Use Case 11: Concurrent Booking Simulation
 *
 * This implementation demonstrates how to handle multiple guest requests
 * simultaneously without corrupting the room inventory.
 */

// 1. SHARED RESOURCE: The Room Inventory
class RoomInventory {
    // Shared mutable state: Only 2 rooms available for this simulation
    private int deluxeRooms = 2;

    /**
     * Critical Section: The 'synchronized' keyword ensures that only one
     * thread can execute this method at a time, preventing Race Conditions.
     */
    public synchronized boolean allocateRoom(String guestName) {
        if (deluxeRooms > 0) {
            System.out.println("[SYSTEM] " + Thread.currentThread().getName() + " found availability for: " + guestName);

            // Simulate brief processing time to emphasize concurrent behavior
            try { Thread.sleep(100); } catch (InterruptedException e) {}

            deluxeRooms--; // Decrement shared state safely
            return true;
        }
        return false; // No rooms left
    }

    public int getRemainingCount() { return deluxeRooms; }
}

// 2. SHARED RESOURCE: The Booking Queue
class BookingRequestQueue {
    private final Queue<String> queue = new LinkedList<>();

    // Add requests to the shared queue
    public synchronized void addRequest(String guestName) {
        queue.add(guestName);
    }

    // Thread-safe method to retrieve the next guest in line
    public synchronized String getNextRequest() {
        return queue.poll();
    }
}

// 3. SERVICE: The Allocation Logic
class AllocationService {
    public void process(String guest, RoomInventory inventory) {
        if (inventory.allocateRoom(guest)) {
            System.out.println("✅ SUCCESS: Room allocated to " + guest);
        } else {
            System.out.println("❌ FAILED: No rooms left for " + guest);
        }
    }
}

// 4. THE THREADED PROCESSOR (Runnable Task)
class ConcurrentBookingProcessor implements Runnable {
    private BookingRequestQueue bookingQueue;
    private RoomInventory inventory;
    private AllocationService allocationService;

    public ConcurrentBookingProcessor(BookingRequestQueue bq, RoomInventory inv, AllocationService as) {
        this.bookingQueue = bq;
        this.inventory = inv;
        this.allocationService = as;
    }

    @Override
    public void run() {
        String guest;
        // Keep processing while there are guests in the shared queue
        while ((guest = bookingQueue.getNextRequest()) != null) {
            allocationService.process(guest, inventory);
        }
    }
}

// 5. APPLICATION ENTRY POINT
public class BookMyStayApp {
    public static void main(String[] args) {
        System.out.println("--- Starting Concurrent Booking Simulation (2 Rooms Available) ---");

        // Step 1: Initialize shared resources
        RoomInventory inventory = new RoomInventory();
        BookingRequestQueue bookingQueue = new BookingRequestQueue();
        AllocationService allocationService = new AllocationService();

        // Step 2: Populate the queue with 5 concurrent guest requests
        String[] guests = {"Alice", "Bob", "Charlie", "David", "Eve"};
        for (String guest : guests) {
            bookingQueue.addRequest(guest);
        }

        // --- START OF CODE FROM SNAPSHOT ---

        // Create booking processor tasks (Worker Threads)
        Thread t1 = new Thread(
                new ConcurrentBookingProcessor(
                        bookingQueue, inventory, allocationService
                ), "Processor-Thread-1"
        );

        Thread t2 = new Thread(
                new ConcurrentBookingProcessor(
                        bookingQueue, inventory, allocationService
                ), "Processor-Thread-2"
        );

        // Start concurrent processing
        t1.start();
        t2.start();

        try {
            // Main thread waits for both workers to finish before proceeding
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            System.out.println("Thread execution interrupted.");
        }

        // --- END OF CODE FROM SNAPSHOT ---

        // Final State Review
        System.out.println("\n--- Final System State ---");
        System.out.println("Remaining Rooms: " + inventory.getRemainingCount());
        System.out.println("Simulation complete. No double-bookings occurred.");
    }
}