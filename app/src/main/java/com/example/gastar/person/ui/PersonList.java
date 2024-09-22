package com.example.gastar.person.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gastar.R;
import com.example.gastar.person.entity.Person;

import java.util.List;

public class PersonList extends RecyclerView.Adapter<PersonList.ViewHolder> {
    private final List<Person> persons;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView nameLabel;
        private final TextView balanceLabel;

        public ViewHolder(View view) {
            super(view);
            nameLabel = view.findViewById(R.id.person_name);
            balanceLabel = view.findViewById(R.id.person_balance);
        }

        public TextView getNameLabel() {
            return nameLabel;
        }

        public TextView getBalanceLabel() {
            return balanceLabel;
        }


    }

    public PersonList(List<Person> persons) {
        this.persons = persons;
    }

    @NonNull
    @Override
    public PersonList.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.person_view, viewGroup, false);

        return new PersonList.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PersonList.ViewHolder viewHolder, final int position) {
        double balance = persons.get(position).getBalance();
        viewHolder.getNameLabel().setText(persons.get(position).getName());
        TextView balanceLabel = viewHolder.getBalanceLabel();
        int color;
        if(balance < 0){
            color = 0xFFEE1111; //red
        }
        else{
            color = 0xFF00AB03; //green
        }
        balanceLabel.setText(String.valueOf(Math.abs(balance)));
        balanceLabel.setTextColor(color);
    }

    @Override
    public int getItemCount() {
        return persons.size();
    }

}