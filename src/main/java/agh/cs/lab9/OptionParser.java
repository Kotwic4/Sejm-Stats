package agh.cs.lab9;

class OptionParser {

    Option parseOption(String[] args) throws IllegalArgumentException{
        OptionType type = null;
        int termNumber = 7;
        String argument = null;
        // TODO: 09.01.2017
        return new Option(type,termNumber,argument);
    }
}
