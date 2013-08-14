package lib.cards.models;

public class CardState {
    private int id;
    private boolean faceUp;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isFaceUp() {
        return faceUp;
    }

    public void setFaceUp(boolean faceUp) {
        this.faceUp = faceUp;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (faceUp ? 1231 : 1237);
        result = prime * result + id;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        CardState other = (CardState) obj;
        if (faceUp != other.faceUp) {
            return false;
        }
        if (id != other.id) {
            return false;
        }
        return true;
    }
}
