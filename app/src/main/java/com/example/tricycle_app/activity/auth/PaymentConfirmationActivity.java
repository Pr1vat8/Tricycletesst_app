package com.example.tricycle_app.activity.auth;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tricycle_app.R;
import com.example.tricycle_app.activity.user.UserPaymentMethodsActivity;

public class PaymentConfirmationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_confirmation);

        TextView tvProvider = findViewById(R.id.tvProvider);
        TextView tvNumber = findViewById(R.id.tvNumber);
        TextView btnBack = findViewById(R.id.btnBackToMethods);

        // Get data passed from registration
        String provider = getIntent().getStringExtra("PROVIDER");
        String number = getIntent().getStringExtra("NUMBER");

        if(tvProvider != null) tvProvider.setText(provider != null ? provider : "Unknown");
        if(tvNumber != null) tvNumber.setText(number != null ? number : "Unknown");

        if(btnBack != null) {
            btnBack.setOnClickListener(v -> {
                // Navigate back to the list and pass the new data to show it
                Intent intent = new Intent(this, UserPaymentMethodsActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.putExtra("NEW_PROVIDER", provider);
                intent.putExtra("NEW_NUMBER", number);
                startActivity(intent);
                finish();
            });
        }
    }
}