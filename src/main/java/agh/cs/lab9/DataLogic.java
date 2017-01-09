package agh.cs.lab9;

import java.io.IOException;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

class DataLogic {

    private final LinkedList<Person> persons;
    private final Option option;
    private final DataGetter dataGetter;

    private static final String TRIPS = "wyjazdy";
    private static final String EXPENSES = "wydatki";
    private static final String RESON = "Koszty drobnych napraw i remontów lokalu biura poselskiego";
    private static final String COUNTRY = "Włochy";

    DataLogic(Option option) throws IOException {
        this.option = option;
        dataGetter = new DataGetter(option.termNumber);
        persons = new LinkedList<>(dataGetter.getPersonList());
    }
    String getResult() {
        StringBuilder builder = new StringBuilder();
                        persons.parallelStream().forEach(person -> {
                        try {
                            person.addExpenses(dataGetter.getExpenseList(person.id));
                        } catch (IOException e) {
                            throw new ParallelIOException(e);
                        }
                        });
                    .parallelStream().forEach(mp -> {
                                try {
                                    mp.setTrips(accessor.getMPtrips(mp.getID()));
                                } catch (IOException ex) {
                                    throw new UncheckedIOException(ex);
                                }
                            }
                    );
                    break;
                default:
                    throw new IllegalArgumentException("Incorrect property passed: " + property);
            }
        return builder.toString();
    }

    private double getExpensesSum(String name) throws IOException {
        Person person = null;
        for(Person p : persons){
            if(p.name.equals(name)){
                person = p;
            }
        }
        if ( person == null){
            throw new IOException("Nie znaleziono posla o nazwie :" + name);
        }
        else {
            person.addExpenses(dataGetter.getExpenseList(person.id));
            return person.getExpensesSum();
        }
    }

    private double getSmallExpenses(String name) throws IOException {
        Person person = null;
        for(Person p : persons){
            if(p.name.equals(name)){
                person = p;
            }
        }
        if ( person == null){
            throw new IOException("Nie znaleziono posla o nazwie :" + name);
        }
        else {
            person.addExpenses(dataGetter.getExpenseList(person.id));
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

    public List<Person> getVisitors(String country) {
        return persons.stream()
                .filter(person -> person.haveVisited(country))
                .collect(Collectors.toList());
    }

}
