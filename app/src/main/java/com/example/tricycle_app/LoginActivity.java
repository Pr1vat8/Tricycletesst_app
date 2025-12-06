package com.example.tricycle_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    // Declare UI elements
    private EditText etUsername, etPassword;
    private TextView btnLogin;
    private TextView tvSignUp;
    private TextView btnGoogle, btnFacebook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginlayout);

        // 1. Initialize Views (Link to XML IDs)
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        tvSignUp = findViewById(R.id.tvSignUp);
        btnGoogle = findViewById(R.id.btnGoogle);
        btnFacebook = findViewById(R.id.btnFacebook);

        // 2. Main Login Button Logic
        if (btnLogin != null) {
            btnLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String user = etUsername.getText().toString().trim();
                    String pass = etPassword.getText().toString().trim();

                    if (user.equals("admin") && pass.equals("admin")) {
                        // Go to Admin Dashboard
                        Intent intent = new Intent(LoginActivity.this, AdminDashboardActivity.class);
                        startActivity(intent);
                    } else if (user.equals("user") && pass.equals("user")) {
                        // Go to User Dashboard
                        Intent intent = new Intent(LoginActivity.this, UserMainDashboardActivity.class);
                        startActivity(intent);
                    } else if (user.equals("driver") && pass.equals("driver")) {
                        // Go to Driver Dashboard
                        Intent intent = new Intent(LoginActivity.this, DriverDashboardActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(LoginActivity.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

        // 3. Sign Up Link Logic
        if (tvSignUp != null) {
            tvSignUp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                    startActivity(intent);
                }
            });
        }

        // 4. Google Login Logic (Fake Browser)
        if (btnGoogle != null) {
            btnGoogle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(LoginActivity.this, FakeBrowserActivity.class);
                    startActivity(intent);
                }
            });
        }

        // 5. Facebook Login Logic (Fake Facebook Screen)
        if (btnFacebook != null) {
            btnFacebook.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(LoginActivity.this, FakeFacebookActivity.class);
                    startActivity(intent);
                }
            });
        }
    }
}