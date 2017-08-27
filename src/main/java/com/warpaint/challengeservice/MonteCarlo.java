package com.warpaint.challengeservice;

import java.util.Random;

public class MonteCarlo {

    private final double[] priceMovements;
    private final Random random = new Random(); // I saw endless disputes about which random should be used for Monte Carlo, but this will be good for now.

    public MonteCarlo(double[] priceMovements) {
        this.priceMovements = priceMovements;
    }

    /**
     * Runs the Monte Carlo simulation
     * @param start starting value, not written into the target
     * @param target out parameter, goal is to minimize garbage collection
     */
    public void fill(double start, double[] target) {
        double price = start;
        for (int i = 0; i < target.length; i++) {
            price *= getRandomPriceMovement();
            target[i] = price;
        }
    }

    private double getRandomPriceMovement() {
        return priceMovements[random.nextInt(priceMovements.length)];
    }
}
