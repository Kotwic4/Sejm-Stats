package agh.cs.lab9;

import java.util.LinkedList;
import java.util.List;

class Person{
    final int id;
    final String name;
    private final List<Trip> trips = new LinkedList<>();
    private final List<Expense> expenses = new LinkedList<>();

    Person(int id, String name){
        this.id = id;
        this.name = name;
    }

    void addTrip(Trip trip){
        this.trips.add(trip);
    }

    void addTrips(List<Trip> trips){
        trips.forEach(this::addTrip);
    }

    void addExpense(Expense expense){
        this.expenses.add(expense);
    }
    void addExpenses(List<Expense> expenses){
        expenses.forEach(this::addExpense);
    }

    boolean haveVisited(String country){
        return this.trips.stream()
                .anyMatch(trip -> trip.country.equals(country));
    }

    double getExpensesSum(String reason){
        if(expenses.isEmpty()) return 0.0;
        else return expenses.stream()
                .filter(expense -> expense.reason.equals(reason))
                .map(expense -> expense.cost)
                .reduce(0.0, (x, y) -> x + y);
    }

    double getExpensesSum(){
        if(expenses.isEmpty()) return 0.0;
        else return expenses.stream()
                .map(expense -> expense.cost)
                .reduce(0.0, (x, y) -> x + y);
    }

    int getNumberTrips(){
        return trips.size();
    }

    int getTripLenght(){
        return trips.stream()
                .map(trip -> trip.length)
                .reduce(0, (x, y) -> x+y);
    }
    double getExpensiveTrip(){
        return trips.stream()
                .map(trip -> trip.cost)
                .reduce(0.0, (x, y) -> x > y ? x : y);
    }
}
