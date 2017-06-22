package four;

import java.util.*;

/*Solving the task boils down to following steps:

 1. Find the "reference point".
    This is the point the line starts from and ends where. To find this point we need:
     1.1 To compute average coordinates from  the list of coordinates as (Xmin+Xmax)/2
          and (Ymin+Ymax)/2 accordingly to abscissa and ordinates. This will be an "average point".
     1.2 To compute distances for all given points to the average point and find one with the minimum distance.
         And this will be the "reference point".
 2. Compute for all given points distances and angles from that point to found from previous step reference point
 3. Sort all given points (except found in step 1 reference point) by angles, then, if angle is the same, by distances.

 P.S. I think it's more sophisticated to encapsulate comparator inside the Point class and to use TreeSet data structure
       instead of implementation of methods: one for finding the point with minimum distance (for reference point in step1)
       and one for sorting by angles and distances.

 */
public class PolyLine {
//    How much points to operate
private final int NUMBER_OF_POINTS = 10;
    private int [] arrayX;
    private int [] arrayY;
    public static void main(String[] args) {
        PolyLine pl=new PolyLine();
        pl.go();
    }

    public void go(){
        arrayX = randomArray(NUMBER_OF_POINTS);
        arrayY = randomArray(NUMBER_OF_POINTS);
        consoleWriter( "This is the source list of points:");
        TreeSet<Point> points = sequenceOfCoordinates();
        consoleWriter(points, "This is the required sequence of coordinates");
    }
/*
Finds the reference point, then computes angles and distances for each point to that ref.point.
As I use TreeSet data structure and the Comparable interface is implemented in class Point, so I can
not to care about next sorting of points by angles and distances. They're already sorted.
 */
    public TreeSet<Point> sequenceOfCoordinates(){
        Point referencePoint = referencePoint(averageValue(arrayX), averageValue(arrayY));
        int referenceX=referencePoint.getX();
        int referenceY=referencePoint.getY();
        TreeSet<Point> points = new TreeSet<>();
        for(int i=0; i<arrayX.length; i++){
                Point point=new Point(arrayX[i], arrayY[i]);
                point.setDistance(referenceX, referenceY);
                point.setAngle(referenceX, referenceY);
                points.add(point);
        }
       return points;
    }
    //Writes to console content of arrays of coordinates and specified message
    public void consoleWriter(String message){
        System.out.println(message);
        for(int i=0; i< arrayX.length; i++)
            System.out.println(String.format("(%d; %d)", arrayX[i], arrayY[i]));
    }
    // Overloaded version, writes to console coordinate pairs of set of Point instances and specified message
    public void consoleWriter(TreeSet<Point> points, String message){
        System.out.println(message);
        for(Point point: points)
            System.out.println(String.format("(%d; %d)", point.getX(),point.getY()));

        System.out.println(String.format("(%d; %d)", points.first().getX(), points.first().getY()));
    }
//Fulfills an array with random int values
    public int[] randomArray(int length){
        int[] values=new int[length];
        for(int i=0; i<length;i++){
            values[i]=(int)(Math.random()*2*length-Math.random()*1.5*length);
        }
        return values;
    }
//    Finds the reference point in an specified arrays of coordinates relatively to an average point
    public Point referencePoint(int averageX, int averageY){
        TreeSet<Point> points = new TreeSet<>();
        for(int i=0; i<arrayX.length; i++){
            Point point=new Point(arrayX[i], arrayY[i]);
            point.setDistance(averageX, averageY);
            points.add(point);
        }
//        First element of TreeSet will have the least distance to average point, which is the required reference point
        return points.first();
    }

    public int averageValue(int[] array){
        return (findMax(array)+findMin(array))/2;
    }
//Returns the maximum element from specified array
    public int findMax(int[] array) {
        int result = array[0];
        for (int i = 1; i < array.length; i++) {
            if (result < array[i]) result = array[i];
        }
        return result;
    }
//Returns the minimum element from specified array
    public int findMin(int[] array){
        int result=array[0];
        for(int i=1; i<array.length; i++){
            if(result>array[i])result=array[i];
        }
        return result;
    }
}
