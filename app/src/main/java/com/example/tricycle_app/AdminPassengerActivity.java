package com.example.tricycle_app;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class AdminPassengerActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private PassengerAdapter adapter;
    private EditText etSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adminpassenger);

        AdminNavbar.setup(this);
        PassengerRepository.init(this);

        LinearLayout btnBack = findViewById(R.id.btnBack);
        if (btnBack != null) btnBack.setOnClickListener(v -> finish());

        recyclerView = findViewById(R.id.recyclerViewPassengers);
        etSearch = findViewById(R.id.etSearchPassenger);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Load initial data
        adapter = new PassengerAdapter(this, PassengerRepository.getAllPassengers());

        adapter.setOnItemClickListener(position -> {
            // Note: Position here refers to the FILTERED list index.
            // For a robust app, you should pass IDs. For this demo, we assume list order is static.
            Intent intent = new Intent(AdminPassengerActivity.this, AdminPassengerDetailsActivity.class);
            intent.putExtra("PASSENGER_INDEX", position);
            startActivity(intent);
        });

        recyclerView.setAdapter(adapter);

        // --- SEARCH LOGIC ---
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                List<Passenger> filtered = PassengerRepository.searchPassengers(s.toString());
                adapter.updateList(filtered);
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (adapter != null) {
            // Refresh list, respecting current search query
            String query = etSearch.getText().toString();
            if (query.isEmpty()) {
                adapter.updateList(PassengerRepository.getAllPassengers());
            } else {
                adapter.updateList(PassengerRepository.searchPassengers(query));
            }
        }
    }
}