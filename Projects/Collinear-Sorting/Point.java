//Compilation: javac-algs4 Point.java
//Execution:   java-algs4 Point

import java.util.Comparator;
import edu.princeton.cs.algs4.StdDraw;

public class Point implements Comparable<Point> {

   private final double x;
   private final double y;
   
   public Point(int x, int y) {
      this.x = x;
      this.y = y;
   }

   public void draw() {
      StdDraw.point(x, y);
   }

   public void drawTo(Point that) {
      StdDraw.line(this.x, this.y, that.x, that.y);
   }

   public double slopeTo(Point that) {
      //takes care of vertical (infinite slope
      if (this.x == that.x) {
         if (this.y == that.y)
            return Double.NEGATIVE_INFINITY;
         else
            return Double.POSITIVE_INFINITY;
      } 
      //computes slope

      //System.out.printf("this.y = %f, that.y = %f, this.x = %f, that.x = %f\n", this.y, that.y, this.x, that.x);
      double slope = (that.y - this.y) / (that.x - this.x);
      //System.out.println(slope);
      //explicitly assigns a zero slope as 0.0
      if (slope == -0.0) slope = 0.0;

      return slope;
   }


   public Comparator<Point> slopeOrder() {
      Comparator<Point> SLOPE = new SlopeOrder();
      return SLOPE;
   } 

  // private final Comparator<Point> SLOPE_ORDER = new SlopeOrder();
  // private final Comparator<Point> POINT_ORDER = new PointOrder();

   public int compareTo(Point that) {
      if (this.y < that.y)   return -1;
      if (this.y > that.y)   return +1;
      if (this.y == that.y)
         if(this.x < that.x) return -1;
         if(this.x > that.x) return +1; 
      return 0;
   }


   public String toString() {
      return "(" + x + ", " + y + ")";
   }

   private class SlopeOrder implements Comparator<Point> {
      public int compare(Point q1, Point q2){
         double slope1 = slopeTo(q1);
         double slope2 = slopeTo(q2);
         return slope1 < slope2 ? -1 : slope1 > slope2 ? 1 : 0;
      }
   }
   private class PointOrder implements Comparator<Point> {
      public int compare(Point q1, Point q2) {
         return q2.compareTo(q1);
      }
   }

   public static void main(String[] args) {
      Point one = new Point(1,2);
      Point two = new Point(2,2);

     // int smaller = one.compareTo(two);
     // System.out.printf("This should be -1: %d\n", smaller);
      double slope = one.slopeTo(two);
      System.out.printf("Slope between (1,2) and (5000,1): %f\n", slope);
      
   }   
}
