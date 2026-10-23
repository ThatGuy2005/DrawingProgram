package command;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CommandControllerTest {

    private static class Counter {
        int value = 0;
    }

    private static class AddToCounterCommand implements Command {
        private final Counter counter;
        private final int amount;

        AddToCounterCommand(Counter counter, int amount) {
            this.counter = counter;
            this.amount = amount;
        }

        @Override public void execute() { counter.value += amount; }
        @Override public void undo()    { counter.value -= amount; }
    }

    private CommandController controller;
    private Counter counter;

    @BeforeEach
    void setUp() {
        controller = new CommandController();
        counter = new Counter();
    }

    @Test
    void executeCommand_runsCommand_andAllowsUndo() {
        controller.executeCommand(new AddToCounterCommand(counter, 5));

        assertEquals(5, counter.value);
        assertTrue(controller.canUndo());
        assertFalse(controller.canRedo());
    }

    @Test
    void undo_revertsLastCommand_andAllowsRedo() {
        controller.executeCommand(new AddToCounterCommand(counter, 5));

        controller.undo();

        assertEquals(0, counter.value);
        assertFalse(controller.canUndo());
        assertTrue(controller.canRedo());
    }

    @Test
    void redo_reappliesUndoneCommand_andItCanBeUndoneAgain() {
        controller.executeCommand(new AddToCounterCommand(counter, 5));
        controller.undo();

        controller.redo();
        assertEquals(5, counter.value);

        controller.undo();   // the bug you had earlier: this must still work
        assertEquals(0, counter.value);
    }

    @Test
    void newCommandAfterUndo_clearsRedoHistory() {
        controller.executeCommand(new AddToCounterCommand(counter, 5));
        controller.undo();

        controller.executeCommand(new AddToCounterCommand(counter, 3));

        assertFalse(controller.canRedo());
        controller.redo();               // should do nothing
        assertEquals(3, counter.value);
    }

    @Test
    void undo_isLastInFirstOut_andEmptyStacksAreSafe() {
        controller.executeCommand(new AddToCounterCommand(counter, 1));
        controller.executeCommand(new AddToCounterCommand(counter, 10));

        controller.undo();               // undoes +10 first
        assertEquals(1, counter.value);
        controller.undo();               // then +1
        assertEquals(0, counter.value);

        assertDoesNotThrow(() -> {
            controller.undo();           // nothing left to undo
            controller.redo();
            controller.redo();
            controller.redo();           // nothing left to redo
        });
        assertEquals(11, counter.value); // both commands redone, extra redo ignored
    }
}