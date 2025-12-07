package com.example.tricycle_app.activity.user;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.tricycle_app.R;
import com.example.tricycle_app.utils.DriverNavbar;
import com.example.tricycle_app.repository.DriverEarningRepository;
import java.util.List;

public class DriverEarningActivity extends AppCompatActivity {

    private TextView tvTotalEarnings, tvTotalRides, tvFareTotal, tvDateHeader;
    private TextView tvDay, tvWeek, tvMonth;
    private View lineDay, lineWeek, lineMonth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.driverearning);

        DriverNavbar.setup(this);
        DriverEarningRepository.init(this);

        tvTotalEarnings = findViewById(R.id.tvTotalEarnings);
        tvTotalRides = findViewById(R.id.tvTotalRides);
        tvFareTotal = findViewById(R.id.tvFareTotal);
        tvDateHeader = findViewById(R.id.tvDateHeader);

        tvDay = findViewById(R.id.tvDay);
        tvWeek = findViewById(R.id.tvWeek);
        tvMonth = findViewById(R.id.tvMonth);

        lineDay = findViewById(R.id.lineDay);
        lineWeek = findViewById(R.id.lineWeek);
        lineMonth = findViewById(R.id.lineMonth);

        findViewById(R.id.tabDay).setOnClickListener(v -> updateUI("Day"));
        findViewById(R.id.tabWeek).setOnClickListener(v -> updateUI("Week"));
        findViewById(R.id.tabMonth).setOnClickListener(v -> updateUI("Month"));

        LinearLayout btnBack = findViewById(R.id.btnBack);
        if (btnBack != null) btnBack.setOnClickListener(v -> finish());

        updateUI("Week");
    }

    private void updateUI(String filter) {
        List<DriverEarningRepository.Earning> list = DriverEarningRepository.getEarningsByFilter(filter);
        int total = DriverEarningRepository.getTotal(list);
        int rideCount = list.size();

        if (tvTotalEarnings != null) tvTotalEarnings.setText("₱" + total);
        if (tvFareTotal != null) tvFareTotal.setText("₱" + total);
        if (tvTotalRides != null) tvTotalRides.setText(String.valueOf(rideCount));

        if (tvDateHeader != null) {
            if (filter.equals("Day")) tvDateHeader.setText("Today");
            else if (filter.equals("Week")) tvDateHeader.setText("This Week");
            else tvDateHeader.setText("This Month");
        }

        int activeColor = Color.parseColor("#121417");
        int inactiveColor = Color.parseColor("#61768A");

        if (tvDay != null) tvDay.setTextColor(inactiveColor);
        if (tvWeek != null) tvWeek.setTextColor(inactiveColor);
        if (tvMonth != null) tvMonth.setTextColor(inactiveColor);

        if (lineDay != null) lineDay.setBackgroundColor(Color.TRANSPARENT);
        if (lineWeek != null) lineWeek.setBackgroundColor(Color.TRANSPARENT);
        if (lineMonth != null) lineMonth.setBackgroundColor(Color.TRANSPARENT);

        if (filter.equals("Day")) {
            if (tvDay != null) tvDay.setTextColor(activeColor);
            if (lineDay != null) lineDay.setBackgroundColor(activeColor);
        } else if (filter.equals("Week")) {
            if (tvWeek != null) tvWeek.setTextColor(activeColor);
            if (lineWeek != null) lineWeek.setBackgroundColor(activeColor);
        } else if (filter.equals("Month")) {
            if (tvMonth != null) tvMonth.setTextColor(activeColor);
            if (lineMonth != null) lineMonth.setBackgroundColor(activeColor);
        }
    }
}