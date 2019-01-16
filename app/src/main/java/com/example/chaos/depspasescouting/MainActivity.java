package com.example.chaos.depspasescouting;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends AppCompatActivity {
    private Intent gameScoringIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gameScoringIntent  = new Intent(this, GameScoring.class);
    }

    public void pressedGo(View view) {
        startActivity(gameScoringIntent);
    }
}
