package com.example.tricycle_app;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class AdminRidesHistoryActivity extends AppCompatActivity {

    private RideAdapter adapter;
    private boolean isNewestFirst = true; // State tracker
    private TextView tvSortLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adminrideshistory);

        AdminNavbar.setup(this);
        RideRepository.init(this);

        RecyclerView recyclerView = findViewById(R.id.recyclerViewRides);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        EditText etSearch = findViewById(R.id.etSearchRide);
        LinearLayout btnBack = findViewById(R.id.btnBack);

        // Sort Widget
        LinearLayout btnSort = findViewById(R.id.btnSort);
        tvSortLabel = findViewById(R.id.tvSortLabel);

        // Initial Load (Newest First)
        updateList();

        // Click Listener -> Pass ID
        adapter.setOnItemClickListener(ride -> {
            Intent intent = new Intent(AdminRidesHistoryActivity.this, AdminRideDetailsActivity.class);
            intent.putExtra("RIDE_ID", ride.getRideId());
            startActivity(intent);
        });

        recyclerView.setAdapter(adapter);

        // Sort Button Logic
        if (btnSort != null) {
            btnSort.setOnClickListener(v -> {
                isNewestFirst = !isNewestFirst; // Toggle state
                updateSortUI();
                updateList(); // Refresh data
            });
        }

        // Search Logic
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                // For simplicity, search always searches the full list, you can combine with sort if needed
                List<Ride> filtered = RideRepository.searchRides(s.toString());
                adapter.updateList(filtered);
            }
            @Override public void afterTextChanged(Editable s) {}
        });

        if (btnBack != null) btnBack.setOnClickListener(v -> finish());
    }

    private void updateList() {
        // Get sorted data from repository
        List<Ride> list = RideRepository.getSortedRides(isNewestFirst);
        if (adapter == null) {
            adapter = new RideAdapter(this, list);
        } else {
            adapter.updateList(list);
        }
    }

    private void updateSortUI() {
        if (isNewestFirst) {
            tvSortLabel.setText("Newest");
        } else {
            tvSortLabel.setText("Oldest");
        }
    }
}