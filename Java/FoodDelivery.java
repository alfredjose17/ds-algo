import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class FoodDelivery {
    public static void main(String[] args) {
        DeliveryManager manager = new DeliveryManager();

        manager.addOrder(new Order("o1", "custA", "restX", 250, "DELIVERED"));
        manager.addOrder(new Order("o2", "custA", "restY", 100, "CANCELLED"));
        manager.addOrder(new Order("o3", "custB", "restX", 300, "DELIVERED"));
        manager.addOrder(new Order("o4", "custB", "restY", 150, "PLACED"));
        manager.addOrder(new Order("o5", "custC", "restX", 200, "DELIVERED"));
        manager.addOrder(new Order("o6", "custC", "restY", 400, "CANCELLED"));
        manager.addOrder(new Order("o7", "custA", "restX", 350, "DELIVERED"));

        // Q1 — Expected: 1100 (o1+o3+o5+o7 = 250+300+200+350)
        System.out.println("Total revenue: " + manager.getTotalRevenue());

        // Q2 — Expected: {custA=3, custB=2, custC=2}
        System.out.println("Orders per customer: " + manager.getOrderCountPerCustomer());
        // Expected: {restX=1100, restY=0} (only restX has DELIVERED orders)
        System.out.println("Revenue per restaurant: " + manager.getRevenuePerRestaurant());

        // Q3 — Expected: custA (250+350=600 vs custB=300 vs custC=200)
        System.out.println("Top customer: " + manager.getTopCustomerByRevenue());

        // Q4 — Expected: {restY=[o2, o6]}
        System.out.println("Cancelled by restaurant: " + manager.getCancelledOrdersByRestaurant());
    }
}

class Order {
    String orderId, customerId, restaurantId, status;
    int amount;

    public Order(String orderId, String customerId, String restaurantId,
                 int amount, String status) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.restaurantId = restaurantId;
        this.amount = amount;
        this.status = status;
    }

    public String getOrderId()      { return orderId; }
    public String getCustomerId()   { return customerId; }
    public String getRestaurantId() { return restaurantId; }
    public int getAmount()          { return amount; }
    public String getStatus()       { return status; }
}

class DeliveryManager {
    List<Order> orders = new ArrayList<>();

    public void addOrder(Order o) { orders.add(o); }

    // Q1 - BUG: string compared with == instead of .equals()
    public int getTotalRevenue() {
        int total = 0;
        for (Order o : orders) {
            if ("DELIVERED".equalsIgnoreCase(o.getStatus())) {
                total += o.getAmount();
            }
        }
        return total;
    }

    // Q2
    public Map<String, Integer> getOrderCountPerCustomer() {
        if (orders.isEmpty()) {
            return Collections.emptyMap();
        }

        return orders.stream()
                .filter(o -> o != null &&
                        o.getCustomerId() != null)
                .collect(Collectors.groupingBy(
                        Order::getCustomerId,
                        Collectors.collectingAndThen(Collectors.counting(), Long::intValue)
                ));
    }

    public Map<String, Integer> getRevenuePerRestaurant() {
        if (orders.isEmpty()) {
            return Collections.emptyMap();
        }

        return orders.stream()
                .filter(o -> o != null &&
                        o.getRestaurantId() != null)
                .collect(Collectors.groupingBy(
                        Order::getRestaurantId,
                        Collectors.summingInt(o -> {
                            if ("DELIVERED".equalsIgnoreCase(o.getStatus())) {
                                return o.getAmount();
                            }
                            else {
                                return 0;
                            }
                        })
                ));
    }

    // Q3
    public String getTopCustomerByRevenue() {
        if (orders.isEmpty()) {
            return "";
        }

        return orders.stream()
                .filter(o -> o != null &&
                        "DELIVERED".equalsIgnoreCase(o.getStatus()) &&
                        o.getCustomerId() != null)
                .collect(Collectors.groupingBy(
                    Order::getCustomerId,
                    Collectors.summingInt(Order::getAmount)
                ))
                .entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("");
    }

    // Q4
    public Map<String, List<String>> getCancelledOrdersByRestaurant() {
        if (orders.isEmpty()) {
            return Collections.emptyMap();
        }

        return orders.stream()
                .filter(o -> o != null &&
                        "CANCELLED".equalsIgnoreCase(o.getStatus()) &&
                        o.getRestaurantId() != null)
                .collect(Collectors.groupingBy(
                        Order::getRestaurantId,
                        Collectors.mapping(Order::getOrderId, Collectors.toList())
                ));
    }
}