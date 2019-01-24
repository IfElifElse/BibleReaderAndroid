package com.example.chaos.depspasescouting;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewAnimator;
import android.widget.ViewSwitcher;

import com.example.chaos.depspasescouting.robots.Event;
import com.example.chaos.depspasescouting.robots.Robot;
import com.example.chaos.depspasescouting.robots.TimeLog;

import java.util.Stack;

public class GameScoring extends AppCompatActivity {
    private Robot robot;

    private ViewAnimator changingLayout;

    private TextView teamNumberDisplay;
    private TextView roundNumberDisplay;

    private Button defenseButton;

    private LinearLayout undoLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int teamNumber = getIntent().getIntExtra(MainActivity.TEAM_NUMBER, 666666);
        String scouterName = getIntent().getStringExtra(MainActivity.SCOUTER_NAME);
        int roundNumber = getIntent().getIntExtra(MainActivity.ROUND_NUMBER, 666666);

        robot = new Robot(teamNumber, scouterName, roundNumber);

        setContentView(R.layout.activity_game_scoring);

        changingLayout = findViewById(R.id.scoringLayoutSwitcher);

        teamNumberDisplay = findViewById(R.id.teamNumberDisplay);
        roundNumberDisplay = findViewById(R.id.roundNumberDisplay);
        defenseButton = findViewById(R.id.defenseButton);
        undoLayout = findViewById(R.id.undoLayout);

        teamNumberDisplay.setText("Team Number: " + teamNumber);
        roundNumberDisplay.setText("Round Number: " + roundNumber);

    }

    /**
     * Called when the start with disc button in the start round layout is pressed.
     */
    public void startBallButtonPressed(View v) {
        this.robot.startRound(Event.BALL);
        this.changingLayout.showNext();
    }
    /**
     * Called when the start with disc button in the start round layout is pressed.
     */
    public void startDiscButtonPressed(View v) {
        this.robot.startRound(Event.DISC);
        this.changingLayout.showNext();
    }
    /**
     * Called when the start with nothing button in the start round layout is pressed.
     */
    public void startNothingButtonPressed(View v) {
        this.robot.startRound(Event.START);
        // skip over the scoring layout because we haven't "picked up" a piece to score yet
        this.changingLayout.showNext();
        this.changingLayout.showNext();
    }

    /**
     * Called when the ball button in the pick up layout is pressed.
     */
    public void pickedUpBallButtonPressed(View v) {
        this.robot.setHasBall();
        this.changingLayout.showPrevious();
    }
    /**
     * Called when the disc button in the pick up layout is pressed.
     */
    public void pickedUpDiscButtonPressed(View v) {
        this.robot.setHasDisc();
        this.changingLayout.showPrevious();
    }

    /**
     * Called when the scored high button in the scoring layout is pressed.
     */
    public void scoredHButton(View v) {
        if (robot.getHasBall()) {
            this.robot.incBallsScoredH();
        }
        else if (robot.getHasDisc()) {
            this.robot.incDiscsScoredH();
        }
        else {
            System.out.println("THIS IS BAD: scoredHButton was pressed even though the robot does not have a game piece");
        }
        this.changingLayout.showNext(); // go to the pick up layout
    }
    /**
     * Called when the scored middle button in the scoring layout is pressed.
     */
    public void scoredMButton(View v) {
        if (robot.getHasBall()) {
            this.robot.incBallsScoredM();
        }
        else if (robot.getHasDisc()) {
            this.robot.incDiscsScoredM();
        }
        else {
            System.out.println("THIS IS BAD: scoredMButton was pressed even though the robot does not have a game piece");
        }
        this.changingLayout.showNext(); // go to the pick up layout
    }
    /**
     * Called when the scored low button in the scoring layout is pressed.
     */
    public void scoredLButton(View v) {
        if (robot.getHasBall()) {
            this.robot.incBallsScoredL();
        }
        else if (robot.getHasDisc()) {
            this.robot.incDiscsScoredL();
        }
        else {
            System.out.println("THIS IS BAD: scoredLButton was pressed even though the robot does not have a game piece");
        }
        this.changingLayout.showNext(); // go to the pick up layout
    }
    /**
     * Called when the drop button in the scoring layout is pressed.
     */
    public void droppedButton(View v) {
        if (robot.getHasBall()) {
            this.robot.setDroppedBall();
        }
        else if (robot.getHasDisc()) {
            this.robot.setDroppedDisc();
        }
        else {
            System.out.println("THIS IS BAD: droppedButton was pressed even though the robot does not have a game piece");
        }
        this.showNext(); // go to the pick up layout
    }

    /**
     * Called when the defense button is pressed.
     */
    public void defenseButton(View v) {
        boolean defending = ((Button) v).getText().equals("Protec");
        if (defending) {
            ((Button) v).setText("Attac");
            this.robot.setUndefending();
        }
        else {
            ((Button) v).setText("Protec");
            this.robot.setDefending();
        }
    }

    private void showNext() {
        this.changingLayout.showNext();
        this.updateUndoLog();
    }
    private void showPrevious() {
        this.changingLayout.showPrevious();
        this.updateUndoLog();
    }
    private void updateUndoLog() {
        // clear the undo layout
        final int childCount = undoLayout.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View v = undoLayout.getChildAt(0);
            undoLayout.removeView(v);
        }
        //get the eventlog and update the undo layout with its contents.
        Stack<Long> allTimes = this.robot.getAllTimesAsStack();
        Stack<Event> allEvents = this.robot.getAllEventsAsStack();

        while (allTimes.peek() != null) {
            TextView newEvent = new TextView();
        }
    }

    /**
     * Called when the undo button is pressed.
     */
    public void undoButton(View v) {
        this.robot.undoLastAction();
    }
}
