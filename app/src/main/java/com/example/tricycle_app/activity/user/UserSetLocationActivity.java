package com.example.tricycle_app.activity.user;

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

import com.example.tricycle_app.R;
import com.example.tricycle_app.repository.SavedPlaceRepository;
import com.example.tricycle_app.adapter.FareAdapter;
import com.example.tricycle_app.adapter.SavedPlaceAdapter;
import com.example.tricycle_app.repository.FareRepository;
import com.example.tricycle_app.utils.UserNavbar;
import com.example.tricycle_app.utils.UserTripManager;

public class UserSetLocationActivity extends AppCompatActivity {

    private EditText etTo, etFrom;
    private EditText activeInput; // Tracks which field is currently selected

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.userpickup);

        // 1. Setup Navigation & Data
        UserNavbar.setup(this);
        FareRepository.init(this);        // Load Available Fares
        SavedPlaceRepository.init(this);  // Load Saved Places

        // 2. Find Views
        etFrom = findViewById(R.id.etFrom);
        etTo = findViewById(R.id.etTo);
        TextView btnNext = findViewById(R.id.btnNext);
        LinearLayout btnBack = findViewById(R.id.btnBack);
        RecyclerView recyclerViewLocations = findViewById(R.id.recyclerViewLocations);
        RecyclerView recyclerViewSaved = findViewById(R.id.recyclerViewSavedPlaces);

        // --- 3. FOCUS LOGIC ---
        // Determine which EditText receives the text when a list item is clicked
        activeInput = etTo; // Default focus

        View.OnFocusChangeListener focusListener = (v, hasFocus) -> {
            if (hasFocus) {
                activeInput = (EditText) v;
            }
        };
        etFrom.setOnFocusChangeListener(focusListener);
        etTo.setOnFocusChangeListener(focusListener);

        // Also capture click events to force update activeInput
        etFrom.setOnClickListener(v -> activeInput = etFrom);
        etTo.setOnClickListener(v -> activeInput = etTo);


        // --- 4. SUGGESTED LOCATIONS (From fares.txt) ---
        if (recyclerViewLocations != null) {
            recyclerViewLocations.setLayoutManager(new LinearLayoutManager(this));
            FareAdapter fareAdapter = new FareAdapter(this, FareRepository.getAllFares());

            fareAdapter.setOnItemClickListener(fare -> {
                if (activeInput != null) {
                    activeInput.setText(fare.getName());
                    activeInput.setSelection(activeInput.getText().length()); // Move cursor to end
                }
            });
            recyclerViewLocations.setAdapter(fareAdapter);
        }


        // --- 5. SAVED PLACES (From saved_places.txt) ---
        if (recyclerViewSaved != null) {
            recyclerViewSaved.setLayoutManager(new LinearLayoutManager(this));
            SavedPlaceAdapter savedAdapter = new SavedPlaceAdapter(this, SavedPlaceRepository.getAllSavedPlaces());

            // Note: The listener arguments depend on your Adapter version.
            // If you updated for Edit/Delete, it takes (position, place).
            savedAdapter.setOnItemClickListener((position, place) -> {
                if (activeInput != null) {
                    activeInput.setText(place.getName());
                    activeInput.setSelection(activeInput.getText().length());
                }
            });
            recyclerViewSaved.setAdapter(savedAdapter);
        }


        // --- 6. NEXT BUTTON ---
        if (btnNext != null) {
            btnNext.setOnClickListener(v -> {
                String from = etFrom.getText().toString().trim();
                String to = etTo.getText().toString().trim();

                if (from.isEmpty()) {
                    Toast.makeText(this, "Please enter a pickup location", Toast.LENGTH_SHORT).show();
                } else if (to.isEmpty()) {
                    Toast.makeText(this, "Please enter a drop-off destination", Toast.LENGTH_SHORT).show();
                } else {
                    // Save to UserTripManager (Singleton) so other screens can access it
                    UserTripManager.getInstance().setLocations(from, to);

                    // Navigate to Driver Select
                    Intent intent = new Intent(UserSetLocationActivity.this, UserDriverSelectActivity.class);
                    startActivity(intent);
                }
            });
        }

        // --- 7. BACK BUTTON ---
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> finish());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Refresh Saved Places list in case user added a new one
        RecyclerView recyclerViewSaved = findViewById(R.id.recyclerViewSavedPlaces);
        if (recyclerViewSaved != null && recyclerViewSaved.getAdapter() != null) {
            SavedPlaceRepository.loadAll(this);
            recyclerViewSaved.getAdapter().notifyDataSetChanged();
        }
    }
}