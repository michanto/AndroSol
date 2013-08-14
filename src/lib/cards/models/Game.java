package lib.cards.models;

import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;

public class Game {
    public Game() {
        term();
        newGameEventHandler = new NewGameEventHandler();
        scoreChangedEventHandler = new ScoreChangedEventHandler();
        gameOverEventHandler = new GameOverEventHandler();
        restoreGameEventHandler = new RestoreGameEventHandler();
        cardsMovedEventHandler = new CardsMovedEventHandler();
        subStackMovedEventHandler = new SubStackMovedEventHandler();
    }

    public Game(GameProperties gameProperties) {
        this();
        init(gameProperties, null);
    }

    private int oldScore;

    private int gameNumber;
    private GameProperties gameProperties;

    private int numberOfDeals;
    private int numberOfReDeals;

    private List<FreeCell> freeCells;
    private List<Tableau> tableaus;
    private List<Foundation> foundations;
    private Stock stock;
    private Waste waste;

    public interface NewGameListener extends EventListener {
        void onNewGameAction(GameEventObject game);
    };

    public interface RestoreGameListener extends EventListener {
        void onRestoreGameAction(GameEventObject game);
    }

    public interface CardsMovedListener extends EventListener {
        void onCardsMoved(CardsMovedEventObject args);
    }

    public interface ScoreChangedListener extends EventListener {
        void onScoreChanged(ScoreChangedEventObject args);
    }

    public interface SubStackMovedListener extends EventListener {
        void onSubStackMoved(SubStackMovedEventObject args);
    }

    public interface GameOverListener extends EventListener {
        void onGameOver(GameOverEventObject args);
    }

    // Events
    final private NewGameEventHandler newGameEventHandler;
    final private RestoreGameEventHandler restoreGameEventHandler;
    final private CardsMovedEventHandler cardsMovedEventHandler;
    final private SubStackMovedEventHandler subStackMovedEventHandler;
    final private ScoreChangedEventHandler scoreChangedEventHandler;
    final private GameOverEventHandler gameOverEventHandler;

    public NewGameEventHandler getNewGameEvent() {
        return newGameEventHandler;
    }

    public RestoreGameEventHandler getRestoreGameEvent() {
        return restoreGameEventHandler;
    }

    public CardsMovedEventHandler getCardsMovedEvent() {
        return cardsMovedEventHandler;
    }

    public SubStackMovedEventHandler getSubStackMovedEvent() {
        return subStackMovedEventHandler;
    }

    private void onNewGameAction() {
        newGameEventHandler.fireOnNewGame(this);
    }

    public void newGame(GameProperties props) {
        init(props, null);
        onNewGameAction();
    }

    public void newGame(GameProperties props, int gameNumber) {
        init(props, gameNumber);
        onNewGameAction();
    }

    public boolean restoreGameState(GameState savedGame) {
        try {
            init(savedGame.getGameProperties(), null);
            gameNumber = savedGame.getGameNumber();
            oldScore = savedGame.getOldScore();

            setupBoard();
            stock.init(savedGame.getStock());
            waste.init(savedGame.getWaste());

            for (int i = 0; i < gameProperties.getNumberOfFreeCells(); i++) {
                freeCells.get(i).init(savedGame.getFreeCells().get(i));
            }

            for (int i = 0; i < gameProperties.getNumberOfTableauPiles(); i++) {
                tableaus.get(i).init(savedGame.getTableaus().get(i));
            }

            for (int i = 0; i < gameProperties.getNumberOfFoundations(); i++) {
                foundations.get(i).init(savedGame.getFoundations().get(i));
            }
            restoreGameEventHandler.fireOnRestoreGame(this);
        } catch (Exception e) {
            return false;
        }

        return true;
    }

    public boolean applyGameState(GameState undoState) {
        if (undoState == null) {
            return false;
        }
        GameState current = new GameState(this);
        if (restoreGameState(undoState)) {
            onCardsMoved(current, undoState);
            return true;
        }
        return false;

    }

    protected void onCardsMoved(GameState oldState, GameState newState) {
        cardsMovedEventHandler.fireOnCardsMoved(this, oldState, newState);
        checkScoreChanged();
    }

    public int getGameNumber() {
        return gameNumber;
    }

    public GameProperties getGameProperties() {
        return gameProperties;
    }

    public int getNumberOfDeals() {
        return numberOfDeals;
    }

    public int getNumberOfReDeals() {
        return numberOfReDeals;
    }

    public List<FreeCell> getFreeCells() {
        return freeCells;
    }

    public List<Tableau> getTableaus() {
        return tableaus;
    }

    public List<Foundation> getFoundations() {
        return foundations;
    }

    public Stock getStock() {
        return stock;
    }

    public Waste getWaste() {
        return waste;
    }

    public int getScore() {
        int score = 0;
        if (foundations != null) {
            for (Foundation foundation : foundations) {
                score += foundation.size();
            }
        }
        return score;
    }

    public int getOldScore() {
        return oldScore;
    }

    public CardStack findStack(CardStackId cardStackId) {
        for (CardStack stack : getStacks()) {
            if (stack.getCardStackId() == cardStackId) {
                return stack;
            }
        }
        return null;
    }

    private void init(GameProperties gameProperties, Integer gameNumber) {
        term();

        this.gameProperties = gameProperties;

        setupBoard();

        if (gameNumber == null) {
            this.gameNumber = stock.shuffle();
        } else {
            this.gameNumber = stock.shuffle(gameNumber);
        }

        checkScoreChanged();
    }

    private void term() {
        numberOfDeals = 0;
        numberOfReDeals = 0;
        oldScore = -1;

        gameProperties = null;
        freeCells = new ArrayList<FreeCell>();
        tableaus = new ArrayList<Tableau>();
        foundations = new ArrayList<Foundation>();
        stock = new Stock();
        waste = new Waste();
    }

    private void setupBoard() {
        if (stock == null
                || stock.getNumberOfDecks() != gameProperties
                        .getNumberOfDecks()) {
            stock = new Stock(gameProperties.getNumberOfDecks());
        }
        if (waste == null) {
            waste = new Waste();
        }
        if (tableaus == null
                || tableaus.size() != gameProperties.getNumberOfTableauPiles()
                || tableaus.get(0).getTableauSequence() != gameProperties
                        .getTableauGroupSequence()
                || tableaus.get(0).getEmptyTableauPileFilledBy() != gameProperties
                        .getEmptyTableauPileFilledBy()) {
            tableaus = new ArrayList<Tableau>();
            for (int i = 0; i < gameProperties.getNumberOfTableauPiles(); i++) {
                Tableau t = new Tableau(i,
                        gameProperties.getTableauBuildingMethod(),
                        gameProperties.getEmptyTableauPileFilledBy());
                tableaus.add(t);
            }
        }
        if (freeCells == null
                || freeCells.size() != gameProperties.getNumberOfFreeCells()) {
            freeCells = new ArrayList<FreeCell>();
            for (int i = 0; i < gameProperties.getNumberOfFreeCells(); i++) {
                freeCells.add(new FreeCell(i));
            }
        }
        if (foundations == null
                || foundations.size() != gameProperties
                        .getNumberOfFoundations()
                || foundations.get(0).getFoundationSequence() != gameProperties
                        .getFoundationBuildingMethod()
                || foundations.get(0).getFoundationBaseCard() != gameProperties
                        .getFoundationBaseCard()) {
            foundations = new ArrayList<Foundation>();
            for (int i = 0; i < gameProperties.getNumberOfFoundations(); i++) {
                Foundation f = new Foundation(i,
                        gameProperties.getFoundationBuildingMethod(),
                        gameProperties.getFoundationBaseCard());
                foundations.add(f);
            }
        }
        checkScoreChanged();
    }

    void checkScoreChanged() {
        if (oldScore != getScore()) {
            scoreChangedEventHandler.fireOnScoreChanged(this, oldScore,
                    getScore());
            oldScore = getScore();
        }
        checkGameOver();
    }

    protected void checkGameOver() {
        boolean won = getWon();
        if (won || getNoMoreMoves()) {
            gameOverEventHandler.fireOnGameOver(this, won);
        }
    }

    protected void onSubStackMoved(SubStack from, CardStack to) {
        subStackMovedEventHandler.fireOnSubStackMoved(this, from, to);
        checkScoreChanged();
    }

    public boolean getWon() {
        return getScore() == gameProperties.getNumberOfDecks()
                * Card.CARDS_PER_DECK;
    }

    public boolean getNoMoreMoves() {
        return !getAnyMoreMoves();
    }

    public boolean getAnyMoreMoves() {
        // TODO: Implement
        return true;
    }

    private Iterable<CardStack> getStacks() {
        List<CardStack> stacks = new ArrayList<CardStack>();
        stacks.add(stock);
        stacks.add(waste);
        for (FreeCell f : freeCells) {
            stacks.add(f);
        }
        for (Tableau t : tableaus) {
            stacks.add(t);
        }
        for (Foundation f : foundations) {
            stacks.add(f);
        }
        return stacks;
    }

    public CardStack stackFromId(CardStackId cardStackId) {
        for (CardStack s : getStacks()) {
            if (s.getCardStackId() == cardStackId) {
                return s;
            }
        }
        return null;
    }

    public class FindCardResult {
        public Card card;
        public CardStack stack;
        public Integer position;
    }

    public FindCardResult findCard(int cardNumber) {
        FindCardResult result = null;
        result = findCard(cardNumber, stock);
        if (result != null) {
            return result;
        }

        result = findCard(cardNumber, waste);
        if (result != null) {
            return result;
        }

        for (Tableau tableau : tableaus) {
            result = findCard(cardNumber, tableau);
            if (result != null) {
                return result;
            }
        }

        for (Foundation foundation : foundations) {
            result = findCard(cardNumber, foundation);
            if (result != null) {
                return result;
            }
        }

        for (FreeCell freeCell : freeCells) {
            result = findCard(cardNumber, freeCell);
            if (result != null) {
                return result;
            }

        }
        return null;
    }

    public FindCardResult findCard(int cardNumber, CardStack stack) {
        for (int i = 0; i < stack.size(); i++) {
            if (stack.get(i).getId() == cardNumber) {
                FindCardResult result = new FindCardResult();
                result.card = stack.get(i);
                result.position = i;
                result.stack = stack;
                return result;
            }
        }

        return null;
    }

    public void deal() {
        if (numberOfDeals == 0) {
            dealInitial();
        } else {
            dealAgain();
        }
    }

    public void dealInitial() {
        int tableausToFill = tableaus.size();
        // Deal off the Stock to the Tableaus
        while (tableaus.get(tableaus.size() - 1).size() < gameProperties
                .getNumberOfCardsPerTableau()) {
            if (stock.size() == 0) {
                break;
            }
            for (int i = 0; i < tableausToFill; i++) {
                Card card = stock.pop();
                if (card == null) {
                    break;
                }

                if (gameProperties.getTableauType() == TableauType.KLONDIKE) {
                    tableaus.get(tableaus.size() - tableausToFill + i)
                            .add(card);
                } else {
                    tableaus.get(i).add(card);
                }

                if (gameProperties.getTableauType() == TableauType.FORTY_THIEVES) {
                    card.setFaceUp(true);
                }
            }
            if (gameProperties.getTableauType() == TableauType.KLONDIKE) {
                tableaus.get(tableaus.size() - tableausToFill).peek()
                        .setFaceUp(true);
                tableausToFill--;
            }
        }
        // Debug.WriteLine("Game number: " + Stock.Seed.ToString());
        // DebugDump(Tableaus);
    }

    public void dealAgain() {
        if (stock.size() == 0) {
            // Check if redeals are allowed and copy the waste to the stock.
            switch (gameProperties.getRedealsAllowed()) {
            case NONE:
                return;
            case UNLIMITED:
                copyWasteToStock();
                return;
            default:
                if (getNumberOfReDeals() <= gameProperties.getRedealsAllowed()
                        .getValue()) {
                    copyWasteToStock();
                }
                return;
            }
        }

        switch (gameProperties.getNumberOfCardsDealtFromStock()) {
        case ONE_TO_WASTE:
            dealOneCardToWaste();
            break;
        case THREE_TO_WASTE:
            dealOneCardToWaste();
            dealOneCardToWaste();
            dealOneCardToWaste();
            break;
        case ONE_TO_EACH_TABLEAU: {
            // TODO: Deal one to each tableau
        }
            break;
        }
    }

    private void dealOneCardToWaste() {
        if (stock.size() > 0) {
            SubStack subStack = new SubStack(stock, 1);
            moveSubStack(subStack, waste);
        }
    }

    public void copyWasteToStock() {
        SubStack subStack = new SubStack(waste, waste.size());
        for (Card card : waste) {
            stock.add(card);
            card.setFaceUp(false);
        }
        waste.clear();

        numberOfReDeals++;

        onSubStackMoved(subStack, stock);
    }

    public boolean moveSubStack(SubStack source, CardStack target) {
        boolean isDeal = source.getStack().getClass() == Stock.class
                && target.getClass() == Waste.class;
        if (!canMoveSubStack(source, target)) {
            return false;
        }
        /*
         * Debug.Write(string.Format("Moved [{0}] from {1}{2} [{3}] to {4}{5} [{6}]"
         * , source.Cards.DumpString(), source.Stack.StackType,
         * source.Stack.Index, source.Stack.DumpString(), target.StackType,
         * target.Index, target.DumpString()));
         */
        for (Card card : source.getCards()) {
            target.push(card);
        }

        source.getStack().remove(
                source.getStack().subList(
                        source.getStack().size() - source.getSize(),
                        source.getSize()));
        if (source.getStack().peek() != null && !isDeal) {
            source.getStack().peek().setFaceUp(true);
        }

        if (isDeal) {
            target.peek().setFaceUp(true);
        }

        onSubStackMoved(source, target);

        /*
         * Debug.WriteLine(" result {0}{1} [{2}] and {3}{4} [{5}]",
         * source.Stack.StackType, target.Index, source.Stack.DumpString(),
         * target.StackType, target.Index, target.DumpString());
         */
        return true;
    }

    public boolean canMoveSubStack(SubStack source, CardStack target) {
        // Can only move from Stock to Waste.
        if (source.getStack().getClass() == Stock.class
                && !(target.getClass() == Waste.class))
            return false;

        // Can only move from Stock to Waste.
        if (target == waste && !(source.getStack() == stock))
            return false;

        // Can't move from foundations
        if (source.getStack().getClass() == Foundation.class)
            return false;

        // Can't move cards to Stock (CardStock.CanPush takes care of this rule)

        // Handle multiple card move rules.
        if (source.getSize() > 1) {
            if (gameProperties.getMoveGroupsOfCardsAsAUnit() == MoveGroupsOfCardsAsAUnit.NO) {
                // See if there are enough free spaces to handle the move
                int numFreeCells = 0;
                for (FreeCell freeCell : freeCells) {
                    if (freeCell.peek() == null) {
                        numFreeCells++;
                    }
                }

                // Are there enough free spaces?
                if (gameProperties.getEmptyTableauPileFilledBy() == EmptyTableauPileFilledBy.ANY_CARD_IN_SEQUENCE) {
                    // Count empty tableaus as free cells
                    for (Tableau tableau : tableaus) {
                        if (tableau.peek() == null) {
                            numFreeCells++;
                        }
                    }
                }

                // I allow (at most) num free cells + 1
                if (source.getSize() > numFreeCells + 1) {
                    return false;
                }
            }
        }

        Card topCard = target.peek();
        for (Card card : source.getCards()) {
            if (topCard == null) {
                if (!target.canFillEmptyStackWith(card)) {
                    return false;
                }
            } else if (!target.couldBuildStackWith(topCard, card)) {
                return false;
            }
            topCard = card;

            if (gameProperties.getMoveGroupsOfCardsAsAUnit() == MoveGroupsOfCardsAsAUnit.YES) {
                // Only the first one has to work.
                break;
            }
        }
        return true;
    }

    public CardStack canMoveCardToStackCollection(
            List<? extends CardStack> stacks, Card card) {
        if (stacks == null) {
            return null;
        }

        for (int i = 0; i < stacks.size(); i++) {
            if (stacks.get(i).canPush(card)) {
                return stacks.get(i);
            }
        }
        return null;
    }
}
