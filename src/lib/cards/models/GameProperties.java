package lib.cards.models;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

public class GameProperties {
    private String mName;
    private String mTitle;

    // Decks

    private int mNumberOfDecks;

    public int getNumberOfCards() {
        return getNumberOfDecks() * Card.CARDS_PER_DECK;
    }

    // Tableau Rules

    private TableauType mTableauType;

    private int mNumberOfTableauPiles; // 4 to 35

    private int mNumberOfCardsPerTableau; // 1 to 13

    private int mNumberOfFaceDownCards; // 0 to (NumberOfCardsPerTableau-1)

    private int mNumberOfFreeCells;

    public int getNumberOfFoundations() {
        return getNumberOfDecks() * 4;
    }

    private TableauSequence mTableauBuildingMethod;

    private EmptyTableauPileFilledBy mEmptyTableauPileFilledBy;

    private TableauSequence mTableauGroupSequence;

    // Foundation Rules

    private FoundationBaseCard mFoundationBaseCard;
    // public bool FoundationBaseDealtAtStart;

    private FoundationSequence mFoundationBuildingMethod;

    private MoveGroupsOfCardsAsAUnit mMoveGroupsOfCardsAsAUnit;
    private NumberOfCardsDealtFromStock mNumberOfCardsDealtFromStock;

    // Redeals
    private RedealsAllowed mRedealsAllowed;

    // Spider only
    private boolean mMakeLastDealComeOutEven;

    private boolean mRemoveCompletedSequencesFromTableau;

    // Other
    private boolean mWasteVisible;

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        this.mTitle = title;
    }

    public int getNumberOfDecks() {
        return mNumberOfDecks;
    }

    public void setNumberOfDecks(int numberOfDecks) {
        this.mNumberOfDecks = numberOfDecks;
    }

    public TableauType getTableauType() {
        return mTableauType;
    }

    public void setTableauType(TableauType tableauType) {
        this.mTableauType = tableauType;
    }

    public int getNumberOfTableauPiles() {
        return mNumberOfTableauPiles;
    }

    public void setNumberOfTableauPiles(int numberOfTableauPiles) {
        this.mNumberOfTableauPiles = numberOfTableauPiles;
    }

    public int getNumberOfCardsPerTableau() {
        return mNumberOfCardsPerTableau;
    }

    public void setNumberOfCardsPerTableau(int numberOfCardsPerTableau) {
        this.mNumberOfCardsPerTableau = numberOfCardsPerTableau;
    }

    public int getNumberOfFaceDownCards() {
        return mNumberOfFaceDownCards;
    }

    public void setNumberOfFaceDownCards(int numberOfFaceDownCards) {
        this.mNumberOfFaceDownCards = numberOfFaceDownCards;
    }

    public int getNumberOfFreeCells() {
        return mNumberOfFreeCells;
    }

    public void setNumberOfFreeCells(int numberOfFreeCells) {
        this.mNumberOfFreeCells = numberOfFreeCells;
    }

    public TableauSequence getTableauBuildingMethod() {
        return mTableauBuildingMethod;
    }

    public void setTableauBuildingMethod(TableauSequence tableauBuildingMethod) {
        this.mTableauBuildingMethod = tableauBuildingMethod;
    }

    public EmptyTableauPileFilledBy getEmptyTableauPileFilledBy() {
        return mEmptyTableauPileFilledBy;
    }

    public void setEmptyTableauPileFilledBy(
            EmptyTableauPileFilledBy emptyTableauPileFilledBy) {
        this.mEmptyTableauPileFilledBy = emptyTableauPileFilledBy;
    }

    public TableauSequence getTableauGroupSequence() {
        return mTableauGroupSequence;
    }

    public void setTableauGroupSequence(TableauSequence tableauGroupSequence) {
        this.mTableauGroupSequence = tableauGroupSequence;
    }

    public FoundationBaseCard getFoundationBaseCard() {
        return mFoundationBaseCard;
    }

    public void setFoundationBaseCard(FoundationBaseCard foundationBaseCard) {
        this.mFoundationBaseCard = foundationBaseCard;
    }

    public FoundationSequence getFoundationBuildingMethod() {
        return mFoundationBuildingMethod;
    }

    public void setFoundationBuildingMethod(
            FoundationSequence foundationBuildingMethod) {
        this.mFoundationBuildingMethod = foundationBuildingMethod;
    }

    public MoveGroupsOfCardsAsAUnit getMoveGroupsOfCardsAsAUnit() {
        return mMoveGroupsOfCardsAsAUnit;
    }

    public void setMoveGroupsOfCardsAsAUnit(
            MoveGroupsOfCardsAsAUnit moveGroupsOfCardsAsAUnit) {
        this.mMoveGroupsOfCardsAsAUnit = moveGroupsOfCardsAsAUnit;
    }

    public NumberOfCardsDealtFromStock getNumberOfCardsDealtFromStock() {
        return mNumberOfCardsDealtFromStock;
    }

    public void setNumberOfCardsDealtFromStock(
            NumberOfCardsDealtFromStock numberOfCardsDealtFromStock) {
        this.mNumberOfCardsDealtFromStock = numberOfCardsDealtFromStock;
    }

    public RedealsAllowed getRedealsAllowed() {
        return mRedealsAllowed;
    }

    public void setRedealsAllowed(RedealsAllowed redealsAllowed) {
        this.mRedealsAllowed = redealsAllowed;
    }

    public boolean getMakeLastDealComeOutEven() {
        return mMakeLastDealComeOutEven;
    }

    public void setMakeLastDealComeOutEven(boolean makeLastDealComeOutEven) {
        this.mMakeLastDealComeOutEven = makeLastDealComeOutEven;
    }

    public boolean getRemoveCompletedSequencesFromTableau() {
        return mRemoveCompletedSequencesFromTableau;
    }

    public void setRemoveCompletedSequencesFromTableau(
            boolean removeCompletedSequencesFromTableau) {
        this.mRemoveCompletedSequencesFromTableau = removeCompletedSequencesFromTableau;
    }

    public boolean getWasteVisible() {
        return mWasteVisible;
    }

    public void setWasteVisible(boolean wasteVisible) {
        this.mWasteVisible = wasteVisible;
    }

    public static GameProperties getGameFromName(String name) {
        Method[] methods = Method.class.getEnclosingMethod().getClass()
                .getDeclaredMethods();
        String methodName = "get" + name;
        for (Method m : methods) {
            if (m.getName() == methodName) {
                try {
                    return (GameProperties) m.invoke(null);
                } catch (Exception e) {
                    // Will return NULL in failure case.
                }
            }
        }
        return null;
    }

    public static List<GameProperties> getGames() {
        List<GameProperties> games = new ArrayList<GameProperties>();
        Method[] methods = GameProperties.class.getDeclaredMethods();
        for (Method m : methods) {
            if (m.getReturnType() == GameProperties.class
                    && Modifier.isStatic(m.getModifiers())) {
                try {
                    games.add((GameProperties) m.invoke(null));
                } catch (Exception e) {
                    // OK to eat errors here.
                    System.out.println("Game property not invoked correctly.");
                }
            }
        }
        return games;
    }

    public static GameProperties getFreeCell() {
        GameProperties g = new GameProperties();
        g.setName("FreeCell");
        g.setTitle("Free Cell");
        g.setNumberOfDecks(1);
        g.setWasteVisible(false);
        g.setTableauType(TableauType.FORTY_THIEVES);
        g.setNumberOfTableauPiles(8);
        g.setNumberOfCardsPerTableau(7);
        g.setNumberOfFaceDownCards(0);
        g.setNumberOfFreeCells(4);
        g.setTableauBuildingMethod(TableauSequence.BUILD_DOWN_IN_ALTERNATE_COLORS);
        g.setEmptyTableauPileFilledBy(EmptyTableauPileFilledBy.ANY_CARD_IN_SEQUENCE);
        g.setMoveGroupsOfCardsAsAUnit(MoveGroupsOfCardsAsAUnit.NO);

        g.setFoundationBaseCard(FoundationBaseCard.ACE);
        g.setFoundationBuildingMethod(FoundationSequence.BUILD_UP_IN_SAME_SUIT);

        g.setNumberOfCardsDealtFromStock(NumberOfCardsDealtFromStock.ONE_TO_EACH_TABLEAU);
        g.setRedealsAllowed(RedealsAllowed.NONE);

        return g;
    }

    public static GameProperties getCanfield() {
        GameProperties g = new GameProperties();
        g.setName("CanField");
        g.setTitle("Canfield");
        g.setNumberOfDecks(1);
        g.setWasteVisible(false);
        g.setTableauType(TableauType.KLONDIKE);
        g.setNumberOfTableauPiles(7);
        g.setNumberOfCardsPerTableau(7);
        g.setNumberOfFaceDownCards(0);
        g.setNumberOfFreeCells(0);
        g.setTableauBuildingMethod(TableauSequence.BUILD_DOWN_IN_ALTERNATE_COLORS);
        g.setEmptyTableauPileFilledBy(EmptyTableauPileFilledBy.KING);
        g.setMoveGroupsOfCardsAsAUnit(MoveGroupsOfCardsAsAUnit.YES_IF_IN_SEQUENCE);

        g.setFoundationBaseCard(FoundationBaseCard.ACE);
        g.setFoundationBuildingMethod(FoundationSequence.BUILD_UP_IN_SAME_SUIT);

        g.setNumberOfCardsDealtFromStock(NumberOfCardsDealtFromStock.THREE_TO_WASTE);
        g.setRedealsAllowed(RedealsAllowed.UNLIMITED);

        return g;
    }

    public static GameProperties getFortyThieves() {
        GameProperties g = new GameProperties();
        g.setName("FourtyThieves");
        g.setTitle("Forty Thieves");
        g.setNumberOfDecks(2);
        g.setWasteVisible(false);
        g.setTableauType(TableauType.FORTY_THIEVES);
        g.setNumberOfTableauPiles(10);
        g.setNumberOfCardsPerTableau(4);
        g.setNumberOfFaceDownCards(0);
        g.setNumberOfFreeCells(0);
        g.setTableauBuildingMethod(TableauSequence.BUILD_DOWN_IN_SAME_SUIT);
        g.setEmptyTableauPileFilledBy(EmptyTableauPileFilledBy.ANY_CARD_IN_SEQUENCE);
        g.setMoveGroupsOfCardsAsAUnit(MoveGroupsOfCardsAsAUnit.NO);

        g.setFoundationBaseCard(FoundationBaseCard.ACE);
        g.setFoundationBuildingMethod(FoundationSequence.BUILD_UP_IN_SAME_SUIT);

        g.setNumberOfCardsDealtFromStock(NumberOfCardsDealtFromStock.ONE_TO_WASTE);
        g.setRedealsAllowed(RedealsAllowed.NONE);

        return g;
    }

    public static GameProperties getJosephine() {
        GameProperties g = GameProperties.getFortyThieves();
        g.setName("Josephine");
        g.setTitle("Josephine");
        g.setMoveGroupsOfCardsAsAUnit(MoveGroupsOfCardsAsAUnit.YES_IF_IN_SEQUENCE);
        return g;
    }

    public static GameProperties getCadran() {
        GameProperties g = GameProperties.getFortyThieves();
        g.setName("Cadran");
        g.setTitle("Cadran");
        g.setWasteVisible(true);
        return g;
    }

    public static GameProperties getMarieRose() {
        GameProperties g = GameProperties.getJosephine();
        g.setName("MarieRose");
        g.setTitle("Marie Rose");
        g.setNumberOfDecks(3);
        g.setNumberOfTableauPiles(12);
        return g;
    }

}
