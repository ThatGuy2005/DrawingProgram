package model;

import command.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import shapes.Line;
import shapes.Point;
import shapes.Shape;

import java.awt.Color;

import static org.junit.jupiter.api.Assertions.*;

class CommandsTest {

    private Draw draw;
    private CommandController controller;
    private Shape line;

    @BeforeEach
    void setUp() {
        draw = new Draw();
        controller = new CommandController();
        line = new Line(new Point(0, 0), new Point(100, 0));
    }

    @Test
    void addCommand_addsShape_andUndoRemovesIt() {
        controller.executeCommand(new AddCommand(draw, line));
        assertEquals(1, draw.getShapes().size());
        assertSame(line, draw.getShapes().get(0));

        controller.undo();
        assertTrue(draw.getShapes().isEmpty());
    }

    @Test
    void deleteCommand_removesShape_andUndoRestoresSamePosition() {
        Shape first = new Line(new Point(0, 0), new Point(10, 10));
        Shape second = new Line(new Point(20, 20), new Point(30, 30));
        controller.executeCommand(new AddCommand(draw, first));
        controller.executeCommand(new AddCommand(draw, line));
        controller.executeCommand(new AddCommand(draw, second));

        controller.executeCommand(new DeleteCommand(draw, line));
        assertFalse(draw.getShapes().contains(line));
        assertEquals(2, draw.getShapes().size());

        controller.undo();
        assertEquals(1, draw.getShapes().indexOf(line));   // back in the middle
    }

    @Test
    void moveCommand_movesShape_andUndoMovesItBack() {
        controller.executeCommand(new AddCommand(draw, line));

        controller.executeCommand(new MoveCommand(line, 0, 50));
        assertTrue(line.contains(new Point(50, 50)));
        assertFalse(line.contains(new Point(50, 0)));

        controller.undo();
        assertTrue(line.contains(new Point(50, 0)));
        assertFalse(line.contains(new Point(50, 50)));
    }

    @Test
    void changeColorCommand_changesColor_andUndoRestoresOldColor() {
        line.setColor(Color.BLACK);
        controller.executeCommand(new AddCommand(draw, line));

        controller.executeCommand(new ColorChangeCommand(line, Color.RED));
        assertEquals(Color.RED, line.getColor());

        controller.undo();
        assertEquals(Color.BLACK, line.getColor());
    }

    @Test
    void redo_reappliesUndoneCommand_onRajz() {
        controller.executeCommand(new AddCommand(draw, line));
        controller.undo();
        assertTrue(draw.getShapes().isEmpty());

        controller.redo();
        assertEquals(1, draw.getShapes().size());

        controller.undo();   // must still be undoable after redo
        assertTrue(draw.getShapes().isEmpty());
    }

    @Test
    void newCommandAfterUndo_clearsRedo() {
        Shape other = new Line(new Point(0, 10), new Point(10, 10));
        controller.executeCommand(new AddCommand(draw, line));
        controller.undo();

        controller.executeCommand(new AddCommand(draw, other));
        controller.redo();   // nothing to redo: 'line' must not come back

        assertEquals(1, draw.getShapes().size());
        assertSame(other, draw.getShapes().get(0));
    }
}