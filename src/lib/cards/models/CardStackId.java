package lib.cards.models;

public class CardStackId {
    public StackType stackType;
    public int index;
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + index;
        result = prime * result
                + ((stackType == null) ? 0 : stackType.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        CardStackId other = (CardStackId) obj;
        if (index != other.index)
            return false;
        if (stackType != other.stackType)
            return false;
        return true;
    }
}
