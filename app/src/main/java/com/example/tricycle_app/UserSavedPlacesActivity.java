package com.example.tricycle_app;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class UserSavedPlacesActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.usersavedplaces);

        LinearLayout btnBack = findViewById(R.id.btnBack);
        // Find the "Add New Place" button from the XML
        // Note: You need to add an ID to that TextView in usersavedplaces.xml first!
        // Assuming ID is btnAddPlace
        TextView btnAddPlace = findViewById(R.id.btnAddPlace);

        if(btnBack != null) btnBack.setOnClickListener(v -> finish());

        if(btnAddPlace != null) {
            btnAddPlace.setOnClickListener(v -> {
                Intent intent = new Intent(UserSavedPlacesActivity.this, UserAddPlaceActivity.class);
                startActivity(intent);
            });
        }
    }
}