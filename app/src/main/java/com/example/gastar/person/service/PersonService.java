package com.example.gastar.person.service;

import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.example.gastar.person.entity.Person;

public class PersonService {
    private final List<Person> persons = new ArrayList<>();

    public void add(Person person) {
        persons.add(person);
    }

    public void remove(UUID id) {
        persons.removeIf((person -> person.getId() == id));
    }

    public void remove(Person person) {
        persons.remove(person);
    }

    public Person get(UUID uuid) {
        Optional<Person> person = persons.stream().filter((p -> p.getId().equals(uuid))).findFirst();
        if (!person.isPresent()) {
            throw new RuntimeException("can't find person xd 404");
        }
        return person.get();
    }

    public void update(UUID id, Person person) {
        persons.set(persons.indexOf(this.get(id)), person);
    }

    public List<Person> get() {
        return persons;
    }

    public double getTotalSpending() {
        double spending = 0;
        for (Person p : persons) {
            spending += p.getTotalSpending();
        }
        return spending;
    }

    public void addContribution(Person person, Double amount) {
        person.addContribution(amount);
        this.update(person.getId(), person);
    }

    public void calculateSpending(List<Person> consumers, Double amount) {
        consumers.forEach(consumer -> {
            Person person = this.get(consumer.getId());
            person.addSpending(amount / consumers.size());
            this.update(consumer.getId(), person);
        });
        Log.i("ELOKO", Arrays.toString(persons.toArray()));
    }

}
