import java.lang.NullPointerException;
import java.util.ArrayList;
import java.util.Collections;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdDraw;

public class BruteCollinearPoints {
   private int maxSegs;
   private int segno;
   private ArrayList<LineSegment> linesegs;
   private ArrayList<Point> pointlist;

   public BruteCollinearPoints(Point[] points) {
      if (points == null) throw new java.lang.NullPointerException();
      for (int i = 0; i < points.length; i++) {
         if (points[i] == null) throw new java.lang.NullPointerException();
         for (int j = 0; j < points.length && j != i; j++) {
            if (points[i].toString().equals(points[j].toString()))
               throw new java.lang.IllegalArgumentException();
         }
      }
      segno = 0;
      linesegs = new ArrayList<LineSegment>();
      pointlist = new ArrayList<Point>();

      //sweep for initial point
      for(int i = 0; i < points.length; i++) {
         Point p1 = points[i];
         //establishes the first slope
         for (int j = i+1; j < points.length; j++) {
            Point p2 = points[j];
            
            for(int k = j+1; k < points.length; k++) {
               Point p3 = points[k];
               
               if (p1.slopeTo(p2) == p2.slopeTo(p3)) {

                  for(int l = k+1; l < points.length; l++) {

                     Point p4 = points[l];

                     if(p1.slopeTo(p3) == p1.slopeTo(p4)) {
                        pointlist.add(p1);
                        pointlist.add(p2);
                        pointlist.add(p3);
                        pointlist.add(p4);
                        Collections.sort(pointlist);
                        p1 = Collections.min(pointlist);
                        p4 = Collections.max(pointlist);
                        LineSegment line = new LineSegment(p1, p4);
                        addSegs(line);
                        pointlist.clear();
                     }
                  }
               }
            }
         }
      }
   }

   public int numberOfSegments() {
      return segno;
   }

   public LineSegment[] segments(){
      LineSegment[] printseg = new LineSegment[linesegs.size()];
      
      for (int i = 0; i < linesegs.size(); i++) {
         printseg[i] = linesegs.get(i);
        // System.out.println(printseg[i].toString());
      }
      return printseg;
   }


   private void addSegs(LineSegment checkLine) {
      String l1 = checkLine.toString();
      for (int i = 0; i < linesegs.size(); i++) {
         String l2 = linesegs.get(i).toString();
         if(l1.equals(l2))
            return;
      }
      linesegs.add(checkLine);
      checkLine.draw();
      segno++;
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
      BruteCollinearPoints collinear = new BruteCollinearPoints(points);
      for (LineSegment segment : collinear.segments()) {
        StdOut.println(segment);
        segment.draw();
      }
   }
}

