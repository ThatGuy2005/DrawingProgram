package command;

import shapes.Shape;

import java.util.List;

public class AddCommand implements Command{
    private final List<Shape> shapes;
    private final Shape shape;

    public AddCommand(List<Shape> shapes, Shape shape) {
        this.shapes = shapes;
        this.shape = shape;
    }

    @Override
    public void execute() { shapes.add(shape); }

    @Override
    public void undo() { shapes.remove(shape); }
}
