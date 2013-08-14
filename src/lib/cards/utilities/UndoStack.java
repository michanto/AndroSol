package lib.cards.utilities;

import java.util.ArrayList;
import java.util.List;

public class UndoStack {
    public UndoStack() {
        commands = new ArrayList<Command>();
        index = -1;
    }

    // Current command index into list.
    private int index;
    private List<Command> commands;

    private void setIndexToTop() {
        index = getLastCommandIndex();
    }

    private int getLastCommandIndex() {
        return commands.size() - 1;
    }

    public boolean executeCommand(Command command) {
        // Do the command. If it is successful then:
        // Remove everything after Index from the list.
        // Add the new command to the list.
        // Set Index to point to the top of the list.
        if (command.invoke()) {
            if (command.getClearsUndoStack()) {
                commands.clear();
            } else if (index < getLastCommandIndex()) {
                commands.remove(commands.subList(index + 1, commands.size()
                        - getLastCommandIndex()));
            }
            commands.add(command);
            setIndexToTop();
        }
        return false;
    }

    public boolean undo() {
        // Undo the current command.
        // Decrement Index;
        if (peek() != null) {
            if (!peek().undo()) {
                // TODO: Something wrong with the undo stack. How to resolve?
                return false;
            }
            index--;
            return true;
        }

        return false;
    }

    public boolean reInvoke() {
        // Do the next command, if possible.
        // Increment index if that succeeds.
        if (peekNext() != null) {
            if (!peekNext().reInvoke()) {
                // TODO: Something wrong with the undo stack. How to resolve?
                return false;
            }
            index++;
            return true;
        }

        return false;
    }

    private boolean getIndexIsValid() {
        return isValidIndex(index);
    }

    private boolean isValidIndex(int index) {
        return index >= 0 && index < commands.size();
    }

    private Command peekNext() {
        if (index < getLastCommandIndex() && isValidIndex(index + 1)) {
            return commands.get(index + 1);
        }
        return null;
    }

    public Command peek() {
        return getIndexIsValid() ? commands.get(index) : null;
    }
}
