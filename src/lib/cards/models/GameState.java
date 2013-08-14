package lib.cards.models;

import java.util.ArrayList;
import java.util.List;

public class GameState {
    // Rules
    private GameProperties gameProperties;

    // State
    private int gameNumber;
    private int oldScore;

    // Cards
    private List<CardState> stock;
    private List<CardState> waste;
    private List<List<CardState>> freeCells;
    private List<List<CardState>> tableaus;
    private List<List<CardState>> foundations;

    public GameState(Game game) {
        setGameProperties(game.getGameProperties());
        setGameNumber(game.getGameNumber());

        // Extract all the card IDs from the stacks in the game and save those.
        // Cards
        setStock(GameState.stackState(game.getStock()));
        setWaste(GameState.stackState(game.getWaste()));
        setFreeCells(GameState.stackCollectionState(game.getFreeCells()));
        setTableaus(GameState.stackCollectionState(game.getTableaus()));
        setFoundations(GameState.stackCollectionState(game.getFoundations()));

        // State
        setOldScore(game.getOldScore());
    }

    public GameProperties getGameProperties() {
        return gameProperties;
    }

    public void setGameProperties(GameProperties gameProperties) {
        this.gameProperties = gameProperties;
    }

    public int getGameNumber() {
        return gameNumber;
    }

    public void setGameNumber(int gameNumber) {
        this.gameNumber = gameNumber;
    }

    public int getOldScore() {
        return oldScore;
    }

    public void setOldScore(int oldScore) {
        this.oldScore = oldScore;
    }

    public List<CardState> getStock() {
        return stock;
    }

    public void setStock(List<CardState> stock) {
        this.stock = stock;
    }

    public List<CardState> getWaste() {
        return waste;
    }

    public void setWaste(List<CardState> waste) {
        this.waste = waste;
    }

    public List<List<CardState>> getFreeCells() {
        return freeCells;
    }

    public void setFreeCells(List<List<CardState>> freeCells) {
        this.freeCells = freeCells;
    }

    public List<List<CardState>> getTableaus() {
        return tableaus;
    }

    public void setTableaus(List<List<CardState>> tableaus) {
        this.tableaus = tableaus;
    }

    public List<List<CardState>> getFoundations() {
        return foundations;
    }

    public void setFoundations(List<List<CardState>> foundations) {
        this.foundations = foundations;
    }

    // Helpers
    public static List<CardState> stackState(Iterable<? extends Card> stack) {
        List<CardState> stackState = new ArrayList<CardState>();
        if (stack == null) {
            return stackState;
        }
        for (Card card : stack) {
            CardState cardState = new CardState();
            cardState.setId(card.getId());
            cardState.setFaceUp(card.isFaceUp());
        }

        return stackState;
    }

    public static List<List<CardState>> stackCollectionState(
            Iterable<? extends CardStack> collection) {
        List<List<CardState>> stacksState = new ArrayList<List<CardState>>();
        if (collection == null) {
            return stacksState;
        }

        for (Iterable<? extends Card> stack : collection) {
            stacksState.add(stackState(stack));
        }
        return stacksState;
    }
}
