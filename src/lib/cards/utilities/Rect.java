package lib.cards.utilities;

public class Rect {
    // Keep these private so rectangle will always be normalized.
    private double top;
    private double left;
    private double bottom;
    private double right;

    public double getTop() {
        return top;
    }

    public void setTop(double top) {
        this.top = top;
        normalize();
    }

    public double getLeft() {
        return left;
    }

    public void setLeft(double left) {
        this.left = left;
        normalize();
    }

    public double getBottom() {
        return bottom;
    }

    public void setBottom(double bottom) {
        this.bottom = bottom;
        normalize();
    }

    public double getRight() {
        return right;
    }

    public void setRight(double right) {
        this.right = right;
        normalize();
    }

    public double getWidth() {
        return right - left;
    }

    public void setWidth(double width) {
        right = left + width;
        normalize();
    }

    public double getHeight() {
        return bottom - top;
    }

    public void setHeight(double height) {
        bottom = top + height;
        normalize();
    }

    void normalize() {
        // TODO: Normalize.
    }

    public Rect() {

    }

    public Rect(Point point, Size size) {
        this(point.x, point.y, size.width, size.height);
    }

    public Rect(double x, double y, double width, double height) {
        // Normalize
        if (width < 0) {
            x += width;
            width *= -1;
        }
        if (height < 0) {
            y += height;
            height *= -1;
        }

        top = y;
        left = x;
        setHeight(height);
        setWidth(width);
    }

    /**
     * Returns true if the rectangle contains the point.
     * 
     * @param point
     *            Point to test.
     * @return True if the rectangle contains the point.
     */
    public boolean Contains(Point point) {
        return point.x > left && point.x < right && point.y > top
                && point.y < bottom;
    }
}
