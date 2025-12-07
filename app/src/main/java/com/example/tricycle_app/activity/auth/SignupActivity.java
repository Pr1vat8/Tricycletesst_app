package com.example.tricycle_app.activity.auth;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tricycle_app.R;

public class SignupActivity extends AppCompatActivity {

    private TextView tvDriverSignUp;
    private TextView btnSignUp; // 1. Declare the Sign Up Button

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        tvDriverSignUp = findViewById(R.id.tvDriverSignUp);
        btnSignUp = findViewById(R.id.btnSignUp); // 2. Find the button by ID

        // Driver Sign Up Link
        tvDriverSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignupActivity.this, SignDriverActivity.class);
                startActivity(intent);
            }
        });

        // 3. Main Sign Up Button Logic
        if (btnSignUp != null) {
            btnSignUp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Perform signup logic here (e.g., validate inputs, API call)

                    // On success, go to confirmation screen
                    Intent intent = new Intent(SignupActivity.this, SignupConfirmationActivity.class);
                    startActivity(intent);
                    finish(); // Optional: Close this activity so they can't go back to the form
                }
            });
        }

        // Login Link
        TextView tvLogin = findViewById(R.id.tvLogin);
        if (tvLogin != null) {
            tvLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish(); // Go back to LoginActivity
                }
            });
        }
    }
}