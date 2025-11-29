package com.example.tricycle_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class UserSetLocationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.userpickup);

        // 1. Setup Navigation Bar
        UserNavbar.setup(this);

        // 2. Find Views
        TextView btnNext = findViewById(R.id.btnNext);
        LinearLayout btnBack = findViewById(R.id.btnBack);

        // 3. Next Button Logic -> Go to Driver Select
        if (btnNext != null) {
            btnNext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // This launches the Driver Selection screen
                    Intent intent = new Intent(UserSetLocationActivity.this, UserDriverSelectActivity.class);
                    startActivity(intent);
                }
            });
        }

        // 4. Back Button Logic
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> finish());
        }
    }
}