package com.ucd.user.weatherfitness;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn = (Button) findViewById(R.id.button1);
        btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View v) {
                Intent newIntent = new Intent(MainActivity.this, MapsActivity.class);
                MainActivity.this.startActivity(newIntent);}
        });

        Button btn1 = (Button) findViewById(R.id.button2);
        btn1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View v) {

        }});
        Button btn2 = (Button) findViewById(R.id.button3);
        btn2.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick (View v) {



                }});
        Button btn3 = (Button) findViewById(R.id.button4);
        btn3.setOnClickListener(new View.OnClickListener(){
        @Override
        public void onClick (View v) {
            Intent newIntent = new Intent(MainActivity.this, HistoryActivity.class);
            MainActivity.this.startActivity(newIntent);
                    }});


    }
}
