import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdDraw;
import java.util.*;

public class FastCollinearPoints {
   private int segno;
   private ArrayList<LineSegment> linesegs;
   private Point[] slopeSort;

   public FastCollinearPoints(Point[] points) {
      linesegs = new ArrayList<LineSegment>();
      ArrayList<Point> oList = new ArrayList<Point>();
      
      for(int i = 0; i < points.length; i++)
         oList.add(points[i]);

      for (int p = 0; p < points.length; p++) {
         Point origin = oList.get(p);
         Comparator<Point> slopes = origin.slopeOrder();
         //insSort(points, slopes);
         Arrays.sort(points, slopes);
         //printPoints(points);
         ArrayList<Point> coPoints = new ArrayList<Point>();
         
         for (int q = 0; q < points.length; q++) {
            if (coPoints.isEmpty())
               coPoints.add(points[q]);
            
            else if (
origin.slopeTo(points[q-1]) == origin.slopeTo(points[q])) {
               coPoints.add(points[q]);
            
               if (coPoints.size() > 2 && q == points.length - 1) {
                  coPoints.add(origin);
                  Collections.sort(coPoints);
                  Point felem = Collections.min(coPoints);
                  Point lelem = Collections.max(coPoints);
                  LineSegment line = new LineSegment(felem, lelem);
                  linesegs.add(line);
                  coPoints.clear();
               }
            }

            else if (coPoints.size() > 2) {
               coPoints.add(origin);
               Collections.sort(coPoints);
               Point felem = Collections.min(coPoints);
               Point lelem = Collections.max(coPoints);
               LineSegment line = new LineSegment(felem, lelem);
               linesegs.add(line);
               coPoints.clear();
               coPoints.add(points[q]);
            }
            else {
               coPoints.clear();
               coPoints.add(points[q]);
            }
         }
      }

   }

/*
   public static void printPoints(Point[] points) {
      for (int i = 0; i < points.length; i++) {
         //System.out.print(points[i].toString());
         System.out.println(" "+points[0].slopeTo(points[i])+" ");
         //System.out.print(" Index: "+i);
         //System.out.println("");
      }
      System.out.printf("\n\n");
   }
*/
   private void insSort(Point[] points, Comparator slopes) {
      int N = points.length;
      for (int i = 0; i < N; i++)
         for (int j = i; j > 0; j--)
            if(less(slopes, points[j], points[j-1]))
               exch(points, j, j-1);
   }


//MERGESORT BOTTOM UP

private static void merge(Point[] points, Point[] auxp, int lo, int mid, int hi, Comparator slopes) {
      //assert isSorted(points, lo, mid);
      //assert isSorted(points, mid+1, high);

      
   for (int k = lo; k <= hi; k++)
      //auxp.add(points[k]);
      auxp[k] = points[k];

   int i = lo, j = mid+1;
   for (int k = lo; k <= hi; k++) {
      if      (i > mid)                            points[k] = auxp[j++];
      else if (j > hi)                             points[k] = auxp[i++];
      else if (less(slopes, points[j], points[i])) points[k] = auxp[j++];
      else                                         points[k] = auxp[i++];
   }
}

/*
private static void sort(Point[] points, Point[] aux, int lo, int hi, Comparator slopes) {
   if (hi <= lo) return;

   int mid = lo + (hi - lo) / 2;
   sort (points, aux, lo, mid, slopes);
   sort (points, aux, mid+1, hi, slopes);
   if (!less(slopes, points[mid+1], points[mid])) return;
   merge(points, aux, lo, mid, hi, slopes);      
}
*/
private static void sort(Point[] points, Comparator slopes) {
  int N = points.length;
  //auxp = new Point[N];
  Point[] auxp = new Point[N];
  for (int sz = 1; sz < N; sz = sz+sz)
     for (int lo = 0; lo < N-sz; lo += sz+sz)
        merge(points, auxp, lo, lo+sz-1, Math.min(lo+sz+sz-1, N-1), slopes);
}

   private static boolean less(Comparator slopes, Point v, Point w) {
      return slopes.compare(v,w) < 0;
   }

   private static void exch(Point[] points, int i, int j) {
      Point temp = points[i];
      points[i] = points[j];
      points[j] = temp;
   }

   public int numberOfSegments() {
      return segno;
   }
   public LineSegment[] segments() {
      LineSegment[] printseg = new LineSegment[linesegs.size()];

     // System.out.println(linesegs.size());      
      for (int i = 0; i < linesegs.size(); i++) {
         printseg[i] = linesegs.get(i);
         //System.out.println(printseg[i].toString());
      }
      return printseg;
   }

   public static void main(String[] args) {

      // read the N points from a file
      In in = new In(args[0]);
      int N = in.readInt();
      Point[] points = new Point[N];
      for (int i = 0; i < N; i++) {
         int x = in.readInt();
         int y = in.readInt();
         points[i] = new Point(x, y);
      }

      // draw the points
      StdDraw.show(0);
      StdDraw.setXscale(0, 32768);
      StdDraw.setYscale(0, 32768);
      for (Point p : points) {
         p.draw();
      }
      StdDraw.show();

      // print and draw the line segments
      FastCollinearPoints collinear = new FastCollinearPoints(points);
      for (LineSegment segment : collinear.segments()) {
        StdOut.println(segment);
        segment.draw();
      }
      
   }
}
