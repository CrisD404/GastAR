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
        private final TextView contributionLabel;
        private final TextView spendingLabel;

        public ViewHolder(View view) {
            super(view);
            nameLabel = view.findViewById(R.id.person_name);
            contributionLabel = view.findViewById(R.id.person_contribution);
            spendingLabel = view.findViewById(R.id.person_spending);
        }

        public TextView getNameLabel() {
            return nameLabel;
        }

        public TextView getContributionLabel() {
            return contributionLabel;
        }

        public TextView getSpendingLabel(){return spendingLabel;}


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
        viewHolder.getNameLabel().setText(persons.get(position).getName());
        viewHolder.getContributionLabel().setText(String.valueOf(persons.get(position).getTotalContribution()));
        viewHolder.getSpendingLabel().setText(String.valueOf(persons.get(position).getTotalSpending()));
    }

    @Override
    public int getItemCount() {
        return persons.size();
    }

}