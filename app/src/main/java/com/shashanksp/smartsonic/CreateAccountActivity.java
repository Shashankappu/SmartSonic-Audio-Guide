package com.shashanksp.smartsonic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class CreateAccountActivity extends AppCompatActivity {
    Button registerbtn;
    TextView signinbtn;
    EditText guideIDEdt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        registerbtn = findViewById(R.id.register_button);
        signinbtn = findViewById(R.id.Signin_txtbtn);
        guideIDEdt = findViewById(R.id.GuideID_edt);
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


        final RadioGroup radio = (RadioGroup) findViewById(R.id.radiogrp);
        radio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                View radioButton = radio.findViewById(checkedId);
                int index = radio.indexOfChild(radioButton);


                switch (index) {
                    case 0: // first button
                        guideIDEdt.setVisibility(View.VISIBLE);
                        Toast.makeText(getApplicationContext(), "Selected button number " + index, Toast.LENGTH_SHORT).show();
                        break;
                    case 1: // secondbutton
                        guideIDEdt.setVisibility(View.GONE);
                        Toast.makeText(getApplicationContext(), "Selected button number " + index, Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });

    }
}