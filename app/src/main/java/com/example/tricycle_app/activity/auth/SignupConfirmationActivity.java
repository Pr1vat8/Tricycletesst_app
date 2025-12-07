package com.example.tricycle_app.activity.auth;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tricycle_app.R;

public class SignupConfirmationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_confirmation);

        TextView btnReturnToLogin = findViewById(R.id.btnReturnToLogin);

        if (btnReturnToLogin != null) {
            btnReturnToLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Navigate back to LoginActivity
                    Intent intent = new Intent(SignupConfirmationActivity.this, LoginActivity.class);
                    // Clear the back stack so the user cannot go back to the confirmation or signup screen
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
            });
        }
    }
}