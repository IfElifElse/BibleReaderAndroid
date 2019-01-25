package com.example.chaos.depspasescouting;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private Intent gameScoringIntent;

    public static final String TEAM_NUMBER = "teamNumber";
    public static final String ROUND_NUMBER = "roundNumber";
    public static final String SCOUTER_NAME = "scouterName";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gameScoringIntent  = new Intent(this, ClimbedScreen.class);
    }

    public void pressedGo(View view) {
        TextView teamNumberInput = findViewById(R.id.teamNumberInput);
        String teamNumber = teamNumberInput.getText().toString();
        gameScoringIntent.putExtra(TEAM_NUMBER, Integer.parseInt(teamNumber));
        TextView roundNumberInput = findViewById(R.id.roundNumberInput);
        String roundNumber = roundNumberInput.getText().toString();
        gameScoringIntent.putExtra(ROUND_NUMBER, Integer.parseInt(roundNumber));
        startActivity(gameScoringIntent);
    }
}
