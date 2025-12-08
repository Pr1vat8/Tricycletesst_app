package com.example.tricycle_app.activity.auth;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tricycle_app.R;
import com.example.tricycle_app.model.PaymentMethod;
import com.example.tricycle_app.repository.PaymentMethodRepository;

import java.util.UUID;

public class PaymentMethodRegistrationActivity extends AppCompatActivity {

    private View selectedProvider = null;
    private String selectedProviderName = "";
    private EditText etPhoneNumber, etCode;
    private PaymentMethodRepository repository;
    private String mode = "ADD";
    private String editId = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.paymentmethodregistration);

        repository = new PaymentMethodRepository(this);

        LinearLayout btnBack = findViewById(R.id.btnBack);
        LinearLayout btnGcash = findViewById(R.id.btnGcash);
        LinearLayout btnMaya = findViewById(R.id.btnMaya);

        etPhoneNumber = findViewById(R.id.etPhoneNumber);
        etCode = findViewById(R.id.etCode);

        TextView btnResend = findViewById(R.id.btnResend);
        TextView btnDone = findViewById(R.id.btnDone);

        // Handle Edit Mode Data Loading
        if (getIntent().hasExtra("MODE")) {
            mode = getIntent().getStringExtra("MODE");
            if ("EDIT".equals(mode)) {
                editId = getIntent().getStringExtra("ID");
                String provider = getIntent().getStringExtra("PROVIDER");
                String phone = getIntent().getStringExtra("PHONE");

                etPhoneNumber.setText(phone);

                // Visual selection for edit
                if ("GCash".equalsIgnoreCase(provider) && btnGcash != null) {
                    selectProvider(btnGcash, "GCash");
                } else if ("PayMaya".equalsIgnoreCase(provider) && btnMaya != null) {
                    selectProvider(btnMaya, "PayMaya");
                }
            }
        }

        if(btnBack != null) btnBack.setOnClickListener(v -> finish());

        if(btnGcash != null) btnGcash.setOnClickListener(v -> selectProvider(v, "GCash"));
        if(btnMaya != null) btnMaya.setOnClickListener(v -> selectProvider(v, "PayMaya"));

        if(btnResend != null) {
            btnResend.setOnClickListener(v -> {
                if(etPhoneNumber.getText().toString().isEmpty()){
                    Toast.makeText(this, "Enter number first", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "OTP Code sent to " + etPhoneNumber.getText().toString(), Toast.LENGTH_SHORT).show();
                }
            });
        }

        if(btnDone != null) {
            btnDone.setOnClickListener(v -> {
                if(selectedProviderName.isEmpty()) {
                    Toast.makeText(this, "Please choose a provider", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(etPhoneNumber.getText().toString().isEmpty()) {
                    Toast.makeText(this, "Please enter number", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(etCode.getText().toString().isEmpty()) {
                    Toast.makeText(this, "Please enter the OTP Code", Toast.LENGTH_SHORT).show();
                    return;
                }

                // 1. Save Data
                String id = (editId != null) ? editId : UUID.randomUUID().toString();
                PaymentMethod method = new PaymentMethod(id, selectedProviderName, etPhoneNumber.getText().toString(), false);
                repository.addOrUpdatePaymentMethod(method);

                // 2. Show Success Pop-out (AlertDialog)
                // This uses the system's default design, no extra XML needed.
                new AlertDialog.Builder(this)
                        .setTitle("Success")
                        .setMessage("Payment method has been successfully saved.")
                        .setCancelable(false) // User must click OK
                        .setPositiveButton("OK", (dialog, which) -> {
                            finish(); // Close activity only after clicking OK
                        })
                        .show();
            });
        }
    }

    private void selectProvider(View v, String name) {
        if (selectedProvider != null) {
            selectedProvider.setAlpha(1.0f);
            selectedProvider.setBackgroundResource(0);
            if(selectedProvider.getId() == R.id.btnGcash) selectedProvider.setBackgroundResource(R.drawable.gcash);
            if(selectedProvider.getId() == R.id.btnMaya) selectedProvider.setBackgroundResource(R.drawable.maya);
        }
        selectedProvider = v;
        selectedProviderName = name;
        v.setAlpha(0.6f);
        v.setBackgroundResource(R.drawable.bg_rounded_border);
    }
}