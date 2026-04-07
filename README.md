This README document outlines the implementation of Use Case 12, focusing on how the "Book My Stay" application transitions from a temporary, in-memory tool to a durable, stateful system.
------------------------------
## Use Case 12: Data Persistence & System Recovery
Goal: Ensure that critical system data (Room Inventory and Booking History) survives application restarts by storing it in a durable physical file.
## Key Concepts Implemented

* Stateful Design: Unlike previous versions where data reset to defaults every time you clicked "Run," this version "remembers" exactly how many rooms were left from the last session.
* Serialization: The process of converting the in-memory HashMap of rooms into a structured text format (RoomType:Count) that can be written to a disk.
* Deserialization: The recovery process where the system reads the text file during startup and reconstructs the HashMap to its exact previous state.
* Failure Tolerance: The system is designed to handle "First Runs" (where no data file exists yet) or corrupted files gracefully by falling back to default values instead of crashing.

------------------------------
## System Architecture

| Component | Responsibility |
|---|---|
| UseCase12DataPersistenceRecovery | The Main Entry Point. Orchestrates the Load -> Process -> Save lifecycle. |
| PersistenceService | The Data Handler. Manages all file I/O operations (FileRead, FileWrite). |
| RoomInventory | The Data Model. Holds the live count of rooms currently in memory. |
| inventory_state.txt | The Persistent Store. A simple text file that acts as the system's "memory." |

------------------------------
## How to Run & Test Recovery

1. Compile and Run the application for the first time.
* Result: The system won't find a file, will load 10 Deluxe rooms, process a booking, and save 9 to the file.
2. Close the Program.
3. Run the application again.
* Result: The system will detect inventory_state.txt, load 9 Deluxe rooms, and decrement it to 8.
4. Verification: Check the inventory_state.txt file in your project folder to see the raw data being stored.

------------------------------
## Benefits of this Approach

* Durable Operations: Real-world hotel systems cannot afford to lose bookings if the power goes out; this use case provides that safety net.
* Production Alignment: This implementation mimics how professional software interacts with databases, providing a smooth conceptual bridge for future learning.
* Auditability: Because the data is stored in a plain-text file, system administrators can easily audit or manually fix inventory if needed.

------------------------------