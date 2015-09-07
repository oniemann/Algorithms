//Name: Okeefe Niemann
//Date: 9/7/2015
//Compile: javac-algs4 PercolationStats.java
//Run:     java-algs4 PercolationStats


//The following program runs Percolation simulation of an NxN grid T
//times and then derives the average percentage of tiles in the grid
//which need to be flipped in order to create a percolated system.
//Other statistics are also provided.

import java.util.*;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
   //grid/data structure
   private Percolation test;
   //array which will hold probabilities of T trials
   private double[] prob;
   //accessor to trials outside of constructor
   private int trials;

  //perform T independent experiments on an N by N grid
   public PercolationStats(int N, int T) {
      if (N <= 0 || T <= 0) throw new IllegalArgumentException();
      trials = T;
      prob = new double[T];

      //creates T tests      
      for (int i = 0; i < T; ++i) {
         test = new Percolation(N);

         //runs test with random indices until the system percolates
         while (!test.percolates()) {
            int row = StdRandom.uniform(1, N+1);
            int col = StdRandom.uniform(1, N+1);

            //disregards instances where randomized indices have
            //already been filled
            if (!test.isOpen(row, col)) {
               prob[i] += 1;
               test.open(row, col);
            }

         }
         prob[i] = prob[i] / (N*N);
      }
   }

   //sample mean of percolation threshold
   public double mean() {
      double sum = 0;

      for (int i = 0; i < trials; ++i)
         sum += prob[i];

      return sum / trials; 
   }

   //sample standard deviation of percolation threshold
   public double stddev() {
      double avg = mean();
      double std = 0;

      for (int i = 0; i < trials; ++i)
         std += (prob[i]-avg)*(prob[i]-avg);

      return Math.sqrt(std / (trials - 1));              
   }

   //low endpoint of 95% confidence level
   public double confidenceLo() {
      double avg = this.mean();
      double std = this.stddev();
      
      return avg - (1.96 * std) / Math.sqrt(trials);
   }

   //high endpoint of 95% confidence level
   public double confidenceHi() {
      double avg = this.mean();
      double std = this.stddev();
      double upconf = avg +  (1.96 * std) / Math.sqrt(trials);      

      return upconf;
   }

   public static void main(String[] args) {
         Scanner scan = new Scanner(System.in);
         int dim = scan.nextInt();
         int tests = scan.nextInt();

         PercolationStats stats = new PercolationStats(dim, tests);
         //double mean = stats.mean();
         //double stddev = stats.stddev();
         double hiconf = stats.confidenceHi();
         double lowconf = stats.confidenceLo();
         double mean = StdStats.mean(stats.prob);
         double stddev = StdStats.stddev(stats.prob);

         System.out.printf("mean                    = %.10f\n", mean);
         System.out.printf("stddev                  = %.10f\n", stddev);
         System.out.printf("95%% confidence interval = ");
         System.out.printf("%.10f, %.10f\n", lowconf, hiconf);
       
   }
}
