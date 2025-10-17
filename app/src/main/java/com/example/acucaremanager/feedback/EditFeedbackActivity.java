package com.example.acucaremanager.feedback;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.acucaremanager.R;
import com.example.acucaremanager.pojo.ClientPojo;
import com.example.acucaremanager.pojo.FeedbackPojo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class EditFeedbackActivity extends AppCompatActivity {

    Spinner spinnerSelectFeedback, spinnerEditFeedbackClient;
    EditText etEditFeedbackNote;
    AppCompatButton btnUpdateFeedback;
    DatabaseReference drFeedback, drClients;

    ArrayList<String> feedbackDisplayList = new ArrayList<>();
    ArrayList<String> feedbackIdList = new ArrayList<>();
    ArrayList<String> clientNamesList = new ArrayList<>();

    String selectedFeedbackId = "";
    String originalTimestamp = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_feedback);

        // Firebase References
        drFeedback = FirebaseDatabase.getInstance("https://acucaremanager111-default-rtdb.asia-southeast1.firebasedatabase.app/")
                .getReference("FeedbackLog");
        drClients = FirebaseDatabase.getInstance("https://acucaremanager111-default-rtdb.asia-southeast1.firebasedatabase.app/")
                .getReference("Clients");

        // Connect XML Views
        spinnerSelectFeedback = findViewById(R.id.spinnerSelectFeedback);
        spinnerEditFeedbackClient = findViewById(R.id.spinnerEditFeedbackClient);
        etEditFeedbackNote = findViewById(R.id.etEditFeedbackNote);
        btnUpdateFeedback = findViewById(R.id.btnUpdateFeedback);

        // Load dropdown data
        loadClientNames();
        loadFeedbackList();

        // When a feedback entry is selected → Load its details
        spinnerSelectFeedback.setOnItemSelectedListener(new android.widget.AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(android.widget.AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {
                    selectedFeedbackId = feedbackIdList.get(position);
                    loadFeedbackDetails(selectedFeedbackId);
                }
            }
            @Override public void onNothingSelected(android.widget.AdapterView<?> parent) {}
        });

        // Update logic
        btnUpdateFeedback.setOnClickListener(v -> updateFeedback());
    }

    private void loadClientNames() {
        clientNamesList.clear();
        clientNamesList.add("Select Client");

        drClients.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    ClientPojo cp = ds.getValue(ClientPojo.class);
                    if (cp != null) {
                        clientNamesList.add(cp.getName());
                    }
                }
                ArrayAdapter<String> clientAdapter = new ArrayAdapter<>(EditFeedbackActivity.this,
                        android.R.layout.simple_spinner_item, clientNamesList);
                clientAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerEditFeedbackClient.setAdapter(clientAdapter);
            }

            @Override public void onCancelled(@NonNull DatabaseError error) {}
        });
    }

    private void loadFeedbackList() {
        feedbackDisplayList.clear();
        feedbackIdList.clear();

        feedbackDisplayList.add("Select Feedback");
        feedbackIdList.add("");

        drFeedback.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    FeedbackPojo feedback = ds.getValue(FeedbackPojo.class);
                    if (feedback != null) {
                        feedbackDisplayList.add(feedback.getClientName() + " | " + feedback.getTimestamp());
                        feedbackIdList.add(feedback.getFeedbackId());
                    }
                }

                ArrayAdapter<String> feedbackAdapter = new ArrayAdapter<>(EditFeedbackActivity.this,
                        android.R.layout.simple_spinner_item, feedbackDisplayList);
                feedbackAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerSelectFeedback.setAdapter(feedbackAdapter);
            }

            @Override public void onCancelled(@NonNull DatabaseError error) {}
        });
    }

    private void loadFeedbackDetails(String feedbackId) {
        drFeedback.child(feedbackId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                FeedbackPojo feedback = snapshot.getValue(FeedbackPojo.class);
                if (feedback != null) {
                    etEditFeedbackNote.setText(feedback.getFeedbackText());
                    originalTimestamp = feedback.getTimestamp(); // ✅ Keep original time

                    // Set client spinner position
                    int index = clientNamesList.indexOf(feedback.getClientName());
                    if (index != -1) spinnerEditFeedbackClient.setSelection(index);
                }
            }
            @Override public void onCancelled(@NonNull DatabaseError error) {}
        });
    }

    private void updateFeedback() {
        if (selectedFeedbackId.isEmpty()) {
            Toast.makeText(this, "Please select feedback to edit", Toast.LENGTH_SHORT).show();
            return;
        }

        String updatedClient = spinnerEditFeedbackClient.getSelectedItem().toString();
        String updatedText = etEditFeedbackNote.getText().toString().trim();

        if (updatedClient.equals("Select Client") || updatedText.isEmpty()) {
            Toast.makeText(this, "Please fill required fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Update without changing timestamp
        FeedbackPojo updatedFeedback = new FeedbackPojo(selectedFeedbackId, updatedClient, updatedText, originalTimestamp);

        drFeedback.child(selectedFeedbackId).setValue(updatedFeedback).addOnCompleteListener(task -> {
            Toast.makeText(EditFeedbackActivity.this, "Feedback Updated Successfully", Toast.LENGTH_SHORT).show();
            spinnerSelectFeedback.setSelection(0);
            etEditFeedbackNote.setText("");
        });
    }
}
