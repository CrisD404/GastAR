package person.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.gastar.R;
import com.example.gastar.person.entity.Person;

public class PersonView extends LinearLayout {
    private Person person;
    private ImageView iconView;
    private TextView personNameView;
    private TextView contributionView;
    private TextView SpendingView;


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
        contributionView = findViewById(R.id.person_contribution);
        SpendingView = findViewById(R.id.person_spending);
    }

    public void setPerson(Person person){
        this.person = person;
    }

    public void updateLabels(){
        personNameView.setText(person.getName());
        contributionView.setText(String.valueOf(person.getTotalContribution()));
        SpendingView.setText(String.valueOf(person.getTotalSpending()));
    }


}
