package agh.cs.lab9;

import org.junit.Test;

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
    }

}