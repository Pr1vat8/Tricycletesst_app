package com.example.tricycle_app.activity.user;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tricycle_app.R;
import com.example.tricycle_app.model.User;

public class UserEditProfileActivity extends AppCompatActivity {

    private EditText etName, etPhone, etEmail, etAddress, etGender, etBirthDate, etAge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.usereditprofile);

        UserRepository.init(this);

        LinearLayout btnBack = findViewById(R.id.btnBack);
        TextView btnSave = findViewById(R.id.btnSave);

        etName = findViewById(R.id.etName);
        etPhone = findViewById(R.id.etPhone);
        etEmail = findViewById(R.id.etEmail);
        etAddress = findViewById(R.id.etAddress);
        etGender = findViewById(R.id.etGender);
        etBirthDate = findViewById(R.id.etBirthDate);
        etAge = findViewById(R.id.etAge); // New

        // Load current data
        User user = UserRepository.getUser();
        if (user != null) {
            etName.setText(user.getName());
            etPhone.setText(user.getPhone());
            etEmail.setText(user.getEmail());
            etAddress.setText(user.getAddress());
            etGender.setText(user.getGender());
            etBirthDate.setText(user.getBirthDate());
            etAge.setText(user.getAge());
        }

        if(btnBack != null) btnBack.setOnClickListener(v -> finish());

        if(btnSave != null) {
            btnSave.setOnClickListener(v -> {
                String name = etName.getText().toString().trim();
                String phone = etPhone.getText().toString().trim();
                String email = etEmail.getText().toString().trim();
                String address = etAddress.getText().toString().trim();
                String gender = etGender.getText().toString().trim();
                String birthDate = etBirthDate.getText().toString().trim();
                String age = etAge.getText().toString().trim();

                if (name.isEmpty() || phone.isEmpty()) {
                    Toast.makeText(this, "Name and Phone are required", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Save to Repository & File
                UserRepository.updateUser(this, name, phone, email, address, gender, birthDate, age);

                Toast.makeText(this, "Profile Updated!", Toast.LENGTH_SHORT).show();
                finish();
            });
        }
    }
}