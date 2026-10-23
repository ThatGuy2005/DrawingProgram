package shapes;

import org.example.Color;

import java.awt.*;

public abstract class Shape {
    Color color;
    double lineThickness;
    abstract void draw(Graphics2D g);
    abstract boolean contains(Point p);
    abstract void move(int dx, int dy);
}
