package com.example.acucaremanager.manageclient;

import android.os.Bundle;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.acucaremanager.R;
import com.example.acucaremanager.adapters.ClientAdapter;
import com.example.acucaremanager.pojo.ClientPojo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import com.example.acucaremanager.R;

public class ViewClientsActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ClientAdapter adapter;
    private ArrayList<ClientPojo> clientList = new ArrayList<>();
    private DatabaseReference dr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_clients);
        dr = FirebaseDatabase.getInstance("https://acucaremanager111-default-rtdb.asia-southeast1.firebasedatabase.app/")
                .getReference("Clients");

        recyclerView = findViewById(R.id.recyclerViewClients);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ClientAdapter(clientList);
        recyclerView.setAdapter(adapter);

        loadClients();
    }
    private void loadClients() {
        dr.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                clientList.clear();
                for (DataSnapshot clientSnapshot : snapshot.getChildren()) {
                    ClientPojo client = clientSnapshot.getValue(ClientPojo.class);
                    clientList.add(client);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ViewClientsActivity.this, "Failed to load data", Toast.LENGTH_SHORT).show();
            }
        });
    }
}