package four;

import static java.lang.Math.PI;
/*
This class represents the point with coordinates (x;y) on the plane.

 */
public class Point implements Comparable{
    private final int x;
    private final int y;
    private double distance;
    private double angle;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public double getDistance() {
        return distance;
    }
// Solves the distance between this and transferred points and sets it as class field
    public void setDistance(int x, int y) {
        this.distance = Math.sqrt(Math.pow(x - this.x, 2) + Math.pow(y - this.y, 2));
    }

    public double getAngle() {
        return angle;
    }
//Solves and sets an angle between transferred point as argument and this point in range [0;2*PI] radians

    public void setAngle(int referenceX, int referenceY) {
//    I use the addendum because of Math.atan() returns values lying on [-pi/2;pi/2], and I need to
//        differ the angle of, for example, 5*pi/4 which lies in 3-rd quart from pi/4, which lies in 1-st
        double addendum = 0;
        double deltaX = this.x - referenceX;
        double deltaY = this.y - referenceY;
//        If points are lying on a vertical line
        if(deltaX == 0){
            if(deltaY == 0){
//deltaX=0 and deltaY=0 only if this point is a reference point, which we need to
// be the first and last point in required sequence.
                this.angle = 0;
                return;
            }
//            then we need to check if the point-argument lies higher (angle will be PI/2) or lower of reference point
//            (angle will be -PI/2)
            this.angle = deltaY >0 ? PI/2: -PI/2;
            return;
        }
        if(deltaX < 0){
            addendum = PI;
            if(deltaY < 0)addendum = 2*PI;
        }
        this.angle = Math.atan(deltaY/deltaX)+addendum;
    }

    /*Compares the angles first, then, if they're equals, the distances between this and transferred as an argument point
    * */
    @Override
    public int compareTo(Object o) {
        Point point = (Point)o;
// multiplying by 1000 serves not to waste difference between arguments that is less than 1 due to type conversation
//        from double to int
        int result=(int)((this.getAngle() - point.getAngle())*1000);
        if(result == 0){
            result=(int)((this.getDistance() - point.getDistance())*1000);
        }
        return result;
    }
}
