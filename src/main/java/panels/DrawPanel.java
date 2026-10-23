package panels;

import command.AddCommand;
import command.CommandController;
import factories.ShapeFactory;
import factories.Tool;
import model.Draw;
import shapes.Point;
import shapes.Shape;

import javax.swing.*;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class DrawPanel extends JPanel {
    private final Draw shapes = new Draw();
    private final CommandController controller = new CommandController();

    private Point start;
    private Shape preview;

    private Tool selectedTool = Tool.LINE;

    private Color selectedColor = Color.BLACK   ;

    public void undo() {
        controller.undo();
        repaint();
    }

    public void redo() {
        controller.redo();
        repaint();
    }

    public void setColor(Color color) {
        this.selectedColor = color;
    }

    public void setTool(Tool tool) {
        this.selectedTool = tool;
    }

    public DrawPanel() {
        MouseAdapter mouse = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                start = new Point(e.getX(), e.getY());
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                try {
                    preview = ShapeFactory.create(selectedTool, start, new Point(e.getX(), e.getY()));
                    preview.setColor(selectedColor);
                } catch (IllegalArgumentException ex) {
                    preview = null;
                }
                repaint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                preview = null;
                try {
                    Shape finished = ShapeFactory.create(selectedTool, start, new Point(e.getX(), e.getY()));
                    finished.setColor(selectedColor);
                    controller.executeCommand(new AddCommand(shapes, finished));
                } catch (IllegalArgumentException ex) {

                }
                repaint();
            }
        };

        addMouseListener(mouse);         // pressed, released, clicked
        addMouseMotionListener(mouse);   // dragged, moved
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        for (Shape s : shapes.getShapes())
            s.draw(g2);

        if (preview != null)
            preview.draw(g2);
    }
}
