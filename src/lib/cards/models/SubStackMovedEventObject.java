package lib.cards.models;

@SuppressWarnings("serial")
public class SubStackMovedEventObject extends GameEventObject {
    SubStackMovedEventObject(Game game, SubStack source, CardStack target) {
        super(game);
        this.source = source;
        this.target = target;
    }

    private SubStack source;
    private CardStack target;

    public SubStack getSource() {
        return source;
    }

    public CardStack getTarget() {
        return target;
    }
}
