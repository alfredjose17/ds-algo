import java.util.*;
import java.util.stream.Collectors;

public class TollBooth {
    public static void main(String[] args) {

        TollManager manager = new TollManager();

        // plate, timestamp, sensorId, action
        manager.addLog(new LogEntry("ABC-1",  100, 1, "ENTER"));
        manager.addLog(new LogEntry("ABC-1",  500, 2, "EXIT"));   // journey 1: 400s
        manager.addLog(new LogEntry("XYZ-9",  200, 1, "ENTER"));
        manager.addLog(new LogEntry("XYZ-9",  350, 2, "EXIT"));   // journey 2: 150s
        manager.addLog(new LogEntry("ABC-1",  600, 2, "ENTER"));
        manager.addLog(new LogEntry("ABC-1",  900, 3, "EXIT"));   // journey 3: 300s
        manager.addLog(new LogEntry("LOST-7", 700, 1, "ENTER"));  // never exits
        manager.addLog(new LogEntry("XYZ-9", 1000, 2, "ENTER"));
        manager.addLog(new LogEntry("XYZ-9", 1200, 3, "EXIT"));   // journey 4: 200s

        // Q1
        System.out.println("Total journeys: " + manager.countCompleteJourneys());
        // Expected: 4

        // Q2
        System.out.println("Journeys per plate: " + manager.getJourneysPerPlate());
        // Expected: {ABC-1=2, XYZ-9=2}
        // (LOST-7 excluded — no completed journey)

        // Q3
        System.out.println("Avg journey duration per plate: " + manager.getAverageJourneyDurationPerPlate());
        // Expected: {ABC-1=350.0, XYZ-9=175.0}

        // Q4
        System.out.println("Busiest sensors (top 2): " + manager.getBusiestSensors(2));
        // Expected: [2, 1]
        // sensor 2: 4 events, sensor 1: 3 events, sensor 3: 2 events
        // tie-break: smaller sensorId wins
    }
}

// ------------------ MODEL ------------------

class LogEntry {
    String plate;
    long timestamp;
    int sensorId;
    String action;   // "ENTER" or "EXIT"

    public LogEntry(String plate, long timestamp, int sensorId, String action) {
        this.plate = plate;
        this.timestamp = timestamp;
        this.sensorId = sensorId;
        this.action = action;
    }

    public String getPlate()   { return plate; }
    public long getTimestamp() { return timestamp; }
    public int getSensorId()   { return sensorId; }
    public String getAction()  { return action; }
}

// ------------------ MANAGER ------------------

class TollManager {
    List<LogEntry> logs = new ArrayList<>();

    public void addLog(LogEntry e) {
        logs.add(e);
    }

    // Q1: There is a BUG. countCompleteJourneys should return the number of
    //     ENTER→EXIT pairs across all plates. The current implementation is
    //     wrong (think about what it actually counts). Fix it.
    public int countCompleteJourneys() {
        int count = 0;

        logs.sort(Comparator.comparing(LogEntry::getTimestamp));

        Set<String> entry = new HashSet<>();

        for (LogEntry e : logs) {
            if (entry.contains(e.getPlate())){
                if (e.getAction().equalsIgnoreCase("EXIT")) {
                    count++;
                    entry.remove(e.getPlate());
                }
            }
            else {
                if (e.getAction().equalsIgnoreCase("ENTER")) {
                    entry.add(e.getPlate());
                }
            }

        }
        return count;
    }

    // Q2: IMPLEMENT
    // plate -> number of completed journeys (ENTER→EXIT pairs).
    // Plates with zero completed journeys are omitted.
    public Map<String, Integer> getJourneysPerPlate() {
        if (logs.isEmpty()) {
            return Collections.emptyMap();
        }

        logs.sort(Comparator.comparing(LogEntry::getTimestamp));

        Set<String> entry = new HashSet<>();
        Map<String, Integer> count = new HashMap<>();

        for (LogEntry e : logs) {
            if (entry.contains(e.getPlate())){
                if (e.getAction().equalsIgnoreCase("EXIT")) {
                    count.put(e.getPlate(), count.getOrDefault(e.getPlate(), 0) + 1);
                    entry.remove(e.getPlate());
                }
            }
            else {
                if (e.getAction().equalsIgnoreCase("ENTER")) {
                    entry.add(e.getPlate());
                }
            }

        }

        return count;
    }

    // Q3: IMPLEMENT
    // plate -> average journey duration (EXIT timestamp - matching ENTER timestamp).
    // Only completed journeys count. Plates with no completed journeys omitted.
    public Map<String, Double> getAverageJourneyDurationPerPlate() {
        if (logs.isEmpty()) {
            return Collections.emptyMap();
        }

        logs.sort(Comparator.comparing(LogEntry::getTimestamp));

        Map<String, Long> entry = new HashMap<>();
        Map<String, Integer> count = new HashMap<>();
        Map<String, Long> duration = new HashMap<>();

        for (LogEntry e : logs) {
            if (entry.get(e.getPlate()) != null){
                if (e.getAction().equalsIgnoreCase("EXIT")) {
                    count.put(e.getPlate(), count.getOrDefault(e.getPlate(), 0) + 1);
                    duration.put(e.getPlate(), duration.getOrDefault(e.getPlate(), 0L) + (e.getTimestamp() - entry.get(e.getPlate())));
                    entry.remove(e.getPlate());
                }
            }
            else {
                if (e.getAction().equalsIgnoreCase("ENTER")) {
                    entry.put(e.getPlate(), e.getTimestamp());
                }
            }

        }

        Map<String, Double> averageDuration = new HashMap<>();

        for (String s : duration.keySet()){
            averageDuration.put(s, ((double) duration.get(s))/count.get(s));
        }
        return averageDuration;
    }

    // Q4: IMPLEMENT
    // Return top K sensorIds sorted by:
    //   1. highest total event count (ENTERs + EXITs combined)
    //   2. tie → smaller sensorId
    public List<Integer> getBusiestSensors(int k) {
        if (logs.isEmpty() || k <= 0) {
            return Collections.emptyList();
        }

        return logs.stream()
                .filter(Objects::nonNull)
                .collect(Collectors.groupingBy(
                        LogEntry::getSensorId,
                        Collectors.counting()
                ))
                .entrySet().stream()
                .sorted(Map.Entry.<Integer, Long>comparingByValue().reversed()
                        .thenComparing(Map.Entry.comparingByKey()))
                .limit(k)
                .map(Map.Entry::getKey)
                .toList();
    }
}