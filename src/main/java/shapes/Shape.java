package shapes;

import org.example.Color;

import java.awt.*;

public abstract class Shape {
    Color color;
    double lineThickness;
    abstract public void draw(Graphics2D g);
    abstract public boolean contains(Point p);
    abstract public void move(int dx, int dy);
}
