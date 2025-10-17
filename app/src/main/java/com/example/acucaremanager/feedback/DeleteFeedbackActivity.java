package com.example.acucaremanager.feedback;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import com.example.acucaremanager.R;
import com.example.acucaremanager.pojo.FeedbackPojo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DeleteFeedbackActivity extends AppCompatActivity {

    Spinner spinnerDeleteFeedback;
    AppCompatButton btnDeleteFeedback;
    DatabaseReference drFeedback;
    ArrayList<String> feedbackDisplayList;
    ArrayList<String> feedbackIdList;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_feedback); // ✅ Make sure it matches XML file name

        spinnerDeleteFeedback = findViewById(R.id.spinnerDeleteFeedback);
        btnDeleteFeedback = findViewById(R.id.btnDeleteFeedback);

        drFeedback = FirebaseDatabase.getInstance("https://acucaremanager111-default-rtdb.asia-southeast1.firebasedatabase.app/")
                .getReference("FeedbackLog");

        feedbackDisplayList = new ArrayList<>();
        feedbackIdList = new ArrayList<>();

        loadFeedbackIntoSpinner();

        btnDeleteFeedback.setOnClickListener(v -> deleteSelectedFeedback());
    }

    private void loadFeedbackIntoSpinner() {
        drFeedback.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                feedbackDisplayList.clear();
                feedbackIdList.clear();

                feedbackDisplayList.add("Select Feedback");
                feedbackIdList.add("");

                for (DataSnapshot ds : snapshot.getChildren()) {
                    FeedbackPojo feedback = ds.getValue(FeedbackPojo.class);
                    if (feedback != null) {
                        feedbackDisplayList.add(feedback.getClientName() + " | " + feedback.getTimestamp());
                        feedbackIdList.add(feedback.getFeedbackId());
                    }
                }

                adapter = new ArrayAdapter<>(DeleteFeedbackActivity.this,
                        android.R.layout.simple_spinner_item, feedbackDisplayList);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerDeleteFeedback.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });
    }

    private void deleteSelectedFeedback() {
        int position = spinnerDeleteFeedback.getSelectedItemPosition();

        if (position == 0) {
            Toast.makeText(this, "Please select a feedback", Toast.LENGTH_SHORT).show();
            return;
        }

        String feedbackId = feedbackIdList.get(position);
        drFeedback.child(feedbackId).removeValue().addOnSuccessListener(aVoid -> {
            Toast.makeText(DeleteFeedbackActivity.this, "Feedback Deleted Successfully", Toast.LENGTH_SHORT).show();
            spinnerDeleteFeedback.setSelection(0);
            loadFeedbackIntoSpinner(); // Refresh
        });
    }
}
