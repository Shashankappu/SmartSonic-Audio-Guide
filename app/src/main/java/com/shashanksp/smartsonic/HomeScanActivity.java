package com.shashanksp.smartsonic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import android.Manifest;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.zxing.Result;

public class HomeScanActivity extends AppCompatActivity {
    private CodeScanner mCodeScanner;
    Button scanBtn;
    ImageView logoutBtn;
    boolean isGuide;
    private FirebaseAuth mAuth;
    private String artId,guideId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_scan);
        permissionCheck();
        scanBtn = findViewById(R.id.scanBtn);
        logoutBtn = findViewById(R.id.logoutbtn);
        mAuth = FirebaseAuth.getInstance();

        CodeScannerView scannerView = findViewById(R.id.scanner_view);
        mCodeScanner = new CodeScanner(this, scannerView);
        isGuide = getIntent().getBooleanExtra("isGuide",false);
        guideId = getIntent().getStringExtra("guideId");
        mCodeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull final Result result) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(HomeScanActivity.this, result.getText(), Toast.LENGTH_SHORT).show();
                        artId = result.getText();
                    }
                });
            }
        });
        scannerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCodeScanner.startPreview();
            }
        });

        scanBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isGuide){
                    Intent i = new Intent(HomeScanActivity.this,EnterContentActivity.class);
                    i.putExtra("guideId",guideId);
                    i.putExtra("artId",artId);
                    startActivity(i);
                    finish();
                } else {
                    Intent i = new Intent(HomeScanActivity.this, GuidelistActivity.class);
                    i.putExtra("artId",artId);
                    startActivity(i);
                    finish();
                }
            }
        });

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Clear the login status in SharedPreferences
                SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("isLoggedIn", false);
                editor.apply();

                // Sign out the current user
                mAuth.signOut();

                // Redirect to the LoginActivity
                Intent intent = new Intent(HomeScanActivity.this, StartActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    protected void permissionCheck() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if(checkSelfPermission(Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.CAMERA},12);
            }
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode!=12){
            permissionCheck();
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mCodeScanner.startPreview();
    }

    @Override
    protected void onPause() {
        mCodeScanner.releaseResources();
        super.onPause();
    }
}