package me.owen.ecsacalculator;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

public class txtFileProcessor {

    @SuppressWarnings("resource")
    public static HashMap<Double, Double> extractDataFromFile(File file){

        boolean isFirstLine = true;

        HashMap<Double, Double> returnedPuzzle = new HashMap<Double, Double>();

        FileInputStream input;
        Scanner scanner = null;
        try {
            input = new FileInputStream(file);
            scanner = new Scanner(input);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("Error processing file.");
            return null;
        }

        while(scanner.hasNextLine()){
            if(isFirstLine){
                isFirstLine = false;
                continue;
            }
            scanner.nextLine();
            //returnedPuzzle.put(Double.valueOf(scanner.nextDouble() + 0.197D + 0.059D * ECSACalculator.pH), Double.valueOf(scanner.nextDouble()));
            returnedPuzzle.put(Double.valueOf(scanner.nextDouble()), Double.valueOf(scanner.nextDouble()));
        }

//		for(Double d : returnedPuzzle.keySet()){
//			System.out.println(d + " and " + returnedPuzzle.get(d));
//		}
        return returnedPuzzle;
    }
}
