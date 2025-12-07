package com.example.tricycle_app;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class UserPaymentSelectActivity extends AppCompatActivity {

    private View selectedPayment = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.userpaymentselect);

        UserNavbar.setup(this);

        TextView btnNext = findViewById(R.id.btnNext);
        LinearLayout btnBack = findViewById(R.id.btnBack);

        // Setup Payments
        setupPaymentSelect(findViewById(R.id.payCard));
        setupPaymentSelect(findViewById(R.id.payWallet));

        if (btnNext != null) {
            btnNext.setOnClickListener(v -> {
                if (selectedPayment == null) {
                    Toast.makeText(this, "Please select a payment method", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(UserPaymentSelectActivity.this, UserDriverConfirmActivity.class);
                    startActivity(intent);
                }
            });
        }

        if (btnBack != null) {
            btnBack.setOnClickListener(v -> finish());
        }
    }

    private void setupPaymentSelect(View view) {
        if (view == null) return;
        view.setOnClickListener(v -> {
            // Reset old selection
            if (selectedPayment != null) {
                selectedPayment.setBackgroundColor(Color.TRANSPARENT);
            }
            // Set new selection
            selectedPayment = view;
            selectedPayment.setBackgroundResource(R.drawable.bg_rounded_light_grey);
        });
    }
}