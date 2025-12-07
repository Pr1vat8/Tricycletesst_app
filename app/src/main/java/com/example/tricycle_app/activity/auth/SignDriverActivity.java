package com.example.tricycle_app.activity.auth;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tricycle_app.R;

public class SignDriverActivity extends AppCompatActivity {

    // Track which button was clicked so we update the correct one
    private TextView activeUploadButton;

    // Setup the Gallery Result Launcher
    private final ActivityResultLauncher<Intent> galleryLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                    // User successfully picked an image
                    Uri selectedImageUri = result.getData().getData();

                    // Update the UI to show it's done
                    if (activeUploadButton != null) {
                        activeUploadButton.setText("Done");
                        // Use standard green or parse your custom hex
                        activeUploadButton.setTextColor(Color.parseColor("#088738"));
                        Toast.makeText(this, "Document selected!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signdriver);

        // 1. Find Views
        LinearLayout btnChoosePayment = findViewById(R.id.btnChoosePayment);
        LinearLayout btnBack = findViewById(R.id.btnBack);

        TextView btnUploadLicense = findViewById(R.id.btnUploadLicense);
        TextView btnUploadRegistration = findViewById(R.id.btnUploadRegistration);

        // Find the Register Button
        TextView btnRegister = findViewById(R.id.btnRegister);

        // 2. Payment Method Logic
        if (btnChoosePayment != null) {
            btnChoosePayment.setOnClickListener(v -> {
                Intent intent = new Intent(SignDriverActivity.this, PaymentMethodRegistrationActivity.class);
                startActivity(intent);
            });
        }

        // 3. Upload License Logic
        if (btnUploadLicense != null) {
            btnUploadLicense.setOnClickListener(v -> {
                activeUploadButton = btnUploadLicense; // Remember which button
                openGallery();
            });
        }

        // 4. Upload Registration Logic
        if (btnUploadRegistration != null) {
            btnUploadRegistration.setOnClickListener(v -> {
                activeUploadButton = btnUploadRegistration; // Remember which button
                openGallery();
            });
        }

        // 5. Register Button Logic -> Go to Confirmation
        if (btnRegister != null) {
            btnRegister.setOnClickListener(v -> {
                // In a real app, you would validate the inputs here first

                Intent intent = new Intent(SignDriverActivity.this, SignupConfirmationActivity.class);
                startActivity(intent);
                finish(); // Close this screen so they can't go back to the form
            });
        }

        // 6. Back Button
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> finish());
        }
    }

    // Helper method to open the phone's gallery
    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        galleryLauncher.launch(intent);
    }
}