package jar005.simplehunts.algorithm;

import jar005.simplehunts.model.*;
import jar005.simplehunts.util.ListUtil;

import java.util.List;

public class Learner {

    public Node decisionTree(RecordList records, List<String> attributes) {
        validateInputParameters(records, attributes);
        String rootAttribute = purestSplitAttribute(records, attributes);

        return grow(rootAttribute, records, attributes);
    }

    Node grow(String currentAttribute, RecordList records, List<String> attributes) {
        Node node = new Node(currentAttribute);

        if(records.impurity() == 0.0) {
            node.setAttributeName(targetAttributeName(records));
            boolean targetValue = records.get(0).getTarget().isTrue();
            node.makeLeaf(targetValue);
            return node;
        }

        if(allIdenticalIndependents(records)) {
            node.setAttributeName(targetAttributeName(records));
            boolean mostCommonTarget = records.targetSplit().mode();
            node.makeLeaf(mostCommonTarget);
            return node;
        }

        Split attributeSplit = records.attributeSplit(currentAttribute);
        List<String> remainingAttributes = ListUtil.cloneListExcludingAttribute(currentAttribute, attributes);

        node.setTrueBranch(branch(records, attributeSplit.getTrueRecords(), remainingAttributes));
        node.setFalseBranch(branch(records, attributeSplit.getFalseRecords(), remainingAttributes));

        return node;
    }

    Node branch(RecordList records, RecordList branchRecords, List<String> remainingAttributes) {
        Node branch;

        if(branchRecords.size() == 0 || remainingAttributes.size() == 0) {
            branch = new Node(targetAttributeName(records));

            boolean mostCommonTarget;
            if(branchRecords.size() == 0)
                mostCommonTarget = records.targetSplit().mode();
            else
                mostCommonTarget = branchRecords.targetSplit().mode();

            branch.makeLeaf(mostCommonTarget);
        } else {
            String branchAttribute = purestSplitAttribute(branchRecords, remainingAttributes);
            branch = grow(branchAttribute, branchRecords, remainingAttributes);
        }

        return branch;
    }

    String purestSplitAttribute(RecordList records, List<String> attributes) {
        String purestSplitAttribute = null;
        double lowestImpurity = Double.MAX_VALUE;

        for(String attribute : attributes) {
            Split attributeSplit = records.attributeSplit(attribute);

            double impurity = attributeSplit.weightedImpurity();

            if(impurity < lowestImpurity) {
                purestSplitAttribute = attribute;
                lowestImpurity = impurity;
            }
        }

        return purestSplitAttribute;
    }

    boolean allIdenticalIndependents(RecordList records) {
        boolean allIdentical = true;

        Record firstRecord = records.get(0);

        RECORDS_LOOP:
        for(Record record : records) {
            for(BooleanAttribute attribute : record.getIndependentAttributes()) {
                allIdentical = attribute.isTrue() == firstRecord.getAttribute(attribute.getName()).isTrue();

                if(!allIdentical)
                    break RECORDS_LOOP;
            }
        }

        return allIdentical;
    }

    String targetAttributeName(RecordList records) {
        return records.get(0).getTarget().getName();
    }

    void validateInputParameters(RecordList records, List<String> attributes) {
        if(records == null || records.size() == 0 || attributes == null || attributes.size() == 0)
            throw new IllegalArgumentException("Neither records nor attributes can be empty.");
    }
}