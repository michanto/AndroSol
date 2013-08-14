package lib.cards.models;

import java.util.Random;

@SuppressWarnings("serial")
public class Stock extends CardStack {
    private int numberOfDecks;

    public int getNumberOfDecks() {
        return numberOfDecks;
    }

    public void setNumberOfDecks(int numberOfDecks) {
        this.numberOfDecks = numberOfDecks;
    }

    public int getNumberOfCards() {
        return numberOfDecks * Card.CARDS_PER_DECK;
    }

    public Stock() {
        super(Card.CARDS_PER_DECK);
        numberOfDecks = 1;
        InitDeck();
    }

    public Stock(int numberOfDecks) {
        super(numberOfDecks * Card.CARDS_PER_DECK);
        this.numberOfDecks = numberOfDecks;
        InitDeck();
    }

    public void InitDeck() {
        this.ensureCapacity(getNumberOfCards());
        for (int i = 0; i < getNumberOfCards(); i++) {
            add(new Card(i));
        }
    }

    // Can't move cards back to the stock.
    @Override
    boolean canFillEmptyStackWith(Card card) {
        return false;
    }

    @Override
    boolean couldBuildStackWith(Card topCard, Card card) {
        return false;
    }

    private int seed;

    public int getSeed() {
        return seed;
    }

    public void setSeed(int seed) {
        this.seed = seed;
    }

    public int shuffle() {
        return shuffle(new Random().nextInt());
    }

    public int shuffle(int seed) {
        setSeed(seed);
        Random rand = new Random(seed);
        shuffle(rand);
        return seed;

    }

    private void shuffle(Random rand) {
        simpleShuffle(rand);
    }

    private void simpleShuffle(Random rand) {
        int n = size(); // The number of items left to shuffle (loop invariant).
        while (n > 1) {
            int k = rand.nextInt(n); // 0 <= k < n.
            n--; // n is now the last pertinent index;
            Card temp = this.get(n); // swap array[n] with array[k] (does
                                     // nothing if k == n).
            this.set(n, get(k));
            set(k, temp);
        }
    }
}
