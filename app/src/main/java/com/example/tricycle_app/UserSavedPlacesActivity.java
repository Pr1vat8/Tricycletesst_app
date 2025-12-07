package com.example.tricycle_app;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class UserSavedPlacesActivity extends AppCompatActivity {

    private SavedPlaceAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.usersavedplaces);

        // 1. Initialize Data
        SavedPlaceRepository.init(this);

        // 2. Setup Views
        LinearLayout btnBack = findViewById(R.id.btnBack);
        TextView btnAddPlace = findViewById(R.id.btnAddPlace);
        RecyclerView recyclerView = findViewById(R.id.recyclerViewSavedPlaces);

        // 3. Setup RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new SavedPlaceAdapter(this, SavedPlaceRepository.getAllSavedPlaces());
        recyclerView.setAdapter(adapter);

        // 4. Buttons
        if (btnBack != null) btnBack.setOnClickListener(v -> finish());

        if (btnAddPlace != null) {
            btnAddPlace.setOnClickListener(v -> {
                Intent intent = new Intent(UserSavedPlacesActivity.this, UserAddPlaceActivity.class);
                startActivity(intent);
            });
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Refresh list when returning from "Add Place" screen
        if (adapter != null) {
            SavedPlaceRepository.loadAll(this); // Reload from file
            adapter.notifyDataSetChanged();
        }
    }
}