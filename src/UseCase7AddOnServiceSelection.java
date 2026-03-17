import java.util.*;

/**
 * Use Case 7: Add-On Service Selection
 *
 * Description:
 * This program demonstrates how optional services
 * can be attached to a confirmed reservation.
 *
 * Services are added after room allocation
 * and do not affect inventory.
 *
 * @version 7.0
 */

public class UseCase7AddOnServiceSelection {

    static class Service {

        private String serviceName;
        private double cost;
        public Service(String serviceName, double cost) {
            this.serviceName = serviceName;
            this.cost = cost;
        }

        public String getServiceName() {
            return serviceName;
        }

        public double getCost() {
            return cost;
        }
    }

    static class AddonServiceManager {

        private Map<String, List<Service>> servicesByReservation;
        public AddonServiceManager() {
            servicesByReservation = new HashMap<>();
        }

        public void addService(String reservationId, Service service) {

            servicesByReservation.putIfAbsent(reservationId, new ArrayList<>());

            servicesByReservation.get(reservationId).add(service);
        }

        public double calculateTotalServiceCost(String reservationId) {

            double total = 0;

            List<Service> services = servicesByReservation.get(reservationId);

            if (services != null) {
                for (Service s : services) {
                    total += s.getCost();
                }
            }

            return total;
        }
    }
    public static void main(String[] args) {

        System.out.println("Add-On Service Selection\n");

        AddonServiceManager manager = new AddonServiceManager();

        String reservationId = "Single-1";

        Service s1 = new Service("Breakfast", 500);
        Service s2 = new Service("Spa", 700);
        Service s3 = new Service("Airport Pickup", 300);

        manager.addService(reservationId, s1);
        manager.addService(reservationId, s2);
        manager.addService(reservationId, s3);

        double totalCost = manager.calculateTotalServiceCost(reservationId);

        System.out.println("Reservation ID: " + reservationId);
        System.out.println("Total Add-On Cost: " + totalCost);
    }
}