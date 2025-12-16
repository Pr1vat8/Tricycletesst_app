package com.example.tricycle_app.activity.user;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tricycle_app.R;
import com.example.tricycle_app.activity.auth.PaymentMethodRegistrationActivity;
import com.example.tricycle_app.adapter.PaymentMethodAdapter;
import com.example.tricycle_app.model.PaymentMethod;
import com.example.tricycle_app.repository.PaymentMethodRepository;
import com.example.tricycle_app.utils.AdminNavbar;
import com.example.tricycle_app.utils.UserNavbar;

import java.util.List;

public class UserPaymentMethodsActivity extends AppCompatActivity {

    private RecyclerView rvPaymentMethods;
    private PaymentMethodRepository repository;
    private PaymentMethodAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.userpaymentmethods);

        UserNavbar.setup(this);

        repository = new PaymentMethodRepository(this);

        LinearLayout btnBack = findViewById(R.id.btnBack);
        TextView btnAdd = findViewById(R.id.btnAddPayment);
        rvPaymentMethods = findViewById(R.id.rvPaymentMethods);

        rvPaymentMethods.setLayoutManager(new LinearLayoutManager(this));

        if(btnBack != null) btnBack.setOnClickListener(v -> finish());

        if(btnAdd != null) {
            btnAdd.setOnClickListener(v -> {
                Intent intent = new Intent(this, PaymentMethodRegistrationActivity.class);
                intent.putExtra("MODE", "ADD");
                startActivity(intent);
            });
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadPaymentMethods();
    }

    private void loadPaymentMethods() {
        List<PaymentMethod> methods = repository.getAllPaymentMethods();

        // Settings Mode = false (Allows Editing/Deleting)
        adapter = new PaymentMethodAdapter(methods, false, new PaymentMethodAdapter.OnItemActionListener() {
            @Override
            public void onItemClick(PaymentMethod method) {
                // EDIT
                Intent intent = new Intent(UserPaymentMethodsActivity.this, PaymentMethodRegistrationActivity.class);
                intent.putExtra("MODE", "EDIT");
                intent.putExtra("ID", method.getId());
                intent.putExtra("PROVIDER", method.getProvider());
                intent.putExtra("PHONE", method.getPhoneNumber());
                startActivity(intent);
            }

            @Override
            public void onDeleteClick(PaymentMethod method) {
                // DELETE POP-OUT (CONFIRMATION)
                // No XML used, just standard AlertDialog
                new AlertDialog.Builder(UserPaymentMethodsActivity.this)
                        .setTitle("Delete Payment Method")
                        .setMessage("Are you sure you want to remove " + method.getProvider() + "?")
                        .setPositiveButton("Delete", (dialog, which) -> {
                            // Actual delete logic
                            repository.deletePaymentMethod(method.getId());
                            loadPaymentMethods(); // Refresh list immediately
                            Toast.makeText(UserPaymentMethodsActivity.this, "Deleted", Toast.LENGTH_SHORT).show();
                        })
                        .setNegativeButton("Cancel", null) // Do nothing on cancel
                        .show();
            }
        });

        rvPaymentMethods.setAdapter(adapter);
    }
}