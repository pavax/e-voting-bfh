package ch.bfh.ti.advancedweb.evoting.web.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public  class JstlUtils {

    /**
     * Converts a Map to a List filled with its entries. This is needed since
     * very few if any JSF iteration components are able to iterate over a map.
     */
    public static <T, S> List<Map.Entry<T, S>> mapToList(Map<T, S> map) {

        if (map == null) {
            return null;
        }

        List<Map.Entry<T, S>> list = new ArrayList<>();
        list.addAll(map.entrySet());

        return list;
    }

    /**
     * Converts a Set to a List. This is needed since
     * very some primefaces components can not iterate a Set
     */
    public static <T> List<T> setToList(Set<T> set) {
        return new ArrayList<>(set);
    }
}
