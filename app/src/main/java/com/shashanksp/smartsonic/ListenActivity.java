package com.shashanksp.smartsonic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class ListenActivity extends AppCompatActivity {
    Button backtoscanBtn;
    TextView timerTV;
    public int seconds = 5;
    public int minutes =0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listen);
        backtoscanBtn = findViewById(R.id.backtoscanBtn);
        timerTV = findViewById(R.id.timerTV);
        Intent i = new Intent(ListenActivity.this,HomeScanActivity.class);

        backtoscanBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(i);
                finish();
            }
        });

        Timer t = new Timer();
        //Set the schedule function and rate
        t.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(minutes<0){
                            timerTV.setText("Time up");
                            t.cancel();
                        } else{
                            timerTV.setText(String.valueOf(minutes)+":"+String.valueOf(seconds)+" mins left");
                            seconds -= 1;

                            if(seconds == 0) {
                                timerTV.setText(String.valueOf(minutes)+":"+String.valueOf(seconds)+" mins left");
                                seconds=60;
                                minutes=minutes-1;
                            }
                        }

                    }
                });
            }

        }, 0, 1000);

    }
}