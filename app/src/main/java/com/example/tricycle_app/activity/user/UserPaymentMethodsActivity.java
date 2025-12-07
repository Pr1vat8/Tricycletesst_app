package com.example.tricycle_app.activity.user;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tricycle_app.R;
import com.example.tricycle_app.activity.auth.PaymentMethodRegistrationActivity;

public class UserPaymentMethodsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.userpaymentmethods);

        LinearLayout btnBack = findViewById(R.id.btnBack);
        TextView btnAdd = findViewById(R.id.btnAddPayment);

        if(btnBack != null) btnBack.setOnClickListener(v -> finish());

        // Could link to PaymentMethodRegistrationActivity if desired
        if(btnAdd != null) {
            btnAdd.setOnClickListener(v -> {
                startActivity(new Intent(this, PaymentMethodRegistrationActivity.class));
            });
        }
    }
}