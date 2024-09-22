package com.example.gastar.product.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import com.example.gastar.person.entity.Person;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gastar.R;

import java.util.List;

public class CreateProductDialog extends DialogFragment {
    private final DialogInterface.OnClickListener clickListener;
    private final List<Person> persons;

    public CreateProductDialog(DialogInterface.OnClickListener clickListener, List<Person> persons) {
        this.clickListener = clickListener;
        this.persons = persons;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.product_add_dialog, null);

        if(persons != null){
            Spinner ownerSpinner = dialogView.findViewById(R.id.product_contributor);
            ArrayAdapter<Person> spinnerAdapter = new ArrayAdapter<>(requireActivity(), android.R.layout.simple_spinner_item, persons);
            spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            ownerSpinner.setAdapter(spinnerAdapter);
            ConsumerAdapter recyclerAdapter = new ConsumerAdapter(persons);
            RecyclerView consumersContainer = dialogView.findViewById(R.id.consumers_container);
            consumersContainer.setLayoutManager(new LinearLayoutManager(this.getActivity()));
            consumersContainer.setAdapter(recyclerAdapter);

        }

        builder.setView(dialogView)
                .setPositiveButton("Agregar", this.clickListener)
                .setNegativeButton("Cancelar", (dialog, id) -> dialog.dismiss());

        return builder.create();
    }

}
