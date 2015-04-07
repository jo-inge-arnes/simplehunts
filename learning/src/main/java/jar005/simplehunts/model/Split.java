package jar005.simplehunts.model;

public class Split {

    private String attributeName;
    private RecordList trueRecords = new RecordList();
    private RecordList falseRecords = new RecordList();

    public Split() {}

    public Split(String attributeName) {
        this();
        this.attributeName = attributeName;
    }

    public RecordList getTrueRecords() {
        return trueRecords;
    }

    public RecordList getFalseRecords() {
        return falseRecords;
    }

    public String getAttributeName() {
        return attributeName;
    }

    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }

    /**
     * Calculates the weighted impurity for the two lists in the split
     * @return The weighted impurity
     */
    public double weightedImpurity() {
        double trueRecordsImpurity = trueRecords.impurity();
        double falseRecordsImpurity = falseRecords.impurity();

        int numTrueRecords = trueRecords.size();
        int numFalseRecords = falseRecords.size();
        int totalRecords = numTrueRecords + numFalseRecords;

        return (trueRecordsImpurity * numTrueRecords + falseRecordsImpurity * numFalseRecords) / totalRecords;
    }

    /**
     * The mode is the most common value of all the records.
     * @return The mode of the split
     */
    public boolean mode() {
        return trueRecords.size() > falseRecords.size();
    }

    /**
     * The split is homogeneous if all values for the attribute are equal.
     * @return True if only one part of the split has records
     */
    public boolean isHomogeneous() {
        return trueRecords.size() == 0 || falseRecords.size() == 0;
    }
}