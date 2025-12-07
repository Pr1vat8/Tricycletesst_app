package com.example.tricycle_app.activity.user;

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

        // Init Views
        RecyclerView recyclerView = findViewById(R.id.recyclerViewRides);
        tvUpcoming = findViewById(R.id.tvUpcoming);
        tvPast = findViewById(R.id.tvPast);
        lineUpcoming = findViewById(R.id.lineUpcoming);
        linePast = findViewById(R.id.linePast);
        LinearLayout btnBack = findViewById(R.id.btnBack);

        // Setup Recycler
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new DriverRideAdapter(this, DriverRideRepository.getRidesByTab("Upcoming"));
        recyclerView.setAdapter(adapter);

        // Tab Logic
        findViewById(R.id.tabUpcoming).setOnClickListener(v -> switchTab("Upcoming"));
        findViewById(R.id.tabPast).setOnClickListener(v -> switchTab("Past"));

        if (btnBack != null) btnBack.setOnClickListener(v -> finish());
    }

    private void switchTab(String tab) {
        adapter.updateList(DriverRideRepository.getRidesByTab(tab));

        if (tab.equals("Upcoming")) {
            tvUpcoming.setTextColor(Color.parseColor("#121417"));
            lineUpcoming.setBackgroundColor(Color.parseColor("#121417"));
            tvPast.setTextColor(Color.parseColor("#61768A"));
            linePast.setBackgroundColor(Color.TRANSPARENT);
        } else {
            tvPast.setTextColor(Color.parseColor("#121417"));
            linePast.setBackgroundColor(Color.parseColor("#121417"));
            tvUpcoming.setTextColor(Color.parseColor("#61768A"));
            lineUpcoming.setBackgroundColor(Color.TRANSPARENT);
        }
    }
}