package com.example.tricycle_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class SignDriverActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signdriver); // This is your long registration form

        // 1. Find the Payment Method Selector (You need to add an ID to it in XML first!)
        // Let's assume you add android:id="@+id/btnChoosePayment" to the LinearLayout wrapping "Choose payment method"
        LinearLayout btnChoosePayment = findViewById(R.id.btnChoosePayment);

        // 2. Click Logic
        if (btnChoosePayment != null) {
            btnChoosePayment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(SignDriverActivity.this, PaymentMethodRegistrationActivity.class);
                    startActivity(intent);
                }
            });
        }

        // 3. Back Button
        LinearLayout btnBack = findViewById(R.id.btnBack);
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> finish());
        }
    }
}