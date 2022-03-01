import ua.princeton.lib.StdDraw;
import ua.princeton.lib.StdOut;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.LinkedList;

public class ConvexHull {
public static void main(String[] args) throws IOException {

    StdDraw.setXscale(0, 32768);
    StdDraw.setYscale(0, 32768);
    StdDraw.show(0);

    Path path = Paths.get("test.txt");

    BufferedReader reader = Files.newBufferedReader(path);
    int n = Integer.parseInt(reader.readLine());


    Point2D[] points = new Point2D[n];
    for(int i=0;i<n;i++) {
        String [] str = reader.readLine().split(" ");
        int x = Integer.parseInt(str[0]);
        int y = Integer.parseInt(str[1]);
        Point2D p = new Point2D(x,y);
        points[i] = p;
        p.draw();
        StdDraw.show(0);
    }
    int left = 0;
    for (int i = 1; i < n; i++)
        if (points[i].x > points[left].x)
            left = i;

    LinkedList<Point2D> list = new LinkedList<Point2D>();

    int p =left;
    int q=left;
    int k =n;
    Arrays.sort(points, points[left].POLAR_ORDER);
    do
    {

        list.add(points[p]);
        q = (p + 1) % n;

        for (int i = 0; i < n; i++)
        {
            if (Point2D.ccw(points[p], points[i], points[q])
                    == 1)
                q = i;
        }

        p = q;
        k--;

    } while (p != left && k>0);
    print(list);
    StdDraw.show(0);

}

    private static void print(LinkedList<Point2D> p) {
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < p.size(); i++) {
            buffer.append(p.get(i));
            if (i < p.size() - 1) {buffer.append(" -> ");

            p.get(i).drawTo(p.get(i+1));}
        }
        StdOut.println(buffer);
        p.getFirst().drawTo(p.getLast());
    }
}
