package com.warpaint.challengeservice;

import java.util.Random;

public class MonteCarlo {

    private final double[] priceMovements;
    private final Random random = new Random(); // I saw endless disputes about which random should be used for Monte Carlo, but this will be good for now.

    public MonteCarlo(double[] priceMovements) {
        this.priceMovements = priceMovements;
    }

    public double[] simulateAndReturnMaxEndPrice(double startPrice, int intervalCount, int iterationCount) {
        double[] simulationWithMaxEndPrice = new double[intervalCount];
        double[] currentSimulation = new double[intervalCount];
        double[] temp;
        int last = intervalCount - 1;

        fill(startPrice, simulationWithMaxEndPrice);
        for (int iteration = 1; iteration < iterationCount; iteration++) {
            fill(startPrice, currentSimulation);
            if (currentSimulation[last] > simulationWithMaxEndPrice[last]) {
                temp = simulationWithMaxEndPrice;
                simulationWithMaxEndPrice = currentSimulation;
                currentSimulation = temp;
            }
        }

        return simulationWithMaxEndPrice;
    }

    /**
     * Runs the Monte Carlo simulation
     * @param start starting value, not written into the target
     * @param target out parameter, goal is to minimize garbage collection
     */
    public void fill(double startPrice, double[] target) {
        double price = startPrice;
        for (int i = 0; i < target.length; i++) {
            price *= getRandomPriceMovement();
            target[i] = price;
        }
    }

    private double getRandomPriceMovement() {
        return priceMovements[random.nextInt(priceMovements.length)];
    }
}
