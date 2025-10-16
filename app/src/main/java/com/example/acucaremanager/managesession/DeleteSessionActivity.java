package com.example.acucaremanager.managesession;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.acucaremanager.R;
import com.example.acucaremanager.pojo.SessionPojo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DeleteSessionActivity extends AppCompatActivity {

    ArrayList<String> sessionDisplayList;
    ArrayList<String> sessionIdList; // to store sessionId for deletion
    Spinner spinnerDeleteSession;
    DatabaseReference drSessions;
    AppCompatButton btnDeleteSession;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_session);

        // Firebase reference
        drSessions = FirebaseDatabase.getInstance("https://acucaremanager111-default-rtdb.asia-southeast1.firebasedatabase.app/")
                .getReference("Sessions");

        spinnerDeleteSession = findViewById(R.id.spinnerDeleteSession);
        btnDeleteSession = findViewById(R.id.btnDeleteSession);

        sessionDisplayList = new ArrayList<>();
        sessionIdList = new ArrayList<>();

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, sessionDisplayList);
        adapter.setDropDownViewResource(R.layout.spinner_item);

        loadSessionsIntoSpinner();

        btnDeleteSession.setOnClickListener(view -> deleteSelectedSession());
    }

    private void loadSessionsIntoSpinner() {
        drSessions.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                sessionDisplayList.clear();
                sessionIdList.clear();

                sessionDisplayList.add("Select Session"); // Default option
                sessionIdList.add(""); // Align index

                for (DataSnapshot ds : snapshot.getChildren()) {
                    SessionPojo session = ds.getValue(SessionPojo.class);
                    if (session != null) {
                        String displayText = session.getClientName() + " - " + session.getDate() + " " + session.getTime();
                        sessionDisplayList.add(displayText);
                        sessionIdList.add(session.getSessionId());
                    }
                }
                spinnerDeleteSession.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }

    private void deleteSelectedSession() {
        int position = spinnerDeleteSession.getSelectedItemPosition();

        if (position == 0) {
            Toast.makeText(this, "Please select a session", Toast.LENGTH_SHORT).show();
            return;
        }

        String sessionId = sessionIdList.get(position);
        drSessions.child(sessionId).removeValue().addOnCompleteListener(task -> {
            Toast.makeText(DeleteSessionActivity.this, "Session Deleted Successfully", Toast.LENGTH_SHORT).show();
            spinnerDeleteSession.setSelection(0);
            loadSessionsIntoSpinner(); // Refresh list
        });
    }
}
