package com.example.tricycle_app.activity.user;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tricycle_app.R;
import com.example.tricycle_app.repository.SavedPlaceRepository;
import com.example.tricycle_app.adapter.FareAdapter;
import com.example.tricycle_app.model.SavedPlace;
import com.example.tricycle_app.repository.FareRepository;

public class UserAddPlaceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.useraddplace);

        // 1. Setup Data Sources
        FareRepository.init(this);        // Source of locations (Available)
        SavedPlaceRepository.init(this);  // Destination for saving (Saved)

        // 2. Views
        LinearLayout btnBack = findViewById(R.id.btnBack);
        RecyclerView recyclerView = findViewById(R.id.recyclerViewPlaces);

        // 3. RecyclerView Setup
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FareAdapter adapter = new FareAdapter(this, FareRepository.getAllFares());

        adapter.setOnItemClickListener(fare -> {
            // --- SAVE LOGIC ---
            SavedPlace newPlace = new SavedPlace(fare.getName(), fare.getDescription());

            SavedPlaceRepository.addSavedPlace(this, newPlace);

            Toast.makeText(this, fare.getName() + " saved!", Toast.LENGTH_SHORT).show();
            finish(); // Go back to the list
        });

        recyclerView.setAdapter(adapter);

        if (btnBack != null) btnBack.setOnClickListener(v -> finish());
    }
}