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

import com.example.chaos.depspasescouting.robots.Robot;

public class GameScoring extends AppCompatActivity {
    private Robot robot;

    private LinearLayout changingLayout;

    private TextView teamNumberDisplay;
    private TextView roundNumberDisplay;

    // for use in the startLayout
    private Button startBallButton;
    private Button startDiscButton;
    private LayoutParams startBallButtonParams;
    private LayoutParams startDiscButtonParams;

    // for use in the pickupLayout
    private Button pickedUpBallButton;
    private Button pickedUpDiscButton;
    private LayoutParams pickedUpBallButtonParams;
    private LayoutParams pickedUpDiscButtonParams;

    // for use in the scoringLayout
    private Button scoredHButton;
    private Button scoredMButton;
    private Button scoredLButton;
    private Button droppedButton;

    private LayoutParams

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int teamNumber = getIntent().getIntExtra(MainActivity.TEAM_NUMBER, 666666);
        String scouterName = getIntent().getStringExtra(MainActivity.SCOUTER_NAME);
        int roundNumber = getIntent().getIntExtra(MainActivity.ROUND_NUMBER, 666666);

        robot = new Robot(teamNumber, scouterName, roundNumber);

        setContentView(R.layout.activity_game_scoring);

        changingLayout = findViewById(R.id.scoringLayout);

        teamNumberDisplay = findViewById(R.id.teamNumberDisplay);
        roundNumberDisplay = findViewById(R.id.roundNumberDisplay);
        teamNumberDisplay.setText("Team Number: " + teamNumber);
        roundNumberDisplay.setText("Round Number: " + roundNumber);

        setButtonParams();
    }

    private void setButtonParams() {
        startBallButtonParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        startDiscButtonParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);

        pickedUpBallButtonParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        pickedUpDiscButtonParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);

        startDiscButtonParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        startDiscButtonParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        startDiscButtonParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
    }

    private void deleteButton(View button) {
        ViewGroup parentView = (ViewGroup) button.getParent();
        parentView.removeView(button);
    }

    private void clearLayout() {
        deleteButton(startBallButton);
        deleteButton(startDiscButton);
        deleteButton(pickedUpBallButton);
        deleteButton(pickedUpDiscButton);
        deleteButton(scoredHButton);
        deleteButton(scoredMButton);
        deleteButton(scoredLButton);
    }

    private void startLayoutDisplay() {
        clearLayout();

    }
}
