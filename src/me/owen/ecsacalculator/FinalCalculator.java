package me.owen.ecsacalculator;

import org.apache.commons.math3.stat.regression.SimpleRegression;

public class FinalCalculator {

    static double[] scanRates = {.010, .020, .040, .060, .080, .100};
    //static double[] scanRates = {.005, .010, .025, .050, .1, .2, .4};
    static double Cdl = .0285;

    public static double calculateECSA(double[] currents){
        double ECSA = 0.0;

        double foundSlope = slope(currents);
        System.out.println("Slope " + foundSlope);
        ECSA = foundSlope/Cdl;
        return ECSA;
    }

    private static double slope(double[] currents){
        SimpleRegression simpleRegression = new SimpleRegression(true);

        double[][] data = new double[6][6];
        for(int i = 0; i < 6; i++){
            data[i][1] = currents[i];
            data[i][0] = scanRates[i];

            System.out.println("Current at scan rate " + scanRates[i] * 1000 + "mV/s was " + currents[i] + " mA");

        }
        simpleRegression.addData(data);

        System.out.println("r^2: " +simpleRegression.getRSquare());


        return simpleRegression.getSlope();

    }
}
