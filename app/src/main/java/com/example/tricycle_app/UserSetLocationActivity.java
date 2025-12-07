package com.example.tricycle_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class UserSetLocationActivity extends AppCompatActivity {

    private EditText etTo, etFrom;
    private EditText activeInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.userpickup);

        UserNavbar.setup(this);
        FareRepository.init(this);
        SavedPlaceRepository.init(this);

        etFrom = findViewById(R.id.etFrom);
        etTo = findViewById(R.id.etTo);
        TextView btnNext = findViewById(R.id.btnNext);
        LinearLayout btnBack = findViewById(R.id.btnBack);
        RecyclerView recyclerViewLocations = findViewById(R.id.recyclerViewLocations);
        RecyclerView recyclerViewSaved = findViewById(R.id.recyclerViewSavedPlaces);

        // Focus Logic
        activeInput = etTo; // Default
        View.OnFocusChangeListener focusListener = (v, hasFocus) -> {
            if (hasFocus) activeInput = (EditText) v;
        };
        etFrom.setOnFocusChangeListener(focusListener);
        etTo.setOnFocusChangeListener(focusListener);
        etFrom.setOnClickListener(v -> activeInput = etFrom);
        etTo.setOnClickListener(v -> activeInput = etTo);

        // Locations Adapter
        recyclerViewLocations.setLayoutManager(new LinearLayoutManager(this));
        FareAdapter fareAdapter = new FareAdapter(this, FareRepository.getAllFares());
        fareAdapter.setOnItemClickListener(fare -> setLocationText(fare.getName()));
        recyclerViewLocations.setAdapter(fareAdapter);

        // Saved Places Adapter
        recyclerViewSaved.setLayoutManager(new LinearLayoutManager(this));
        SavedPlaceAdapter savedAdapter = new SavedPlaceAdapter(this, SavedPlaceRepository.getAllSavedPlaces());
        savedAdapter.setOnItemClickListener(place -> setLocationText(place.getName()));
        recyclerViewSaved.setAdapter(savedAdapter);

        // Next Button
        if (btnNext != null) {
            btnNext.setOnClickListener(v -> {
                String from = etFrom.getText().toString().trim();
                String to = etTo.getText().toString().trim();

                if (from.isEmpty() || to.isEmpty()) {
                    Toast.makeText(this, "Please fill both locations", Toast.LENGTH_SHORT).show();
                } else {
                    // SAVE DATA TO MANAGER
                    UserTripManager.getInstance().setLocations(from, to);

                    Intent intent = new Intent(UserSetLocationActivity.this, UserDriverSelectActivity.class);
                    startActivity(intent);
                }
            });
        }

        if (btnBack != null) btnBack.setOnClickListener(v -> finish());
    }

    private void setLocationText(String text) {
        if (activeInput != null) {
            activeInput.setText(text);
            activeInput.setSelection(activeInput.getText().length());
        }
    }
}