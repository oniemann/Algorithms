import java.util.ArrayList;
import java.lang.Math;
import java.lang.StringBuilder;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdRandom;

public class Board {
   private int dim;
   private int[][] tiles;   
   private static final int[] DELTA_X = new int[]{1, -1, 0, 0};
   private static final int[] DELTA_Y = new int[]{0, 0, 1, -1};
   private boolean firstHam;
   private boolean firstMan;
   private int ham;
   private int man;

   //construct a board from an N-by-N array where blocks[i][j] = block
   //in row i, column j.
   public Board(int[][] blocks) {
      dim = blocks[0].length;
      tiles = new int[dim][dim];
      firstHam = true;
      firstMan = true;

      for(int i = 0; i < dim; i++) {
         for(int j = 0; j < dim; j++) {
            //System.out.printf("%d", blocks[i][j]);
            tiles[i][j] = blocks[i][j];
         }
      }
   } 

   //board dimension N
   public int dimension() {
      return dim;
   }

   //number of blocks out of place
   public int hamming() {
      if(!firstHam)
         return ham;

      int hamcount = 0;

      for (int row = 0; row < dim; row++) {
         //System.out.printf("%2d", tiles[i]);
         for(int col = 0; col < dim; col++) {
            if (tiles[row][col] == coordsToIndex(row, col) || tiles[row][col] == 0) continue;
            //System.out.printf("tiles: %d,   coordsToIndex: %d\n", tiles[row][col], coordsToIndex(row,col));
            hamcount++;
         }
      }

      firstHam = false;
      ham = hamcount;      
      return hamcount;
   }

   //sum of Manhatten distances between blocks and goal
   public int manhattan() {
      if(!firstMan)
         return man;

      int manhattan = 0;

      for (int i = 0; i < dim; i++) {
         for (int j = 0; j < dim; j++) {
         
         if (tiles[i][j] == coordsToIndex(i,j) || tiles[i][j] == 0) continue;
         //System.out.printf("tiles: %d,   coordsToIndex: %d\n", tiles[i][j], coordsToIndex(i,j));
        
         manhattan += findDiff(tiles[i][j], i, j);
         }
      }
      
      firstMan = false;
      man = manhattan;
      return manhattan;
   }

   private int coordsToIndex(int row, int col) {
      int index;
      index = dim * row + (col + 1);

      return index;
   }

   private int[] indexToCoords(int index) {
      int[] coords = new int[2];
      coords[0] = (index - 1) / dim;
      coords[1] = (index + dim - 1) % dim;
      return coords;
   }

   private int findDiff(int index, int x2, int y2) {
      int[] coords = indexToCoords(index);
      //System.out.println("Index: " + index);
      //System.out.printf("tiles: (%d, %d)   target: (%d, %d)\n", x1, y1, x2, y2);
      int sum = Math.abs(coords[0]-x2) + Math.abs(coords[1]-y2);
      return sum;
   }

   //is this board the goal board?
   public boolean isGoal() {
      return hamming() == 0;
   }

   //a board that is obtained by exchanging any pair of blocks
   //switching two random block neighbors
   public Board twin() {
      int[][] newtiles = tileTwin(tiles);
      boolean valid = true;
      /*
      int[] coords = new int[2];
      int[] twin = new int[2];
      while(valid) {
         coords = indexToCoords(StdRandom.uniform(0, dim*dim));
         if(newtiles[coords[0]][coords[1]] != 0) valid = false;
      }
      valid = true;
      while(valid) {
         twin = indexToCoords(StdRandom.uniform(0, dim*dim));
         if(newtiles[twin[0]][twin[1]] != 0 && (coords[0] != twin[0]))
            valid = false;
      }

      //System.out.printf("coords: (%d, %d)   twin: (%d, %d)\n", coords[0], coords[1], twin[0], twin[1]);

      int temp = newtiles[twin[0]][twin[1]];
      newtiles[twin[0]][twin[1]] = newtiles[coords[0]][coords[1]];
      newtiles[coords[0]][coords[1]] = temp;
      */
      for(int i = 0; i < dim; i++) {
         if (newtiles[i][0] != 0 && newtiles[i][1] != 0) {
            int temp = newtiles[i][0];
            newtiles[i][0] = newtiles[i][1];
            newtiles[i][1] = temp;
            break;
         }
      }

      return new Board(newtiles);
   }

   private int[][] tileTwin(int[][] tiles) {
      int[][] newtiles = new int[dim][dim];
      for (int i = 0; i < dim; i++)
         for (int j = 0; j < dim; j++)
            newtiles[i][j] = tiles[i][j];
   
      return newtiles;
   }
      
   //does this board equal y?
   public boolean equals(Object y) {
      //for(int i = 0; i < y.length; i++)
      //   if (this.tiles[i] != y.tiles[i])
      //      return false;
      if (this == y) return true;
      return toString().equals(y.toString());
   }

   //all neighboring boards
   //Credit to the following for general structure of iterator:
   //https://github.com/zellux/coursera-algs4/blob/master/8puzzle/Board.java
   public Iterable<Board> neighbors() {
      int blankX = 0;
      int blankY = 0;
      //find the blank spot
      ArrayList<Board> nextTurn = new ArrayList<Board>();
      int[][] newtiles = tileTwin(tiles);

      for(int i = 0; i < dim; i++) {
         for(int j = 0; j < dim; j++) {
            if (newtiles[i][j] == 0) {
               blankX = i;
               blankY = j;
            }
         }
      }
      
      //swap blank index with top/bottom/left/right
      for(int i = 0; i < 4; i++) {
         int newx = blankX + DELTA_X[i];
         int newy = blankY + DELTA_Y[i];
         boolean switching = switchBlocks(newtiles, blankX, blankY, newx, newy);
         if (switching) {
            nextTurn.add(new Board(newtiles));
            //switch back for next potential move
            switchBlocks(newtiles, blankX, blankY, newx, newy);
         }
      }
      
      //for(Board board : nextTurn) {
      //   System.out.print(board.toString());
      //}

      return nextTurn;
   }

   private boolean switchBlocks(int[][] newtiles, int bi, int bj, int ti, int tj) {
      //saves from IndexOutOfBoundsException
      if (ti >= dim || ti < 0 || tj >= dim || tj < 0) return false;

      int temp = newtiles[bi][bj];
      newtiles[bi][bj] = newtiles[ti][tj];
      newtiles[ti][tj] = temp;

      return true;
   }

   //string representation of this board
   public String toString() {
      StringBuilder s = new StringBuilder();
      //s.append("hamming: " + hamming() + "\n");
      s.append(dim + "\n");
      for (int i = 0; i < dim; i++) {
         for (int j = 0; j < dim; j++) {
         s.append(String.format("%2d ", tiles[i][j]));
         }
         s.append("\n");
      }
   
      return s.toString();
   }

   //unit test
   public static void main(String[] args) {
      In in = new In(args[0]);
      int N = in.readInt();
      int[][] blocks = new int[N][N];
      for (int i = 0; i < N; i++) {
         for (int j = 0; j < N; j++){
            blocks[i][j] = in.readInt();
         }
      }
      Board initial = new Board(blocks);
      System.out.print(initial.toString());

      initial.neighbors();   
      System.out.println("");
      System.out.println("Twin:");
      Board twin = initial.twin();
      //Board twin = new Board(blocks);
     // System.out.print(twin.toString());
     // System.out.println(initial.equals(twin));
   }

   
}
