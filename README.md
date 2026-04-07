## Booking History and Reporting System (Use Case 8)
This project implements a simple Java-based reporting module for a hotel management system. It captures guest names and their selected room types, storing them in a chronological audit trail to generate a formatted history report.
------------------------------
## 📋 Features

* Ordered Storage: Uses ArrayList to ensure bookings are displayed in the exact order they were checked in.
* Formatted Reporting: Generates a clean, human-readable summary matching the specified documentation format.
* Object-Oriented Design: Separates the data structure (BookingRecord) from the reporting logic (BookingHistoryManager).

## 🛠️ Implementation Details
The system is built using Java 8+ and focuses on three core areas:

1. Data Capture: Initializing guest records (e.g., Abhi, Subha, Vanmathi).
2. Audit Trail: Maintaining a sequential list of all confirmed transactions.
3. Report Generation: Outputting a titled report with specific formatting: Guest: [Name], Room Type: [Type].

## 🚀 How to Run

1. Compile the code:

javac BookingHistoryManager.java

2. Execute the program:

java BookingHistoryManager


## 📄 Sample Output

Booking History and Reporting

Booking History Report
Guest: Abhi, Room Type: Single
Guest: Subha, Room Type: Double
Guest: Vanmathi, Room Type: Suite


