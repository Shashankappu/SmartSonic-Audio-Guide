package com.shashanksp.smartsonic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SubscriptionActivity extends AppCompatActivity {
    Button payBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscription);
        payBtn = findViewById(R.id.payBtn);

        payBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SubscriptionActivity.this,PaymentGatewayActivity.class);
                startActivity(i);
            }
        });
    }
}