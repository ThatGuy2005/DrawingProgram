package factories;

import shapes.*;

import java.util.List;

public class ShapeFactory {
    public static Shape create(Tool tool, Point a, Point b) {
        switch (tool) {
            case LINE: return new Line(a,b);
            case CIRCLE: return new Circle(a,
                    (int) Math.round(Math.hypot(b.getX() - a.getX(),
                            b.getY() - a.getY())));
            case RECTANGLE: return new Rectangle(a,
                    Math.abs(b.getX()- a.getX()),
                    Math.abs(b.getY()- a.getY()));
            case TRIANGLE: {
                int left   = Math.min(a.getX(), b.getX());
                int right  = Math.max(a.getX(), b.getX());
                int top    = Math.min(a.getY(), b.getY());
                int bottom = Math.max(a.getY(), b.getY());

                return new Triangle(List.of(
                        new Point((left + right) / 2, top),   // apex
                        new Point(right, bottom),             // bottom-right
                        new Point(left, bottom)               // bottom-left
                ));
            }
            default: throw new IllegalStateException("Unknown tool");
        }
    }
}
