package com.example.tricycle_app;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class PaymentMethodRegistrationActivity extends AppCompatActivity {

    private View selectedProvider = null;
    private EditText etPhoneNumber, etCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.paymentmethodregistration);

        LinearLayout btnBack = findViewById(R.id.btnBack);
        LinearLayout btnGcash = findViewById(R.id.btnGcash);
        LinearLayout btnMaya = findViewById(R.id.btnMaya);

        etPhoneNumber = findViewById(R.id.etPhoneNumber);
        etCode = findViewById(R.id.etCode);

        TextView btnResend = findViewById(R.id.btnResend);
        TextView btnDone = findViewById(R.id.btnDone);

        if(btnBack != null) btnBack.setOnClickListener(v -> finish());

        // Provider Selection Logic
        View.OnClickListener providerListener = v -> {
            // Reset old selection border/background
            if (selectedProvider != null) {
                selectedProvider.setBackgroundResource(0); // Remove highlight
                // Restore original drawable background logic if needed,
                // but for this simple XML structure, we can just reset padding/bg or use a border.
                // For simplicity: We will just toggle a background color filter or shape.
                if(selectedProvider.getId() == R.id.btnGcash) selectedProvider.setBackgroundResource(R.drawable.gcash);
                if(selectedProvider.getId() == R.id.btnMaya) selectedProvider.setBackgroundResource(R.drawable.maya);
            }

            selectedProvider = v;
            // Apply Highlight (e.g., a simple alpha change or border)
            v.setAlpha(0.7f); // Visual feedback
            Toast.makeText(this, "Selected", Toast.LENGTH_SHORT).show();
        };

        if(btnGcash != null) btnGcash.setOnClickListener(v -> {
            if(selectedProvider != null) selectedProvider.setAlpha(1.0f); // Reset old
            selectedProvider = v;
            v.setAlpha(0.5f); // Highlight
        });

        if(btnMaya != null) btnMaya.setOnClickListener(v -> {
            if(selectedProvider != null) selectedProvider.setAlpha(1.0f); // Reset old
            selectedProvider = v;
            v.setAlpha(0.5f); // Highlight
        });

        // Resend Logic
        if(btnResend != null) {
            btnResend.setOnClickListener(v -> Toast.makeText(this, "Code sent!", Toast.LENGTH_SHORT).show());
        }

        // Done Logic
        if(btnDone != null) {
            btnDone.setOnClickListener(v -> {
                if(selectedProvider == null) {
                    Toast.makeText(this, "Please choose a provider", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(etPhoneNumber.getText().toString().isEmpty()) {
                    Toast.makeText(this, "Please enter number", Toast.LENGTH_SHORT).show();
                    return;
                }

                Toast.makeText(this, "Payment Method Added!", Toast.LENGTH_SHORT).show();
                finish(); // Go back to profile/list
            });
        }
    }
}