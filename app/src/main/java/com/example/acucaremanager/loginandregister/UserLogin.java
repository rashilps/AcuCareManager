package com.example.acucaremanager.loginandregister;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.acucaremanager.R;
import com.example.acucaremanager.homescreen.UserHomeScreen;
import com.google.firebase.auth.FirebaseAuth;

import android.content.Intent;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

public class UserLogin extends AppCompatActivity implements View.OnClickListener {
    androidx.appcompat.widget.AppCompatButton login;
    TextView signup, forgot, admin;
    EditText user, password;
    FirebaseAuth mAuth;
    private SharedPreferenceConfig sharedPreferenceConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        login = findViewById(R.id.login);
        signup = findViewById(R.id.signup);
        user = findViewById(R.id.user);
        password = findViewById(R.id.password);
        forgot = findViewById(R.id.forgot);
        admin = findViewById(R.id.admin);
        mAuth = FirebaseAuth.getInstance();
        login.setOnClickListener(this);
        signup.setOnClickListener(this);
        forgot.setOnClickListener(this);
        admin.setOnClickListener(this);


        sharedPreferenceConfig = new SharedPreferenceConfig(getApplicationContext());
//        Toast.makeText(this,"" + sharedPreferenceConfig.readLoginStatus(), Toast.LENGTH_SHORT).show();

        if (sharedPreferenceConfig.readLoginStatus()) {
            Intent i = new Intent(this, UserHomeScreen.class);
            startActivity(i);
            finish();
        }
    }

    @Override
    public void onClick(View view) {
        if (view == signup) {
            Intent i = new Intent(UserLogin.this, UserSignup.class);
            startActivity(i);
        }

        if (view == login) {

            String username_entered = user.getText().toString();
            String password_entered = password.getText().toString();


            if (TextUtils.isEmpty(username_entered)) {
                user.setError("Username cannot be empty");
                user.requestFocus();
            } else if (TextUtils.isEmpty(password_entered)) {
                password.setError("Password cannot be empty");
                password.requestFocus();
            } else {
                mAuth.signInWithEmailAndPassword(username_entered, password_entered).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(UserLogin.this, "User logged in successfully", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(UserLogin.this, UserHomeScreen.class));
                            sharedPreferenceConfig.writeLoginStatus(true);
                            SharedPreferences.Editor editor = getSharedPreferences("name", MODE_PRIVATE).edit();
                            editor.putString("name", username_entered);
                            editor.apply();
//                                Toast.makeText(getApplicationContext(),"" + sharedPreferenceConfig.readLoginStatus(), Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(UserLogin.this, "UserLogin error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }
        if (view == forgot){
            Intent i = new Intent(UserLogin.this, PasswordReset.class);
            startActivity(i);
        }
    }
}