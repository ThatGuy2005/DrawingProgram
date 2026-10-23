package model;

import shapes.Shape;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Draw {
    private final List<Shape> shapes = new ArrayList<>();

    public void add(Shape shape)          { shapes.add(shape); }
    public void add(int index, Shape s)   { shapes.add(index, s); }
    public boolean remove(Shape shape)    { return shapes.remove(shape); }
    public int indexOf(Shape shape)       { return shapes.indexOf(shape); }

    public List<Shape> getShapes() {
        return Collections.unmodifiableList(shapes);
    }
}
