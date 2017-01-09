package agh.cs.lab9;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

class DataLogic {

    private final LinkedList<Person> persons;
    private final Option option;
    private final DataGetter dataGetter;

    private static final String RESON = "Koszty drobnych napraw i remontów lokalu biura poselskiego";
    private static final String COUNTRY = "Włochy";

    DataLogic(Option option) throws IOException {
        this.option = option;
        dataGetter = new DataGetter(option.termNumber);
        persons = new LinkedList<>(dataGetter.getPersonList());
        try{
            persons.parallelStream().forEach(person -> {
                try {
                    person.addExpenses(dataGetter.getExpenseList(person.id));
                    person.addTrips(dataGetter.getTripList(person.id));
                } catch (IOException e) {
                    throw new ParallelIOException(e);
                }
            });
        } catch (ParallelIOException e){
            throw new IOException(e);
        }
    }
    String getResult(){
        StringBuilder builder = new StringBuilder();
        Person person;
        double val;
        DecimalFormat df = new DecimalFormat("0.00");
        switch(option.type){
            case sumExepenses:
                val = getExpensesSum(option.argument);
                builder.append("Posel " + option.argument + " wydal: " + df.format(val) + " zł");
                break;
            case smallExepense:
                val = getSmallExpenses(option.argument);
                builder.append("Posel " + option.argument + " wydal: " + df.format(val) + " zł na " + RESON);
                break;
            case avgExepenses:
                val = getAvgExpenses();
                builder.append("Srednie wydatki poslow: " + df.format(val) + " zł");
                break;
            case numberTrips:
                person = getNumberTrips();
                builder.append("Najwieciej podrozy zagrnanicznych mial " + person);
                break;
            case lenghtTrip:
                person = getLenghtTrip();
                builder.append("Najdlużej zagranicza był " + person);
                break;
            case exepensiveTrip:
                person = getExepensiveTrip();
                builder.append("Najdroższa zagraniczna podróz miał " + person);
                break;
            case italy:
                List<Person> anser = getVisitors(COUNTRY);
                builder.append("Lista osób które były we włoszech: " + anser);
                break;
        }
        return builder.toString();
    }

    private double getExpensesSum(String name){
        Person person = null;
        for(Person p : persons){
            if(p.name.equals(name)){
                person = p;
            }
        }
        if ( person == null){
            throw new IllegalArgumentException("Nie znaleziono posla o nazwie :" + name);
        }
        else {
            return person.getExpensesSum();
        }
    }

    private double getSmallExpenses(String name){
        Person person = null;
        for(Person p : persons){
            if(p.name.equals(name)){
                person = p;
            }
        }
        if ( person == null){
            throw new IllegalArgumentException("Nie znaleziono posla o nazwie :" + name);
        }
        else {
            return person.getExpensesSum(RESON);
        }
    }

    private double getAvgExpenses() {
        return persons.stream()
                .mapToDouble(Person::getExpensesSum)
                .sum() / persons.size();
    }

    private Person getNumberTrips() {
        persons.sort(Comparator.comparingInt(Person::getNumberTrips));
        return persons.get(persons.size() - 1);
    }

    private Person getLenghtTrip() {
        persons.sort(Comparator.comparingInt(Person::getTripLenght));
        return persons.get(persons.size() - 1);
    }

    private Person getExepensiveTrip() {
        persons.sort(Comparator.comparingDouble(Person::getExpensiveTrip));
        return persons.get(persons.size() - 1);
    }

    private List<Person> getVisitors(String country) {
        return persons.stream()
                .filter(person -> person.haveVisited(country))
                .collect(Collectors.toList());
    }

}
