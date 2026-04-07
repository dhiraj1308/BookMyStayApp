Use Case 11: Concurrent Booking Simulation
Goal: Demonstrate how the system maintains integrity and prevents "double-booking" when multiple guests attempt to book rooms at the exact same time.
Overview
In a real-world scenario, a booking system isn't used by one person at a time. Hundreds of requests may arrive simultaneously. Without proper Thread Safety, the system could suffer from Race Conditions, where two users are assigned the same room because the system hasn't finished updating the inventory for the first user before processing the second.
Key Concepts Implemented
Thread Safety: Ensuring shared resources (Inventory and Queue) behave correctly when accessed by multiple threads.
Critical Sections: Using the synchronized keyword to protect blocks of code that modify shared data.
Shared Mutable State: Managing the RoomInventory and BookingRequestQueue across different worker threads (t1 and t2).
Atomicity: Ensuring the "Check Availability -> Decrement Inventory" process happens as a single, uninterrupted operation.
System Architecture
Component	Responsibility
RoomInventory	The Shared Resource. Contains a synchronized method to safely decrement room counts.
BookingRequestQueue	A synchronized list of guest names waiting for a room.
AllocationService	Contains the core logic for matching a guest to a room.
ConcurrentBookingProcessor	Implements Runnable. This is the "Worker" that runs on a background thread to process the queue.
UseCase11ThreadSafety	The Driver Class. It initializes threads, starts them, and uses .join() to synchronize the final report.
How to Run
File Name: Ensure your file is named UseCase11ThreadSafety.java.
Compile:
bash
javac UseCase11ThreadSafety.java
Use code with caution.

Execute:
bash
java UseCase11ThreadSafety
Use code with caution.


Expected Behavior
When you run this simulation with 2 rooms and 5 guests:
Thread-01 and Thread-02 will start simultaneously.
They will "compete" to pull guests from the queue.
Only the first two guests to reach the allocateRoom logic will succeed.
The remaining three guests will be safely denied because the inventory count is protected.
The final inventory count will be 0 (never negative).
Benefits of this Design
Scalability: The system can now handle multiple processors working in parallel.
Reliability: Eliminates the risk of "Double Booking," which is critical for business reputation.
Predictability: The system state remains consistent regardless of how many threads are added.