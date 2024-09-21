package com.example.gastar.product.ui;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gastar.R;
import com.example.gastar.person.entity.Person;
import com.example.gastar.person.ui.PersonList;

import java.util.ArrayList;
import java.util.List;

public class ConsumerAdapter extends RecyclerView.Adapter<ConsumerAdapter.ViewHolder> {

    private final List<Person> selected = new ArrayList<>();

    private final List<Person> persons;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final CheckBox checkBox;
        private final TextView nameLabel;


        public ViewHolder(View view) {
            super(view);
            nameLabel = view.findViewById(R.id.consumer_name);
            checkBox = view.findViewById(R.id.checkbox_consumer);
        }

        public TextView getNameLabel() {
            return nameLabel;
        }

        public CheckBox getCheckBox() {return checkBox;}

    }

    public ConsumerAdapter(List<Person> persons) {
        Log.d("CONSUMER_ADAPTER", String.valueOf(persons.size()));
        this.persons = persons;
    }

    @NonNull
    @Override
    public ConsumerAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.consumer_item, viewGroup, false);

        return new ConsumerAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ConsumerAdapter.ViewHolder viewHolder, final int position) {
        final int pos = viewHolder.getAdapterPosition();

        viewHolder.getNameLabel().setText(persons.get(position).getName());
        viewHolder.getCheckBox().setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    selected.add(persons.get(pos));
                }
                else{
                    selected.remove(persons.get(pos));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return persons.size();
    }

    public List<Person> getSelected() {
        return selected;
    }
}