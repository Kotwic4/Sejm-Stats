package agh.cs.lab9;

class Option {
    final OptionType type;
    final int termNumber;
    final String argument;
    Option(OptionType type, int termNumber, String argument) {
        this.type = type;
        this.termNumber = termNumber;
        this.argument = argument;
    }
}
