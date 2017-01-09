package agh.cs.lab9;

import java.util.LinkedList;

class OptionParser {

    Option parseOption(String[] args) throws IllegalArgumentException{
        OptionType type = null;
        String argument = null;

        if(args.length < 2) throw new IllegalArgumentException("Zbyt mało argumentów, minimalna liczba argumentów 2");
        if(!args[0].matches("\\d+")) throw new IllegalArgumentException("Nie poprawny argument : " + args[0] + " Powinien być numer kadencji");
        int termNumber = Integer.parseInt(args[0]);
        switch(args[1]){
            case "sumExepenses":
                if(args.length < 4) throw new IllegalArgumentException("Nie podano imienia i nazwiaska");
                type = OptionType.sumExepenses;
                argument = args[2] + " " + args[3];
                break;
            case "smallExepense":
                if(args.length < 4) throw new IllegalArgumentException("Nie podano imienia i nazwiaska");
                type = OptionType.smallExepense;
                argument = args[2] + " " + args[3];
                break;
            case "avgExepenses":
                type = OptionType.avgExepenses;
                break;
            case "numberTrips":
                type = OptionType.numberTrips;
                break;
            case "lenghtTrip":
                type = OptionType.lenghtTrip;
                break;
            case "exepensiveTrip":
                type = OptionType.exepensiveTrip;
                break;
            case "italy":
                type = OptionType.italy;
                break;
            default:
                String[] array = {"sumExepenses","smallExepense","avgExepenses","numberTrips","lenghtTrip","exepensiveTrip","italy"};
                throw new IllegalArgumentException("Nie poprawny typ zapytania, poprawne typy: " + array);

        }
        return new Option(type,termNumber,argument);
    }
}
