package lib.cards.models;

import java.util.ArrayList;

public abstract class EventHandler<TEventInterface, TEventObject> {
    private ArrayList<TEventInterface> listeners = new ArrayList<TEventInterface>();

    public void add(TEventInterface listener) {
        listeners.add(listener);
    }

    public void remove(TEventInterface listener) {
        listeners.remove(listener);
    }

    public boolean hasListeners() {
        return listeners.size() > 0;
    }

    public void notifyListeners(TEventObject eventObject) {
        for (TEventInterface listener : listeners) {
            notifyListener(listener, eventObject);
        }
    }

    protected abstract void notifyListener(TEventInterface listener,
            TEventObject eventObject);
}
