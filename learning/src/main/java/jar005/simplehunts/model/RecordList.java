package jar005.simplehunts.model;

import jar005.simplehunts.math.Impurity;

import java.util.ArrayList;
import java.util.List;

public class RecordList extends ArrayList<Record> {
    private static final TargetEval targetEval = new TargetEval();

    public Split targetSplit() {
        return split(targetEval);
    }

    public Split attributeSplit(String attributeName) {
        Split split = split(new AttributeEval(attributeName));
        split.setAttributeName(attributeName);
        return split;
    }

    public List<Boolean> attributeValues(String attributeName) {
        return extractValues(new AttributeEval(attributeName));
    }

    public List<Boolean> targetValues() {
        return extractValues(targetEval);
    }

    public double impurity() {
        return Impurity.misclassificationError(this);
    }

    private List<Boolean> extractValues(IAttributeEval eval) {
        List<Boolean> values = new ArrayList<>();

        for(Record record : this)
            values.add(eval.isTrue(record));

        return values;
    }

    private Split split(IAttributeEval eval) {
        Split split = new Split();

        for(Record record : this)
            if(eval.isTrue(record))
                split.getTrueRecords().add(record);
            else
                split.getFalseRecords().add(record);

        return split;
    }

    private static interface IAttributeEval {
        public boolean isTrue(Record record);
    }

    private static class AttributeEval implements IAttributeEval {
        private String attributeName;

        public AttributeEval(String attributeName) {
            this.attributeName = attributeName;
        }

        public boolean isTrue(Record record) {
            return record.getAttribute(attributeName).isTrue();
        }
    }

    private static class TargetEval implements IAttributeEval {
        public boolean isTrue(Record record) {
            return record.getTarget().isTrue();
        }
    }
}
