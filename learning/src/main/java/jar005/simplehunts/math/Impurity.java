package jar005.simplehunts.math;

import jar005.simplehunts.model.Record;
import jar005.simplehunts.model.RecordList;

import java.util.List;

public class Impurity {
    public static double misclassificationError(List<Boolean> values) {
        return misclassificationError(new BooleanListFractionCounter(values));
    }

    public static double misclassificationError(RecordList records) {
        return misclassificationError(new RecordListFractionCounter(records));
    }

    private static double misclassificationError(IFractionCounter fractionCounter) {
        double totalCount = (double)fractionCounter.totalCount();

        if(totalCount > 0)
            return 1.0d - (fractionCounter.fractionCount() / totalCount);
        else
            return 0.0d;
    }

    private interface IFractionCounter {
        int fractionCount();
        int totalCount();
    }

    private static abstract class FractionCounterBase implements IFractionCounter {
        int fractionCount = -1;

        protected abstract int trueValueCount();

        public int fractionCount() {
            if(fractionCount == -1) {
                fractionCount = trueValueCount();
                int totalCount = totalCount();

                if ((2 * fractionCount) < totalCount)
                    fractionCount = totalCount - fractionCount;
            }

            return fractionCount;
        }
    }

    private static class RecordListFractionCounter extends FractionCounterBase {
        private RecordList records;

        public RecordListFractionCounter(RecordList records) {
            this.records = records;
        }

        @Override
        protected int trueValueCount() {
            int count = 0;
            for(Record record : records)
                if(record.getTarget().isTrue())
                    count++;
            return count;
        }

        @Override
        public int totalCount() {
            return records.size();
        }
    }

    private static class BooleanListFractionCounter extends FractionCounterBase {
        private List<Boolean> valueList;

        public BooleanListFractionCounter(List<Boolean> valueList) {
            this.valueList = valueList;
        }

        @Override
        protected int trueValueCount() {
            int count = 0;
            for(boolean value : valueList)
                if(value)
                    count++;
            return count;
        }

        @Override
        public int totalCount() {
            return valueList.size();
        }
    }
}
