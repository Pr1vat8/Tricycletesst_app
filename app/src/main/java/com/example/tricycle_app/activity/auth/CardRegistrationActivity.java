package com.example.tricycle_app.activity.auth;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tricycle_app.R;
import com.example.tricycle_app.model.PaymentMethod;
import com.example.tricycle_app.repository.PaymentMethodRepository;

import java.util.UUID;

public class CardRegistrationActivity extends AppCompatActivity {

    private TextView btnVisa, btnMastercard, btnSaveCard;
    private EditText etCardNumber, etExpiry, etCvv;

    private String selectedCardType = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_registration);

        btnVisa = findViewById(R.id.btnVisa);
        btnMastercard = findViewById(R.id.btnMastercard);
        etCardNumber = findViewById(R.id.etCardNumber);
        etExpiry = findViewById(R.id.etExpiry);
        etCvv = findViewById(R.id.etCvv);
        btnSaveCard = findViewById(R.id.btnSaveCard);

        findViewById(R.id.btnBack).setOnClickListener(v -> finish());

        // Card Type Selection
        btnVisa.setOnClickListener(v -> selectCard("Visa"));
        btnMastercard.setOnClickListener(v -> selectCard("Mastercard"));

        btnSaveCard.setOnClickListener(v -> saveCard());
    }

    private void selectCard(String type) {
        selectedCardType = type;
        if ("Visa".equals(type)) {
            btnVisa.setBackgroundResource(R.drawable.bg_border_black);
            btnMastercard.setBackgroundResource(R.drawable.bg_rounded_light_grey);
        } else {
            btnMastercard.setBackgroundResource(R.drawable.bg_border_black);
            btnVisa.setBackgroundResource(R.drawable.bg_rounded_light_grey);
        }
    }

    private void saveCard() {
        String number = etCardNumber.getText().toString().trim();
        String expiry = etExpiry.getText().toString().trim();
        String cvv = etCvv.getText().toString().trim();

        if (selectedCardType.isEmpty()) {
            Toast.makeText(this, "Select Visa or Mastercard", Toast.LENGTH_SHORT).show();
            return;
        }
        if (number.isEmpty() || number.length() < 12) {
            Toast.makeText(this, "Enter valid card number", Toast.LENGTH_SHORT).show();
            return;
        }
        if (expiry.isEmpty() || cvv.isEmpty()) {
            Toast.makeText(this, "Fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        PaymentMethodRepository repo = new PaymentMethodRepository(this);

        PaymentMethod method = new PaymentMethod(
                UUID.randomUUID().toString(),
                "Card",
                number,
                selectedCardType,
                false
        );

        repo.addOrUpdatePaymentMethod(method);
        Toast.makeText(this, "Card Added Successfully", Toast.LENGTH_SHORT).show();
        finish();
    }
}