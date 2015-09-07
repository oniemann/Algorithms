import java.lang.*;
import java.util.*;

class Program {
   public static void main(String[] args) {
      Percolation perc = new Percolation(3);
      Scanner scan = new Scanner(System.in);
      while (perc.percolates() == false) {
         int row = scan.nextInt();
         int col = scan.nextInt();
         perc.open(row,col);
         perc.printGrid();
      }

      System.out.printf("Percolated!\n");
   }
}
