package me.owen.ecsacalculator;

import java.io.File;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

public class ECSACalculator {
    public static double desiredVoltage;
    //public static int pH;
    public static double[][] savedVoltages = new double[6][6];
    public static double[] savedCurrents = new double[6];

    public static void main(String args[]){

        try{ECSACalculator.desiredVoltage = Double.valueOf(args[0]);
        }catch(Exception e){
            System.out.println("You must specify the target voltage.");
            return;
        }
//		try {
//		      pH = Integer.valueOf(args[1]).intValue();
//		    } catch (Exception e) {
//		      System.out.println("You must specify the pH of solution.");
//		      return;
//		    }

        String runningDir = System.getProperty("user.dir");
        ArrayList<File> filesInFolder = new ArrayList<File>();
        int fileOn = 0;

        try(DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get(runningDir))){

            for(Path path : stream){
                if(!Files.isDirectory(path) && path.toString().contains(".txt")){
                    filesInFolder.add(path.toFile());
                    System.out.println("Opening File: "+path);
                }
            }
        }catch(Exception ignored){

        }

        for(File file : filesInFolder){
            HashMap<Double, Double> workingData = me.owen.ecsacalculator.txtFileProcessor.extractDataFromFile(file);

            double top = 10.0;
            double bottom = 10.0;

//			double localMiddlePoint = (Collections.max(workingData.entrySet()()) + Collections.min(workingData.entrySet()) / 2);
//			System.out.println("middle: " + localMiddlePoint);
//			System.out.println("max: " + Collections.max(workingData.entrySet()));
//			System.out.println("min: " + Collections.min(workingData.entrySet()));
            //System.out.println(file.getName());
            double averageCurrent = 0.00;

            for(double current : workingData.values()){
                averageCurrent = current + averageCurrent;
            }
            averageCurrent = averageCurrent / workingData.values().size();
            System.out.println("----------------------------------------------------");

            for(double voltage : workingData.keySet()){
                //System.out.println("positive is currently " + positive);
                if(workingData.get(voltage) >= averageCurrent){
                    //	System.out.println(workingData.get(voltage) + " is a top current and its voltage is " + voltage)


                    if(Math.abs(ECSACalculator.desiredVoltage - voltage) < Math.abs(top - ECSACalculator.desiredVoltage)){

                        //System.out.println(voltage + " is closer to -0.75 than " + top);
                        top = voltage;

                    }
                }else{
                    if(Math.abs(ECSACalculator.desiredVoltage - voltage) < Math.abs(bottom - ECSACalculator.desiredVoltage)){
                        bottom = voltage;
                    }
                }
            }

            //System.out.println(top + " was the closest to " + ECSACalculator.desiredVoltage + " being at " + top);
            //System.out.println("" + positive + " is the positive and " + negative + " is the negative");
            savedVoltages[fileOn][0] = top;
            savedVoltages[fileOn][1] = bottom;
            //System.out.println("Top = " + top + " with Bottom = " + bottom);
            //average the currents of positive and negative
            savedCurrents[fileOn] = Math.abs(((workingData.get(bottom) - workingData.get(top))/2));
            System.out.println("Anodic voltage: " + top + "\nCurrent: " + workingData.get(top));
            System.out.println("Cathodic voltage: " + bottom + "\nCurrent: " + workingData.get(bottom));

            fileOn++;

        }
        System.out.println("Surface Area: " + me.owen.ecsacalculator.FinalCalculator.calculateECSA(savedCurrents) + " cm^2");
    }
}
