package shapes;

import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Rectangle extends Shape {
    private final List<Point> points;

    public Rectangle(List<Point> edges) {
        if(edges.size() != 4)
            throw new IllegalArgumentException("A rectangle must have four edges!");
        // First sort the corners so that we'll have:
        // upper left -> upper right -> lower right -> lower left
        // this makes finding a point easy.
        this.points = List.copyOf(sortCorners(edges));
    }

    public Rectangle(Point point, int width, int height) {
        if(width < 0 || height < 0)
            throw new IllegalArgumentException("A rectangle must have positive width and height!");
        this(List.of(new Point(point.getX(), point.getY()),
                new Point(point.getX()+width,point.getY()),
                new Point(point.getX()+width,point.getY()+height),
                new Point(point.getX(),point.getY()+height)));
    }

    // Returns corners in order: upper-left, upper-right, lower-right, lower-left
    private static List<Point> sortCorners(List<Point> edges) {
        double cx = edges.stream().mapToDouble(Point::getX).average().orElse(0);
        double cy = edges.stream().mapToDouble(Point::getY).average().orElse(0);

        List<Point> sorted = new ArrayList<>(edges);

        // With y pointing down, increasing angle = clockwise on screen
        sorted.sort(Comparator.comparingDouble(
                p -> Math.atan2(p.getY() - cy, p.getX() - cx)));

        // Upper-left = smallest x + y; rotate the list so it comes first
        int start = 0;
        for (int i = 1; i < 4; i++) {
            Point p = sorted.get(i), best = sorted.get(start);
            if (p.getX() + p.getY() < best.getX() + best.getY())
                start = i;
        }

        List<Point> result = new ArrayList<>(4);
        for (int i = 0; i < 4; i++)
            result.add(sorted.get((start + i) % 4));
        return result;
    }

    @Override
    public void draw(Graphics2D graphics2D) {
        graphics2D.draw(new java.awt.Rectangle(points.getFirst().getX(),
                                             points.getFirst().getY(),
                        points.get(2).getX()-points.getFirst().getX(),
                        points.get(2).getY()-points.getFirst().getY())
                );
    };

    @Override
    public boolean contains(Point point) {
        // Check the opposite corners
        return(point.getX() < points.getFirst().getX()
                || point.getY() < points.getFirst().getY()
                || point.getX() > points.get(2).getX()
                || point.getY() > points.get(2).getY());
    }

    @Override
    public void move(int dx, int dy) {
        for(Point point : points) {
            point.setX(point.getX() + dx);
            point.setY(point.getY() + dy);
        }
    }
}
