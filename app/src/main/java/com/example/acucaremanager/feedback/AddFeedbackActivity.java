package com.example.acucaremanager.feedback;

import android.os.Bundle;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class AddFeedbackActivity extends AppCompatActivity {

    Spinner spinnerFeedbackClient;
    EditText etFeedbackNote;
    AppCompatButton btnSaveFeedback;
    DatabaseReference drClients, drFeedback;
    ArrayList<String> clientNames = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_feedback); // ✅ XML must match

        // Firebase references
        drClients = FirebaseDatabase.getInstance("https://acucaremanager111-default-rtdb.asia-southeast1.firebasedatabase.app/")
                .getReference("Clients");
        drFeedback = FirebaseDatabase.getInstance("https://acucaremanager111-default-rtdb.asia-southeast1.firebasedatabase.app/")
                .getReference("FeedbackLog");

        // Connect XML widgets
        spinnerFeedbackClient = findViewById(R.id.spinnerFeedbackClient);
        etFeedbackNote = findViewById(R.id.etFeedbackNote);
        btnSaveFeedback = findViewById(R.id.btnSaveFeedback);

        // Load client names into Spinner
        loadClientNames();

        // ✅ Save Feedback Button Logic
        btnSaveFeedback.setOnClickListener(v -> saveFeedback());
    }

    private void loadClientNames() {
        clientNames.clear();
        clientNames.add("Select Client");

        drClients.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    ClientPojo cp = ds.getValue(ClientPojo.class);
                    if (cp != null) {
                        clientNames.add(cp.getName());
                    }
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<>(AddFeedbackActivity.this, android.R.layout.simple_spinner_item, clientNames);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerFeedbackClient.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }

    private void saveFeedback() {
        String clientName = spinnerFeedbackClient.getSelectedItem().toString();
        String feedbackText = etFeedbackNote.getText().toString().trim();

        if (clientName.equals("Select Client") || feedbackText.isEmpty()) {
            Toast.makeText(this, "Please select a client and enter feedback", Toast.LENGTH_SHORT).show();
            return;
        }

        // ✅ Generate timestamp automatically
        String timestamp = new SimpleDateFormat("dd-MM-yyyy | hh:mm a", Locale.getDefault())
                .format(Calendar.getInstance().getTime());

        // ✅ Unique feedback ID
        String feedbackId = drFeedback.push().getKey();

        FeedbackPojo feedback = new FeedbackPojo(feedbackId, clientName, feedbackText, timestamp);

        drFeedback.child(feedbackId).setValue(feedback).addOnCompleteListener(task -> {
            Toast.makeText(AddFeedbackActivity.this, "Feedback Saved", Toast.LENGTH_SHORT).show();
            etFeedbackNote.setText("");
            spinnerFeedbackClient.setSelection(0);
        });
    }
}
