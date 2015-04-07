package jar005.simplehunts.model;

import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RecordListTest {
    private static final String ATTRIBUTE_PREFIX = "attribute-";
    private static final String SECOND_ATTRIBUTE_NAME = ATTRIBUTE_PREFIX + 2;

    private RecordList records;
    private List<Boolean> secondAttributeValues;

    @BeforeTest
    public void setup() {
        records = new RecordList();
        Boolean[] trueSecondAttributeValue = { false, true };
        Boolean[] falseSecondAttributeValue = { true, false };
        records.add(createRecord(trueSecondAttributeValue, true));
        records.add(createRecord(trueSecondAttributeValue, false));
        records.add(createRecord(falseSecondAttributeValue, false));
        records.add(createRecord(falseSecondAttributeValue, true));
        records.add(createRecord(trueSecondAttributeValue, true));
        records.add(createRecord(falseSecondAttributeValue, false));
        records.add(createRecord(falseSecondAttributeValue, true));

        Boolean[] secondAttributeValuesArray = { true, true, false, false, true, false, false };
        this.secondAttributeValues = Arrays.asList(secondAttributeValuesArray);
    }

    @Test
    public void When_ListIsSplitOnTargetValues_Expect_AllRecordsToBeInCorrectList() {
        Split split = records.targetSplit();

        Assert.assertEquals(split.getFalseRecords().size(), 3);
        Assert.assertEquals(split.getTrueRecords().size(), 4);

        for(Record record : split.getFalseRecords())
            Assert.assertFalse(record.getTarget().isTrue());

        for(Record record : split.getTrueRecords())
            Assert.assertTrue(record.getTarget().isTrue());

    }

    @Test
    public void When_ListIsSplitOnAnAttributeValue_Expect_AllRecordsToBeInCorrectList() {
        Split split = records.attributeSplit(SECOND_ATTRIBUTE_NAME);

        Assert.assertEquals(split.getFalseRecords().size(), 4);
        Assert.assertEquals(split.getTrueRecords().size(), 3);

        for(Record record : split.getFalseRecords())
            Assert.assertFalse(record.getAttribute(SECOND_ATTRIBUTE_NAME).isTrue());

        for(Record record : split.getTrueRecords())
            Assert.assertTrue(record.getAttribute(SECOND_ATTRIBUTE_NAME).isTrue());
    }

    @Test
    public void When_AttributesAreExtractedByName_Expect_AllValuesForThatAttributesToBeReturned() {
        List<Boolean> values = records.attributeValues(SECOND_ATTRIBUTE_NAME);

        Assert.assertEquals(values.size(), 7);
        for(int i = 0; i < values.size(); i++)
            Assert.assertEquals(values.get(i), secondAttributeValues.get(i));
    }

    @Test
    public void When_TestSetImpurityIsCalculated_Expect_ThreeBySeven() {
        double impurity = records.impurity();
        Assert.assertEquals(impurity, 3/(double)7, 0.0000001);
    }

    private Record createRecord(Boolean[] attributeValues, boolean targetValue) {
        BooleanAttribute target = new BooleanAttribute("target", targetValue);

        List<BooleanAttribute> attributes = new ArrayList<>();
        for(int i = 0; i < attributeValues.length; i++) {
            String attributeName =
                    i == 1 ? SECOND_ATTRIBUTE_NAME : String.format("%s%d", ATTRIBUTE_PREFIX, i + 1);
            BooleanAttribute attribute = new BooleanAttribute(
                    attributeName,
                    attributeValues[i]);
            attributes.add(attribute);
        }

        return new Record(attributes, target);
    }
}
