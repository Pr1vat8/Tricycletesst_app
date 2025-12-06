package com.example.tricycle_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class FakeFacebookActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fake_facebook_login);

        LinearLayout btnFBLogin = findViewById(R.id.btnFBLogin);

        if (btnFBLogin != null) {
            btnFBLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Simulate successful login
                    Toast.makeText(FakeFacebookActivity.this, "Logged in via Facebook", Toast.LENGTH_SHORT).show();

                    // Go to User Dashboard
                    Intent intent = new Intent(FakeFacebookActivity.this, UserMainDashboardActivity.class);
                    // Clear activity stack so Back button doesn't return to login
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                }
            });
        }
    }
}