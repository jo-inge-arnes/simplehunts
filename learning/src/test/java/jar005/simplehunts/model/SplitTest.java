package jar005.simplehunts.model;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

public class SplitTest {
    @Test
    public void Wnen_FiftyFiftySplit_Expect_ZeroWeightedImpurity() {
        Split split = new Split();

        List<BooleanAttribute> attributes = new ArrayList<>();

        Record record = new Record(attributes, new BooleanAttribute("", true));
        split.getTrueRecords().add(record);
        record = new Record(attributes, new BooleanAttribute("", true));
        split.getTrueRecords().add(record);

        record = new Record(attributes, new BooleanAttribute("", false));
        split.getFalseRecords().add(record);
        record = new Record(attributes, new BooleanAttribute("", false));
        split.getFalseRecords().add(record);

        double weightedImpurity = split.weightedImpurity();


        Assert.assertEquals(weightedImpurity, 0.0);
    }

    @Test
    public void Wnen_TwoDifferentTargetValuesInEachSplit_Expect_PointFiveWeightedImpurity() {
        Split split = new Split();

        List<BooleanAttribute> attributes = new ArrayList<>();

        Record record = new Record(attributes, new BooleanAttribute("", true));
        split.getTrueRecords().add(record);
        record = new Record(attributes, new BooleanAttribute("", false));
        split.getTrueRecords().add(record);

        record = new Record(attributes, new BooleanAttribute("", true));
        split.getFalseRecords().add(record);
        record = new Record(attributes, new BooleanAttribute("", false));
        split.getFalseRecords().add(record);

        double weightedImpurity = split.weightedImpurity();


        Assert.assertEquals(weightedImpurity, 0.5d);
    }
}