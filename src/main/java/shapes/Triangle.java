package shapes;

import java.awt.*;
import java.util.List;

public class Triangle extends Shape {
    private final List<Point> points;
    public Triangle(List<Point> points) {
        if (points == null || points.size() != 3)
            throw new IllegalArgumentException("A triangle must have three points!");

        if (side(points.get(0), points.get(1), points.get(2)) == 0)
            throw new IllegalArgumentException("The three points must not be on the same line!");

        this.points = List.copyOf(points);
    }
    @Override
    public void draw(Graphics2D g) {
        g.setColor(color);
        int[] xs = new int[3];
        int[] ys = new int[3];
        for (int i = 0; i < 3; i++) {
            xs[i] = points.get(i).getX();
            ys[i] = points.get(i).getY();
        }
        g.drawPolygon(xs, ys, 3);
    }
    @Override
    public boolean contains(Point p) {
        Point a = points.get(0), b = points.get(1), c = points.get(2);

        long d1 = side(a, b, p);
        long d2 = side(b, c, p);
        long d3 = side(c, a, p);

        boolean hasNeg = d1 < 0 || d2 < 0 || d3 < 0;
        boolean hasPos = d1 > 0 || d2 > 0 || d3 > 0;
        return !(hasNeg && hasPos);
    }

    private static long side(Point a, Point b, Point p) {
        return (long) (b.getX() - a.getX()) * (p.getY() - a.getY())
                - (long) (b.getY() - a.getY()) * (p.getX() - a.getX());
    }

    @Override
    public void move(int dx, int dy) {
        for (Point point : points) {
            point.setX(point.getX() + dx);
            point.setY(point.getY() + dy);
        }
    }
}
