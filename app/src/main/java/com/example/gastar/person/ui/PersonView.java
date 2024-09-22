package com.example.gastar.person.ui;

import android.content.Context;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.gastar.R;
import com.example.gastar.person.entity.Person;

public class PersonView extends LinearLayout {
    private Person person;
    private ImageView iconView;
    private TextView personNameView;
    private TextView balanceView;

    public PersonView(Context context){
        super(context);
        init(context);
    }

    public PersonView(Context context, Person person){
        super(context);
        this.person = person;
        init(context);
    }

    private void init(Context context){
        inflate(context, R.layout.person_view, this);
        iconView = findViewById(R.id.person_icon);
        personNameView = findViewById(R.id.person_name);
        balanceView = findViewById(R.id.person_balance);
    }

    public void setPerson(Person person){
        this.person = person;
    }

}
