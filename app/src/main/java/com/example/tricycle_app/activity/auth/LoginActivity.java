package com.example.tricycle_app.activity.auth;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tricycle_app.activity.admin.AdminDashboardActivity;
import com.example.tricycle_app.activity.user.DriverDashboardActivity;
import com.example.tricycle_app.activity.driver.DriverProfileActivity;
import com.example.tricycle_app.activity.user.UserClientProfileActivity;
import com.example.tricycle_app.activity.user.UserMainDashboardActivity;
import com.example.tricycle_app.activity.user.UserRepository;
import com.example.tricycle_app.R;
import com.example.tricycle_app.model.Driver;
import com.example.tricycle_app.model.Passenger;
import com.example.tricycle_app.model.User;
import com.example.tricycle_app.repository.DriverRepository;
import com.example.tricycle_app.repository.PassengerRepository;

public class LoginActivity extends AppCompatActivity {

    private EditText etUsername, etPassword;
    private TextView btnLogin, tvSignUp;
    // Added back: Google and Facebook buttons
    private TextView btnGoogle, btnFacebook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginlayout);

        DriverRepository.init(this);
        PassengerRepository.init(this);
        UserRepository.init(this); // Init session repo

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        tvSignUp = findViewById(R.id.tvSignUp);

        // Initialize Google and Facebook buttons
        btnGoogle = findViewById(R.id.btnGoogle);
        btnFacebook = findViewById(R.id.btnFacebook);

        if (btnLogin != null) {
            btnLogin.setOnClickListener(v -> {
                String user = etUsername.getText().toString().trim();
                String pass = etPassword.getText().toString().trim();

                // Admin
                if (user.equals("admin") && pass.equals("admin")) {
                    startActivity(new Intent(LoginActivity.this, AdminDashboardActivity.class));
                    return;
                }

                // Driver Login
                Driver d = DriverRepository.login(user, pass);
                if (d != null) {
                    if (d.isSuspended()) {
                        Intent intent = new Intent(LoginActivity.this, DriverProfileActivity.class);
                        intent.putExtra("DRIVER_ID", d.getId());
                        startActivity(intent);
                        Toast.makeText(this, "Driver Account Suspended", Toast.LENGTH_LONG).show();
                    } else {
                        startActivity(new Intent(LoginActivity.this, DriverDashboardActivity.class));
                    }
                    return;
                }

                // Passenger (User) Login
                Passenger p = PassengerRepository.login(user, pass);
                if (p != null) {
                    // Sync ALL Passenger data to User Session
                    User sessionUser = new User(
                            p.getName(),
                            p.getPhone(),
                            p.getEmail(),
                            p.getAddress(),
                            p.getGender(),
                            p.getBirthDate(),
                            p.getAge(),
                            p.getDateJoined(),
                            p.isSuspended(),
                            p.getSuspendStartDate(),
                            p.getSuspendEndDate()
                    );
                    UserRepository.setCurrentUser(this, sessionUser);

                    if (p.isSuspended()) {
                        startActivity(new Intent(LoginActivity.this, UserClientProfileActivity.class));
                        Toast.makeText(this, "Account Suspended", Toast.LENGTH_LONG).show();
                    } else {
                        startActivity(new Intent(LoginActivity.this, UserMainDashboardActivity.class));
                    }
                    return;
                }

                // Hardcoded fallback (if needed)
                if (user.equals("user") && pass.equals("user")) {
                    startActivity(new Intent(LoginActivity.this, UserMainDashboardActivity.class));
                    return;
                }

                Toast.makeText(LoginActivity.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
            });
        }

        if (tvSignUp != null) {
            tvSignUp.setOnClickListener(v -> startActivity(new Intent(LoginActivity.this, SignupActivity.class)));
        }

        // Restored: Google Login Logic
        if (btnGoogle != null) {
            btnGoogle.setOnClickListener(v -> {
                startActivity(new Intent(LoginActivity.this, FakeBrowserActivity.class));
            });
        }

        // Restored: Facebook Login Logic
        if (btnFacebook != null) {
            btnFacebook.setOnClickListener(v -> {
                startActivity(new Intent(LoginActivity.this, FakeFacebookActivity.class));
            });
        }
    }
}