package com.example.chaos.depspasescouting.robots;

import java.util.HashMap;
import java.util.Stack;

public class Robot {

    private int number; // the number of the team that owns the robot
    private String scouter;

    private int roundNumber;

    private boolean startingBonus; // true if the robot started on the 6" platform and got the points.
    private EndingState endingState; // the hab platform position that the robot ended on.
    private boolean helpedOtherRobot; // true if the robot assisted another robot in climbing the hab platform.
    private EndingState helpedState; // the hab platform position that the robot helped another robot end on.

    private int ballsScoredL; // the number of cargo the robot has successfully scored in the lower rocket level or the cargo ship
    private int ballsScoredM; // the number of cargo the robot has successfully scored in the middle rocket level
    private int ballsScoredH; // the number of cargo the robot has successfully stored in the upper rocket level
    private int discsScoredL; // the number of hatch panels the robot has successfully scored in the lower rocket level or the cargo ship
    private int discsScoredM; // the number of hatch panels the robot has successfully scored in the middle rocket level
    private int discsScoredH; // the number of hatch panels the robot has successfully scored in the upper rocket level
    private int ballsDropped; // the number of cargo that the robot has dropped, purposefully OR unpurposefully
    private int discsDropped; // the number of hatch panels that the robot has dropped, purposefully OR unpurposefully

    private boolean isDefending; // true if the robot is on the enemy side of the field.
    private boolean hasBall; // true if the robot has a cargo in their possession.
    private boolean hasDisc; // true if the robot has a hatch panel in their possession.

    private TimeLog eventLog; // a list of robot events, each mapped to a point in time.

    /**
     * Constructor for Robot class. Initializes all scoring values to zero, sets isDefending to false.
     *
     * @param number - The number of the team that made the Robot.
     * @param scouter - The name of the person scouting the Robot.
     * @param roundNumber - The number of the round the Robot is participating in.
     */
    public Robot(int number, String scouter, int roundNumber) {
        this.number = number;
        this.scouter = scouter;
        this.roundNumber = roundNumber;
        this.ballsScoredL = 0;
        this.ballsScoredM = 0;
        this.ballsScoredH = 0;
        this.discsScoredL = 0;
        this.discsScoredM = 0;
        this.discsScoredH = 0;
        this.ballsDropped = 0;
        this.discsDropped = 0;
        this.isDefending = false;
        this.hasBall = false;
        this.hasDisc = false;
        this.eventLog = new TimeLog();
    }

    /**
     * Adds an event to signify the start of a round and which piece the robot starts the round with.
     */
    public void startRound(Event startsWithPiece) {
        this.eventLog.log(Event.START);
        if (startsWithPiece == Event.BALL) {
            System.out.println("Starting with Event.BALL");
            setHasBall();
        }
        else if (startsWithPiece == Event.DISC) {
            System.out.println("Starting with Event.DISC");
            setHasDisc();
        }
    }

    /**
     * Sets hasBall to true and adds a BALL event to the eventLog.
     */
    public void setHasBall() {
        this.hasBall = true;
        this.eventLog.log(Event.BALL);
    }
    /**
     * Increments the number of lo scored balls and adds a SCORE_BALL_L event to the eventLog.
     */
    public void incBallsScoredL() {
        this.ballsScoredL ++;
        this.hasBall = false;
        this.eventLog.log(Event.SCORE_BALL_L);
    }

    /**
     * Increments the number of mid scored balls and adds a SCORE_BALL_M event to the eventLog.
     */
    public void incBallsScoredM() {
        this.ballsScoredM ++;
        this.hasBall = false;
        this.eventLog.log(Event.SCORE_BALL_M);
    }
    /**
     * Increments the number of hi scored balls and adds a SCORE_BALL_H event to the eventLog.
     */
    public void incBallsScoredH() {
        this.ballsScoredH ++;
        this.hasBall = false;
        this.eventLog.log(Event.SCORE_BALL_H);
    }

    /**
     * Sets hasDisc to true and adds a DISC event to the eventLog.
     */
    public void setHasDisc() {
        this.hasDisc = true;
        this.eventLog.log(Event.DISC);
    }
    /**
     * Increments the number of lo scored discs and adds a SCORE_DISC_L event to the eventLog.
     */
    public void incDiscsScoredL() {
        this.discsScoredL ++;
        this.hasDisc = false;
        this.eventLog.log(Event.SCORE_DISC_L);
    }

    /**
     * Increments the number of mid scored discs and adds a SCORE_DISC_M event to the eventLog.
     */
    public void incDiscsScoredM() {
        this.discsScoredM ++;
        this.hasDisc = false;
        this.eventLog.log(Event.SCORE_DISC_M);
    }
    /**
     * Increments the number of hi scored discs and adds a SCORE_DISC_H event to the eventLog.
     */
    public void incDiscsScoredH() {
        this.discsScoredH ++;
        this.hasDisc = false;
        this.eventLog.log(Event.SCORE_DISC_H);
    }

    /**
     * Sets hasBall to false and adds a DROP_BALL event to the eventLog.
     */
    public void setDroppedBall() {
        this.hasBall = false;
        this.eventLog.log(Event.DROP_BALL);
    }

    /**
     * Sets hasDisc to false and adds a DROP_DISC event to the eventLog.
     */
    public void setDroppedDisc() {
        this.hasDisc = false;
        this.eventLog.log(Event.DROP_DISC);
    }

    /**
     * Switches isDefending to true and adds a DEFENSE event to the eventLog.
     */
    public void setDefending() {
        this.isDefending = true;
        this.eventLog.log(Event.DEFENSE);
    }

    /**
     * Switches isDefending to false and adds a UNDEFENSE event to the eventLog.
     */
    public void setUndefending() {
        this.isDefending = false;
        this.eventLog.log(Event.UNDEFENSE);
    }

    /**
     * Removes the last logged event and reverts any changes to the robot's scoring that would have fired due to the event.
     */
    public Event undoLastAction() {
        Event event = this.eventLog.unlog();
        if (event == null) return null;
        switch (event) {
            case START:
                break;
            case BALL:
                this.hasBall = false;
                break;
            case DISC:
                this.hasDisc = false;
                break;
            case SCORE_BALL_H:
                this.ballsScoredH --;
                break;
            case SCORE_BALL_M:
                this.ballsScoredM --;
                break;
            case SCORE_BALL_L:
                this.ballsScoredL --;
                break;
            case SCORE_DISC_H:
                this.discsScoredH --;
                break;
            case SCORE_DISC_M:
                this.discsScoredM --;
                break;
            case SCORE_DISC_L:
                this.discsScoredL --;
                break;
            case DEFENSE:
                this.isDefending = false;
                break;
            case UNDEFENSE:
                this.isDefending = true;
                break;
            default:
                System.out.println("bro wtf my event isn't an event");
                break;
        }
        return event;
    }

    public int getNumber() { return number; }
    public String getScouter() { return scouter; }
    public int getRoundNumber() { return roundNumber; }
    public int getBallsScoredL() { return ballsScoredL; }
    public int getBallsScoredM() { return ballsScoredM; }
    public int getBallsScoredH() { return ballsScoredH; }
    public int getDiscsScoredL() { return discsScoredL; }
    public int getDiscsScoredM() { return discsScoredM; }
    public int getDiscsScoredH() { return discsScoredH; }
    public int getBallsDropped() { return ballsDropped; }
    public int getDiscsDropped() { return discsDropped; }
    public boolean getIsDefending() { return isDefending; }
    public boolean getHasBall() { return hasBall; }
    public boolean getHasDisc() { return hasDisc; }
    public Stack<Long> getAllTimesAsStack() { return eventLog.getAllTimesAsStack(); }
    public Stack<Event> getAllEventsAsStack() { return eventLog.getAllEventsAsStack(); }
    public HashMap<Long, Event> getEventLogCopy() {
        return eventLog.getTimestampsCopy();
    }
    /** Returns true if the robot is in control of a game piece. */
    public boolean getHasPiece() { return (this.hasBall || this.hasDisc); }
}
