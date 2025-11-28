package com.example.tricycle_app;

import android.content.Intent; // Import Intent
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;

public class AdminFaresActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adminfares);

        AdminNavbar.setup(this);

        // Back button logic...
        LinearLayout btnBack = findViewById(R.id.btnBack);
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> finish());
        }

        // Setup all your locations
        setupLocationClick(R.id.btnCentamina, "Centamina");
        setupLocationClick(R.id.btnBusTerminal, "Bus Terminal");
        setupLocationClick(R.id.btnMerkado, "Merkado");
        setupLocationClick(R.id.btnPurok1, "Purok 1");
        setupLocationClick(R.id.btnLumbo, "Lumbo");
        setupLocationClick(R.id.btnHangkol, "Hangkol");
        setupLocationClick(R.id.btnPlaza, "Plaza");
        setupLocationClick(R.id.btnCentralSchool, "Central School");
        setupLocationClick(R.id.btnOldTerminal, "Old Terminal");
    }

    private void setupLocationClick(int buttonId, final String locationName) {
        LinearLayout btn = findViewById(buttonId);
        if (btn != null) {
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // --- THIS IS THE PART THAT LOADS THE NEW SCREEN ---
                    Intent intent = new Intent(AdminFaresActivity.this, AdminFareSetActivity.class);
                    // Pass the name (e.g., "Centamina") to the next screen
                    intent.putExtra("LOCATION_NAME", locationName);
                    startActivity(intent);
                }
            });
        }
    }
}