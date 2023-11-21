package com.shashanksp.smartsonic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EnterContentActivity extends AppCompatActivity {
    private DatabaseReference databaseReference;
    private String guideID,ArtID;
    EditText ArtName,details;
    Button submitBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_content);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        ArtName = findViewById(R.id.Artname);
        details = findViewById(R.id.detailsEdt);
        submitBtn = findViewById(R.id.submitBtn);

        guideID = getIntent().getStringExtra("guideID");
        ArtID = getIntent().getStringExtra("ArtID");
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (guideID.isEmpty() || ArtID.isEmpty() || details.getText().toString().isEmpty()) {
                    // Handle the case where any field is empty
                    Toast.makeText(EnterContentActivity.this,"Enter the details",Toast.LENGTH_LONG).show();
                }else{
                    DatabaseReference artReference = databaseReference.child(ArtID).child(guideID);
                    artReference.child("Details").setValue(details.getText().toString());
                    Toast.makeText(EnterContentActivity.this,"Details Entered Successfully",Toast.LENGTH_LONG).show();
                    Log.d("Details","sidithu Bhaiyya");
                    Intent i = new Intent(EnterContentActivity.this,HomeScanActivity.class);
                    startActivity(i);
                    finish();
                }
            }
        });
    }
}