package com.example.tricycle_app.activity.user;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tricycle_app.R;
import com.example.tricycle_app.repository.SavedPlaceRepository;
import com.example.tricycle_app.adapter.SavedPlaceAdapter;

public class UserSavedPlacesActivity extends AppCompatActivity {

    private SavedPlaceAdapter adapter;
    private TextView btnEdit, btnDelete;
    private int selectedIndex = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.usersavedplaces);

        SavedPlaceRepository.init(this);

        LinearLayout btnBack = findViewById(R.id.btnBack);
        TextView btnAddPlace = findViewById(R.id.btnAddPlace);
        btnEdit = findViewById(R.id.btnEdit);
        btnDelete = findViewById(R.id.btnDelete);
        RecyclerView recyclerView = findViewById(R.id.recyclerViewSavedPlaces);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new SavedPlaceAdapter(this, SavedPlaceRepository.getAllSavedPlaces());

        adapter.setOnItemClickListener((position, place) -> {
            selectedIndex = position;
            enableActionButtons(true);
        });

        recyclerView.setAdapter(adapter);

        if (btnBack != null) btnBack.setOnClickListener(v -> finish());

        if (btnAddPlace != null) {
            btnAddPlace.setOnClickListener(v -> {
                startActivity(new Intent(this, UserAddPlaceActivity.class));
            });
        }

        // Delete Logic
        if (btnDelete != null) {
            btnDelete.setOnClickListener(v -> {
                if (selectedIndex != -1) {
                    SavedPlaceRepository.deleteSavedPlace(this, selectedIndex);
                    refreshList();
                    Toast.makeText(this, "Deleted", Toast.LENGTH_SHORT).show();
                }
            });
        }

        // Edit Logic
        if (btnEdit != null) {
            btnEdit.setOnClickListener(v -> {
                if (selectedIndex != -1) {
                    Intent intent = new Intent(this, UserEditSavedPlaceActivity.class);
                    intent.putExtra("PLACE_INDEX", selectedIndex);
                    startActivity(intent);
                }
            });
        }
    }

    private void enableActionButtons(boolean enable) {
        float alpha = enable ? 1.0f : 0.5f;

        btnEdit.setClickable(enable);
        btnEdit.setAlpha(alpha);

        btnDelete.setClickable(enable);
        btnDelete.setAlpha(alpha);
    }

    private void refreshList() {
        adapter.notifyDataSetChanged();
        selectedIndex = -1;
        adapter.clearSelection();
        enableActionButtons(false);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (adapter != null) {
            SavedPlaceRepository.loadAll(this);
            refreshList();
        }
    }
}