package com.example.acucaremanager.managesession;

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
import com.example.acucaremanager.adapters.SessionAdapter;
import com.example.acucaremanager.pojo.SessionPojo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SearchSessionsActivity extends AppCompatActivity {

    private EditText etSearchSession;
    private RecyclerView recyclerViewSearchSession;
    private SessionAdapter sessionAdapter;
    private ArrayList<SessionPojo> sessionList = new ArrayList<>();
    private ArrayList<SessionPojo> filteredList = new ArrayList<>();

    private DatabaseReference drSessions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_sessions);

        // Firebase reference
        drSessions = FirebaseDatabase.getInstance("https://acucaremanager111-default-rtdb.asia-southeast1.firebasedatabase.app/")
                .getReference("Sessions");

        // Initialize UI components
        etSearchSession = findViewById(R.id.etSearchSession);
        recyclerViewSearchSession = findViewById(R.id.recyclerViewSearchSession);

        // Set up RecyclerView
        recyclerViewSearchSession.setLayoutManager(new LinearLayoutManager(this));
        sessionAdapter = new SessionAdapter(filteredList); // 🔥 Only filteredList shown
        recyclerViewSearchSession.setAdapter(sessionAdapter);

        // Load all sessions initially
        loadSessions();

        // Search Filter Listener
        etSearchSession.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterSessions(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    private void loadSessions() {
        drSessions.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                sessionList.clear();
                filteredList.clear();

                for (DataSnapshot ds : snapshot.getChildren()) {
                    SessionPojo session = ds.getValue(SessionPojo.class);
                    if (session != null) {
                        sessionList.add(session);
                    }
                }

                // Show all by default
                filteredList.addAll(sessionList);
                sessionAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(SearchSessionsActivity.this, "Failed to load sessions", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void filterSessions(String query) {
        filteredList.clear();

        for (SessionPojo session : sessionList) {
            if (session.getClientName().toLowerCase().contains(query.toLowerCase()) ||
                    session.getDate().toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(session);
            }
        }

        sessionAdapter.notifyDataSetChanged();
    }
}
