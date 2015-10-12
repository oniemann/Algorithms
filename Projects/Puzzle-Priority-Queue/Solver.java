//credit:
//https://github.com/zellux/coursera-algs4/blob/master/8puzzle/Solver.java
import java.util.HashSet;
import java.util.LinkedList;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import java.util.List;

public class Solver {
   private List<Board> steps;
   private HashSet<String> history;

   //find a solution to the initial board (using the A* algorithm)
   public Solver(Board initial) {
      MinPQ<SearchNode> queue = new MinPQ<SearchNode>();
      queue.insert(new SearchNode(null, initial));
      queue.insert(new SearchNode(null, initial.twin()));
      history = new HashSet<String>();
      SearchNode last = null;

      while(!queue.isEmpty()) {
         //pop the queue with the minimum value
         SearchNode current = queue.delMin();

         //check to see if this is the final board
         if (current.board.isGoal()) {
            last = current;
            break;
         }
      //add the current board to the history hashset
         history.add(current.board.toString());

         if (current.equals(last))
            continue;
      //check if current equals last

      last = current;

      //search the neighbors of the current board, inserting the ones
      //that haven't already been done in history
      //(Critical optimization)
         for (Board b : current.board.neighbors()) {
            if (!history.contains(b.toString()))
               queue.insert(new SearchNode(current, b));
         }
      }

      //create the result LinkedList
      LinkedList<Board> result = new LinkedList<Board>();
      while (last != null) {
         result.addFirst(last.getBoard());
         last = last.getParent();
      }
      if (result.getFirst().equals(initial))
         steps = result;
   }

   //is the initial board solvable?
   public boolean isSolvable() {
      return steps != null;
   }

   //minimum number of moves to solve initial board; -1 if unsolvable
   public int moves() {
      if (isSolvable())
         return steps.size() - 1;
      else
         return -1;
   }

   //sequence of boards in a shortest solution; null if unsolvable
   public Iterable<Board> solution() {
      return steps;
   }
   
   private class SearchNode implements Comparable<SearchNode> {
      private SearchNode parent;
      private Board board;
      private int depth;

      public SearchNode(SearchNode parent, Board board) {
         this.board = board;
         this.parent = parent;

         if(parent == null) depth = 0;
         else               depth = parent.depth + 1;
      }

      public int getDepth() {
         return depth;
      }

      public SearchNode getParent() {
         return parent;
      }

      public Board getBoard() {
         return board;
      }

      public String toString() {
         return "Depth: " + depth + "\n" + board;
      }

      public int compareTo(SearchNode that) {
         int thisVal = depth + board.manhattan();
         int thatVal = that.getDepth() + that.getBoard().manhattan();
         return thisVal - thatVal;
      }
   }


   //solve a slider puzzle
   public static void main(String[] args) {

    // create initial board from file
    In in = new In(args[0]);
    int N = in.readInt();
    int[][] blocks = new int[N][N];
    for (int i = 0; i < N; i++)
        for (int j = 0; j < N; j++)
            blocks[i][j] = in.readInt();
    Board initial = new Board(blocks);

    // solve the puzzle
    Solver solver = new Solver(initial);

    // print solution to standard output
    if (!solver.isSolvable())
        StdOut.println("No solution possible");
    else {
        StdOut.println("Minimum number of moves = " + solver.moves());
        for (Board board : solver.solution())
            StdOut.println(board);
    }
   
   }
}
