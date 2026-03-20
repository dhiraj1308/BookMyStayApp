Overview
This project provides a Java-based solution for managing optional services (such as Spa, Breakfast, or Airport Pickups) associated with confirmed hotel reservations. It allows for the dynamic attachment of multiple services to a single booking ID without impacting room inventory.
Key Features
Service Encapsulation: Defines a Service class to store specific service names and their associated costs.
Centralized Management: Uses an AddOnServiceManager to map unique Reservation IDs to a collection of selected services using a HashMap.
Cost Calculation: Automatically aggregates the total cost of all add-ons linked to a specific reservation.
Scalability: Supports adding an unlimited number of services to any confirmed booking.
Project Structure
Service: The data model representing an individual add-on.
AddOnServiceManager: The logic layer that handles storage and cost summation.
UseCase7AddOnServiceSelection: The entry point that demonstrates the system using sample data (e.g., Reservation "Single-1" with a total cost of 1500.0).
How to Run
Compile all classes using a Java compiler (e.g., javac UseCase7AddOnServiceSelection.java).
Run the main class to view the Selection Summary in the console