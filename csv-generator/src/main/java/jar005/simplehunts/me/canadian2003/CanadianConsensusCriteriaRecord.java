package jar005.simplehunts.me.canadian2003;

public class CanadianConsensusCriteriaRecord {
    private boolean criteriaMatch;

    private boolean criteria_1;

    private boolean[] criteria_2 = new boolean[4];

    private boolean criteria_3;

    private boolean criteria_4;

    private boolean[] criteria_5 = new boolean[6];

    private boolean[][] criteria_6 = new boolean[3][];

    private boolean criteria_7;

    public CanadianConsensusCriteriaRecord() {
        criteria_6[0] = new boolean[8];
        criteria_6[1] = new boolean[5];
        criteria_6[2] = new boolean[5];
    }

    public boolean isCriteria_1() {
        return criteria_1;
    }

    public void setCriteria_1(boolean criteria_1) {
        this.criteria_1 = criteria_1;
    }

    public boolean[] getCriteria_2() {
        return criteria_2;
    }

    public void setCriteria_2(boolean[] criteria_2) {
        this.criteria_2 = criteria_2;
    }

    public boolean isCriteria_3() {
        return criteria_3;
    }

    public void setCriteria_3(boolean criteria_3) {
        this.criteria_3 = criteria_3;
    }

    public boolean isCriteria_4() {
        return criteria_4;
    }

    public void setCriteria_4(boolean criteria_4) {
        this.criteria_4 = criteria_4;
    }

    public boolean[] getCriteria_5() {
        return criteria_5;
    }

    public void setCriteria_5(boolean[] criteria_5) {
        this.criteria_5 = criteria_5;
    }

    public void setCriteria_6(boolean[][] criteria_6) {
        this.criteria_6 = criteria_6;
    }

    public boolean[][] getCriteria_6() {
        return criteria_6;
    }

    public boolean isCriteria_7() {
        return criteria_7;
    }

    public void setCriteria_7(boolean criteria_7) {
        this.criteria_7 = criteria_7;
    }

    public boolean isCriteriaMatch() {
        return criteriaMatch;
    }

    public void setCriteriaMatch(boolean criteriaMatch) {
        this.criteriaMatch = criteriaMatch;
    }
}
