package com.example.tricycle_app;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class UserSetLocationActivity extends AppCompatActivity {

    private View selectedOption = null;
    private EditText etTo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.userpickup);

        UserNavbar.setup(this);

        etTo = findViewById(R.id.etTo);
        TextView btnNext = findViewById(R.id.btnNext);
        LinearLayout btnBack = findViewById(R.id.btnBack);

        // Define selectable options
        setupOption(findViewById(R.id.btnHome), "Home");
        setupOption(findViewById(R.id.btnWork), "Work");
        setupOption(findViewById(R.id.btnGym), "Gym");
        setupOption(findViewById(R.id.btnCafe), "Cafe");

        if (btnNext != null) {
            btnNext.setOnClickListener(v -> {
                // Require either a manual entry OR a selected option
                if (selectedOption == null && etTo.getText().toString().isEmpty()) {
                    Toast.makeText(this, "Please select a destination", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(UserSetLocationActivity.this, UserDriverSelectActivity.class);
                    startActivity(intent);
                }
            });
        }

        if (btnBack != null) {
            btnBack.setOnClickListener(v -> finish());
        }
    }

    private void setupOption(View view, String locationName) {
        if (view == null) return;
        view.setOnClickListener(v -> {
            // Reset previous selection
            if (selectedOption != null) {
                selectedOption.setBackgroundColor(Color.TRANSPARENT);
            }
            // Set new selection
            selectedOption = view;
            selectedOption.setBackgroundResource(R.drawable.bg_rounded_light_grey); // Use your grey drawable for highlight

            // Auto-fill the text
            etTo.setText(locationName);
        });
    }
}