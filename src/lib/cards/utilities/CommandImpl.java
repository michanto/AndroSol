package lib.cards.utilities;

public abstract class CommandImpl<TState> implements Command {
    private TState state;

    // State that can be persisted by the application, if necessary.
    public TState getState() {
        return state;
    }

    public void setState(TState state) {
        this.state = state;
    }

    // Does running this command result in the clearing of the undo stacks?
    @Override
    public boolean getClearsUndoStack() {
        return false;
    }

    // Re-invoke an action that has been undone. Default is to call Invoke
    // again.
    @Override
    public boolean reInvoke() {
        return invoke();
    }

}
