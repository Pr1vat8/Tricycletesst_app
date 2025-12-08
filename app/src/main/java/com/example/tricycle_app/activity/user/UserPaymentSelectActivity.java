package com.example.tricycle_app.activity.user;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tricycle_app.R;
import com.example.tricycle_app.adapter.PaymentMethodAdapter;
import com.example.tricycle_app.model.PaymentMethod;
import com.example.tricycle_app.repository.PaymentMethodRepository;
import com.example.tricycle_app.utils.UserNavbar;

import java.util.List;

public class UserPaymentSelectActivity extends AppCompatActivity {

    private RecyclerView rvPaymentSelect;
    private PaymentMethodAdapter adapter;
    private PaymentMethodRepository repository;
    private PaymentMethod selectedMethod = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.userpaymentselect);

        UserNavbar.setup(this);
        repository = new PaymentMethodRepository(this);

        LinearLayout btnBack = findViewById(R.id.btnBack);
        TextView btnNext = findViewById(R.id.btnNext);
        rvPaymentSelect = findViewById(R.id.rvPaymentSelect);

        rvPaymentSelect.setLayoutManager(new LinearLayoutManager(this));

        // Load Data from Repository
        loadPaymentMethods();

        if (btnBack != null) {
            btnBack.setOnClickListener(v -> finish());
        }

        if (btnNext != null) {
            btnNext.setOnClickListener(v -> {
                // Get selection from adapter
                if (adapter != null) selectedMethod = adapter.getSelectedMethod();

                if (selectedMethod == null) {
                    Toast.makeText(this, "Please select a payment method", Toast.LENGTH_SHORT).show();
                } else {
                    // Proceed to Confirmation
                    Intent intent = new Intent(UserPaymentSelectActivity.this, UserDriverConfirmActivity.class);
                    // Pass the selected payment details
                    intent.putExtra("PAYMENT_METHOD", selectedMethod.getProvider());
                    intent.putExtra("PAYMENT_DETAILS", selectedMethod.getPhoneNumber());
                    startActivity(intent);
                }
            });
        }
    }

    private void loadPaymentMethods() {
        List<PaymentMethod> methods = repository.getAllPaymentMethods();

        // FIX: Use anonymous inner class instead of lambda
        adapter = new PaymentMethodAdapter(methods, true, new PaymentMethodAdapter.OnItemActionListener() {
            @Override
            public void onItemClick(PaymentMethod method) {
                // Track selection
                selectedMethod = method;
            }

            @Override
            public void onDeleteClick(PaymentMethod method) {
                // Do nothing (Delete button is hidden in this mode)
            }
        });

        rvPaymentSelect.setAdapter(adapter);
    }
}