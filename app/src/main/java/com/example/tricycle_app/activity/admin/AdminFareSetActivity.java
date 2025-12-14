package com.example.tricycle_app.activity.admin;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tricycle_app.R;
import com.example.tricycle_app.model.FareLocation;
import com.example.tricycle_app.repository.FareRepository;
import com.example.tricycle_app.utils.AdminNavbar;

public class AdminFareSetActivity extends AppCompatActivity {

    private String locationName;
    private EditText etBaseFare, etLocationName, etDescription; // Added etDescription
    private TextView tvLocationDisplay, tvHeaderTitle;
    private View layoutLocationInput;
    private boolean isAddMode = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adminfareset);

        AdminNavbar.setup(this);
        FareRepository.init(this);

        locationName = getIntent().getStringExtra("LOCATION_NAME");

        initViews();

        if (locationName == null) {
            setupAddMode();
        } else {
            setupEditMode();
        }

        TextView btnSave = findViewById(R.id.btnSave);
        if (btnSave != null) {
            btnSave.setOnClickListener(v -> saveFare());
        }

        LinearLayout btnBack = findViewById(R.id.btnBack);
        if(btnBack != null) btnBack.setOnClickListener(v -> finish());
    }

    private void initViews() {
        etBaseFare = findViewById(R.id.etBaseFare);
        etLocationName = findViewById(R.id.etLocationName);
        etDescription = findViewById(R.id.etDescription); // Initialize the new input
        tvHeaderTitle = findViewById(R.id.tvHeaderTitle);
        tvLocationDisplay = findViewById(R.id.tvLocationDisplay);
        layoutLocationInput = findViewById(R.id.layoutLocationInput);
    }

    private void setupAddMode() {
        isAddMode = true;
        tvHeaderTitle.setText("Add New Location");

        // Show Input fields (Name & Description), Hide Display Text
        layoutLocationInput.setVisibility(View.VISIBLE);
        tvLocationDisplay.setVisibility(View.GONE);
    }

    private void setupEditMode() {
        isAddMode = false;
        tvHeaderTitle.setText("Edit Fare");

        // Hide Input fields, Show Display Text
        layoutLocationInput.setVisibility(View.GONE);
        tvLocationDisplay.setVisibility(View.VISIBLE);

        FareLocation fare = FareRepository.getFareByName(locationName);
        if (fare != null) {
            tvLocationDisplay.setText(fare.getName());
            // Format double to String to avoid setText error
            etBaseFare.setText(String.format("%.2f", fare.getBaseFare()));
        }
    }

    private void saveFare() {
        String price = etBaseFare.getText().toString().trim();

        if (price.isEmpty()) {
            Toast.makeText(this, "Please enter a price", Toast.LENGTH_SHORT).show();
            return;
        }

        if (isAddMode) {
            String name = etLocationName.getText().toString().trim();
            String description = etDescription.getText().toString().trim(); // Get description

            if (name.isEmpty()) {
                Toast.makeText(this, "Please enter a location name", Toast.LENGTH_SHORT).show();
                return;
            }

            if (description.isEmpty()) {
                Toast.makeText(this, "Please enter a description/address", Toast.LENGTH_SHORT).show();
                return;
            }

            if (FareRepository.checkFareExists(name)) {
                Toast.makeText(this, "Location already exists!", Toast.LENGTH_SHORT).show();
                return;
            }

            // Pass the actual description now instead of "General Area"
            FareRepository.addFare(this, name, description, price);
            Toast.makeText(this, "New Location Added!", Toast.LENGTH_SHORT).show();
        } else {
            // Note: FareRepository.updateFare only updates price currently
            FareRepository.updateFare(this, locationName, price);
            Toast.makeText(this, "Fare Updated!", Toast.LENGTH_SHORT).show();
        }
        finish();
    }
}