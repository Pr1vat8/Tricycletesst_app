package com.example.tricycle_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class UserArrivedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.userarrived); // Your XML file

        // 1. Setup Navigation
        UserNavbar.setup(this);

        // 2. Find Views
        TextView btnMeetDriver = findViewById(R.id.btnMeetDriver);
        LinearLayout btnBack = findViewById(R.id.btnBack);

        // 3. Meet Driver Button Logic -> Go to Trip Progress
        if (btnMeetDriver != null) {
            btnMeetDriver.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(UserArrivedActivity.this, UserTripProgressActivity.class);
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