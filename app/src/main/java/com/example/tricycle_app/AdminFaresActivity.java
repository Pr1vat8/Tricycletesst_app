package com.example.tricycle_app;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class AdminFaresActivity extends AppCompatActivity {

    private FareAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adminfares);

        AdminNavbar.setup(this);
        FareRepository.init(this); // Initialize Data

        RecyclerView recyclerView = findViewById(R.id.recyclerViewFares);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new FareAdapter(this, FareRepository.getAllFares());

        adapter.setOnItemClickListener(fare -> {
            Intent intent = new Intent(AdminFaresActivity.this, AdminFareSetActivity.class);
            intent.putExtra("LOCATION_NAME", fare.getName());
            startActivity(intent);
        });

        recyclerView.setAdapter(adapter);

        LinearLayout btnBack = findViewById(R.id.btnBack);
        if (btnBack != null) btnBack.setOnClickListener(v -> finish());
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (adapter != null) adapter.notifyDataSetChanged(); // Refresh if price changed
    }
}