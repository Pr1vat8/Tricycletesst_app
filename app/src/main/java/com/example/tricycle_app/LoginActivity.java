package com.example.tricycle_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private EditText etUsername, etPassword;
    private TextView btnLogin;
    private TextView tvSignUp; // 1. Declare the TextView

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginlayout);

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        tvSignUp = findViewById(R.id.tvSignUp); // 2. Find the view by ID

        // Logic for Login Button
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = etUsername.getText().toString().trim();
                String pass = etPassword.getText().toString().trim();

                if (user.equals("admin") && pass.equals("admin")) {
                    Intent intent = new Intent(LoginActivity.this, AdminDashboardActivity.class);
                    startActivity(intent);
                }
                else if (user.equals("user") && pass.equals("user")) {
                    Intent intent = new Intent(LoginActivity.this, UserMainDashboardActivity.class);
                    startActivity(intent);
                }
                else if (user.equals("driver") && pass.equals("driver")) {
                    Intent intent = new Intent(LoginActivity.this, DriverDashboardActivity.class);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(LoginActivity.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // 3. Logic for Sign Up Click
        tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // This loads the SignupActivity, which holds the signup.xml
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });
    }
}