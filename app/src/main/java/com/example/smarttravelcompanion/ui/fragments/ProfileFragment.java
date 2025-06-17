package com.example.smarttravelcompanion.ui.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.smarttravelcompanion.R;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class ProfileFragment extends Fragment {
    private static final int PICK_IMAGE = 1001;
    private ImageView imageProfile;
    private EditText editName;
    private SharedPreferences prefs;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        imageProfile = view.findViewById(R.id.image_profile);
        editName = view.findViewById(R.id.edit_profile_name);
        Button btnChangePic = view.findViewById(R.id.btn_change_picture);
        Button btnSave = view.findViewById(R.id.btn_save_profile);
        prefs = requireContext().getSharedPreferences("profile", Context.MODE_PRIVATE);

        // Load saved name and image
        editName.setText(prefs.getString("name", ""));
        String imageUri = prefs.getString("imageUri", null);
        if (imageUri != null) {
            imageProfile.setImageURI(Uri.parse(imageUri));
        }

        btnChangePic.setOnClickListener(v -> pickImage());
        btnSave.setOnClickListener(v -> saveProfile());
        return view;
    }

    private void pickImage() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode == Activity.RESULT_OK && data != null) {
            Uri imageUri = data.getData();
            if (imageUri != null) {
                imageProfile.setImageURI(imageUri);
                prefs.edit().putString("imageUri", imageUri.toString()).apply();
            }
        }
    }

    private void saveProfile() {
        String name = editName.getText().toString().trim();
        prefs.edit().putString("name", name).apply();
        Toast.makeText(requireContext(), "Profile saved!", Toast.LENGTH_SHORT).show();
    }
} 