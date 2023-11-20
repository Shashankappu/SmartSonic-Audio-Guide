package com.shashanksp.smartsonic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CreateAccountActivity extends AppCompatActivity {
    Button registerbtn;
    TextView signinbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        registerbtn = findViewById(R.id.register_button);
        signinbtn = findViewById(R.id.Signin_txtbtn);

        registerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(CreateAccountActivity.this,HomeScanActivity.class);
                startActivity(i);
            }
        });

        signinbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(CreateAccountActivity.this,SignInActivity.class);
                startActivity(i);
            }
        });


    }
}