package com.seefeel.leslie.algorithm.percolation;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.Stopwatch;

/**
 * ${DESCRIPTION}
 * Author: Leslie
 * Date: 14/5/17
 * Time: PM2:42
 */
public class PercolationStats {
    private double[] threshold;
    private int trials;
    private double mean;
    private double stddev;

    public PercolationStats(int n, int trials) {    // perform trials independent experiments on an n-by-n grid

        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException("n and trials cannot be less than 1");
        }

        this.trials = trials;
        this.threshold = new double[trials];

        for (int i = 0; i < trials; ++i) {
            Percolation perolation = new Percolation(n);

            while (!perolation.percolates()) {
                int row = StdRandom.uniform(n) + 1;
                int col = StdRandom.uniform(n) + 1;

                perolation.open(row, col);
            }

            int nOpen = perolation.numberOfOpenSites();
            threshold[i] = (double) nOpen / (n * n);
        }

        this.mean = StdStats.mean(threshold);
        this.stddev = StdStats.stddev(threshold);
    }
    public double mean() {                          // sample mean of percolation threshold

        return this.mean;
    }
    public double stddev() {                        // sample standard deviation of percolation threshold

        return this.stddev;
    }
    public double confidenceLo() {                  // low  endpoint of 95% confidence interval

        return mean - (1.96 * stddev / Math.sqrt(trials));
    }
    public double confidenceHi() {                  // high endpoint of 95% confidence interval

        return mean + (1.96 * stddev / Math.sqrt(trials));
    }
    public static void main(String[] args) {        // test client (described below)

        if (args.length < 2) {
            System.out.printf("at least 2 argument");
        } else {
            int n = Integer.parseInt(args[0]);
            int trials = Integer.parseInt(args[1]);

            Stopwatch stopwatch = new Stopwatch();
            PercolationStats stats = new PercolationStats(n, trials);
            double timeEsp = stopwatch.elapsedTime();

            System.out.println("mean                    = " + stats.mean());
            System.out.println("stddev                  = " + stats.stddev());
            System.out.println("95% confidence interval = [" + stats.confidenceLo() +", "+ stats.confidenceHi() +"]");
            System.out.println("cost: " +  timeEsp + "s.");
        }
    }
}
