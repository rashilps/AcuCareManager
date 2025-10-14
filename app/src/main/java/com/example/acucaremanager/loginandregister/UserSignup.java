package com.example.acucaremanager.loginandregister;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.acucaremanager.R;
import com.example.acucaremanager.homescreen.UserHomeScreen;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class UserSignup extends AppCompatActivity implements View.OnClickListener {

    TextView tv;
    EditText user, password, password1;
    androidx.appcompat.widget.AppCompatButton signup_b;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        user = findViewById(R.id.user);
        password = findViewById(R.id.password);
        password1 = findViewById(R.id.password1);
        tv = findViewById(R.id.tv);
        signup_b = findViewById(R.id.signup_b);
        mAuth = FirebaseAuth.getInstance();
        signup_b.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

//        Toast.makeText(this, "clicked", Toast.LENGTH_SHORT).show();
        if (view == signup_b) {
            String p1 = password.getText().toString();
            String p2 = password1.getText().toString();
            if (p1.equals(p2)) {
                tv.setText("");

                String username = user.getText().toString();
                String pass = password.getText().toString();
                String pass1 = password1.getText().toString();

                if (TextUtils.isEmpty(username)) {
                    user.setError("Username cannot be empty");
                    user.requestFocus();
                }

                else if (TextUtils.isEmpty(pass)) {
                    password.setError("Password cannot be empty");
                    password.requestFocus();
                }

                else if (TextUtils.isEmpty(pass1)) {
                    password1.setError("You must confirm your password");
                    password1.requestFocus();
                }

                else {
                    mAuth.createUserWithEmailAndPassword(username, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {

                                Toast.makeText(UserSignup.this, "User registered successfully", Toast.LENGTH_SHORT).show();

//                                User user = new User(username, pass);
//                                dr.child("users").push().setValue(user);

                                Intent i = new Intent(UserSignup.this, UserHomeScreen.class);
                                startActivity(i);
                            }
                            else {
//                                Toast.makeText(UserSignup.this, "Failed", Toast.LENGTH_SHORT).show();
                                Toast.makeText(UserSignup.this, "Registration error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
            else{
                tv.setText("Password confirmation incorrect");
            }
        }
    }
}