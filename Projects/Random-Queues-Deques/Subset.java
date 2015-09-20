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
