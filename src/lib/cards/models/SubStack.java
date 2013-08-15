package lib.cards.models;

public class SubStack {
    public SubStack(CardStack stack, Card card) {
        int idx = stack.indexOf(card);
        if (idx != -1) {
            Init(stack, stack.size() - idx);
        }
    }

    public SubStack(CardStack stack, int size) {
        Init(stack, size);
    }

    private void Init(CardStack stack, int size) {
        // Validate substack exists.
        if (stack.size() < size) {
            return;
            // throw new Exception("Bad sub stack size.");
        }

        this.stack = stack;
        this.size = size;

        cards = new CardStack();
        cards.addAll(stack.subList(stack.size() - size, stack.size()));
    }

    private CardStack stack;

    public boolean isValid() {
        return stack != null;
    }

    public CardStack getStack() {
        return stack;
    }

    public int getSize() {
        return size;
    }

    public CardStack getCards() {
        return cards;
    }

    boolean IsValid() {
        // TODO: containsAll probably not enough.
        return getStack().subList(getStack().size() - getSize(),
                getStack().size()).containsAll(getCards());
    }

    private int size;
    private CardStack cards;
}
