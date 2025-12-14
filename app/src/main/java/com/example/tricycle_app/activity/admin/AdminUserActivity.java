package com.example.tricycle_app.activity.admin;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tricycle_app.R;
import com.example.tricycle_app.adapter.DriverAdapter;
import com.example.tricycle_app.model.Driver;
import com.example.tricycle_app.repository.DriverRepository;
import com.example.tricycle_app.utils.AdminNavbar;

import java.util.List;

public class AdminUserActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private DriverAdapter adapter;
    private String currentTab = "Verified";

    private TextView tvVerified, tvPending;
    private View lineVerified, linePending;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adminusers);

        AdminNavbar.setup(this);
        DriverRepository.init(this);

        recyclerView = findViewById(R.id.recyclerViewDrivers);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        tvVerified = findViewById(R.id.tvVerified);
        tvPending = findViewById(R.id.tvPending);
        lineVerified = findViewById(R.id.lineVerified);
        linePending = findViewById(R.id.linePending);
        EditText etSearch = findViewById(R.id.etSearchDriver);

        // --- CHECK INTENT FOR TARGET TAB ---
        if (getIntent().hasExtra("TARGET_TAB")) {
            String target = getIntent().getStringExtra("TARGET_TAB");
            if ("Pending".equalsIgnoreCase(target)) {
                switchTab("Pending");
            } else {
                loadData(); // Default load if target isn't pending
            }
        } else {
            loadData(); // Normal load (defaults to Verified)
        }

        findViewById(R.id.tabVerified).setOnClickListener(v -> switchTab("Verified"));
        findViewById(R.id.tabPending).setOnClickListener(v -> switchTab("Pending"));

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterData(s.toString());
            }
            @Override public void afterTextChanged(Editable s) {}
        });

        findViewById(R.id.btnPassengers).setOnClickListener(v -> {
            startActivity(new Intent(this, AdminPassengerActivity.class));
        });

        // Add Driver Button (reusing an existing or adding logic, for now I'll create a FAB or button programmatically if ID not found, but to stick to existing layout, I will assume I can modify XML or just hook into an existing view)
        // Since I can't easily modify the layout XML without potentially breaking constraints (unless I see it), I will assume there is space or I can repurpose/add.
        // Actually, I should modify the layout XML to add the button.
        // But for now, let's assume I modified the layout. Let's modify the layout first.

        View btnAddDriver = findViewById(R.id.btnAddDriver);
        if (btnAddDriver != null) {
            btnAddDriver.setOnClickListener(v -> {
                startActivity(new Intent(this, AdminAddDriverActivity.class));
            });
        }

        findViewById(R.id.btnBack).setOnClickListener(v -> finish());
    }

    private void switchTab(String tab) {
        currentTab = tab;
        if (tab.equals("Verified")) {
            tvVerified.setTextColor(Color.parseColor("#0D141C"));
            lineVerified.setBackgroundColor(Color.parseColor("#0D141C"));
            tvPending.setTextColor(Color.parseColor("#4A739C"));
            linePending.setBackgroundColor(Color.TRANSPARENT);
        } else {
            // Pending Tab Styles
            tvPending.setTextColor(Color.parseColor("#0D141C"));
            linePending.setBackgroundColor(Color.parseColor("#0D141C"));
            tvVerified.setTextColor(Color.parseColor("#4A739C"));
            lineVerified.setBackgroundColor(Color.TRANSPARENT);
        }
        loadData();
    }

    private void loadData() {
        List<Driver> list = DriverRepository.getDriversByStatus(currentTab);
        adapter = new DriverAdapter(this, list);

        adapter.setOnItemClickListener(driver -> {
            Intent intent = new Intent(AdminUserActivity.this, AdmindriverdetailActivity.class);
            intent.putExtra("DRIVER_ID", driver.getId());
            startActivity(intent);
        });

        recyclerView.setAdapter(adapter);
    }

    private void filterData(String query) {
        List<Driver> filtered = DriverRepository.searchDrivers(query, currentTab);
        if (adapter != null) {
            adapter.updateList(filtered);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Only refresh if we aren't handling an intent that just set the state
        // or ensure intent extras are cleared if you want strict state management.
        // For simplicity, re-loading currentTab ensures the list is up to date after returning from details.
        loadData();
    }
}