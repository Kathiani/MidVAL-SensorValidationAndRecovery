package com.example.kathiani.service;

public class PreProcessing {
    // obter array com media movel
    public static double[] movingAverage(double[] temperatures){      //suavizando array de valores elimina pesos autos ou baixos

        int window = 3;  // Número de janelas para a média móvel
        double[] movingAverages = new double[temperatures.length - window + 1];
        for (int i = 0; i < movingAverages.length; i++) {
            double sum = 0;
            for (int j = i; j < i + window; j++) {
                sum += temperatures[j];
            }
            movingAverages[i] = sum / window;
        }
         
        return movingAverages;

    }
    
}
