package com.example.acucaremanager.homescreen;

import android.os.Bundle;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.acucaremanager.R;
import com.example.acucaremanager.pojo.SessionPojo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class FinanceSummary extends AppCompatActivity {

    RecyclerView recyclerFinanceSummary;
    TextView tvTotalIncome;
    DatabaseReference drSessions;

    ArrayList<String> clientNamesList = new ArrayList<>();
    ArrayList<Integer> sessionCountList = new ArrayList<>();
    ArrayList<Integer> totalEarningsList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finance_summary);

        recyclerFinanceSummary = findViewById(R.id.recyclerFinanceSummary);
        tvTotalIncome = findViewById(R.id.tvTotalIncome);
        recyclerFinanceSummary.setLayoutManager(new LinearLayoutManager(this));

        drSessions = FirebaseDatabase.getInstance("https://acucaremanager111-default-rtdb.asia-southeast1.firebasedatabase.app/")
                .getReference("Sessions");

        loadFinanceData();
    }

    private void loadFinanceData() {
        drSessions.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                // Clear previous data to avoid duplication after refresh
                clientNamesList.clear();
                sessionCountList.clear();
                totalEarningsList.clear();

                HashMap<String, Integer> earningsMap = new HashMap<>();
                HashMap<String, Integer> sessionCountMap = new HashMap<>();

                int overallTotalIncome = 0;

                // Read all session entries
                for (DataSnapshot ds : snapshot.getChildren()) {
                    SessionPojo session = ds.getValue(SessionPojo.class);

                    if (session != null) {
                        String clientName = session.getClientName();
                        int fee = Integer.parseInt(session.getFee());

                        // Increment earnings
                        earningsMap.put(clientName, earningsMap.getOrDefault(clientName, 0) + fee);

                        // Increment session count
                        sessionCountMap.put(clientName, sessionCountMap.getOrDefault(clientName, 0) + 1);

                        // Add to total income
                        overallTotalIncome += fee;
                    }
                }

                // Convert HashMaps to array lists for RecyclerView
                for (String client : earningsMap.keySet()) {
                    clientNamesList.add(client);
                    totalEarningsList.add(earningsMap.get(client));
                    sessionCountList.add(sessionCountMap.get(client));
                }

                // ✅ Display Overall Total
                tvTotalIncome.setText("Total Earnings: ₹" + overallTotalIncome);

                // ✅ Set Adapter
                com.example.acucaremanager.homescreen.FinanceSummaryAdapter adapter = new com.example.acucaremanager.homescreen.FinanceSummaryAdapter(clientNamesList, sessionCountList, totalEarningsList);
                recyclerFinanceSummary.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }
}
