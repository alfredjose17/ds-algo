/*
We are writing software to collect and manage data on how fast racers can complete
obstacle courses. An obstacle course is a series of difficult physical challenges
(like walls, hurdles, and ponds) that a racer must go through.

Each course consists of multiple obstacles. The software stores how long it takes
for racers to finish each obstacle, and provides useful statistics based on those times.

Definitions:
* A "run" is a particular attempt to complete an entire obstacle course
* A "run collection" is a group of runs on a particular course by the user.
* An "obstacle" is a portion of a course. We track how long it takes to finish
  each portion of the course

For example, here are some times for an obstacle course with four obstacles:

 Obstacles:    O1  O2  O3  O4
    Run 1:      3   4   5   6    (total: 18 seconds)
    Run 2:      4   4   4   5    (total: 17 seconds)
    Run 3:      4   5   4   6    (total: 19 seconds)
    Run 4:      5   5   3        (13 seconds, but run is incomplete)

All of these runs for one obstacle course (including the incomplete run) make up
a run collection.

To begin with, we present you with two tasks:
1-1) Read through and understand the code below. Please take as much time as
     necessary, and feel free to run the code.
1-2) The test for RunCollection is not passing due to a bug in the code. Make
     the necessary changes to RunCollection to fix the bug.
*/

/*
2) We would like to implement a new function in RunCollection called "bestOfBests".
   This is a measure of how fast a run could be if everything went perfectly, and
   is determined by taking the fastest times for each obstacle across all runs
   (even incomplete ones) and summing them.

   In the run collection above, the times 3, 4, 3, and 5 combine to make a best
   of bests of 15 seconds.

   Implement bestOfBests() and add a test to verify that it works.
*/

/*
3) Implement the following function in RunCollection:

   3.1) getObstacleAverages(): returns a List<Double> where each index contains
        the average time taken for that obstacle across all runs (complete and
        incomplete). Only include runs that actually recorded a time for that
        obstacle.

   For the example above:
     O1 avg: (3+4+4+5)/4 = 4.0
     O2 avg: (4+4+5+5)/4 = 4.5
     O3 avg: (5+4+4+3)/4 = 4.0
     O4 avg: (6+5+6)/3   = 5.67  (run 4 never reached O4)

   To assist you in testing, we have provided testGetObstacleAverages.
*/

/*
4) Implement the following function in RunCollection:

   4.1) getTopKRuns(int k): returns a list of up to k complete runs sorted by:
        1. Total run time ascending (fastest first)
        2. Tie-break: the run that was added first (earlier index in runs list)

   To assist you in testing, we have provided testGetTopKRuns.
*/

import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.*;

class Course {
    public String title;
    public int obstacleCount;

    public Course(String courseTitle, int obstacles) {
        title = courseTitle;
        obstacleCount = obstacles;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Course)) { return false; }
        Course c = (Course) o;
        boolean isSameTitle = (c.title == null && this.title == null) ||
                (this.title != null && this.title.equals(c.title));
        return isSameTitle && c.obstacleCount == this.obstacleCount;
    }

    @Override
    public int hashCode() {
        return (title == null ? 0 : title.hashCode()) * obstacleCount;
    }
}

class Run {
    public Course course;
    public boolean complete;
    public List<Integer> obstacleTimes;

    public Run(Course runCourse) {
        course = runCourse;
        complete = false;
        obstacleTimes = new ArrayList<>();
    }

    public List<Integer> getObstacleTimes() {
        return obstacleTimes;
    }

    public void addObstacleTime(int obstacleTime) {
        if (complete) {
            throw new IllegalStateException("Cannot add obstacle to complete run");
        }
        obstacleTimes.add(obstacleTime);
        if (obstacleTimes.size() == course.obstacleCount) {
            complete = true;
        }
    }

    public int getRunTime() {
        return obstacleTimes.stream().mapToInt(Integer::intValue).sum();
    }
}

class RunCollection {
    public Course course;
    public List<Run> runs;

    public RunCollection(Course collectionCourse) {
        course = collectionCourse;
        runs = new ArrayList<>();
    }

    public int getNumRuns() {
        return runs.size();
    }

    public void addRun(Run run) {
        if (!run.course.equals(course)) {
            throw new IllegalArgumentException("run's Course is not the same as the RunCollection's");
        }
        runs.add(run);
    }

    // Q1: BUG — personalBest() should return the best (lowest) complete run time,
    //     but it is returning the wrong value. Fix it.
    public int personalBest() {
        return runs.stream()
                .filter(r -> r.complete == true)
                .mapToInt(v -> v.getRunTime())
                .min()               // BUG
                .orElse(Integer.MAX_VALUE);
    }

    // Q2: IMPLEMENT
    // Returns the sum of the fastest time for each obstacle across all runs
    // (complete and incomplete).
    public int bestOfBests() {
        if (runs.isEmpty()) return 0;

        int[] min = new int[course.obstacleCount];
        Arrays.fill(min, Integer.MAX_VALUE);

        for (Run r : runs) {
            if (r == null) continue;

            List<Integer> ot = r.getObstacleTimes();

            for (int i = 0; i < ot.size(); i++) {
                if (ot.get(i) < min[i]) {
                    min[i] = ot.get(i);
                }
            }
        }

        return Arrays.stream(min).sum();
    }

    // Q3: IMPLEMENT
    // Returns a List<Double> where index i is the average time for obstacle i
    // across all runs that reached that obstacle.
    public List<Double> getObstacleAverages() {
        if (runs.isEmpty()) return new ArrayList<>();

        List<List<Integer>> total = new ArrayList<>();
        for (int i = 0; i < course.obstacleCount; i++) total.add(Arrays.asList(0,0));

        for (Run r : runs) {
            if (r == null) continue;

            List<Integer> ot = r.getObstacleTimes();

            for (int i = 0; i < ot.size(); i++) {
                total.set(i, Arrays.asList(total.get(i).get(0) + ot.get(i), total.get(i).get(1) + 1));
            }
        }

        List<Double> avg = new ArrayList<>();

        for (List<Integer> l : total) {
            avg.add(((double) l.get(0)) / l.get(1));
        }

        return avg;
    }

    // Q4: IMPLEMENT
    // Returns up to k complete runs sorted by run time ascending.
    // Tie-break: earlier run in the list wins.
    public List<Run> getTopKRuns(int k) {
        if (runs.isEmpty()) return new ArrayList<>();

        return runs.stream()
                .filter(r -> r != null &&
                        r.complete)
                .sorted(Comparator.comparing(Run::getRunTime))
                .limit(k)
                .toList();
    }
}

public class ObstacleCourse {
    public static void main(String[] argv) {
        testRun();
        testRunCollection();
        testBestOfBests();
        testGetObstacleAverages();
        testGetTopKRuns();
    }

    public static void testRun() {
        System.out.println("Running testRun");
        Course testCourse = new Course("Test course", 2);
        Run testRun = new Run(testCourse);
        testRun.addObstacleTime(3);
        assert !testRun.complete : "Test run should not be complete";
        testRun.addObstacleTime(5);
        assert testRun.complete : "Test run should be complete";
        assert testRun.obstacleTimes.equals(new ArrayList<Integer>(List.of(3, 5))) :
                "obstacleTimes should be [3, 5], was " + testRun.obstacleTimes;
        assert testRun.getRunTime() == 8 :
                "getRunTime should return 8, returned " + testRun.getRunTime();
        try {
            testRun.addObstacleTime(4);
            assert false : "adding obstacle time to complete run should throw";
        } catch (IllegalStateException e) {
            // expected
        }
    }

    public static RunCollection makeRunCollection(Course course, int[][] obstacleData) {
        RunCollection runCollection = new RunCollection(course);
        for (int[] runData : obstacleData) {
            Run run = new Run(course);
            for (int obstacleTime : runData) {
                run.addObstacleTime(obstacleTime);
            }
            runCollection.addRun(run);
        }
        return runCollection;
    }

    public static void testRunCollection() {
        System.out.println("Running testRunCollection");
        int[][] obstacleData = new int[][] {
                {3, 4, 5, 6},
                {4, 4, 4, 5},
                {4, 5, 4, 6},
                {5, 5, 3}
        };
        Course testCourse = new Course("Test course", 4);
        RunCollection rc = makeRunCollection(testCourse, obstacleData);

        assert rc.getNumRuns() == 4 :
                "number of runs should be 4, was " + rc.getNumRuns();
        assert rc.personalBest() == 17 :
                "personalBest should be 17, was " + rc.personalBest();
    }

    public static void testBestOfBests() {
        System.out.println("Running testBestOfBests");
        int[][] obstacleData = new int[][] {
                {3, 4, 5, 6},
                {4, 4, 4, 5},
                {4, 5, 4, 6},
                {5, 5, 3}
        };
        Course testCourse = new Course("Test course", 4);
        RunCollection rc = makeRunCollection(testCourse, obstacleData);

        assert rc.bestOfBests() == 15 :
                "bestOfBests should be 15, was " + rc.bestOfBests();
    }

    public static void testGetObstacleAverages() {
        System.out.println("Running testGetObstacleAverages");
        int[][] obstacleData = new int[][] {
                {3, 4, 5, 6},
                {4, 4, 4, 5},
                {4, 5, 4, 6},
                {5, 5, 3}
        };
        Course testCourse = new Course("Test course", 4);
        RunCollection rc = makeRunCollection(testCourse, obstacleData);

        List<Double> avgs = rc.getObstacleAverages();
        assert Math.abs(avgs.get(0) - 4.0)  < 0.01 : "O1 avg should be 4.0, was "  + avgs.get(0);
        assert Math.abs(avgs.get(1) - 4.5)  < 0.01 : "O2 avg should be 4.5, was "  + avgs.get(1);
        assert Math.abs(avgs.get(2) - 4.0)  < 0.01 : "O3 avg should be 4.0, was "  + avgs.get(2);
        assert Math.abs(avgs.get(3) - 5.67) < 0.01 : "O4 avg should be 5.67, was " + avgs.get(3);
    }

    public static void testGetTopKRuns() {
        System.out.println("Running testGetTopKRuns");
        int[][] obstacleData = new int[][] {
                {3, 4, 5, 6},   // total 18, complete
                {4, 4, 4, 5},   // total 17, complete
                {4, 5, 4, 6},   // total 19, complete
                {5, 5, 3}       // incomplete, excluded
        };
        Course testCourse = new Course("Test course", 4);
        RunCollection rc = makeRunCollection(testCourse, obstacleData);

        List<Run> top2 = rc.getTopKRuns(2);
        assert top2.size() == 2 :
                "top 2 size should be 2, was " + top2.size();
        assert top2.get(0).getRunTime() == 17 :
                "fastest run should be 17, was " + top2.get(0).getRunTime();
        assert top2.get(1).getRunTime() == 18 :
                "second run should be 18, was " + top2.get(1).getRunTime();
    }
}