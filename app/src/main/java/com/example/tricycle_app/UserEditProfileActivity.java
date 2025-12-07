package com.example.tricycle_app;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class UserEditProfileActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.usereditprofile);

        LinearLayout btnBack = findViewById(R.id.btnBack);
        TextView btnSave = findViewById(R.id.btnSave);

        if(btnBack != null) btnBack.setOnClickListener(v -> finish());

        if(btnSave != null) {
            btnSave.setOnClickListener(v -> {
                Toast.makeText(this, "Profile Updated!", Toast.LENGTH_SHORT).show();
                finish();
            });
        }
    }
}