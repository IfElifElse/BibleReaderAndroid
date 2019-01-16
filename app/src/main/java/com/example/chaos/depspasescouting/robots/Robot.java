package com.example.chaos.depspasescouting.robots;

public class Robot {

    private int number; // the number of the team that owns the robot

    private boolean startingBonus; // true if the robot started on the 6" platform and got the points.
    private EndingState endingState; // the hab platform position that the robot ended on.
    private boolean helpedOtherRobot; // true if the robot assisted another robot in climbing the hab platform.
    private EndingState helpedState; // the hab platform position that the robot helped another robot end on.

    private int ballsScoredLo; // the number of cargo the robot has successfully scored in the lower rocket level or the cargo ship
    private int ballsScoredHi; // the number of cargo the robot has successfully stored in the upper rocket levels
    private int discsScoredLo; // the number of hatch panels the robot has successfully scored in the lower rocket level or the cargo ship
    private int discsScoredHi; // the number of hatch panels the robot has successfully scored in the upper rocket levels
    private int ballsDropped; // the number of cargo that the robot has dropped, purposefully OR unpurposefully
    private int discsDropped; // the number of hatch panels that the robot has dropped, purposefully OR unpurposefully

    private boolean isDefending; // true if the robot is on the enemy side of the field.
    private boolean hasBall; // true if the robot has a cargo in their possession.
    private boolean hasDisc; // true if the robot has a hatch panel in their possession.

    private Event lastScored; // either Event.DISC or Event.BALL - the piece the robot last successfully scored.

    private TimeLog eventLog; // a list of robot events, each mapped to a point in time.

    /**
     * Constructor for Robot class. Initializes all scoring values to zero, sets isDefending to false.
     *
     * @param number - The number of the team that made the Robot.
     * @param startsWithPiece (one of (TimeLog.BALL, TimeLog.DISC)) - The game piece with which the robot begins the round.
     */
    public Robot(int number, Event startsWithPiece) {
        this.number = number;
        this.ballsScoredLo = 0;
        this.ballsScoredHi = 0;
        this.discsScoredLo = 0;
        this.discsScoredHi = 0;
        this.ballsDropped = 0;
        this.discsDropped = 0;
        this.isDefending = false;
        this.hasBall = startsWithPiece == Event.BALL;
        this.hasDisc = startsWithPiece == Event.DISC;
        this.eventLog = new TimeLog();
    }

    /**
     * Adds an event to signify the start of a round and which piece the robot starts the round with.
     */
    public void startRound() {
        Event startsWithPiece;
        this.eventLog.log(Event.START);
        if (! this.getHasPiece()) {
            System.out.println("robot doesn't start with a piece, this is probably an issue");
            return;
        }
        startsWithPiece = this.hasBall ? Event.BALL : Event.DISC;
        this.eventLog.log(startsWithPiece);
    }

    /**
     * Sets hasBall to true and adds a BALL event to the eventLog.
     */
    public void setHasBall() {
        this.hasBall = true;
        this.eventLog.log(Event.BALL);
    }
    /**
     * Increments the number of lo scored balls and adds a SCORELO event to the eventLog.
     */
    public void incBallsScoredLo() {
        this.ballsScoredLo ++;
        this.lastScored = Event.BALL;
        this.hasBall = false;
        this.eventLog.log(Event.SCORELO);
    }
    /**
     * Increments the number of hi scored balls and adds a SCOREHI event to the eventLog.
     */
    public void incBallsScoredHi() {
        this.ballsScoredHi ++;
        this.lastScored = Event.BALL;
        this.hasBall = false;
        this.eventLog.log(Event.SCOREHI);
    }

    /**
     * Sets hasDisc to true and adds a DISC event to the eventLog.
     */
    public void setHasDisc() {
        this.hasDisc = true;
        this.eventLog.log(Event.DISC);
    }
    /**
     * Increments the number of lo scored discs and adds a SCORELO event to the eventLog.
     */
    public void incDiscsScoredLo() {
        this.discsScoredLo ++;
        this.lastScored = Event.DISC;
        this.hasDisc = false;
        this.eventLog.log(Event.SCORELO);
    }
    /**
     * Increments the number of hi scored discs and adds a SCOREHI event to the eventLog.
     */
    public void incDiscsScoredHi() {
        this.discsScoredHi ++;
        this.lastScored = Event.DISC;
        this.hasDisc = false;
        this.eventLog.log(Event.SCOREHI);
    }

    /**
     * Switches the value of isDefending.
     */
    public void toggleDefending() {
        this.isDefending = ! this.isDefending;
    }

    /**
     * Removes the last logged event and reverts any changes to the robot's scoring that would have fired due to the event.
     */
    public void undoLastAction() {
        Event event = this.eventLog.unlog();
        if (event == Event.SCORELO) {
            if (this.lastScored == Event.BALL) { this.ballsScoredLo --; }
            else if (this.lastScored == Event.DISC) { this.discsScoredLo --; }
            this.lastScored = null;
        }
        else if (event == Event.SCOREHI) {
            if (this.lastScored == Event.BALL) { this.ballsScoredHi --; }
            else if (this.lastScored == Event.DISC) { this.discsScoredHi --; }
            this.lastScored = null;
        }
        else if (event == Event.BALL) {
            this.hasBall = false;
        }
        else if (event == Event.DISC) {
            this.hasDisc = false;
        }
        else if (event == Event.DEFENSE) {
            this.isDefending = false;
        }
        else if (event == Event.UNDEFENSE) {
            this.isDefending = true;
        }
    }

    public int getNumber() { return number; }
    public int getBallsScoredLo() { return ballsScoredLo; }
    public int getBallsScoredHi() { return ballsScoredHi; }
    public int getDiscsScoredLo() { return discsScoredLo; }
    public int getDiscsScoredHi() { return discsScoredHi; }
    public int getBallsDropped() { return ballsDropped; }
    public int getDiscsDropped() { return discsDropped; }
    public boolean getIsDefending() { return isDefending; }
    public boolean getHasBall() { return hasBall; }
    public boolean getHasDisc() { return hasDisc; }
    /** Returns true if the robot is in control of a game piece. */
    public boolean getHasPiece() { return (this.hasBall || this.hasDisc); }
}
