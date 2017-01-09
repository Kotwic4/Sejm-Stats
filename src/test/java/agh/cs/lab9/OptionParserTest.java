package agh.cs.lab9;

import org.junit.Test;

import java.util.Arrays;
import java.util.LinkedList;

import static org.junit.Assert.*;

public class OptionParserTest {
    @Test
    public void parseOption() throws Exception {
        OptionParser optionParser = new OptionParser();
        assertEquals(OptionType.sumExepenses, optionParser.parseOption("7 sumExepenses a b".split("\\s+")).type);
        assertEquals(OptionType.smallExepense, optionParser.parseOption("7 smallExepense a b".split("\\s+")).type);
        assertEquals(OptionType.avgExepenses, optionParser.parseOption("7 avgExepenses".split("\\s+")).type);
        assertEquals(OptionType.numberTrips, optionParser.parseOption("7 numberTrips".split("\\s+")).type);
        assertEquals(OptionType.lenghtTrip, optionParser.parseOption("7 lenghtTrip".split("\\s+")).type);
        assertEquals(OptionType.exepensiveTrip, optionParser.parseOption("7 exepensiveTrip".split("\\s+")).type);
        assertEquals(OptionType.italy, optionParser.parseOption("7 italy".split("\\s+")).type);

        try{
            optionParser.parseOption("sum".split("\\s+"));
            fail();
        }catch (IllegalArgumentException e){
            assertEquals(e.getMessage(),"Zbyt mało argumentów, minimalna liczba argumentów 2");
        }

        try{
            optionParser.parseOption("sum bla".split("\\s+"));
            fail();
        }catch (IllegalArgumentException e){
            assertEquals(e.getMessage(),"Nie poprawny argument : sum Powinien być numer kadencji");
        }

        try{
            optionParser.parseOption("6 bla".split("\\s+"));
            fail();
        }catch (IllegalArgumentException e){
            assertEquals(e.getMessage(),"Program obsluguje tylko 7 i 8 kadencje");
        }

        try{
            optionParser.parseOption("7 bla".split("\\s+"));
            fail();
        }catch (IllegalArgumentException e){
            String[] array = {"sumExepenses","smallExepense","avgExepenses","numberTrips","lenghtTrip","exepensiveTrip","italy"};
            assertEquals(e.getMessage(),"Nie poprawny typ zapytania, poprawne typy: " + new LinkedList<>(Arrays.asList(array)));
        }

    }

}