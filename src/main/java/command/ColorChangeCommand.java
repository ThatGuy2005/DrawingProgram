package command;


import shapes.Shape;

import java.awt.*;

public class ColorChangeCommand implements Command {
    private final Shape shape;
    private final Color color;
    private final Color oldColor;

    public ColorChangeCommand(Shape shape, Color color) {
        this.shape = shape;
        this.color = color;
        this.oldColor = color;
    }
    @Override
    public void execute() { shape.setColor(color); }

    @Override
    public void undo() { shape.setColor(oldColor); }
}
