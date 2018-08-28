import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
//import edu.princeton.cs.algs4.StdStats;

// Estimates percolation threshold for an N-by-N percolation system.
public class PercolationStats {
    private double numberOfTries;
    private Percolation ex;
    private double[] x;

    // Perform T independent experiments (Monte Carlo simulations) on an 
    // N-by-N grid.
    public PercolationStats(int N, int T) {
        if (N <= 0 || T <= 0) {
            throw new IllegalArgumentException("Given N <= 0 || T <= 0");
        }
        numberOfTries = (double) T;
        x = new double[T];
        int n = 0;
        for (int no = 0; no < numberOfTries; no++) {
            ex = new Percolation(N);
            while (!ex.percolates()) { 
                int i = StdRandom.uniform(0, N);
                int j = StdRandom.uniform(0, N);
                if (!ex.isOpen(i, j)) {
                    ex.open(i, j);
                }
            }
            x[n] = (double) ex.numberOfOpenSites()/(N*N);
            n++;
        }
    }
    
    // Sample mean of percolation threshold.
    public double mean() {
        double sum = 0;
        for (double i : x) { sum += i; }
        return sum/numberOfTries;       
    }

    // Sample standard deviation of percolation threshold.
    public double stddev() {
        double stddev = 0;
        for (double i : x) { 
            stddev = (i - mean())*(i - mean()); 
        }
        return Math.pow(stddev/(numberOfTries-1), 0.5);
    }

    // Low endpoint of the 95% confidence interval.
    public double confidenceLow() {
        double low = mean() - 1.96*stddev()/Math.pow(numberOfTries, 0.5);
        return low;
    }

    // High endpoint of the 95% confidence interval.
    public double confidenceHigh() {
        double  high = mean() + 1.96*stddev()/Math.pow(numberOfTries, 0.5);
        return high;
    }

    // Test client. [DO NOT EDIT]
    public static void main(String[] args) {
        int N = Integer.parseInt(args[0]);
        int T = Integer.parseInt(args[1]);
        PercolationStats stats = new PercolationStats(N, T);
        StdOut.printf("mean           = %f\n", stats.mean());
        StdOut.printf("stddev         = %f\n", stats.stddev());
        StdOut.printf("confidenceLow  = %f\n", stats.confidenceLow());
        StdOut.printf("confidenceHigh = %f\n", stats.confidenceHigh());
    }
}
