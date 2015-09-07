//Okeefe Niemann
//Date: 9/6/2015

//Compile: javac-alg4 Percolation.java
//Runs:    java-alg4 Percolation


//Creates data structure "Quick Union", which keeps track of indexes
//in a grid belonging to the same subset. This is then mapped to
//a corresponding NxN grid containing a "virtual top" node and a "virtual
//bottom" node for simplification of checking for percolation. The grid
//is then randomly selected to open and the QU data struct keeps track
//of if this index should be chained together with any of its neighboring
//tiles by condition of if they are open. When a path from the "virtual
//top" and "virtual bottom" is found, percolation has been achieved.

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

   private int[][] grid;
   private WeightedQuickUnionUF tree;
   private int dim;

   //creates the grid and the data structure, connecting the first
   //row to a virtual top and the last row to a virtual bottom.
   public Percolation(int N) {
      if (N <= 0) throw new IllegalArgumentException();
      
      dim = N;
      grid = new int[N][N];
      tree  = new WeightedQuickUnionUF(N*N + 2);
      
      for (int i = 0; i < dim; ++i)
         tree.union(0, i);
      for (int i = dim*dim; i >= dim*dim - dim; --i)
         tree.union(dim*dim+1 , i);
   }

   //Opens the selected tile, and assigns it to the subset its
   //neighbors are in if they are open
   public void open(int i, int j) {
      checkBounds(i, j);
      int row = i - 1;
      int col = j - 1;

      //checks to see if the tile is open
      if (!isOpen(i, j)) {
         grid[row][col] = 1;
         int index = gridToArray(row, col);
         int parent;
 
         //check to the right
         if (row + 1 < dim) {
            if (grid[row+1][col] == 1) {
               parent = gridToArray(row+1, col);
               tree.union(parent, index);
            }
         }

         //check to the left
         if (row - 1 >= 0) { 
            if (grid[row-1][col] == 1) {
               parent = gridToArray(row-1, col);
               tree.union(parent, index);
            }
         }

         //check below
         if (col + 1 < dim) {
            if (grid[row][col+1] == 1) {
               parent = gridToArray(row, col+1);
               tree.union(parent, index);
            }
         }
         
         //check above
         if (col - 1 >= 0) {
            if (grid[row][col-1] == 1) {
               parent = gridToArray(row, col-1);
               tree.union(parent, index);
            }
         }

         //printGrid();
      }
   }

   //checks if the selected tile is open
   public boolean isOpen(int i, int j) {
      checkBounds(i, j);
     // System.out.printf("check! %d %d\n", i-1, j-1);
      return grid[i-1][j-1] == 1;
   }

   //checks if the given indice is in the same subset as the 
   //virtual top
   public boolean isFull(int i, int j) {
      checkBounds(i, j);
      if (tree.connected(gridToArray(i-1, j-1), 0) && grid[i-1][j-1] == 1)
         return true;
      return false;
   }

   //checks to see if the virtual top and virtual bottom are in the
   //same subset
   public boolean percolates() {
      //boolean doesit = tree.connected(0,dim*dim+1);
      //System.out.println(doesit);
      return tree.connected(0, dim*dim + 1);
   }

   //prints the grid for terminal visuals
   private void printGrid() {
      for (int i = 0; i < dim; ++i) {
         for (int j = 0; j < dim; ++j)
            System.out.printf("%2d", grid[i][j]);
         
         System.out.printf("\n");
      } 
      System.out.printf("\n");
   }


   //converts grid indices to an index in the data structure's
   //array.
   private int gridToArray(int row, int col) {
      return dim * row + col;
   }

   //makes sure the inputted indices are within the bounds of the
   //grid.
   private void checkBounds(int i, int j) {
      if ((0 > i || i > dim+1) || (0 > j || j > dim+1))
         throw new IndexOutOfBoundsException();
   }

   //running the program through the command line
/*   public static void main(String[] args) {
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
*/
}
