package command;

import java.util.ArrayDeque;

public class CommandController {
    ArrayDeque<Command> undo;
    ArrayDeque<Command> redo;

    public CommandController() {
        undo = new ArrayDeque<>();
        redo = new ArrayDeque<>();
    }

    public void executeCommand(Command cmd) {
        cmd.execute();
        undo.push(cmd);
        redo.clear();
    }

    public void undo() {
        if(!undo.isEmpty()) {
            Command cmd = undo.pop();
            cmd.undo();
            redo.push(cmd);
        }
    }

    public void redo() {
        if(!redo.isEmpty()) {
            Command cmd = redo.pop();
            cmd.execute();
            redo.push(cmd);
        }
    }
}
