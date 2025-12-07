package com.example.tricycle_app;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tricycle_app.model.SavedPlace;
import com.example.tricycle_app.repository.SavedPlaceRepository;

public class UserEditSavedPlaceActivity extends AppCompatActivity {

    private EditText etName, etAddress;
    private int placeIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.usereditsavedplace);

        SavedPlaceRepository.init(this);

        etName = findViewById(R.id.etName);
        etAddress = findViewById(R.id.etAddress);
        LinearLayout btnBack = findViewById(R.id.btnBack);
        TextView btnSave = findViewById(R.id.btnSave);

        // Get Data passed
        placeIndex = getIntent().getIntExtra("PLACE_INDEX", -1);
        if (placeIndex != -1) {
            SavedPlace place = SavedPlaceRepository.getAllSavedPlaces().get(placeIndex);
            etName.setText(place.getName());
            etAddress.setText(place.getAddress());
        }

        if (btnBack != null) btnBack.setOnClickListener(v -> finish());

        if (btnSave != null) {
            btnSave.setOnClickListener(v -> {
                String newName = etName.getText().toString().trim();
                String newAddress = etAddress.getText().toString().trim();

                if (newName.isEmpty() || newAddress.isEmpty()) {
                    Toast.makeText(this, "Fields cannot be empty", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Update Repo
                SavedPlace updatedPlace = new SavedPlace(newName, newAddress);
                SavedPlaceRepository.updateSavedPlace(this, placeIndex, updatedPlace);

                Toast.makeText(this, "Saved!", Toast.LENGTH_SHORT).show();
                finish();
            });
        }
    }
}