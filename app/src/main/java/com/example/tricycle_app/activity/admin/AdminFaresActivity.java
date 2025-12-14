package com.example.tricycle_app.activity.admin;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tricycle_app.R;
import com.example.tricycle_app.adapter.FareAdapter;
import com.example.tricycle_app.repository.FareRepository;
import com.example.tricycle_app.utils.AdminNavbar;

public class AdminFaresActivity extends AppCompatActivity {

    private FareAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adminfares);

        AdminNavbar.setup(this);
        FareRepository.init(this);

        RecyclerView recyclerView = findViewById(R.id.recyclerViewFares);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new FareAdapter(this, FareRepository.getAllFares());

        adapter.setOnItemClickListener(fare -> {
            // Edit Mode
            Intent intent = new Intent(AdminFaresActivity.this, AdminFareSetActivity.class);
            intent.putExtra("LOCATION_NAME", fare.getName());
            startActivity(intent);
        });

        recyclerView.setAdapter(adapter);

        // Add Button Logic
        View btnAddLocation = findViewById(R.id.btnAddLocation);
        if (btnAddLocation != null) {
            btnAddLocation.setOnClickListener(v -> {
                // Add Mode (No Extra passed)
                startActivity(new Intent(AdminFaresActivity.this, AdminFareSetActivity.class));
            });
        }

        LinearLayout btnBack = findViewById(R.id.btnBack);
        if (btnBack != null) btnBack.setOnClickListener(v -> finish());
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (adapter != null) {
            // Reload data in case a new item was added
            FareRepository.loadAll(this);
            adapter.notifyDataSetChanged();
        }
    }
}