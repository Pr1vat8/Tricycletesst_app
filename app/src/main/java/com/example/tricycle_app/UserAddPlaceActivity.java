package com.example.tricycle_app;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class UserAddPlaceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.useraddplace);

        // 1. Setup
        FareRepository.init(this); // Load data from fares.txt

        // 2. Views
        LinearLayout btnBack = findViewById(R.id.btnBack);
        RecyclerView recyclerView = findViewById(R.id.recyclerViewPlaces);

        // 3. RecyclerView Setup
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Reuse FareAdapter because it already displays Name + Description nicely
        FareAdapter adapter = new FareAdapter(this, FareRepository.getAllFares());

        adapter.setOnItemClickListener(fare -> {
            // Logic to save the place would go here.
            // For now, we simulate success.
            Toast.makeText(this, fare.getName() + " added to Saved Places!", Toast.LENGTH_SHORT).show();
            finish();
        });

        recyclerView.setAdapter(adapter);

        if(btnBack != null) btnBack.setOnClickListener(v -> finish());
    }
}