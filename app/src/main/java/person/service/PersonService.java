package person.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import person.entity.Person;

public class PersonService {
    private final List<Person> persons = new ArrayList<>();

    public void add (Person person){
        persons.add(person);
    }

    public void remove(UUID id){
        persons.removeIf((person -> person.getId() == id));
    }

    public void remove(Person person){
        persons.remove(person);
    }

    public Person get(UUID id){
        return (Person) persons.stream().filter((person -> person.getId() == id));
    }

    public List<Person> get(){
        return persons;
    }

    public double getTotalSpending(){
        double spending = 0;
        for(Person p: persons){
            spending += p.getTotalSpending();
        }
        return spending;
    }

}
