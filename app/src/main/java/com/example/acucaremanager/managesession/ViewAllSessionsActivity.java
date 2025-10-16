package com.example.acucaremanager.managesession;

import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
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

public class ViewAllSessionsActivity extends AppCompatActivity {

    private RecyclerView recyclerViewSessions;
    private SessionAdapter sessionAdapter;
    private ArrayList<SessionPojo> sessionList = new ArrayList<>();
    private DatabaseReference drSessions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_sessions); // Make sure XML name matches

        // Firebase Reference
        drSessions = FirebaseDatabase.getInstance("https://acucaremanager111-default-rtdb.asia-southeast1.firebasedatabase.app/")
                .getReference("Sessions");

        // Setup RecyclerView
        recyclerViewSessions = findViewById(R.id.recyclerViewSessions);
        recyclerViewSessions.setLayoutManager(new LinearLayoutManager(this));
        sessionAdapter = new SessionAdapter(sessionList); // ✅ Adapter will be created next
        recyclerViewSessions.setAdapter(sessionAdapter);

        loadSessions(); // Fetch sessions from Firebase
    }

    private void loadSessions() {
        drSessions.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                sessionList.clear();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    SessionPojo session = ds.getValue(SessionPojo.class);
                    if (session != null) {
                        sessionList.add(session);
                    }
                }
                sessionAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ViewAllSessionsActivity.this, "Failed to load sessions", Toast.LENGTH_SHORT).show();
            }
        });
    }
}