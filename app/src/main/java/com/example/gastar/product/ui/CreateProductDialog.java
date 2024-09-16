package com.example.gastar.product.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.example.gastar.R;

public class CreateProductDialog extends DialogFragment {
    private DialogInterface.OnClickListener clickListener;

    public CreateProductDialog(DialogInterface.OnClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();

        builder.setView(inflater.inflate(R.layout.product_add_dialog, null))
                .setPositiveButton("Agregar", this.clickListener)
                .setNegativeButton("Cancelar", (dialog, id) -> {
                    // User cancels the dialog.
                });
        // Create the AlertDialog object and return it.
        return builder.create();
    }

}
