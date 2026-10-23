package command;

import model.Draw;
import shapes.Shape;

public class DeleteCommand implements Command{
    private final Draw shapes;
    private final Shape shape;
    private int index = -1;

    public DeleteCommand(Draw shapes, Shape shape) {
        this.shapes = shapes;
        this.shape = shape;
    }

    @Override
    public void execute() {
        index = shapes.indexOf(shape);
        shapes.remove(shape);
    }

    @Override
    public void undo() { shapes.add(index, shape); }
}
