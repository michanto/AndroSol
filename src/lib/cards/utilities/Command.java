package lib.cards.utilities;

public interface Command {
    // Localized string describing command for undo menu.
    String getName();

    // Does running this command result in the clearing of the undo stacks?
    boolean getClearsUndoStack();

    // Action that can be done.
    boolean invoke();

    // Re-invoke an action that has been undone.
    boolean reInvoke();

    // Undo the above action.
    boolean undo();
}
