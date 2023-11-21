package com.shashanksp.smartsonic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class GuidelistActivity extends AppCompatActivity implements GuideAdapter.OnGuideClickListener {
    Button continueBtn;
    private RecyclerView recyclerView;
    private GuideAdapter guideAdapter;
    private List<String> guideIds;
    String artId;

    private DatabaseReference databaseReference;

    public GuidelistActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guidelist);
        artId = getIntent().getStringExtra("ArtID");
        continueBtn = findViewById(R.id.continueBtn);
        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(GuidelistActivity.this,SubscriptionActivity.class);
                startActivity(i);
            }
        });
        guideAdapter = new GuideAdapter(guideIds, this, artId);
        recyclerView.setAdapter(guideAdapter);

    }

    @Override
    public void onGuideClick(String artId ,String guideId) {
        retrieveDetails(artId, guideId);
    }
    private void retrieveDetails(String artId, String guideId) {
        // Build the database reference based on the provided art ID and guide ID
        DatabaseReference detailsReference = FirebaseDatabase.getInstance().getReference()
                .child(artId)
                .child(guideId);

        detailsReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String details = dataSnapshot.child("Details").getValue(String.class);

                    // Use the details as needed (e.g., display in UI)
                    // For example, you might update a TextView or log the details
                    Log.d("Details", "Details for Guide " + guideId + ": " + details);
                } else {
                    Log.d("Details", "Details for Guide " + guideId + " do not exist.");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle errors, if any
            }
        });
    }

}