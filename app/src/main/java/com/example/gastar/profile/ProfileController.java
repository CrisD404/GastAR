package com.example.gastar.profile;

import android.app.AlertDialog;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.gastar.Handler;
import com.example.gastar.R;
import com.example.gastar.profile.dto.UserProfileDto;

public class ProfileController extends Fragment {
    private final Handler handler = Handler.getInstance();
    private ImageView imgViewField;
    private TextView fullNameField;

    public ProfileController() {
        super(R.layout.profile_toolbar);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        Button editProfile = view.findViewById(R.id.edit_profile_btn);
        this.imgViewField = view.findViewById(R.id.profile_img);
        this.fullNameField = view.findViewById(R.id.full_name);

        editProfile.setOnClickListener(l -> this.editProfile());
        this.getProfile();
    }

    public void getProfile() {
        this.handler.getProfileService().getProfile().thenAccept(user -> {
            Uri imgUri = Uri.parse(user.getImgUrl());
            String fullName = user.getName() + " " + user.getLastName();
            this.imgViewField.setImageURI(imgUri);
            this.fullNameField.setText(fullName);
        });
    }

    public void editProfile() {
        final EditText nameInput = new EditText(getContext());
        nameInput.setHint("Nombre");
        nameInput.setTextColor(0xFFFFFFFF);
        nameInput.setHintTextColor(0xFFAAAAAA);

        final EditText lastNameInput = new EditText(getContext());
        lastNameInput.setHint("Apellido");
        lastNameInput.setTextColor(0xFFFFFFFF);
        lastNameInput.setHintTextColor(0xFFAAAAAA);

        final EditText imgUriInput = new EditText(getContext());
        imgUriInput.setHint("Imagen");
        imgUriInput.setTextColor(0xFFFFFFFF);
        imgUriInput.setHintTextColor(0xFFAAAAAA);

        LinearLayout layout = new LinearLayout(getContext());
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.addView(nameInput);
        layout.addView(lastNameInput);
        layout.addView(imgUriInput);

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.DialogTheme);
        builder.setTitle("Editar perfil")
                .setView(layout)
                .setPositiveButton("Añadir", (dialog, which) -> {
                    String name = nameInput.getText().toString().trim();
                    String lastName = lastNameInput.getText().toString().trim();
                    String uri = imgUriInput.getText().toString().trim();
                    if (!name.isEmpty() || !lastName.isEmpty() || !uri.isEmpty()) {
                        UserProfileDto userProfileDto = new UserProfileDto();
                        userProfileDto.setName(name);
                        userProfileDto.setLastName(lastName);
                        userProfileDto.setImgUrl(uri);
                        this.handler.getProfileService().updateProfile(userProfileDto).thenAccept(isSuccess -> {
                            Toast.makeText(getContext(), "Perfil editado con éxito", Toast.LENGTH_SHORT).show();
                        }).exceptionally(err -> {
                            Toast.makeText(getContext(), "Error al editar el perfil", Toast.LENGTH_SHORT).show();
                            this.getProfile();
                            return null;
                        });
                    }
                })
                .setNegativeButton("Cancelar", (dialog, which) -> dialog.dismiss())
                .show();
    }

}
