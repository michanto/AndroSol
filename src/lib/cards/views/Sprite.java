package lib.cards.views;

import lib.cards.utilities.Point;
import lib.cards.utilities.Rect;
import lib.cards.utilities.Size;

public interface Sprite {
    String getName();

    void setName(String value);

    boolean getHighlight();

    void setHighlight(boolean value);

    boolean getSoftHighlight();

    void setSoftHighlight(boolean value);

    Point getPosition();

    void setPosition(Point value);

    Size getSize();

    Rect getRect();

    void setSize(Size value);

    int getZOrder();

    void setZOrder(int value);

    boolean isVisibile();

    void setVisibile(boolean value);
}
