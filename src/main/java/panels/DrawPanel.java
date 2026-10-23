package panels;

import command.CommandController;
import shapes.Point;
import shapes.Shape;

import javax.swing.*;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.util.ArrayList;
import java.util.List;

public class DrawPanel extends JPanel {
    private final List<Shape> shapes = new ArrayList<>();
    private final CommandController controller = new CommandController();

    private Point start;
    private Shape preview;

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
