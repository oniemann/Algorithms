//Okeefe Niemann
//9/20/2015

//Implementation of randomized queue. Accepts all arguments in the command line
//and randomly prints out a specified amount of them.

//e.g. StdIn: AA BB CC DD EE | java-algs4 Subset 3
//     StdOut: BB EE AA

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Subset {
   public static void main(String[] args) {
      RandomizedQueue<String> queue = new RandomizedQueue<String>();
      while (!StdIn.isEmpty()) {
         String item = StdIn.readString();
         queue.enqueue(item);
      }

      int k = Integer.parseInt(args[0]);

      for (int i = 0; i < k; i++) {
         String output = queue.dequeue();
         StdOut.println(output);
      }
   }
}
