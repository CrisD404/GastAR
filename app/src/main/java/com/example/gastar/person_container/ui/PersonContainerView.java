package com.example.gastar.person_container.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.gastar.R;
import com.example.gastar.person.entity.Person;
import com.example.gastar.person.ui.PersonView;

import java.util.List;

public class PersonContainerView extends ConstraintLayout {
    private List<Person> persons;
    private LinearLayout container;
    private Button addPersonButton;

    public static PersonContainerView constructFromLayout(ConstraintLayout layout, List<Person> persons){
        return new PersonContainerView(layout.getContext(), persons);
    }

    public PersonContainerView(Context context){
        super(context);
        init(context);
    }

    public PersonContainerView(Context context, AttributeSet attributeSet){
        super(context, attributeSet);
        init(context);
    }

    public PersonContainerView(Context context, List<Person> persons){
        super(context);
        this.persons = persons;
        init(context);
        render();
    }

    private void init(Context context){
        inflate(context, R.layout.person_container_view,this);
        container = findViewById(R.id.person_list);
        addPersonButton = findViewById(R.id.add_person_button);
    }

    public void update(){
        render();
    }

    //todo: optimize rendering
    private void render(){
        container.removeAllViewsInLayout();
        for(Person p : persons){
            PersonView personView = new PersonView(this.getContext(),p);
            personView.updateLabels();
            container.addView(personView);
        }

    }

    public List<Person> getPersons() {
        return persons;
    }

    public void setPersons(List<Person> persons) {
        this.persons = persons;
        update();
    }

    public LinearLayout getContainer() {
        return container;
    }

    public Button getAddPersonButton() {
        return addPersonButton;
    }

}
