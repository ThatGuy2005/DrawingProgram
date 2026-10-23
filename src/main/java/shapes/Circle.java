package shapes;

import java.awt.*;

public class Circle extends Shape{
    private final Point center;
    private final int radius;

    public Circle(Point center, int radius) {
        if(radius < 0)
            throw new IllegalArgumentException("The radius must be positive.");
        if(center == null)
            throw new IllegalArgumentException("The center point can't be null!");
        this.center = new Point(center.getX(), center.getY());
        this.radius = radius;
    }

    @Override
    public void draw(Graphics2D g) {
        g.setColor(color);
        g.drawOval(center.getX(), center.getY(),
                radius, radius);
    }
    @Override
    public boolean contains(Point p) {
        double dx = p.getX() - center.getX();
        double dy = p.getY() - center.getY();
        return dx * dx + dy * dy <= radius * radius;
    }
    @Override
    public void move(int dx, int dy) {
        center.setX(center.getX() + dx);
        center.setY(center.getY() + dy);
    }
}
