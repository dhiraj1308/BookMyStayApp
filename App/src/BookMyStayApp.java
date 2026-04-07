/**
 * =====================================================================
 * PROJECT: Hotel Booking System - Use Case 8
 * =====================================================================
 * This program implements the "Booking History and Reporting"
 * requirements shown in the documentation image.
 *
 * CORE FEATURES:
 * 1. Ordered Storage: Maintains the sequence of confirmed bookings.
 * 2. Formatted Output: Generates a human-readable summary report.
 * 3. Separation of Concerns: Uses a dedicated class for booking records.
 */

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a single confirmed reservation record.
 */
class BookingRecord {
    // Fields to store guest details and room selection
    private String guestName;
    private String roomType;

    /**
     * Constructor to initialize a new booking record.
     * @param guestName The name of the guest
     * @param roomType The category of room (Single, Double, Suite)
     */
    public BookingRecord(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    /**
     * Overriding toString to match the exact report format in the image:
     * "Guest: [Name], Room Type: [Type]"
     */
    @Override
    public String toString() {
        return "Guest: " + guestName + ", Room Type: " + roomType;
    }
}

/**
 * Main application class to manage and report booking history.
 */
public class BookMyStayApp {

    /**
     * Entry point of the application.
     */
    public static void main(String[] args) {

        // --- 1. DATA COLLECTION PHASE ---
        // Using an ArrayList to maintain the "Audit Trail" (chronological order)
        List<BookingRecord> history = new ArrayList<>();

        // Adding the specific data points from the image
        history.add(new BookingRecord("Abhi", "Single"));
        history.add(new BookingRecord("Subha", "Double"));
        history.add(new BookingRecord("Vanmathi", "Suite"));

        // --- 2. REPORT GENERATION PHASE ---
        // Printing the primary system header
        System.out.println("Booking History and Reporting");

        // Printing a line break for visual clarity as seen in the layout
        System.out.println();

        // Printing the specific report title
        System.out.println("Booking History Report");

        // --- 3. OUTPUT PHASE ---
        // Iterating through the history list to display each record
        for (BookingRecord record : history) {
            System.out.println(record);
        }
    }
}
