package com.example.chaos.depspasescouting;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.content.Intent;

import com.example.chaos.depspasescouting.robots.EndingState;
import com.example.chaos.depspasescouting.robots.Robot;

public class ClimbedScreen extends AppCompatActivity {

    private Intent loginIntent;
    private Intent scoringIntent;
    private Robot robot;
    
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_climbed_screen);
        this.robot = new Robot(1,"jaga",10);
    }


    public void onClimbSelect(View v) {
        if (((RadioButton) v).isChecked()) {
            switch (v.getId()) {
                case R.id.noClimb:
                    this.robot.setEndingState(EndingState.NONE);
                    break;
                case R.id.lowClimb:
                    this.robot.setEndingState(EndingState.LOW);
                    break;
                case R.id.midClimb:
                    this.robot.setEndingState(EndingState.MIDDLE);
                    break;
                case R.id.highClimb:
                    this.robot.setEndingState(EndingState.HIGH);
                    break;
            }
        }
    }

    public void onHelpedSelect(View v) {
        if(((RadioButton) v).isChecked()) {
            switch(v.getId()) {
                case R.id.noHelp:
                    this.robot.setHelpedState(EndingState.NONE);
                    break;
                case R.id.midHelp:
                    this.robot.setHelpedState(EndingState.MIDDLE);
                    break;
                case R.id.highHelp:
                    this.robot.setHelpedState(EndingState.HIGH);
                    break;
            }
        }
    }

    public void onRankSelect(View v) {
        if(((RadioButton) v).isChecked()) {
            switch (v.getId()) {
                case R.id.oneRating:
                    this.robot.setRank(1);
                    break;
                case R.id.twoRating:
                    this.robot.setRank(2);
                    break;
                case R.id.threeRating:
                    this.robot.setRank(3);
                    break;
            }
        }
    }

    public void onEnding(View v) {
        loginIntent = new Intent ( this, MainActivity.class);
        startActivity(loginIntent);
    }
    
    /**
     * Called when the back button is pressed.
     * Switches the screen back to the game scoring screen.
     * @param v
     */
    public void onGoBack(View v) {
        scoringIntent = new Intent(this, GameScoring.class);
        startActivity(scoringIntent);
    }
}
