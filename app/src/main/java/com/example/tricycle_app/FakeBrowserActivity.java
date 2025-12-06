package com.example.tricycle_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class FakeBrowserActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fake_browser);

        LinearLayout btnFakeConfirm = findViewById(R.id.btnFakeConfirm);

        if (btnFakeConfirm != null) {
            btnFakeConfirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Simulate successful login -> Go to User Main Dashboard
                    Toast.makeText(FakeBrowserActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(FakeBrowserActivity.this, UserMainDashboardActivity.class);
                    // Clear history so pressing back doesn't go back to the "Fake Browser"
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                }
            });
        }
    }
}