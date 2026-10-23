package panels;

import command.AddCommand;
import command.CommandController;
import factories.ShapeFactory;
import factories.Tool;
import shapes.Point;
import shapes.Shape;

import javax.swing.*;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class DrawPanel extends JPanel {
    private final List<Shape> shapes = new ArrayList<>();
    private final CommandController controller = new CommandController();

    private Point start;
    private Shape preview;

    private Tool selectedTool;

    public DrawPanel() {
        MouseAdapter mouse = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                start = new Point(e.getX(), e.getY());
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                preview = ShapeFactory.create(selectedTool,start, new Point(e.getX(), e.getY()));
                repaint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                Shape finished = ShapeFactory.create(selectedTool,start, new Point(e.getX(), e.getY()));
                preview = null;
                controller.executeCommand(new AddCommand(shapes, finished));
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

        g2.setColor(Color.RED);
        g2.drawRect(50, 50, 100, 60);     // x, y, width, height
        g2.fillOval(200, 80, 40, 40);
        g2.drawLine(10, 10, 300, 200);
    }


}
