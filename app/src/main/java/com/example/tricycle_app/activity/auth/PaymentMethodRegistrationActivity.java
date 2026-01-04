package com.example.tricycle_app.activity.auth;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tricycle_app.R;
import com.example.tricycle_app.model.PaymentMethod;
import com.example.tricycle_app.repository.PaymentMethodRepository;

import java.util.UUID;

public class PaymentMethodRegistrationActivity extends AppCompatActivity {

    private FrameLayout btnGcash, btnMaya;
    private EditText etPhoneNumber, etCode;
    private TextView btnResend, btnDone, btnGoToCard;

    private String selectedProvider = "GCash"; // Default

    // Variables for Edit Mode
    private boolean isEditMode = false;
    private String methodId = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.paymentmethodregistration);

        initViews();
        handleIntentData();

        findViewById(R.id.btnBack).setOnClickListener(v -> finish());

        // --- SELECTION LOGIC ---
        btnGcash.setOnClickListener(v -> selectProvider("GCash"));
        btnMaya.setOnClickListener(v -> selectProvider("Maya"));

        // --- Resend Button ---
        btnResend.setOnClickListener(v -> {
            String phone = etPhoneNumber.getText().toString().trim();
            if(phone.isEmpty()) {
                Toast.makeText(this, "Enter phone number first", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Verification code resent!", Toast.LENGTH_SHORT).show();
            }
        });

        // --- Switch to Card Activity ---
        btnGoToCard.setOnClickListener(v -> {
            Intent intent = new Intent(this, CardRegistrationActivity.class);
            startActivity(intent);
            finish(); // Close this to prevent back-stack confusion
        });

        btnDone.setOnClickListener(v -> savePaymentMethod());

        // Initial Selection UI
        selectProvider(selectedProvider);
    }

    private void initViews() {
        btnGcash = findViewById(R.id.btnGcash);
        btnMaya = findViewById(R.id.btnMaya);
        etPhoneNumber = findViewById(R.id.etPhoneNumber);
        etCode = findViewById(R.id.etCode);
        btnResend = findViewById(R.id.btnResend);
        btnDone = findViewById(R.id.btnDone);
        btnGoToCard = findViewById(R.id.btnGoToCard);
    }

    private void handleIntentData() {
        String mode = getIntent().getStringExtra("MODE");
        if ("EDIT".equals(mode)) {
            isEditMode = true;
            methodId = getIntent().getStringExtra("ID");
            String provider = getIntent().getStringExtra("PROVIDER");
            String phone = getIntent().getStringExtra("PHONE");

            if ("GCash".equals(provider) || "Maya".equals(provider)) {
                etPhoneNumber.setText(phone);
                selectProvider(provider);
            }
        }
    }

    private void selectProvider(String provider) {
        selectedProvider = provider;

        // Apply Border to Selected, Remove from Unselected
        if ("GCash".equalsIgnoreCase(provider)) {
            btnGcash.setBackgroundResource(R.drawable.bg_border_black);
            btnMaya.setBackgroundResource(0);
        } else if ("Maya".equalsIgnoreCase(provider)) {
            btnMaya.setBackgroundResource(R.drawable.bg_border_black);
            btnGcash.setBackgroundResource(0);
        }
    }

    private void savePaymentMethod() {
        String phone = etPhoneNumber.getText().toString().trim();
        String code = etCode.getText().toString().trim();

        if (selectedProvider.isEmpty()) {
            Toast.makeText(this, "Please select a payment method", Toast.LENGTH_SHORT).show();
            return;
        }
        if (phone.isEmpty()) {
            Toast.makeText(this, "Please enter phone number", Toast.LENGTH_SHORT).show();
            return;
        }
        // STRICT REQUIREMENT: Code is required for E-wallets
        if (code.isEmpty()) {
            Toast.makeText(this, "Please enter verification code", Toast.LENGTH_SHORT).show();
            return;
        }

        PaymentMethodRepository repo = new PaymentMethodRepository(this);

        String finalId = (isEditMode && methodId != null) ? methodId : UUID.randomUUID().toString();

        // 'false' indicates this is not the default payment method initially
        PaymentMethod method = new PaymentMethod(finalId, selectedProvider, phone, "N/A", false);

        repo.addOrUpdatePaymentMethod(method);

        String msg = isEditMode ? "Payment Method Updated" : "Payment Method Added";
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();

        finish();
    }
}