/*
We are building a program to manage a gym's membership. The gym has multiple members,
each with a unique ID, name, and membership status. The program allows gym staff to
add new members, update member status, and get membership statistics.

Definitions:
* A "member" is an object that represents a gym member. It has properties for the
  ID, name, and membership status.
* A "membership" is a class which is used for managing members in the gym.

To begin with, we present you with two tasks:
1-1) Read through and understand the code below. Please take as much time as necessary,
     and feel free to run the code.
1-2) The test for Membership is not passing due to a bug in the code. Make the
     necessary changes to Membership to fix the bug.
*/

/*
2) We are currently updating our system to include information about workouts for our
   members. As part of this update, we have introduced the Workout class, which
   represents a single workout session for a member. Each object of the Workout class
   has a unique ID, as well as a start time and end time that are represented in the
   number of minutes spent from the start of the day. You can assume that all the
   Workouts are from the same day.

   To implement these changes, we need to add two functions to the Membership class:

   2.1) The addWorkout function should be used to add a workout session for a member.
        If the given member does not exist while calling this function, the workout
        can be ignored.

   2.2) The getAverageWorkoutDurations function should calculate the average duration
        of workouts for each member in minutes and return the results as a map.

   To assist you in testing these new functions, we have provided the
   testGetAverageWorkoutDurations function.
*/

/*
3) Add the following function to the Membership class:

   3.1) getDuePayments(): returns a Map<Integer, Double> of memberId to monthly amount
        due based on membership tier.
        BRONZE = $0.00, SILVER = $30.00, GOLD = $60.00.
        Include all members.

   To assist you in testing, we have provided the testGetDuePayments function.
*/

/*
4) Add the following function to the Membership class:

   4.1) getGymBuddies(int k): for each member, returns a list of up to k other members
        whose workouts have the highest total overlap time (in minutes) with that
        member's workouts.

        Sort suggested buddies by:
          1. Total overlap descending
          2. Tie-break: smaller memberId first

        Members with no overlap get an empty list.
        All members must be present as keys in the result map.

   To assist you in testing, we have provided the testGetGymBuddies function.

5) Add the following function to the Membership class:

   5.1) getMostActiveMembers(int k): returns a list of up to k memberIds ranked by
        total workout duration across all their workouts.

        Sort by:
          1. Total duration descending
          2. Tie-break: smaller memberId first

        Only include members who have at least one workout.

   To assist you in testing, we have provided the testGetMostActiveMembers function.
*/

import java.lang.classfile.MethodBuilder;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

enum MembershipStatus {
    /*
        Membership Status is of three types: BRONZE, SILVER and GOLD.
        BRONZE is the default membership a new member gets.
        SILVER and GOLD are paid memberships for the gym.
    */
    BRONZE,
    SILVER,
    GOLD
}

class Workout {
    /**
     * This class represents a single workout session for a member.
     * Each object of the Workout class has a unique ID, as well as
     * a start time and end time that are represented in the number
     * of minutes spent from the start of the day.
     */

    private int id;
    private int startTime;
    private int endTime;

    public Workout(int id, int startTime, int endTime) {
        this.id = id;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public int getId()        { return id; }
    public int getStartTime() { return startTime; }
    public int getEndTime()   { return endTime; }
    public int getDuration()  { return endTime - startTime; }
}

class Member {
    public int memberId;
    public String name;
    public MembershipStatus membershipStatus;
    public List<Workout> workouts;

    public Member(int memberId, String name, MembershipStatus membershipStatus) {
        this.memberId = memberId;
        this.name = name;
        this.membershipStatus = membershipStatus;
        this.workouts = new ArrayList<>();
    }

    public int getMemberId() {
        return memberId;
    }

    public String getName() {
        return name;
    }

    public MembershipStatus getMembershipStatus() {
        return membershipStatus;
    }

    public List<Workout> getWorkouts() {
        return workouts;
    }

    @Override
    public String toString() {
        return "Member ID: " + memberId + ", Name: " + name + ", Membership Status: " + membershipStatus;
    }
}

class MembershipStatistics {
    public int totalMembers;
    public int totalPaidMembers;
    public double conversionRate;

    public MembershipStatistics(int totalMembers, int totalPaidMembers, double conversionRate) {
        this.totalMembers = totalMembers;
        this.totalPaidMembers = totalPaidMembers;
        this.conversionRate = conversionRate;
    }
}

class Membership {
    public List<Member> members;

    public Membership() {
        members = new ArrayList<>();
    }

    public void addMember(Member member) {
        members.add(member);
    }

    public void updateMembership(int memberId, MembershipStatus membershipStatus) {
        for (Member member : members) {
            if (member.memberId == memberId) {
                member.membershipStatus = membershipStatus;
                break;
            }
        }
    }

    // Q1: BUG — getMembershipStatistics() is not counting paid members correctly.
    //     Fix it.
    public MembershipStatistics getMembershipStatistics() {
        int totalMembers = members.size();
        int totalPaidMembers = 0;
        for (Member member : members) {
            if (member.membershipStatus == MembershipStatus.GOLD ||
                    member.membershipStatus == MembershipStatus.SILVER) {
                totalPaidMembers++;
            }
        }
        double conversionRate = (totalPaidMembers / (double) totalMembers) * 100.0;
        return new MembershipStatistics(totalMembers, totalPaidMembers, conversionRate);
    }

    // Q2.1: IMPLEMENT
    public void addWorkout(int memberId, Workout workout) {
        for (Member member : members) {
            if (member.memberId == memberId) member.workouts.add(workout);
        }
    }

    // Q2.2: IMPLEMENT
    public Map<Integer, Double> getAverageWorkoutDurations() {
        if (members.isEmpty()) {
            return new HashMap<>();
        }

        Map<Integer, Double> average = new HashMap<>();
        for (Member m : members) {
            if (m == null || m.getWorkouts().isEmpty()) continue;
            double avg = 0.0;
            for (Workout w : m.getWorkouts()) {
                if (w == null) continue;
                avg += w.getDuration();
            }
            avg = avg/m.getWorkouts().size();
            average.put(m.memberId, avg);
        }

        return average;
    }

    // Q3: IMPLEMENT
    public Map<Integer, Double> getDuePayments() {
        if (members.isEmpty()) return new HashMap<>();

        return members.stream()
                .filter(Objects::nonNull)
                .collect(Collectors.toMap(
                        Member::getMemberId,
                        m -> {
                            if (MembershipStatus.BRONZE.equals(m.getMembershipStatus())) return 0.0;
                            else if (MembershipStatus.SILVER.equals(m.getMembershipStatus())) return 30.0;
                            else return 60.0;
                        }
                ));
    }

    // Q4: IMPLEMENT
    public Map<Integer, List<Integer>> getGymBuddies(int k) {
        return new HashMap<>();
    }

    // Q5: IMPLEMENT
    public List<Integer> getMostActiveMembers(int k) {
        if (members.isEmpty()) return new ArrayList<>();

        return members.stream()
                .filter(m -> m != null &&
                        !m.getWorkouts().isEmpty())
                .collect(Collectors.toMap(
                        Member::getMemberId,
                        m -> m.getWorkouts().stream()
                                .mapToInt(Workout::getDuration)
                                .sum()
                ))
                .entrySet().stream()
                .sorted(Map.Entry.<Integer, Integer>comparingByValue().reversed()
                        .thenComparing(Map.Entry.comparingByKey()))
                .limit(k)
                .map(Map.Entry::getKey)
                .toList();
    }
}

public class GymMembership {
    public static void main(String[] args) {
        testMember();
        testMembership();
        testGetAverageWorkoutDurations();
        testGetDuePayments();
        //testGetGymBuddies();
        testGetMostActiveMembers();
    }

    public static void testMember() {
        System.out.println("Running testMember");
        Member testMember = new Member(1, "John Doe", MembershipStatus.BRONZE);
        assert testMember.memberId == 1 :
                "Member ID should be 1, was " + testMember.memberId;
        assert testMember.name.equals("John Doe") :
                "Member name should be \"John Doe\", was \"" + testMember.name + "\"";
        assert testMember.membershipStatus == MembershipStatus.BRONZE :
                "Membership status should be BRONZE, was " + testMember.membershipStatus;
    }

    public static void testMembership() {
        System.out.println("Running testMembership");
        Membership testMembership = new Membership();

        Member testMember1 = new Member(1, "John Doe", MembershipStatus.BRONZE);
        testMembership.addMember(testMember1);
        assert testMembership.members.size() == 1 :
                "Members size should be 1, was " + testMembership.members.size();

        testMembership.updateMembership(1, MembershipStatus.SILVER);
        assert testMembership.members.get(0).membershipStatus == MembershipStatus.SILVER :
                "Membership status should be SILVER, was " + testMembership.members.get(0).membershipStatus;

        Member testMember2 = new Member(2, "Alex C", MembershipStatus.BRONZE);
        testMembership.addMember(testMember2);

        Member testMember3 = new Member(3, "Marie C", MembershipStatus.GOLD);
        testMembership.addMember(testMember3);

        Member testMember4 = new Member(4, "Joe D", MembershipStatus.SILVER);
        testMembership.addMember(testMember4);

        Member testMember5 = new Member(5, "June R", MembershipStatus.BRONZE);
        testMembership.addMember(testMember5);

        Member testMember6 = new Member(6, "Westley D", MembershipStatus.SILVER);
        testMembership.addMember(testMember6);

        MembershipStatistics stats = testMembership.getMembershipStatistics();
        assert stats.totalMembers == 6 :
                "Total members should be 6, was " + stats.totalMembers;
        assert stats.totalPaidMembers == 4 :
                "Total paid members should be 4, was " + stats.totalPaidMembers;
        assert Math.abs(stats.conversionRate - 66.67) < 0.1 :
                "Conversion rate should be 66.67, was " + stats.conversionRate;
    }

    public static void testGetAverageWorkoutDurations() {
        System.out.println("Running testGetAverageWorkoutDurations");
        Membership testMembership = new Membership();

        Member testMember1 = new Member(12, "John Doe", MembershipStatus.SILVER);
        testMembership.addMember(testMember1);

        Member testMember2 = new Member(22, "Alex Cleeve", MembershipStatus.BRONZE);
        testMembership.addMember(testMember2);

        Member testMember3 = new Member(31, "Marie Cardiff", MembershipStatus.GOLD);
        testMembership.addMember(testMember3);

        Member testMember4 = new Member(37, "George Costanza", MembershipStatus.SILVER);
        testMembership.addMember(testMember4);

        testMembership.addWorkout(12, new Workout(11, 10, 20));   // duration 10
        testMembership.addWorkout(22, new Workout(24, 15, 35));   // duration 20
        testMembership.addWorkout(31, new Workout(32, 45, 90));   // duration 45
        testMembership.addWorkout(12, new Workout(47, 100, 155)); // duration 55
        testMembership.addWorkout(22, new Workout(56, 120, 200)); // duration 80
        testMembership.addWorkout(31, new Workout(62, 300, 400)); // duration 100
        testMembership.addWorkout(12, new Workout(78, 1000, 1010)); // duration 10
        testMembership.addWorkout(4,  new Workout(80, 1010, 1045)); // member 4 doesn't exist

        Map<Integer, Double> avgDurations = testMembership.getAverageWorkoutDurations();
        assert Math.abs(avgDurations.get(12) - 25.0) < 0.1 :
                "Average duration for member 12 should be 25.0, was " + avgDurations.get(12);
        assert Math.abs(avgDurations.get(22) - 50.0) < 0.1 :
                "Average duration for member 22 should be 50.0, was " + avgDurations.get(22);
        assert Math.abs(avgDurations.get(31) - 72.5) < 0.1 :
                "Average duration for member 31 should be 72.5, was " + avgDurations.get(31);
        assert !avgDurations.containsKey(4) :
                "Member 4 does not exist, should not be in result";
    }

    public static void testGetDuePayments() {
        System.out.println("Running testGetDuePayments");
        Membership testMembership = new Membership();

        testMembership.addMember(new Member(1, "Alice", MembershipStatus.GOLD));
        testMembership.addMember(new Member(2, "Bob", MembershipStatus.SILVER));
        testMembership.addMember(new Member(3, "Charlie", MembershipStatus.BRONZE));

        Map<Integer, Double> due = testMembership.getDuePayments();
        assert Math.abs(due.get(1) - 60.0) < 0.01 :
                "Member 1 (GOLD) should owe 60.0, was " + due.get(1);
        assert Math.abs(due.get(2) - 30.0) < 0.01 :
                "Member 2 (SILVER) should owe 30.0, was " + due.get(2);
        assert Math.abs(due.get(3) - 0.0) < 0.01 :
                "Member 3 (BRONZE) should owe 0.0, was " + due.get(3);
    }

    public static void testGetGymBuddies() {
        System.out.println("Running testGetGymBuddies");
        Membership testMembership = new Membership();

        testMembership.addMember(new Member(1, "Alice", MembershipStatus.GOLD));
        testMembership.addMember(new Member(2, "Bob", MembershipStatus.SILVER));
        testMembership.addMember(new Member(3, "Charlie", MembershipStatus.BRONZE));

        testMembership.addWorkout(1, new Workout(1, 0, 60));   // Alice:   0-60
        testMembership.addWorkout(2, new Workout(2, 30, 90));  // Bob:    30-90
        testMembership.addWorkout(3, new Workout(3, 80, 120)); // Charlie: 80-120

        // Alice vs Bob overlap:    30 mins (30-60)
        // Alice vs Charlie overlap: 0 mins
        // Bob vs Charlie overlap:  10 mins (80-90)

        Map<Integer, List<Integer>> buddies = testMembership.getGymBuddies(2);
        assert buddies.get(1).equals(List.of(2)) :
                "Alice's buddy should be [2], was " + buddies.get(1);
        assert buddies.get(2).equals(List.of(1, 3)) :
                "Bob's buddies should be [1, 3], was " + buddies.get(2);
        assert buddies.get(3).equals(List.of(2)) :
                "Charlie's buddy should be [2], was " + buddies.get(3);
    }

    public static void testGetMostActiveMembers() {
        System.out.println("Running testGetMostActiveMembers");
        Membership testMembership = new Membership();

        testMembership.addMember(new Member(1, "Alice", MembershipStatus.GOLD));
        testMembership.addMember(new Member(2, "Bob", MembershipStatus.SILVER));
        testMembership.addMember(new Member(3, "Charlie", MembershipStatus.BRONZE));
        testMembership.addMember(new Member(4, "Diana", MembershipStatus.GOLD));

        testMembership.addWorkout(1, new Workout(1, 0, 60));    // Alice:   60 mins
        testMembership.addWorkout(1, new Workout(2, 100, 130)); // Alice:  +30 = 90 total
        testMembership.addWorkout(2, new Workout(3, 0, 120));   // Bob:   120 mins
        testMembership.addWorkout(3, new Workout(4, 0, 90));    // Charlie: 90 mins
        // Diana: no workouts

        List<Integer> top2 = testMembership.getMostActiveMembers(2);
        if (!top2.equals(List.of(2, 1)))
            throw new RuntimeException("Top 2 should be [2, 1], was " + top2);
        // Bob=120, Alice=90, Charlie=90 — Alice vs Charlie tie broken by memberId

        List<Integer> top3 = testMembership.getMostActiveMembers(3);
        if (!top3.equals(List.of(2, 1, 3)))
            throw new RuntimeException("Top 3 should be [2, 1, 3], was " + top3);
    }
}