package com.example.tricycle_app;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class UserRideHistoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.userridehistory);

        UserNavbar.setup(this);
        UserRideRepository.init(this); // Load User Data

        LinearLayout btnBack = findViewById(R.id.btnBack);
        if (btnBack != null) btnBack.setOnClickListener(v -> finish());

        RecyclerView recyclerView = findViewById(R.id.recyclerViewRides);
        if (recyclerView != null) {
            recyclerView.setLayoutManager(new LinearLayoutManager(this));

            // Use the specific UserRideAdapter (No IDs)
            UserRideAdapter adapter = new UserRideAdapter(this, UserRideRepository.getAllRides());

            adapter.setOnItemClickListener(ride -> {
                // We still pass the ID internally so the next screen knows which ride to load
                Intent intent = new Intent(UserRideHistoryActivity.this, UserRideDetailsActivity.class);
                intent.putExtra("RIDE_ID", ride.getRideId());
                startActivity(intent);
            });

            recyclerView.setAdapter(adapter);
        }
    }
}