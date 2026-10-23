package shapes;

import java.awt.*;
import java.awt.geom.Line2D;

public class Line extends Shape{
    private final Point point1;
    private final Point point2;

    public Line(Point start, Point end) {
        this.point1 = start;
        this.point2 = end;
    }

    @Override
    public void draw(Graphics2D g) {
        g.drawLine(point1.getX(), point1.getY(),
                point2.getX(), point2.getY());
    }
    @Override
    public boolean contains(Point point) {
        double dist = Line2D.ptSegDist(
                point1.getX(), point1.getY(),
                point2.getX(), point2.getY(),
                point.getX(), point.getY());
        return dist <= 4;
    }
    @Override
    public void move(int dx, int dy) {
        point1.setX(point1.getX() + dx);
        point1.setY(point1.getY() + dy);
        point2.setX(point2.getX() + dx);
        point2.setY(point2.getY() + dy);
    }
}
