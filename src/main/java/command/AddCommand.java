package command;

import model.Draw;
import shapes.Shape;

import java.util.List;

public class AddCommand implements Command{
    private final Draw shapes;
    private final Shape shape;

    public AddCommand(Draw shapes, Shape shape) {
        this.shapes = shapes;
        this.shape = shape;
    }

    @Override
    public void execute() { shapes.add(shape); }

    @Override
    public void undo() { shapes.remove(shape); }
}
