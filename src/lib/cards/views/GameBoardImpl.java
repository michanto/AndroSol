package lib.cards.views;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import lib.cards.controllers.GameController;
import lib.cards.models.Card;
import lib.cards.models.CardStack;
import lib.cards.models.CardsMovedEventObject;
import lib.cards.models.FreeCell;
import lib.cards.models.Game;
import lib.cards.models.Game.CardsMovedListener;
import lib.cards.models.Game.NewGameListener;
import lib.cards.models.Game.RestoreGameListener;
import lib.cards.models.Game.SubStackMovedListener;
import lib.cards.models.GameEventObject;
import lib.cards.models.GameState;
import lib.cards.models.SubStack;
import lib.cards.models.SubStackMovedEventObject;
import lib.cards.utilities.CollectionUtils;
import lib.cards.utilities.Point;

public abstract class GameBoardImpl<TTexture> extends GameBoardMetrics
        implements GameBoard {
    protected GameBoardImpl() {
        game = new Game();
        game.getNewGameEvent().add(new NewGameListener() {
            @Override
            public void onNewGameAction(GameEventObject game) {
                GameBoardImpl.this.onNewGameAction(game);
            }
        });
        game.getRestoreGameEvent().add(new RestoreGameListener() {
            @Override
            public void onRestoreGameAction(GameEventObject game) {
                GameBoardImpl.this.onRestoreGameAction(game);
            }

        });
        game.getSubStackMovedEvent().add(new SubStackMovedListener() {
            @Override
            public void onSubStackMoved(SubStackMovedEventObject args) {
                GameBoardImpl.this.onSubStackMoved(args);
            }
        });
        game.getCardsMovedEvent().add(new CardsMovedListener() {
            @Override
            public void onCardsMoved(CardsMovedEventObject args) {
                GameBoardImpl.this.onCardsMoved(args);
            }
        });
        gameController = new GameController(this);
    }

    private SpriteAddedEventHandler spriteAddedEventHandler = new SpriteAddedEventHandler();
    private SpriteRemovedEventHandler spriteRemovedEventHandler = new SpriteRemovedEventHandler();
    private SpriteSelectedEventHandler spriteSelectedEventHandler = new SpriteSelectedEventHandler();
    private SpriteDefaultActionEventHandler spriteDefaultActionEventHandler = new SpriteDefaultActionEventHandler();

    private Game game;

    @Override
    public Game getGame() {
        return game;
    }

    @Override
    public SpriteAddedEventHandler getSpriteAddedEvent() {
        return spriteAddedEventHandler;
    }

    @Override
    public SpriteRemovedEventHandler getSpriteRemovedEvent() {
        return spriteRemovedEventHandler;
    }

    @Override
    public SpriteSelectedEventHandler getSpriteSelectedEvent() {
        return spriteSelectedEventHandler;
    }

    @Override
    public SpriteDefaultActionEventHandler getSpriteDefaultActionEvent() {
        return spriteDefaultActionEventHandler;
    }

    protected abstract void doMoveCardAnimation(CardStack cards, Point delta);

    protected abstract void doMoveCardAnimation(GameState oldState,
            GameState newState);

    public void onCardsMoved(CardsMovedEventObject args) {
        doMoveCardAnimation(args.getOldState(), args.getNewState());
    }

    public void onSubStackMoved(SubStackMovedEventObject args) {
        // Get position of first card removed from the source stack.
        Point oldPosition = CardPosition(args.getSource().getStack(), args
                .getSource().getStack().size());
        Point newPosition = CardPosition(args.getTarget(), args.getTarget()
                .size() - args.getSource().getSize());
        Point delta = new Point(newPosition.x - oldPosition.x, newPosition.y
                - oldPosition.y);

        doMoveCardAnimation(args.getSource().getCards(), delta);
    }

    public GameController gameController;

    protected CardSprite addCardSprite(Card card, String name) {
        CardSprite sprite = addCardSprite(card, getDeck().cardTexture(card));
        sprite.setName(name);
        onSpriteAdded(sprite);
        return sprite;
    }

    protected StackSprite addStackSprite(CardStack stack, String name) {
        StackSprite sprite = addStackSprite(stack, getDeck().getBlankTexture());
        sprite.setName(name);
        onSpriteAdded(sprite);
        return sprite;
    }

    private void onSpriteAdded(Sprite sprite) {
        spriteAddedEventHandler.fireOnSpriteAdded(this, sprite);
    }

    private void onSpriteRemoved(Sprite sprite) {
        spriteRemovedEventHandler.fireOnSpriteRemoved(this, sprite);
    }

    public abstract StackSprite addStackSprite(CardStack stack,
            TTexture cardImage);

    public abstract CardSprite addCardSprite(Card card, TTexture cardImage);

    public void deleteSprites(Iterable<? extends Sprite> sprites) {
        if (sprites == null) {
            return;
        }
        for (Sprite sprite : sprites) {
            deleteSprite(sprite);
            onSpriteRemoved(sprite);
        }
    }

    public abstract void deleteSprite(Sprite sprite);

    public void termBoard() {
        deleteSprites(getSprites());
    }

    public List<Sprite> getSprites() {
        final Comparator<Sprite> cmp = new Comparator<Sprite>() {
            @Override
            public int compare(Sprite s1, Sprite s2) {
                if (s1.getZOrder() > s2.getZOrder()) {
                    return 1;
                } else if (s1.getZOrder() < s2.getZOrder()) {
                    return -1;
                }
                return 0;
            }
        };

        List<Sprite> sprites = new ArrayList<Sprite>();
        if (stockSprite != null)
            sprites.add(stockSprite);
        if (wasteSprite != null)
            sprites.add(wasteSprite);
        if (freeCellSprites != null)
            sprites.addAll(freeCellSprites);
        if (tableauSprites != null)
            sprites.addAll(tableauSprites);
        if (foundationSprites != null)
            sprites.addAll(foundationSprites);
        if (cardSprites != null)
            sprites.addAll(cardSprites);

        Collections.sort(sprites, cmp);
        return sprites;
    }

    public void initBoard() {
        termBoard();
        game.deal();
        remakeSprites();
        layoutBoard();
    }

    private void remakeSprites() {
        // Add card sprites
        deleteSprites(cardSprites);
        // Remake card sprites.
        cardSprites = new ArrayList<CardSprite>(game.getGameProperties()
                .getNumberOfCards());
        for (Integer i = 0; i < game.getGameProperties().getNumberOfCards(); i++) {
            Card card = game.findCard(i).card;
            cardSprites.add(addCardSprite(card, "Card_" + i.toString() + "_"
                    + card.getName()));
        }

        // Add base images for each stack, below the cards.
        deleteSprites(stockSprite == null ? null : CollectionUtils
                .toList(stockSprite));
        stockSprite = addStackSprite(game.getStock(), "Stock_0");

        deleteSprites(wasteSprite == null ? null : CollectionUtils
                .toList(wasteSprite));
        wasteSprite = addStackSprite(game.getWaste(), "Waste_0");

        deleteSprites(foundationSprites);
        foundationSprites = new ArrayList<StackSprite>(game.getFoundations()
                .size());
        for (Integer i = 0; i < game.getFoundations().size(); i++) {
            foundationSprites.add(addStackSprite(game.getFoundations().get(i),
                    "Foundation_" + i.toString()));
        }

        deleteSprites(tableauSprites);
        tableauSprites = new ArrayList<StackSprite>(game.getTableaus().size());
        for (Integer i = 0; i < game.getTableaus().size(); i++) {
            tableauSprites.add(addStackSprite(game.getTableaus().get(i),
                    "Tableau_" + i.toString()));
        }

        deleteSprites(freeCellSprites);
        freeCellSprites = new ArrayList<StackSprite>(game.getFreeCells().size());
        for (Integer i = 0; i < game.getFreeCells().size(); i++) {
            freeCellSprites.add(addStackSprite(game.getFreeCells().get(i),
                    "FreeCell_" + i.toString()));
        }
    }

    public void layoutBoard() {
        // Debug.WriteLine("layoutBoard");
        int cardOrder = 0;
        cardOrder = layoutStock(cardOrder);
        cardOrder = layoutWaste(cardOrder);
        cardOrder = layoutTableaus(cardOrder);
        cardOrder = layoutFoundations(cardOrder);
        cardOrder = layoutFreeCells(cardOrder);
    }

    private int layoutStock(int zOrder) {
        stockSprite.setVisibile(usesStock());
        if (!usesStock()) {
            return zOrder;
        }

        Point position = new Point(getStockPosition().x, getStockPosition().y);
        stockSprite.setPosition(position);
        stockSprite.setZOrder(++zOrder);

        for (int j = 0; j < game.getStock().size(); j++) {
            Card card = game.getStock().get(j);
            layoutCard(card, position, ++zOrder);
        }
        return zOrder;
    }

    private int layoutWaste(int zOrder) {
        wasteSprite.setVisibile(usesWaste());
        if (!usesWaste()) {
            return zOrder;
        }
        Point position = new Point(getWastePosition().x, getWastePosition().y);
        wasteSprite.setPosition(position);
        wasteSprite.setZOrder(++zOrder);

        for (int j = 0; j < game.getWaste().size(); j++) {
            Card card = game.getWaste().get(j);
            layoutCard(card, position, ++zOrder);
        }

        return zOrder;
    }

    private int layoutFoundations(int zOrder) {
        for (int i = 0; i < game.getFoundations().size(); i++) {
            Point position = getFoundationPosition(i);
            foundationSprites.get(i).setPosition(position);
            foundationSprites.get(i).setZOrder(++zOrder);
            for (int j = 0; j < game.getFoundations().get(i).size(); j++) {
                Card card = game.getFoundations().get(i).get(j);
                layoutCard(card, position, ++zOrder);
            }
        }
        return zOrder;
    }

    public int layoutTableaus(int zOrder) {
        for (int i = 0; i < game.getTableaus().size(); i++) {
            Point position = getTableauPosition(i, 0);
            tableauSprites.get(i).setPosition(position);
            tableauSprites.get(i).setZOrder(++zOrder);
            for (int j = 0; j < game.getTableaus().get(i).size(); j++) {
                Card card = game.getTableaus().get(i).get(j);
                position = getTableauPosition(i, j);
                layoutCard(card, position, ++zOrder);
            }
        }
        return zOrder;
    }

    public int layoutFreeCells(int zOrder) {
        for (int i = 0; i < game.getGameProperties().getNumberOfFreeCells(); i++) {
            Point position = getFreeCellPosition(i);
            freeCellSprites.get(i).setPosition(position);
            freeCellSprites.get(i).setZOrder(++zOrder);
            for (int j = 0; j < game.getFreeCells().get(i).size(); j++) {
                FreeCell freeCell = game.getFreeCells().get(i);
                Card card = freeCell.get(j);
                layoutCard(card, position, ++zOrder);
            }
        }
        return zOrder;
    }

    protected abstract void layoutCard(Card card, Point position, int zIndex);

    public CardSprite getCard(int cardId) {
        return cardSprites.get(cardId);
    }

    public Sprite GetSprite(String spriteName) {
        // Which stack is it in?
        String[] splitName = spriteName.split("_");
        int index = Integer.parseInt(splitName[1]);

        if (splitName[0].equals("Stock"))
            return stockSprite;
        if (splitName[0].equals("Waste"))
            return wasteSprite;
        if (splitName[0].equals("FreeCell"))
            return freeCellSprites.get(index);
        if (splitName[0].equals("Tableau"))
            return tableauSprites.get(index);
        if (splitName[0].equals("Foundation"))
            return foundationSprites.get(index);
        if (splitName[0].equals("Card"))
            return cardSprites.get(index);

        return null;
    }

    public CardStack getStack(Sprite sprite) {
        return getStack(sprite.getName());
    }

    public SubStack getSubStack(String spriteName) {
        // Which stack is it in?
        String[] splitName = spriteName.split("_");
        int cardNumber = Integer.parseInt(splitName[1]);

        if (splitName[0].equals("Card")) {
            // What stack is this card in? What position in the stack is it?
            Game.FindCardResult fcr = game.findCard(cardNumber);
            if (null != fcr) {
                return new SubStack(fcr.stack, fcr.stack.size() - fcr.position);
            }
        }

        return null;
    }

    public CardStack getStack(String spriteName) {
        // Which stack is it in?
        String[] splitName = spriteName.split("_");
        int cardNumber = Integer.parseInt(splitName[1]);

        if (splitName[0].equals("Stock"))
            return game.getStock();
        if (splitName[0].equals("Waste"))
            return game.getWaste();
        if (splitName[0].equals("FreeCell"))
            return game.getFreeCells().get(cardNumber);
        if (splitName[0].equals("Tableau"))
            return game.getTableaus().get(cardNumber);
        if (splitName[0].equals("Foundation"))
            return game.getFoundations().get(cardNumber);
        if (splitName[0].equals("Card")) {
            // What stack is this card in? What position in the stack is it?
            Game.FindCardResult fcr = game.findCard(cardNumber);
            if (null != fcr) {
                return fcr.stack;
            }
        }

        return null;
    }

    public void doSpriteSelected(Sprite sprite) {
        spriteSelectedEventHandler.fireOnSpriteSelected(this, sprite);
    }

    public void doSpriteDefaultAction(Sprite sprite) {
        spriteDefaultActionEventHandler.fireOnSpriteDefaultAction(this, sprite);
    }

    public void onNewGameAction(GameEventObject args) {
        initBoard();
    }

    public void onRestoreGameAction(GameEventObject args) {
        termBoard();
        remakeSprites();
        layoutBoard();
    }

    private Deck<TTexture> deck;

    @Override
    public DeckMetrics getDeckMetrics() {
        return deck;
    }

    public Deck<TTexture> getDeck() {
        return deck;
    }

    public void setDeck(Deck<TTexture> deck) {
        this.deck = deck;
    }

    protected StackSprite stockSprite;
    protected StackSprite wasteSprite;
    protected List<StackSprite> freeCellSprites;
    protected List<StackSprite> tableauSprites;
    protected List<StackSprite> foundationSprites;
    protected List<CardSprite> cardSprites;
}
