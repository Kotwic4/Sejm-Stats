package agh.cs.lab9;

import com.google.gson.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

class DataGetter {

    private static final String MAIN_URL = "https://api-v3.mojepanstwo.pl/dane/poslowie";
    private static final String EXTENSION = ".json";
    private static final String LAYER = "?layers[]=";
    private static final String TERM = "?conditions[poslowie.kadencja]=";
    private static final String EXPENSES = "wydatki";
    private static final String TRIPS = "wyjazdy";

    private final int termNumber;

    DataGetter(int termNumber) {
        this.termNumber = termNumber;
    }

    List<Person> getPersonList() throws IOException {
        String url = MAIN_URL + EXTENSION + TERM + termNumber;
        LinkedList<Person> persons = new LinkedList<>();
        while(url != null){
            JsonObject page = getJsonFromUrl(url);
            JsonArray personsJsons = page.get("Dataobject").getAsJsonArray();
            for (JsonElement personJson : personsJsons) {
                JsonObject personData = personJson.getAsJsonObject()
                        .get("data")
                        .getAsJsonObject();
                int id = personData.get("poslowie.id").getAsInt();
                String name = personData.get("poslowie.nazwa").getAsString();
                persons.add(new Person(id,name));
            }
            JsonObject links = page.get("Links").getAsJsonObject();
            if(links.has("next")){
                url = links.get("next").getAsString();
            }
            else{
                url = null;
            }
        }
        return persons;
    }

    List<Trip> getTripList(int id) throws IOException {
        LinkedList<Trip> trips = new LinkedList<>();
        JsonObject layers = getJsonLayer(id,TRIPS);
        JsonElement tripLayer = layers.get(TRIPS);
        if(tripLayer.isJsonArray()){
            for(JsonElement tripData : tripLayer.getAsJsonArray()){
                JsonObject trip = tripData.getAsJsonObject();
                String country = trip.get("kraj").getAsString();
                int length = trip.get("liczba_dni").getAsInt();
                double cost = trip.get("koszt_suma").getAsDouble();
                trips.add(new Trip(country, length, cost));
            }
        }
        return trips;
    }

    List<Expense> getExpenseList(int id) throws IOException {
        LinkedList<Expense> expenses = new LinkedList<>();
        JsonObject layers = getJsonLayer(id,EXPENSES);
        JsonObject expensesLayer = layers.get(EXPENSES).getAsJsonObject();
        if(!expensesLayer.isJsonNull()){
            JsonArray reasons = expensesLayer.get("punkty")
                    .getAsJsonArray();
            JsonArray years = expensesLayer
                    .get("roczniki")
                    .getAsJsonArray();
            for (JsonElement year : years) {
                JsonArray yearFields = year.getAsJsonObject()
                        .get("pola")
                        .getAsJsonArray();
                for (int i = 0; i < yearFields.size(); i++) {
                    double cost = yearFields.get(i)
                            .getAsDouble();
                    String reason;
                    if (i < 20)
                        reason = reasons
                                .get(i)
                                .getAsJsonObject()
                                .get("tytul")
                                .getAsString();
                    else
                        reason = "Others/no reason";
                    System.out.println(cost + " " + reason);
                    expenses.add(new Expense(cost, reason));
                }
            }
        }
        return expenses;
    }

    private JsonObject getJsonLayer(int id, String layer) throws IOException {
        String url = MAIN_URL + "/" + id + EXTENSION + LAYER + layer;
        return getJsonFromUrl(url)
                .get("layers")
                .getAsJsonObject();
    }

    private JsonObject getJsonFromUrl(String urlString) throws IOException {
        BufferedReader reader = null;
        try {
            URL url = new URL(urlString);
            reader = new BufferedReader(new InputStreamReader(url.openStream()));
            StringBuffer buffer = new StringBuffer();
            int read;
            char[] chars = new char[4096];
            while ((read = reader.read(chars)) != -1)
                buffer.append(chars, 0, read);

            return (JsonObject) new JsonParser().parse(buffer.toString());
        } finally {
            if (reader != null)
                reader.close();
        }
    }
}
