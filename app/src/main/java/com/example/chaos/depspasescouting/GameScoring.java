package com.example.chaos.depspasescouting;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.example.chaos.depspasescouting.robots.Robot;

public class GameScoring extends AppCompatActivity {
    private Robot robot;

    // for use in the startLayout
    private Button startBallButton;
    private Button startDiscButton;

    // for use in the pickupLayout
    private Button pickedUpBallButton;
    private Button pickedUpDiscButton;

    // for use in the scoringLayout
    private Button scoredHiButton;
    private Button scoredMdButton;
    private Button scoredLoButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int teamNumber = getIntent().getIntExtra(MainActivity.TEAM_NUMBER, 666666);
        String scouterName = getIntent().getStringExtra(MainActivity.SCOUTER_NAME);
        int roundNumber = getIntent().getIntExtra(MainActivity.ROUND_NUMBER, 666666);

        robot = new Robot(teamNumber, scouterName, roundNumber);

        setContentView(R.layout.activity_game_scoring);

        TextView teamNumberDisplay = findViewById(R.id.teamNumberDisplay);
        teamNumberDisplay.setText("Team Number: " + teamNumber);
        TextView roundNumberDisplay = findViewById(R.id.roundNumberDisplay);
        roundNumberDisplay.setText("Round Number: " + roundNumber);
    }

    private void clearLayout() {
        startBallButton.
    }

    private void startLayoutDisplay() {

    }
}
