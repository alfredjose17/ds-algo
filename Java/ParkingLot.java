import java.util.*;
import java.util.stream.Collectors;

public class ParkingLot {
    public static void main(String[] args) {
        ParkingManager manager = new ParkingManager();

        manager.addTicket(new ParkingTicket("A1", 101, 10, 20, false)); // completed, 10 mins
        manager.addTicket(new ParkingTicket("A2", 102, 15, -1, true));  // active
        manager.addTicket(new ParkingTicket("A3", 101, 30, 50, false)); // completed, 20 mins
        manager.addTicket(new ParkingTicket("A4", 103, 40, -1, true));  // active
        manager.addTicket(new ParkingTicket("A5", 102, 60, 90, false)); // completed, 30 mins

        // Q1
        System.out.println("Active Ticket Count: " + manager.getActiveTicketCount());
        // Expected: 2

        // Q2
        System.out.println("Completed Parking Time By Vehicle: " + manager.getCompletedParkingTimeByVehicle());
        // Expected: {101=30, 102=30}

        // Q3
        System.out.println("Overstayed Vehicles: " + manager.getOverstayedVehicles(100, 50));
        // Expected: [102, 103]
    }
}

class ParkingTicket {
    String ticketId;
    int vehicleId;
    int entryMinute;
    int exitMinute; // -1 means not exited yet
    boolean active;

    public ParkingTicket(String ticketId, int vehicleId, int entryMinute, int exitMinute, boolean active) {
        this.ticketId = ticketId;
        this.vehicleId = vehicleId;
        this.entryMinute = entryMinute;
        this.exitMinute = exitMinute;
        this.active = active;
    }

    public boolean isActive() {
        return active;
    }

    public boolean isCompleted() {
        return exitMinute != -1;
    }

    public int getVehicleId() {
        return vehicleId;
    }

    public int getEntryMinute() {
        return entryMinute;
    }

    public int getExitMinute() {
        return exitMinute;
    }

    public int getDuration() {
        return exitMinute - entryMinute;
    }
}

class ParkingManager {
    List<ParkingTicket> tickets = new ArrayList<>();

    public void addTicket(ParkingTicket ticket) {
        tickets.add(ticket);
    }

    // Q1: BUG - currently counts all tickets
    public int getActiveTicketCount() {
        int count = 0;
        for (ParkingTicket ticket : tickets) {
            if (ticket.isActive()) {
                count++;
            }
        }
        return count;
    }

    // Q2: IMPLEMENT
    // Return vehicleId -> total completed parking duration
    // Only completed tickets should count.
    public Map<Integer, Integer> getCompletedParkingTimeByVehicle() {
        if (tickets.isEmpty()) {
            return Collections.emptyMap();
        }

        return tickets.stream()
                .filter(ticket -> ticket != null && ticket.isCompleted())
                .collect(Collectors.groupingBy(
                        ParkingTicket::getVehicleId,
                        Collectors.summingInt(ParkingTicket::getDuration)
                ));
    }

    // Q3: IMPLEMENT
    // Return vehicleIds for active tickets where:
    // currentMinute - entryMinute > maxAllowedMinutes
    public List<Integer> getOverstayedVehicles(int currentMinute, int maxAllowedMinutes) {
        if (tickets.isEmpty() || currentMinute < 0 || maxAllowedMinutes < 0) {
            return Collections.emptyList();
        }

        return tickets.stream()
                .filter(ticket -> ticket != null &&
                        ticket.isActive() &&
                        currentMinute - ticket.getEntryMinute() > maxAllowedMinutes)
                .map(ParkingTicket::getVehicleId)
                .distinct()
                .toList();

    }
}