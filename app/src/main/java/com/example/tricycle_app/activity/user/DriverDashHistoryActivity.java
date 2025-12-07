package com.example.tricycle_app.activity.user; // Update package if needed

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.tricycle_app.R;
import com.example.tricycle_app.utils.DriverNavbar;
import com.example.tricycle_app.repository.DriverRideRepository;
import com.example.tricycle_app.adapter.DriverRideAdapter;

public class DriverDashHistoryActivity extends AppCompatActivity {

    private DriverRideAdapter adapter;
    private TextView tvUpcoming, tvPast;
    private View lineUpcoming, linePast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.driverdashhistory);

        DriverNavbar.setup(this);
        DriverRideRepository.init(this);

        RecyclerView recyclerView = findViewById(R.id.recyclerViewRides);
        tvUpcoming = findViewById(R.id.tvUpcoming);
        tvPast = findViewById(R.id.tvPast);
        lineUpcoming = findViewById(R.id.lineUpcoming);
        linePast = findViewById(R.id.linePast);
        LinearLayout btnBack = findViewById(R.id.btnBack);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Load default list
        adapter = new DriverRideAdapter(this, DriverRideRepository.getRidesByTab("Upcoming"));

        // --- ADD CLICK LISTENER ---
        adapter.setOnItemClickListener(ride -> {
            Intent intent = new Intent(DriverDashHistoryActivity.this, DriverRideDetailsActivity.class);
            intent.putExtra("RIDE_ID", ride.getRideId());
            startActivity(intent);
        });

        recyclerView.setAdapter(adapter);

        findViewById(R.id.tabUpcoming).setOnClickListener(v -> switchTab("Upcoming"));
        findViewById(R.id.tabPast).setOnClickListener(v -> switchTab("Past"));

        if (btnBack != null) btnBack.setOnClickListener(v -> finish());
    }

    private void switchTab(String tab) {
        adapter.updateList(DriverRideRepository.getRidesByTab(tab));

        int activeColor = Color.parseColor("#121417");
        int inactiveColor = Color.parseColor("#61768A");
        int transparent = Color.TRANSPARENT;

        if (tab.equals("Upcoming")) {
            tvUpcoming.setTextColor(activeColor);
            lineUpcoming.setBackgroundColor(activeColor);
            tvPast.setTextColor(inactiveColor);
            linePast.setBackgroundColor(transparent);
        } else {
            tvPast.setTextColor(activeColor);
            linePast.setBackgroundColor(activeColor);
            tvUpcoming.setTextColor(inactiveColor);
            lineUpcoming.setBackgroundColor(transparent);
        }
    }
}