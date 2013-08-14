package lib.cards.utilities;

import java.util.ArrayList;
import java.util.List;

public class CollectionUtils {
    public static <T> List<T> intersect(List<T> first, List<T> second) {
        ArrayList<T> result = new ArrayList<T>();
        for (T obj : first) {
            if (second.contains(obj)) {
                result.add(obj);
            }
        }
        return result;
    }

    public static <T> List<T> toList(T only) {
        ArrayList<T> result = new ArrayList<T>();
        result.add(only);
        return result;
    }
}
