Use Case 9: Error Handling & Validation
Goal: Strengthen system reliability by introducing structured validation and error handling, ensuring that invalid inputs and inconsistent states are detected and handled early.
Key Concepts Implemented
Fail-Fast Design: The system detects errors at the earliest possible point (during input validation) and stops further processing to avoid cascading failures.
Custom Exceptions: Uses the domain-specific InvalidBookingException to provide clear, human-readable error causes rather than generic system crashes.
Guarding System State: Validation checks are performed before any inventory updates or queue additions, ensuring the system remains in a consistent state.
Graceful Failure Handling: Errors are caught and communicated clearly to the user via the try-catch block, allowing the application to remain stable and continue running.
Components
Class	Responsibility
InvalidBookingException	Custom exception class for booking-specific errors.
RoomInventory	Manages room types and availability counts.
ReservationValidator	The "Guard" logic that validates input against system rules.
BookingRequestQueue	Processes the booking only after it passes validation.
BookingSystemApp	The main entry point that coordinates the Actor (Guest) and the System.
How to Run
Save the Code: Copy the provided code into a file named BookingSystemApp.java.
Compile:
javac BookingSystemApp.java
Execute:
java BookingSystemApp 
Example Scenarios
Scenario 1: Success (Happy Path)
Input: Guest: "Alice", Room: "Deluxe"
Result: Validation passes, and the booking is added to the queue.
Scenario 2: Invalid Room Type
Input: Guest: "Bob", Room: "Penthouse"
Result: InvalidBookingException is thrown: "Invalid room type: Penthouse". System state is preserved.
Scenario 3: No Availability
Input: Guest: "Charlie", Room: "Suite"
Result: InvalidBookingException is thrown: "No availability for room type: Suite". (Note: Suite count starts at 0).
Benefits of this Approach
No Silent Failures: Errors are loud and clear, making debugging significantly easier.
Data Integrity: Prevents the inventory from ever reaching negative values or corrupted states.
User Experience: Provides immediate, helpful feedback to the guest when an input error occurs.