package com.shashanksp.smartsonic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class GuidelistActivity extends AppCompatActivity implements GuideAdapter.OnGuideClickListener {
    Button continueBtn;
    private RecyclerView recyclerView;
    private GuideAdapter guideAdapter;
    private ArrayList<String> guideIds;
    String artId;

    public GuidelistActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guidelist);
        artId = getIntent().getStringExtra("artId");
        continueBtn = findViewById(R.id.continueBtn);
        guideIds = new ArrayList<>();


        fetchGuideIdsFromFirebase();
        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(GuidelistActivity.this,ListenActivity.class);
                startActivity(i);
            }
        });
        recyclerView = findViewById(R.id.guideRV);  // Add this line to initialize recyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        guideAdapter = new GuideAdapter(guideIds, this, artId);
        recyclerView.setAdapter(guideAdapter);

    }
    private void fetchGuideIdsFromFirebase() {
        DatabaseReference guidesReference = FirebaseDatabase.getInstance().getReference().child(artId);
        guidesReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot guideSnapshot : dataSnapshot.getChildren()) {
                        String guideId = guideSnapshot.getKey();
                        if (guideId != null) {
                            guideIds.add(guideId);
                        }
                    }
                    // Notify the adapter that the data has changed
                    guideAdapter.notifyDataSetChanged();
                } else {
                    Log.d("GuidelistActivity", "No guides found for artId: " + artId);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle errors, if any
                Log.e("GuidelistActivity", "Error fetching guide IDs: " + databaseError.getMessage());
            }
        });
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
                    Toast.makeText(GuidelistActivity.this, "Details for Guide " + guideId + ": " + details, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(GuidelistActivity.this, "Details for Guide" + guideId + "do not exist.", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle errors, if any
            }
        });
    }

}