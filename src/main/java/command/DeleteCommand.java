package command;

import shapes.Shape;

import java.util.List;

public class DeleteCommand implements Command{
    private final List<Shape> shapes;
    private final Shape shape;

    public DeleteCommand(List<Shape> shapes, Shape shape) {
        this.shapes = shapes;
        this.shape = shape;
    }

    @Override
    public void execute() { shapes.remove(shape); }

    @Override
    public void undo() { shapes.add(shape); }
}
