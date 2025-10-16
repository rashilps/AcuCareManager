package com.example.acucaremanager.managesession;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.acucaremanager.R;
import com.example.acucaremanager.pojo.ClientPojo;
import com.example.acucaremanager.pojo.SessionPojo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;

public class EditSessionActivity extends AppCompatActivity {

    Spinner spinnerSelectSession, spinnerEditClientName;
    AppCompatButton btnEditPickDate, btnEditPickTime, btnUpdateSession;
    TextView tvEditSelectedDate, tvEditSelectedTime;
    EditText etEditSessionFee, etEditSessionNotes;

    ArrayList<String> sessionDisplayList = new ArrayList<>();
    ArrayList<String> sessionIdList = new ArrayList<>();
    ArrayList<String> clientNames = new ArrayList<>();

    String selectedSessionId = "";
    String selectedDate = "";
    String selectedTime = "";

    DatabaseReference drSessions, drClients;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_session);

        // Firebase references
        drSessions = FirebaseDatabase.getInstance("https://acucaremanager111-default-rtdb.asia-southeast1.firebasedatabase.app/")
                .getReference("Sessions");
        drClients = FirebaseDatabase.getInstance("https://acucaremanager111-default-rtdb.asia-southeast1.firebasedatabase.app/")
                .getReference("Clients");

        // Linking XML views
        spinnerSelectSession = findViewById(R.id.spinnerSelectSession);
        spinnerEditClientName = findViewById(R.id.spinnerEditClientName);
        btnEditPickDate = findViewById(R.id.btnEditPickDate);
        btnEditPickTime = findViewById(R.id.btnEditPickTime);
        tvEditSelectedDate = findViewById(R.id.tvEditSelectedDate);
        tvEditSelectedTime = findViewById(R.id.tvEditSelectedTime);
        etEditSessionFee = findViewById(R.id.etEditSessionFee);
        etEditSessionNotes = findViewById(R.id.etEditSessionNotes);
        btnUpdateSession = findViewById(R.id.btnUpdateSession);

        loadClientNames();      // Load clients for client spinner
        loadSessionsIntoSpinner(); // Load sessions list for session selector

        setupDatePicker();
        setupTimePicker();

        spinnerSelectSession.setOnItemSelectedListener(new android.widget.AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(android.widget.AdapterView<?> parent, android.view.View view, int position, long id) {
                if (position != 0) {
                    selectedSessionId = sessionIdList.get(position);
                    loadSessionDetails(selectedSessionId);
                }
            }
            @Override
            public void onNothingSelected(android.widget.AdapterView<?> parent) {}
        });

        btnUpdateSession.setOnClickListener(v -> updateSession());
    }

    private void loadClientNames() {
        clientNames.clear();
        clientNames.add("Select Client");

        drClients.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    ClientPojo client = ds.getValue(ClientPojo.class);
                    if (client != null) clientNames.add(client.getName());
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<>(EditSessionActivity.this, android.R.layout.simple_spinner_item, clientNames);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerEditClientName.setAdapter(adapter);
            }
            @Override public void onCancelled(@NonNull DatabaseError error) {}
        });
    }

    private void loadSessionsIntoSpinner() {
        drSessions.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                sessionDisplayList.clear();
                sessionIdList.clear();

                sessionDisplayList.add("Select Session");
                sessionIdList.add("");

                for (DataSnapshot ds : snapshot.getChildren()) {
                    SessionPojo session = ds.getValue(SessionPojo.class);
                    if (session != null) {
                        sessionDisplayList.add(session.getClientName() + " - " + session.getDate() + " " + session.getTime());
                        sessionIdList.add(session.getSessionId());
                    }
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<>(EditSessionActivity.this, android.R.layout.simple_spinner_item, sessionDisplayList);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerSelectSession.setAdapter(adapter);
            }

            @Override public void onCancelled(@NonNull DatabaseError error) {}
        });
    }

    private void loadSessionDetails(String sessionId) {
        drSessions.child(sessionId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                SessionPojo session = snapshot.getValue(SessionPojo.class);
                if (session != null) {
                    tvEditSelectedDate.setText("Date: " + session.getDate());
                    tvEditSelectedTime.setText("Time: " + session.getTime());
                    etEditSessionFee.setText(session.getFee());
                    etEditSessionNotes.setText(session.getNotes());

                    selectedDate = session.getDate();
                    selectedTime = session.getTime();

                    // Set client spinner selection
                    int index = clientNames.indexOf(session.getClientName());
                    if (index != -1) spinnerEditClientName.setSelection(index);
                }
            }
            @Override public void onCancelled(@NonNull DatabaseError error) {}
        });
    }

    private void setupDatePicker() {
        btnEditPickDate.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    (view, y, m, d) -> {
                        selectedDate = d + "-" + (m + 1) + "-" + y;
                        tvEditSelectedDate.setText("Date: " + selectedDate);
                    }, year, month, day);
            datePickerDialog.show();
        });
    }

    private void setupTimePicker() {
        btnEditPickTime.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            int minute = calendar.get(Calendar.MINUTE);

            TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                    (view, h, m) -> {
                        selectedTime = String.format("%02d:%02d", h, m);
                        tvEditSelectedTime.setText("Time: " + selectedTime);
                    }, hour, minute, true);
            timePickerDialog.show();
        });
    }

    private void updateSession() {
        if (selectedSessionId.isEmpty()) {
            Toast.makeText(this, "Please select a session", Toast.LENGTH_SHORT).show();
            return;
        }

        String clientName = spinnerEditClientName.getSelectedItem().toString();
        String fee = etEditSessionFee.getText().toString();
        String notes = etEditSessionNotes.getText().toString();

        if (clientName.equals("Select Client") || selectedDate.isEmpty() || selectedTime.isEmpty() || fee.isEmpty()) {
            Toast.makeText(this, "Please fill all required fields", Toast.LENGTH_SHORT).show();
            return;
        }

        SessionPojo updatedSession = new SessionPojo(selectedSessionId, clientName, selectedDate, selectedTime, fee, notes);

        // Update Firebase
        drSessions.child(selectedSessionId).setValue(updatedSession).addOnCompleteListener(task ->
                Toast.makeText(EditSessionActivity.this, "Session Updated Successfully", Toast.LENGTH_SHORT).show()
        );
    }
}
