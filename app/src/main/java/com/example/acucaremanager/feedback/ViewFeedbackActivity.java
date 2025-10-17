package com.example.acucaremanager.feedback;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.acucaremanager.R;
import com.example.acucaremanager.adapters.FeedbackAdapter;
import com.example.acucaremanager.pojo.FeedbackPojo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class ViewFeedbackActivity extends AppCompatActivity {

    RecyclerView recyclerViewFeedback;
    FeedbackAdapter feedbackAdapter;
    ArrayList<FeedbackPojo> feedbackList = new ArrayList<>();
    DatabaseReference drFeedback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_feedback); // ✅ Make sure file name matches

        recyclerViewFeedback = findViewById(R.id.recyclerViewFeedback);
        recyclerViewFeedback.setLayoutManager(new LinearLayoutManager(this));

        // ✅ Firebase Reference
        drFeedback = FirebaseDatabase.getInstance("https://acucaremanager111-default-rtdb.asia-southeast1.firebasedatabase.app/")
                .getReference("FeedbackLog");

        loadFeedbackData();
    }

    private void loadFeedbackData() {
        drFeedback.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                feedbackList.clear();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    FeedbackPojo feedback = ds.getValue(FeedbackPojo.class);
                    if (feedback != null) {
                        feedbackList.add(feedback);
                    }
                }

                // ✅ Sort to show latest feedback at top
                Collections.reverse(feedbackList);

                feedbackAdapter = new FeedbackAdapter(feedbackList);
                recyclerViewFeedback.setAdapter(feedbackAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ViewFeedbackActivity.this, "Failed to load feedback", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
