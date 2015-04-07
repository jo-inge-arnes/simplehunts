package jar005.simplehunts.algorithm;

import jar005.simplehunts.model.BooleanAttribute;
import jar005.simplehunts.model.Node;
import jar005.simplehunts.model.Record;
import jar005.simplehunts.model.RecordList;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

public class LearnerTest {

    @Test
    public void When_AllWorkNoPlay_Expect_DullBoy() {
        Learner learner = new Learner();

        RecordList records = allWorkNoPlayRecords();
        List<String> attributeNames = new ArrayList<>(records.get(0).getIndependentAttributeNames());

        Node root = learner.decisionTree(records, attributeNames);

//        Assert.assertEquals(root.getAttributeName(), "all_work");
//
//
//        Assert.assertEquals(root.getFalseBranchNode().getAttributeName(), "dull_boy");
//        Assert.assertTrue(root.getFalseBranchNode().isLeaf());
//        Assert.assertFalse(root.getFalseBranchNode().getValue());
//
//        Assert.assertEquals(root.getTrueBranchNode().getAttributeName(), "no_play");
//
//        Assert.assertEquals(root.getTrueBranchNode().getTrueBranchNode().getAttributeName(), "dull_boy");
//        Assert.assertTrue(root.getTrueBranchNode().getTrueBranchNode().isLeaf());
//        Assert.assertTrue(root.getTrueBranchNode().getTrueBranchNode().getValue());
//
//        Assert.assertEquals(root.getTrueBranchNode().getFalseBranchNode().getAttributeName(), "dull_boy");
//        Assert.assertTrue(root.getTrueBranchNode().getFalseBranchNode().isLeaf());
//        Assert.assertFalse(root.getTrueBranchNode().getFalseBranchNode().getValue());
    }

    @Test
    public void When_AllRecordsHaveIdenticalIdependents_Expect_TrueResult() {
        Learner learner = new Learner();

        boolean identical = learner.allIdenticalIndependents(recordsForAllIdenticalIndependentsTest());

        Assert.assertTrue(identical);
    }

    @Test
    public void When_NotAllRecordsHaveIdenticalIdependents_Expect_FalseResult() {
        Learner learner = new Learner();

        RecordList records = recordsForAllIdenticalIndependentsTest();
        records.get(1).getAttribute("first").setValue(true);

        boolean identical = learner.allIdenticalIndependents(records);

        Assert.assertFalse(identical);
    }

    @Test
    public void When_AskingForPurestSplitAttribute_Expect_TheCorrectAttributeName() {
        RecordList records = recordsForPurestSplitAttributeTest();
        List<String> attributeNames = new ArrayList<>(records.get(0).getIndependentAttributeNames());

        Learner learner = new Learner();
        String purestSplitAttributeName = learner.purestSplitAttribute(records, attributeNames);
        Assert.assertEquals(purestSplitAttributeName, "same_as_target");
    }

    private RecordList recordsForPurestSplitAttributeTest() {
        RecordList records = new RecordList();

        List<BooleanAttribute> independents = new ArrayList<>();
        independents.add(new BooleanAttribute("first", false));
        independents.add(new BooleanAttribute("same_as_target", true));
        independents.add(new BooleanAttribute("last", true));
        BooleanAttribute target = new BooleanAttribute("target", true);
        Record record = new Record(independents, target);
        records.add(record);

        independents = new ArrayList<>();
        independents.add(new BooleanAttribute("first", false));
        independents.add(new BooleanAttribute("same_as_target", false));
        independents.add(new BooleanAttribute("last", true));
        target = new BooleanAttribute("target", false);
        record = new Record(independents, target);
        records.add(record);

        independents = new ArrayList<>();
        independents.add(new BooleanAttribute("first", true));
        independents.add(new BooleanAttribute("same_as_target", false));
        independents.add(new BooleanAttribute("last", false));
        target = new BooleanAttribute("target", false);
        record = new Record(independents, target);
        records.add(record);

        independents = new ArrayList<>();
        independents.add(new BooleanAttribute("first", true));
        independents.add(new BooleanAttribute("same_as_target", true));
        independents.add(new BooleanAttribute("last", false));
        target = new BooleanAttribute("target", true);
        record = new Record(independents, target);
        records.add(record);

        return records;
    }

    private RecordList recordsForAllIdenticalIndependentsTest() {
        RecordList records = new RecordList();

        List<BooleanAttribute> independents = new ArrayList<>();
        independents.add(new BooleanAttribute("first", false));
        independents.add(new BooleanAttribute("same_as_target", true));
        independents.add(new BooleanAttribute("last", true));
        BooleanAttribute target = new BooleanAttribute("target", true);
        Record record = new Record(independents, target);
        records.add(record);

        independents = new ArrayList<>();
        independents.add(new BooleanAttribute("first", false));
        independents.add(new BooleanAttribute("same_as_target", true));
        independents.add(new BooleanAttribute("last", true));
        target = new BooleanAttribute("target", false);
        record = new Record(independents, target);
        records.add(record);

        independents = new ArrayList<>();
        independents.add(new BooleanAttribute("first", false));
        independents.add(new BooleanAttribute("same_as_target", true));
        independents.add(new BooleanAttribute("last", true));
        target = new BooleanAttribute("target", false);
        record = new Record(independents, target);
        records.add(record);

        return records;
    }

    private RecordList allWorkNoPlayRecords() {
        RecordList records = new RecordList();

        List<BooleanAttribute> independents = new ArrayList<>();
        independents.add(new BooleanAttribute("all_work", true));
        independents.add(new BooleanAttribute("no_play", true));
        BooleanAttribute target = new BooleanAttribute("dull_boy", true);
        Record record = new Record(independents, target);
        records.add(record);

        independents = new ArrayList<>();
        independents.add(new BooleanAttribute("all_work", false));
        independents.add(new BooleanAttribute("no_play", false));
        target = new BooleanAttribute("dull_boy", false);
        record = new Record(independents, target);
        records.add(record);

        independents = new ArrayList<>();
        independents.add(new BooleanAttribute("all_work", true));
        independents.add(new BooleanAttribute("no_play", false));
        target = new BooleanAttribute("dull_boy", false);
        record = new Record(independents, target);
        records.add(record);

        independents = new ArrayList<>();
        independents.add(new BooleanAttribute("all_work", false));
        independents.add(new BooleanAttribute("no_play", true));
        target = new BooleanAttribute("dull_boy", false);
        record = new Record(independents, target);
        records.add(record);

        return records;
    }
}
