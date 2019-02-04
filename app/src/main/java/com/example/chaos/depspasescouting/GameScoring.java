package com.example.chaos.depspasescouting;

import android.graphics.Color;
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;

public class GameScoring extends AppCompatActivity {
    private Robot robot;

    private ViewAnimator changingLayout;

    private TextView teamNumberDisplay;
    private TextView roundNumberDisplay;

    private Button defenseButton;

    private LinearLayout undoLayout;
    
    /**
     * Instantiation function (like a constructor).
     * @param savedInstanceState - who tf knows
     */
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
        updateUndoLog();

        teamNumberDisplay.setText("Team Number: " + teamNumber);
        roundNumberDisplay.setText("Round Number: " + roundNumber);

    }

    /**
     * Called when the start with disc button in the start round layout is pressed.
     */
    public void startBallButton(View v) {
        this.robot.startRound(Event.BALL);
        this.showNext();
    }
    /**
     * Called when the start with disc button in the start round layout is pressed.
     */
    public void startDiscButton(View v) {
        this.robot.startRound(Event.DISC);
        this.showNext();
    }
    /**
     * Called when the start with nothing button in the start round layout is pressed.
     */
    public void startNothingButton(View v) {
        this.robot.startRound(Event.START);
        // skip over the scoring layout because we haven't "picked up" a piece to score yet
        this.showNext();
        this.showNext();
    }

    /**
     * Called when the ball button in the pick up layout is pressed.
     */
    public void pickedUpBallButton(View v) {
        this.robot.setHasBall();
        this.showPrevious();
    }
    /**
     * Called when the disc button in the pick up layout is pressed.
     */
    public void pickedUpDiscButton(View v) {
        this.robot.setHasDisc();
        this.showPrevious();
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
        this.showNext(); // go to the pick up layout
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
        this.showNext(); // go to the pick up layout
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
        this.showNext(); // go to the pick up layout
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
        this.updateUndoLog();
    }
    
    /**
     * Called when the undo button is pressed.
     */
    public void undoButton(View v) {
        Event event = this.robot.undoLastAction();
        this.updateUndoLog();
        if (event == null) return;
        View currentV = this.changingLayout.getCurrentView();
        int vID = currentV.getId();
        switch (event) {
            case BALL:
            case DISC:
                showNext();
                break;
            case SCORE_BALL_L:
            case SCORE_BALL_M:
            case SCORE_BALL_H:
            case DROP_BALL:
            case SCORE_DISC_L:
            case SCORE_DISC_M:
            case SCORE_DISC_H:
            case DROP_DISC:
                showPrevious();
                break;
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
            View v = undoLayout.getChildAt(0); // this is zero because we can't remove things from a list as we iterate through it
            this.undoLayout.removeView(v);
        }
        //get the eventlog and update the undo layout with its contents.
        HashMap<Long, Event> eventLog = this.robot.getEventLogCopy();
        ArrayList<Long> allTimes = new ArrayList<>(eventLog.keySet());
        Collections.sort(allTimes);
        Collections.reverse(allTimes);
        Long startTime = 0L;
        for (Long time : allTimes) {
            if (eventLog.get(time) == Event.START) {
                startTime = time;
                break;
            }
        }
        
        TextView newEvent;

        for (Long time : allTimes) {
            Event event = eventLog.get(time);
            
            newEvent = new TextView(this);
            newEvent.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
            newEvent.setText(event.name() + " at " + (time - startTime));
            newEvent.setTextColor(Color.WHITE);
            newEvent.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            newEvent.setPadding(1, 1, 1, 1);
            
            this.undoLayout.addView(newEvent);
        }
    }

}
