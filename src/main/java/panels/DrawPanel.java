package panels;

import command.AddCommand;
import command.CommandController;
import command.MoveCommand;
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

    private Shape selected;
    private Point lastPoint;
    private int totalDx, totalDy;

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
                Point p = new Point(e.getX(), e.getY());

                if (selectedTool == Tool.SELECT) {
                    selected = findShapeAt(p);
                    lastPoint = p;
                    totalDx = 0;
                    totalDy = 0;
                    return;
                }

                start = p;
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                if (selectedTool == Tool.SELECT) {
                    if (selected == null) return;
                    int dx = e.getX() - lastPoint.getX();
                    int dy = e.getY() - lastPoint.getY();
                    selected.move(dx, dy);          // live feedback while dragging
                    totalDx += dx;
                    totalDy += dy;
                    lastPoint = new Point(e.getX(), e.getY());
                    repaint();
                    return;
                }
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
                if (selectedTool == Tool.SELECT) {
                    if (selected != null && (totalDx != 0 || totalDy != 0)) {
                        selected.move(-totalDx, -totalDy);   // put it back...
                        controller.executeCommand(new MoveCommand(selected, totalDx, totalDy)); // ...and move it as one undoable step
                    }
                    selected = null;
                    repaint();
                    return;
                }
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

    private Shape findShapeAt(Point p) {
        List<Shape> list = shapes.getShapes();
        for (int i = list.size() - 1; i >= 0; i--)
            if (list.get(i).contains(p))
                return list.get(i);
        return null;
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
