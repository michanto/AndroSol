package lib.cards.controllers;

import java.util.List;

import lib.cards.models.CardStackId;
import lib.cards.models.CardState;

public class StackMoveState extends GameActionState {
    public CardStackId source;
    public CardStackId target;
    public List<CardState> cardsToMove;
}
