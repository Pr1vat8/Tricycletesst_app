package com.example.tricycle_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class SignupActivity extends AppCompatActivity {

    private TextView tvDriverSignUp; // 1. Declare variable

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        // 2. Link variable to XML ID
        tvDriverSignUp = findViewById(R.id.tvDriverSignUp);

        // 3. Set Click Listener to open SignDriverActivity
        tvDriverSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // This creates the link (Intent) between Signup and SignDriver
                Intent intent = new Intent(SignupActivity.this, SignDriverActivity.class);
                startActivity(intent);
            }
        });

        // (Optional) You likely want to wire up the "Log In" text here too:
        TextView tvLogin = findViewById(R.id.tvLogin);
        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Go back to LoginActivity
            }
        });
    }
}