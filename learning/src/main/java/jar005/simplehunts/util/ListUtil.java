package jar005.simplehunts.util;

import java.util.ArrayList;
import java.util.List;

public class ListUtil {
    public static List<String> cloneListExcludingAttribute(String attribute, List<String> attributes) {
        List<String> clonedList = new ArrayList<>(attributes);
        clonedList.remove(attribute);
        return clonedList;
    }
}
