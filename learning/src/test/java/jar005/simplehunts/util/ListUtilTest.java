package jar005.simplehunts.util;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

public class ListUtilTest {
    @Test
    public void When_StringIsExcludedFromList_Expect_StringToBeMissingFromClone() {
        List<String> attributes = new ArrayList<>();
        attributes.add("name1");
        attributes.add("name2");
        attributes.add("name3");

        List<String> clone = ListUtil.cloneListExcludingAttribute("name2", attributes);

        Assert.assertEquals(attributes.size(), 3);
        Assert.assertEquals(clone.size(), 2);
        Assert.assertEquals(clone.get(0), "name1");
        Assert.assertEquals(clone.get(1), "name3");
    }
}
