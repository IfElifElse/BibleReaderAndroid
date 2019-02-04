package com.example.chaos.depspasescouting.robots;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Set;
import java.util.Stack;

public class TimeLog {
    private HashMap<Long, Event> timestamps; // a list of events, each tied to a time.
    private Stack<Long> undidTimes;

    /**
     * Constructor for TimeLog class. Initializes timestamps hashmap.
     */
    public TimeLog() {
        this.timestamps = new HashMap<>();
        this.undidTimes = new Stack<>();
    }

    /**
     * Adds a timestamp to `timestamps` with the specified flag.
     *
     * @param event - the flag to be added at the current time.
     */
    public void log(Event event) {
        long time = System.currentTimeMillis();
        if (! this.undidTimes.empty()) {
            time = undidTimes.pop();
            System.out.println("using undidTime");
        }
        System.out.println("should log " + event.name() + " at time " + time);
        if (event == Event.START) { time --; } // can't have 2 events at the same time, so we offset the start event by 1
        this.timestamps.put(time, event);
    }

    /**
     * Removes the last timestamp logged from `timestamps`.
     *
     * @return - the event that was removed.
     */
    public Event unlog() {
        Set<Long> allKeys = this.timestamps.keySet();
        if (allKeys.size() > 0) {
            Long lastTime = Collections.max(allKeys);
            if (this.timestamps.get(lastTime) != Event.START) {
                this.undidTimes.push(lastTime);
                return this.timestamps.remove(lastTime);
            }
        }
        return null;
    }

    /**
     * Takes the time between each event and returns a list of the time taken to pick up a piece to
     *  the time taken to place that piece
     *
     * @return - an arraylist of arraylists of lap times, ordered like [lo ball -> hi ball -> lo disc -> hi disc].
     */
    public ArrayList<ArrayList<Long>> getLapTimes() {
        ArrayList<Long> times = new ArrayList<>(this.timestamps.keySet());
        ArrayList<Long> loBallLaps = new ArrayList<>();
        ArrayList<Long> hiBallLaps = new ArrayList<>();
        ArrayList<Long> loDiscLaps = new ArrayList<>();
        ArrayList<Long> hiDiscLaps = new ArrayList<>();
        Long lastTime = 0L;
        Long pickupTime = 0L;
        Long pausedStart = 0L;
        Long pausedTime = 0L;
        String pieceType = "ball";
        for (Long time : times) {
            Event event = this.timestamps.get(time);

            if (event == Event.BALL) {
                pickupTime = time;
                pieceType = "ball";
            }
            else if (event == Event.DISC) {
                pickupTime = time;
                pieceType = "disc";
            }
            else if (event == Event.DEFENSE) {
                pausedStart = time;
            }
            else if (event == Event.UNDEFENSE) {
                pausedTime += time - pausedStart;
            }
            /*
            else if (event == Event.SCORELO) {
                Long totalTime = pickupTime - time - pausedTime;
                if (pieceType.equals("ball")) { loBallLaps.add(totalTime); }
                else if (pieceType.equals("disc")) { loDiscLaps.add(totalTime); }
            }
            else if (event == Event.SCOREHI) {
                Long totalTime = pickupTime - time - pausedTime;
                if (pieceType.equals("ball")) { hiBallLaps.add(totalTime); }
                else if (pieceType.equals("disc")) { hiDiscLaps.add(totalTime); }
            }
            */
        }
        ArrayList<ArrayList<Long>> returns = new ArrayList<>();
        returns.add(loBallLaps);
        returns.add(hiBallLaps);
        returns.add(loDiscLaps);
        returns.add(hiDiscLaps);
        return returns;
    }

    public Stack<Long> getAllTimesAsStack() {
        ArrayList<Long> timesArrayList = new ArrayList<>(this.timestamps.keySet());
        Collections.sort(timesArrayList);
        Stack<Long> timesStack = new Stack<>();
        for (Long time : timesArrayList) {
            timesStack.push(time);
        }
        return timesStack;
    }

    public Stack<Event> getAllEventsAsStack() {
        ArrayList<Long> timesArrayList = new ArrayList<>(this.timestamps.keySet());
        Collections.sort(timesArrayList);
        Stack<Event> eventsStack = new Stack<>();
        for (Long time : timesArrayList) {
            eventsStack.push(this.timestamps.get(time));
        }
        return eventsStack;
    }
    
    public HashMap<Long, Event> getTimestampsCopy() {
        return (HashMap<Long, Event>) this.timestamps.clone();
    }
}
