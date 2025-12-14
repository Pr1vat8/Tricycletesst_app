package com.example.tricycle_app.activity.admin;

import android.os.Bundle;
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
    private EditText etBaseFare;
    private TextView tvLocationDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adminfareset);

        AdminNavbar.setup(this);

        locationName = getIntent().getStringExtra("LOCATION_NAME");
        FareLocation fare = FareRepository.getFareByName(locationName);

        etBaseFare = findViewById(R.id.etBaseFare);
        TextView tvHeader = findViewById(R.id.tvHeaderTitle);
        TextView btnSave = findViewById(R.id.btnSave);
        tvLocationDisplay = findViewById(R.id.tvLocationDisplay); // New TextView ID

        if (fare != null) {
            if(tvHeader != null) tvHeader.setText("Edit Fare");
            // Set the visible location name in the body
            if(tvLocationDisplay != null) tvLocationDisplay.setText(fare.getName());
            etBaseFare.setText(fare.getBaseFare());
        }

        if (btnSave != null) {
            btnSave.setOnClickListener(v -> {
                String newPrice = etBaseFare.getText().toString();
                if (!newPrice.isEmpty()) {
                    FareRepository.updateFare(this, locationName, newPrice);
                    Toast.makeText(this, "Fare Updated!", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(this, "Please enter a valid amount", Toast.LENGTH_SHORT).show();
                }
            });
        }

        LinearLayout btnBack = findViewById(R.id.btnBack);
        if(btnBack != null) btnBack.setOnClickListener(v -> finish());
    }
}