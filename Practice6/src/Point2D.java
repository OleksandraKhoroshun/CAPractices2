import java.util.Comparator;

public class Point2D {
    int x;
    int y;

    Point2D(int x, int y){
        this.x=x;
        this.y=y;
    }

    public static int ccw(Point2D a, Point2D b, Point2D c){
        int value = (b.x - a.x) * (c.y - a.y) - (b.y - a.y) * (c.x - a.x);

        if (value == 0) return 0;
        else if(value>0) return 1;
        else return -1;

    }
    public boolean equals(Object other){
        if (! (other instanceof Point2D))
                 return false;
           Point2D p = (Point2D) other;
           return x == p.x && y == p.y;
    }
    public String toString(){
        return "(" + x + ", " + y + ")";
    }
    public void draw(){
        ua.princeton.lib.StdDraw.point(x, y);
    }
    public void drawTo(Point2D that){
        ua.princeton.lib.StdDraw.line(this.x, this.y, that.x, that.y);
    }
    public int compareTo(Point2D that){
        if (this.y == that.y) {
            if (this.x > that.x) {
                return 1;
            }
            else if (this.x < that.x) {
                return -1;
            }
            else {
                return 0;
            }
        }
        else if (this.y > that.y) {
            return 1;
        }
        else {
            return -1;
        }
    }
    public final Comparator<Point2D> POLAR_ORDER = new PolarOrder();

    private class PolarOrder implements Comparator<Point2D>{

        public PolarOrder() {

        }

        public int compare(Point2D p1, Point2D p2) {
            if(compareTo(p1)==-1 && compareTo(p2)==1) return -1;
            else if(compareTo(p1)==1 && compareTo(p2)==-1) return 1;
            else return ccw(Point2D.this,p1,p2);
        }

    }
}
