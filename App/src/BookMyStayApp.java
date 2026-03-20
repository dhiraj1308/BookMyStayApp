import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * =====================================================================
 * CLASS - Service
 * =====================================================================
 *
 * Use Case 7: Add-On Service Selection
 *
 * Description:
 * This class represents an optional service
 * that can be added to a confirmed reservation.
 *
 * @version 7.0
 */
class Service {
    /** Name of the service. */
    private String serviceName;

    /** Cost of the service. */
    private double cost;

    /**
     * Creates a new add-on service.
     *
     * @param serviceName name of the service
     * @param cost cost of the service
     */
    public Service(String serviceName, double cost) {
        this.serviceName = serviceName;
        this.cost = cost;
    }

    /** @return service name */
    public String getServiceName() {
        return serviceName;
    }

    /** @return service cost */
    public double getCost() {
        return cost;
    }
}

/**
 * =====================================================================
 * CLASS - AddOnServiceManager
 * =====================================================================
 *
 * Description:
 * This class manages optional services associated with confirmed reservations.
 *
 * @version 7.0
 */
class AddOnServiceManager {
    /**
     * Maps reservation ID to selected services.
     * Key -> Reservation ID
     * Value -> List of selected services
     */
    private Map<String, List<Service>> servicesByReservation;

    /** Initializes the service manager. */
    public AddOnServiceManager() {
        servicesByReservation = new HashMap<>();
    }

    /**
     * Attaches a service to a reservation.
     *
     * @param reservationId confirmed reservation ID
     * @param service add-on service
     */
    public void addService(String reservationId, Service service) {
        servicesByReservation.computeIfAbsent(reservationId, k -> new ArrayList<>());
        servicesByReservation.get(reservationId).add(service);
    }

    /**
     * Calculates total add-on cost for a reservation.
     *
     * @param reservationId reservation ID
     * @return total service cost
     */
    public double calculateTotalServiceCost(String reservationId) {
        List<Service> services = servicesByReservation.get(reservationId);
        if (services == null) return 0.0;

        double total = 0.0;
        for (Service s : services) {
            total += s.getCost();
        }
        return total;
    }
}

/**
 * =====================================================================
 * MAIN CLASS - UseCase7AddOnServiceSelection
 * =====================================================================
 *
 * Description:
 * This class demonstrates how optional services can be attached
 * to a confirmed booking.
 *
 * @version 7.0
 */
public class BookMyStayApp {

    /**
     * Application entry point.
     *
     * @param args Command-line arguments
     */
    public static void main(String[] args) {
        // Initialize manager
        AddOnServiceManager manager = new AddOnServiceManager();

        // Target Reservation ID from requirements
        String resId = "Single-1";

        // Adding services that sum up to 1500.0
        manager.addService(resId, new Service("Spa", 1000.0));
        manager.addService(resId, new Service("Breakfast", 500.0));

        // Calculate total
        double totalCost = manager.calculateTotalServiceCost(resId);

        // Print final output
        System.out.println("Add-On Service Selection");
        System.out.println("Reservation ID: " + resId);
        System.out.println("Total Add-On Cost: " + totalCost);
    }
}
