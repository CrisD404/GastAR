package com.example.gastar.person.entity;

import androidx.annotation.NonNull;

import java.util.UUID;

public class Person {
    private final UUID id = UUID.randomUUID();
    private String name;
    private double totalSpending;
    private double totalContribution;
    private double balance = 0;

    public Person(String name) {
        this.name = name;
        totalSpending = 0;
        totalContribution = 0;
    }

    public Person(String name, double spending, double contribution) {
        this.name = name;
        totalSpending = spending;
        totalContribution = contribution;
    }

    public void addSpending(double spending) {
        totalSpending += spending;
        balance -= spending;
    }

    public void addContribution(double contribution) {
        totalContribution += contribution;
        balance += contribution;
    }

    public double getBalance() {
        return balance;
    }

    public void setTotalContribution(double totalContribution) {
        this.totalContribution = totalContribution;
    }

    public String getName() {
        return name;
    }

    public double getTotalSpending() {
        return totalSpending;
    }

    public double getTotalContribution() {
        return totalContribution;
    }

    public UUID getId() {
        return this.id;
    }

    public void setName(String name) {
        this.name = name;
    }

    @NonNull
    @Override
    public String toString() {
        return name;
    }

}
