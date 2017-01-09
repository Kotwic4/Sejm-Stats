package agh.cs.lab9;

import org.junit.Before;
import org.junit.Test;

import java.util.LinkedList;

import static org.junit.Assert.*;

public class PersonTest {

    private Person person;

    @Before
    public void setUp() throws Exception {
        person = new Person(1,"test");
        person.addTrip(new Trip("bla",2,2.0));
        LinkedList<Trip> trips = new LinkedList<>();
        trips.add(new Trip("bla2",3,1.0));
        trips.add(new Trip("bla3",3,1.0));
        trips.add(new Trip("bla2",3,1.0));
        person.addTrips(trips);
        person.addExpense(new Expense(2.0,"bla"));
        LinkedList<Expense> expenses = new LinkedList<>();
        expenses.add(new Expense(1.0,"bla2"));
        expenses.add(new Expense(1.0,"bla3"));
        expenses.add(new Expense(1.0,"bla2"));
        person.addExpenses(expenses);
    }

    @Test
    public void testToString() throws Exception {
        assertEquals(person.toString(),"test");
        assertEquals(new Person(1,"test2").toString(),"test2");
    }

    @Test
    public void testHaveVisited() throws Exception {
        assertTrue(person.haveVisited("bla"));
        assertTrue(person.haveVisited("bla2"));
        assertTrue(person.haveVisited("bla3"));
        assertFalse(person.haveVisited("1bla"));
    }

    @Test
    public void testGetExpensesSum() throws Exception {
        assertEquals(person.getExpensesSum(),5.0,0.1);
        assertEquals(new Person(1,"test2").getExpensesSum(),0.0,0.1);
    }

    @Test
    public void testGetExpensesSumParamiters() throws Exception {
        assertEquals(person.getExpensesSum("bla"),2.0,0.1);
        assertEquals(person.getExpensesSum("1bla"),0.0,0.1);
        assertEquals(new Person(1,"test2").getExpensesSum("bla"),0,0.1);
    }

    @Test
    public void testGetNumberTrips() throws Exception {
        assertEquals(person.getNumberTrips(),4);
        assertEquals(new Person(1,"test2").getNumberTrips(),0);
    }

    @Test
    public void testGetTripLenght() throws Exception {
        assertEquals(person.getTripLenght(),11);
        assertEquals(new Person(1,"test2").getTripLenght(),0);
    }

    @Test
    public void testGetExpensiveTrip() throws Exception {
        assertEquals(person.getExpensiveTrip(),2.0,0.1);
        assertEquals(new Person(1,"test2").getExpensiveTrip(),0.0,0.1);
    }

}