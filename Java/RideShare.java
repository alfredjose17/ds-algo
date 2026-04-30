import java.util.*;
import java.util.stream.Collectors;

public class RideShare {
    public static void main(String[] args) {

        RideManager manager = new RideManager();

        manager.addRide(new Ride(1, 101, 1, 10, 5));   // duration 9
        manager.addRide(new Ride(1, 102, 12, 20, 8));  // duration 8
        manager.addRide(new Ride(2, 103, 2, 15, 10));  // duration 13
        manager.addRide(new Ride(2, 104, 16, 25, 6));  // duration 9
        manager.addRide(new Ride(3, 105, 5, 30, 20));  // duration 25

        // Q1
        System.out.println("Total Distance Driver 1: " + manager.getTotalDistance(1));
        // Expected: 13

        // Q2
        System.out.println("Average Ride Duration Per Driver: " + manager.getAverageRideDurationPerDriver());
        // Expected: {1=8.5, 2=11.0, 3=25.0}

        // Q3
        System.out.println("Inactive Drivers: " + manager.getInactiveDrivers(40, 10));
        // Expected: [1, 2]  (last ride too old)

        // Q4
        System.out.println("Top 2 Drivers by Distance: " + manager.getTopKDriversByDistance(2));
        // Expected: [3, 2]
    }
}

// ------------------ MODEL ------------------

class Ride {
    int driverId;
    int riderId;
    int startTime;
    int endTime;
    int distance;

    public Ride(int driverId, int riderId, int startTime, int endTime, int distance) {
        this.driverId = driverId;
        this.riderId = riderId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.distance = distance;
    }

    public int getDriverId() { return driverId; }
    public int getStartTime() { return startTime; }
    public int getEndTime() { return endTime; }
    public int getDistance() { return distance; }

    public int getDuration() {
        return endTime - startTime;
    }
}

// ------------------ MANAGER ------------------

class RideManager {
    List<Ride> rides = new ArrayList<>();

    public void addRide(Ride r) {
        rides.add(r);
    }

    // Q1: BUG - incorrect distance calculation
    public int getTotalDistance(int driverId) {
        int total = 0;
        for (Ride r : rides) {
            if (r.getDriverId() == driverId) {
                total += r.getDistance();
            }
        }
        return total;
    }

    // Q2: IMPLEMENT
    // driverId -> average ride duration
    public Map<Integer, Double> getAverageRideDurationPerDriver() {
        if (rides.isEmpty()) {return Collections.emptyMap();}

        return rides.stream()
                .filter(Objects::nonNull)
                .collect(Collectors.groupingBy(
                        Ride::getDriverId,
                        Collectors.averagingInt(Ride::getDuration)
                ));
    }

    // Q3: IMPLEMENT
    // A driver is inactive if:
    // currentTime - lastRideEndTime > threshold
    public List<Integer> getInactiveDrivers(int currentTime, int threshold) {
        if (rides.isEmpty()) {return Collections.emptyList();}

        return rides.stream()
                .collect(Collectors.groupingBy(
                        Ride::getDriverId,
                        Collectors.collectingAndThen(
                                Collectors.maxBy(Comparator.comparing(Ride::getEndTime)),
                                opt -> opt.map(Ride::getEndTime).orElse(0)
                        )
                )).entrySet().stream()
                .filter(ride -> currentTime - ride.getValue() > threshold)
                .map(Map.Entry::getKey)
                .toList();

    }

    // Q4: IMPLEMENT
    // Return top K drivers sorted by:
    // 1. highest total distance
    // 2. tie → smaller driverId
    public List<Integer> getTopKDriversByDistance(int k) {
        if (rides.isEmpty()) {return Collections.emptyList();}

        return rides.stream()
                .filter(Objects::nonNull)
                .collect(Collectors.groupingBy(
                        Ride::getDriverId,
                        Collectors.summingInt(Ride::getDistance)
                ))
                .entrySet().stream()
                .sorted(Map.Entry.<Integer, Integer>comparingByValue().reversed()
                        .thenComparing(Map.Entry.comparingByKey()))
                .limit(k)
                .map(Map.Entry::getKey)
                .toList();
    }
}