package person;

public class Person {

    private final String name;
    private double totalSpending;
    private double totalContribution;

    public Person(String name){
        this.name = name;
        totalSpending = 0;
        totalContribution = 0;
    }

    public Person(String name, double spending, double contribution){
        this.name = name;
        totalSpending = spending;
        totalContribution = contribution;
    }

    public void addSpending(double spending){
        totalSpending += spending;
    }

    public void addContribution(double contribution){
        totalContribution += contribution;
    }

    public void setTotalContribution(double totalContribution){
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





}
