package agh.cs.lab9;

import java.io.IOException;

public class Sejm_Stats {
    public static void main(String[] args) {
        try {
            System.setProperty("java.util.concurrent.ForkJoinPool.common.parallelism", "50");
            Option option = new OptionParser().parseOption(args);
            DataLogic dataLogic = new DataLogic(option);
            System.out.println(dataLogic.getResult());
        } catch (IOException e) {
            System.out.println("Connection error.");
        }
        catch(IllegalArgumentException e){
            System.out.println(e.getMessage());
        }
        catch (Exception e) {
            System.out.println("Strange error:");
            e.printStackTrace();
        }
    }
}
