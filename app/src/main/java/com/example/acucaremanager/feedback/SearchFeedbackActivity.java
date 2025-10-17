package com.example.acucaremanager.feedback;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
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

public class SearchFeedbackActivity extends AppCompatActivity {

    EditText etSearchFeedback;
    RecyclerView recyclerViewSearchFeedback;
    FeedbackAdapter feedbackAdapter;
    ArrayList<FeedbackPojo> feedbackList = new ArrayList<>();
    ArrayList<FeedbackPojo> filteredList = new ArrayList<>();

    DatabaseReference drFeedback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_feedback);

        // Connect XML
        etSearchFeedback = findViewById(R.id.etSearchFeedback);
        recyclerViewSearchFeedback = findViewById(R.id.recyclerViewSearchFeedback);

        recyclerViewSearchFeedback.setLayoutManager(new LinearLayoutManager(this));
        feedbackAdapter = new FeedbackAdapter(filteredList);
        recyclerViewSearchFeedback.setAdapter(feedbackAdapter);

        // Firebase Reference
        drFeedback = FirebaseDatabase.getInstance("https://acucaremanager111-default-rtdb.asia-southeast1.firebasedatabase.app/")
                .getReference("FeedbackLog");

        // Load initial data
        loadFeedbackData();

        // Live Search Functionality
        etSearchFeedback.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void afterTextChanged(Editable s) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterFeedback(s.toString());
            }
        });
    }

    private void loadFeedbackData() {
        drFeedback.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                feedbackList.clear();
                filteredList.clear();

                for (DataSnapshot ds : snapshot.getChildren()) {
                    FeedbackPojo feedback = ds.getValue(FeedbackPojo.class);
                    if (feedback != null) {
                        feedbackList.add(feedback);
                    }
                }
                filteredList.addAll(feedbackList); // Show all by default
                feedbackAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(SearchFeedbackActivity.this, "Failed to load data", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void filterFeedback(String query) {
        filteredList.clear();
        for (FeedbackPojo item : feedbackList) {
            if (item.getClientName().toLowerCase().contains(query.toLowerCase())
                    || item.getFeedbackText().toLowerCase().contains(query.toLowerCase())
                    || item.getTimestamp().toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(item);
            }
        }
        feedbackAdapter.notifyDataSetChanged();
    }
}
