package com.ucd.user.weatherfitness;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.ucd.user.weatherfitness.model.FetchWeatherTask;



////comment

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView score_id = (TextView)findViewById(R.id.score_ID);
        FetchWeatherTask weatherTask = new FetchWeatherTask(score_id);
        weatherTask.execute("7778677");
// branch test
        // Added a comment - Kumar
    }


}