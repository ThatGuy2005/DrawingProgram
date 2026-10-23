package frames;

import factories.Tool;
import panels.DrawPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

public class DrawFrame extends JFrame {
    private final DrawPanel panel = new DrawPanel();

    public DrawFrame() {
        super("DrawingApp");

        panel.setPreferredSize(new Dimension(800, 600));
        panel.setBackground(Color.WHITE);

        add(createToolBar(), BorderLayout.NORTH);
        add(panel, BorderLayout.CENTER);

        setupShortcuts();

        pack();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private JToolBar createToolBar() {
        JToolBar toolBar = new JToolBar();
        toolBar.setFloatable(false);

        // Tool selection: one toggle button per Tool, only one can be selected
        ButtonGroup group = new ButtonGroup();
        for (Tool tool : Tool.values()) {
            JToggleButton button = new JToggleButton(tool.name());
            button.addActionListener(e -> panel.setTool(tool));
            group.add(button);
            toolBar.add(button);

            if (tool == Tool.RECTANGLE)        // matches the panel's default tool
                button.setSelected(true);
        }

        toolBar.addSeparator();

        // Color picker
        JButton colorButton = new JButton("Color");
        colorButton.setForeground(Color.BLACK);
        colorButton.addActionListener(e -> {
            Color chosen = JColorChooser.showDialog(this, "Pick a color", colorButton.getForeground());
            if (chosen != null) {               // null when the user cancels
                panel.setColor(chosen);
                colorButton.setForeground(chosen);   // shows the current color
            }
        });
        toolBar.add(colorButton);

        toolBar.addSeparator();

        // Undo / redo buttons
        JButton undoButton = new JButton("Undo");
        undoButton.addActionListener(e -> panel.undo());
        toolBar.add(undoButton);

        JButton redoButton = new JButton("Redo");
        redoButton.addActionListener(e -> panel.redo());
        toolBar.add(redoButton);

        // Buttons shouldn't take keyboard focus away from the shortcuts
        for (Component c : toolBar.getComponents())
            c.setFocusable(false);

        return toolBar;
    }

    private void setupShortcuts() {
        JComponent root = getRootPane();
        InputMap inputMap = root.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = root.getActionMap();

        // KeyStroke → action name
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_Z, InputEvent.CTRL_DOWN_MASK), "undo");
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_Y, InputEvent.CTRL_DOWN_MASK), "redo");

        // action name → what to do
        actionMap.put("undo", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panel.undo();
            }
        });
        actionMap.put("redo", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panel.redo();
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new DrawFrame().setVisible(true));
    }
}
