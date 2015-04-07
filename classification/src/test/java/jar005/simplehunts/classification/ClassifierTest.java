package jar005.simplehunts.classification;

import jar005.simplehunts.model.BooleanAttribute;
import jar005.simplehunts.model.Node;
import jar005.simplehunts.model.Record;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ClassifierTest {
    @Test
    public void When_WalksLikeDuckTalksLikeDuck_Expect_IsDuck() {
        Node root = duckDecisionTree();

        Classifier classifier = new Classifier(root);


        BooleanAttribute[] independentAttributes = {
                new BooleanAttribute("Walks like duck?", true),
                new BooleanAttribute("Talks like duck?", true)
        };
        Record record = new Record(Arrays.asList(independentAttributes), null);

        boolean isDuck = classifier.decide(record);

        Assert.assertTrue(isDuck);
    }

    @Test
    public void When_WalksLikeDuckDoesNotTalksLikeDuck_Expect_IsDuck() {
        Node root = duckDecisionTree();

        Classifier classifier = new Classifier(root);


        BooleanAttribute[] independentAttributes = {
                new BooleanAttribute("Walks like duck?", true),
                new BooleanAttribute("Talks like duck?", false)
        };
        Record record = new Record(Arrays.asList(independentAttributes), null);

        boolean isDuck = classifier.decide(record);

        Assert.assertFalse(isDuck);
    }

    @Test
    public void When_TestingAccuracyOfPerfectTree_Expect_ExactlyOne() {
        Node root = duckDecisionTree();
        List<Record> records = new ArrayList<>();

        List<BooleanAttribute> independentAttributes = new ArrayList<>();
        independentAttributes.add(new BooleanAttribute("Walks like duck?", true));
        independentAttributes.add(new BooleanAttribute("Talks like duck?", true));
        records.add(new Record(independentAttributes, new BooleanAttribute("Is Duck?", true)));

        independentAttributes = new ArrayList<>();
        independentAttributes.add(new BooleanAttribute("Walks like duck?", false));
        independentAttributes.add(new BooleanAttribute("Talks like duck?", false));
        records.add(new Record(independentAttributes, new BooleanAttribute("Is Duck?", false)));

        independentAttributes = new ArrayList<>();
        independentAttributes.add(new BooleanAttribute("Walks like duck?", false));
        independentAttributes.add(new BooleanAttribute("Talks like duck?", true));
        records.add(new Record(independentAttributes, new BooleanAttribute("Is Duck?", false)));

        independentAttributes = new ArrayList<>();
        independentAttributes.add(new BooleanAttribute("Walks like duck?", true));
        independentAttributes.add(new BooleanAttribute("Talks like duck?", false));
        records.add(new Record(independentAttributes, new BooleanAttribute("Is Duck?", false)));

        Classifier classifier = new Classifier(duckDecisionTree());
        double accuracy = classifier.accuracy(records);

        Assert.assertEquals(accuracy, 1.0d);
    }

    @Test
    public void When_TestingAccuracyOfCompletelyWrongTree_Expect_ExactlyZero() {
        Node root = duckDecisionTree();
        List<Record> records = new ArrayList<>();

        List<BooleanAttribute> independentAttributes = new ArrayList<>();
        independentAttributes.add(new BooleanAttribute("Walks like duck?", true));
        independentAttributes.add(new BooleanAttribute("Talks like duck?", true));
        records.add(new Record(independentAttributes, new BooleanAttribute("Is Duck?", false)));

        independentAttributes = new ArrayList<>();
        independentAttributes.add(new BooleanAttribute("Walks like duck?", false));
        independentAttributes.add(new BooleanAttribute("Talks like duck?", false));
        records.add(new Record(independentAttributes, new BooleanAttribute("Is Duck?", true)));

        independentAttributes = new ArrayList<>();
        independentAttributes.add(new BooleanAttribute("Walks like duck?", false));
        independentAttributes.add(new BooleanAttribute("Talks like duck?", true));
        records.add(new Record(independentAttributes, new BooleanAttribute("Is Duck?", true)));

        independentAttributes = new ArrayList<>();
        independentAttributes.add(new BooleanAttribute("Walks like duck?", true));
        independentAttributes.add(new BooleanAttribute("Talks like duck?", false));
        records.add(new Record(independentAttributes, new BooleanAttribute("Is Duck?", true)));

        Classifier classifier = new Classifier(duckDecisionTree());
        double accuracy = classifier.accuracy(records);

        Assert.assertEquals(accuracy, 0.0d);
    }

    @Test
    public void When_TestingAccuracyOfHalfCorrectTree_Expect_ExactlyPointFive() {
        Node root = duckDecisionTree();
        List<Record> records = new ArrayList<>();

        List<BooleanAttribute> independentAttributes = new ArrayList<>();
        independentAttributes.add(new BooleanAttribute("Walks like duck?", true));
        independentAttributes.add(new BooleanAttribute("Talks like duck?", true));
        records.add(new Record(independentAttributes, new BooleanAttribute("Is Duck?", true)));

        independentAttributes = new ArrayList<>();
        independentAttributes.add(new BooleanAttribute("Walks like duck?", false));
        independentAttributes.add(new BooleanAttribute("Talks like duck?", false));
        records.add(new Record(independentAttributes, new BooleanAttribute("Is Duck?", false)));

        independentAttributes = new ArrayList<>();
        independentAttributes.add(new BooleanAttribute("Walks like duck?", false));
        independentAttributes.add(new BooleanAttribute("Talks like duck?", true));
        records.add(new Record(independentAttributes, new BooleanAttribute("Is Duck?", true)));

        independentAttributes = new ArrayList<>();
        independentAttributes.add(new BooleanAttribute("Walks like duck?", true));
        independentAttributes.add(new BooleanAttribute("Talks like duck?", false));
        records.add(new Record(independentAttributes, new BooleanAttribute("Is Duck?", true)));

        Classifier classifier = new Classifier(duckDecisionTree());
        double accuracy = classifier.accuracy(records);

        Assert.assertEquals(accuracy, 0.5d);
    }

    private Node duckDecisionTree() {
        Node walkNode = new Node("Walks like duck?");
        Node talkNode = new Node("Talks like duck?");
        Node falseLeaf = new Node("Is Duck?");
        falseLeaf.makeLeaf(false);
        Node trueLeaf = new Node("Is Duck?");
        trueLeaf.makeLeaf(true);

        walkNode.setTrueBranch(talkNode);
        walkNode.setFalseBranch(falseLeaf);

        talkNode.setFalseBranch(falseLeaf);
        talkNode.setTrueBranch(trueLeaf);
        return walkNode;
    }
}