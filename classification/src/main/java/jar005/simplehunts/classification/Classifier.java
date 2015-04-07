package jar005.simplehunts.classification;

import jar005.simplehunts.model.BooleanAttribute;
import jar005.simplehunts.model.Node;
import jar005.simplehunts.model.Record;

import java.util.List;

public class Classifier {
    private Node root;

    public Classifier(Node root) {
        this.root = root;
    }

    /**
     * Classifies the record as true of false. Does not change the original record.
     * @param record The record to be classified
     * @return The result of the classification (true or false)
     */
    public boolean decide(Record record) {
        return traverse(root, record);
    }

    /**
     * Returns the accuracy [0.0, 1.0] of the target values predicted by the classifier
     * compared to the input records' actual target values.
     * @param records The records to be tested against the classifier's predictions
     * @return The accuracy of the predictions
     */
    public double accuracy(List<Record> records) {
        int numRecords = records.size();
        int numCorrectPredictions = 0;

        for(Record record : records)
            if(decide(record) == record.getTarget().isTrue())
                numCorrectPredictions++;

        return numCorrectPredictions / (double) numRecords;
    }

    private boolean traverse(Node node, Record record) {

        if(node.isLeaf())
            return node.getValue();

        BooleanAttribute currentAttribute = record.getAttribute(node.getAttributeName());
        if(currentAttribute.isTrue())
            node = node.getTrueBranchNode();
        else
            node = node.getFalseBranchNode();

        return traverse(node, record);
    }
}