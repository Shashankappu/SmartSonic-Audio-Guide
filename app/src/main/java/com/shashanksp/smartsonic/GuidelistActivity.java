package com.shashanksp.smartsonic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class GuidelistActivity extends AppCompatActivity {
    Button continueBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guidelist);
        continueBtn = findViewById(R.id.continueBtn);
        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(GuidelistActivity.this,SubscriptionActivity.class);
                startActivity(i);
            }
        });
    }
}